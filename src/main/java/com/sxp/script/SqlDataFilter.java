package com.sxp.script;

import com.alibaba.fastjson.JSON;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.*;

/**
 * 通过sql语句过滤行
 *   1 使用 Jsqlparser 对sql where语句进行解析，得到 groovy 行过滤条件，后续对遍历的每行进行验证
 *   2 使用 Jsqlparser 对sql select语句进行解析，得到 {'alais':'columnName'}的Map，后续利用groovy脚本，对其进行取值，得到新行
 *   3 遍历数据集每行，进行行过滤，及列key与value的选取及计算
 * @author sxp
 * @create 2017/11/26.
 */
public class SqlDataFilter {
    public static void main(String[] args) {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("real", 3);
        row.put("normal", "normal");
        row.put("ignore", "ignore");
        row.put("operate", 6);

        Map<String, Object> row2 = new HashMap<String, Object>();
        row2.put("real", 3);
        row2.put("normal", null);
        row2.put("ignore", "ignore");
        row2.put("operate", 5);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(row);
        list.add(row2);

        String selectSql = "select normal,real as alias,real+2 as add1,operate*4 as  operate from transfer where normal is not null and real >=3 and operate not in (1,2,3,5)  ";
        SqlDataFilter sqlDataFilter = new SqlDataFilter(selectSql);
        List<Map<String, Object>> filter = sqlDataFilter.filter(list);
        System.out.println(JSON.toJSONString(filter));

    }

    private String selectSql =null;// "select normal,real as alias,real+2 as add1,operate*4 as  operate from transfer where normal is not null and real >=3 and operate not in (1,2,3,5)  ";
    private String whereGrooyExpression;
    private Map<String, String> columnMap;

    public SqlDataFilter(String selectSql) {
        this.selectSql=selectSql;

        //1  得到行过滤  groovy sentence
        try {
            initWhere();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }

        //
        try {
            columnMap = initSelect();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }


    }

    private void initWhere() throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(selectSql));
        Select selectStatement = (Select) statement;
        PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
        //提取where条件
        Expression where = selectBody.getWhere();
        String whereGrooyExpression = extractExpression(where);

        this.whereGrooyExpression = whereGrooyExpression;
    }

    static String extractExpression(Expression expression) {

        List<Expression> expressionList = new ArrayList<Expression>();
        while (expression instanceof AndExpression) {
            Expression leftExpression = ((AndExpression) expression).getLeftExpression();
            expressionList.add(((AndExpression) expression).getRightExpression());

            expression = leftExpression;
        }
        expressionList.add(expression);
        Collections.reverse(expressionList);

        List<String> listExpStr = new ArrayList<String>();
        for (Expression item : expressionList) {
            //System.out.println(item.getClass().getName());

            if (item instanceof InExpression) {
                //item.toString()
                String exp = item.toString().replace("NOT", "").replace("IN", "in").replace("(", "[").replace(")", "]");
                boolean not = ((InExpression) item).isNot();

                if (not)
                    exp = "!(" + exp + ")";
                listExpStr.add(exp);
                // System.out.println(exp);
            } else if (item instanceof IsNullExpression) {
                Expression leftExpression = ((IsNullExpression) item).getLeftExpression();
                boolean not = ((IsNullExpression) item).isNot();

                String express = leftExpression.toString();
                if (not)
                    express += " != null";
                else
                    express += " == null";
                listExpStr.add(express);
                //System.out.println(express);
            } else if (item instanceof EqualsTo) {
                String s = item.toString();
                if (!((EqualsTo) item).isNot())
                    s = s.replace("=", "==");

                listExpStr.add(s);

            } else {
                listExpStr.add(item.toString());
            }

        }
        String join = StringUtils.join(listExpStr, " && ");
        //System.out.println(join);
        return join;
    }

    Map<String, String> initSelect() throws JSQLParserException {
        Map<String, String> resultMap = new HashMap<String, String>();

        CCJSqlParserManager pm = new CCJSqlParserManager();
        net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(selectSql));

        if (statement instanceof Select) {
            Select selectStatement = (Select) statement;
            PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();

            List<SelectItem> selectItems = selectBody.getSelectItems();
            for (SelectItem item : selectItems) {

                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) item;
                Expression expression = selectExpressionItem.getExpression();
                Alias alias = selectExpressionItem.getAlias();
                String aliasName = alias == null ? null : alias.getName();

                resultMap.put(aliasName, expression.toString());

            }
            return resultMap;
        }
        return null;
    }


    /**
     * 对 List<Map> 进行过滤
     *
     * @param dataList
     * @return
     */
    private List<Map<String, Object>> filter(List<Map<String, Object>> dataList) {
        //ignore field /alias field/add field/change field value
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> oldRow : dataList) {
            //validate row
            setBinding(oldRow);
            if (!validateRow()) {
                System.out.println("the row has not pass the where condition");
                continue;
            }

            //
            Map<String, Object> newRow = new HashMap<String, Object>();
            //依次获取列
            for (Map.Entry<String, String> column : columnMap.entrySet()) {
                String key = column.getKey() != null ? column.getKey() : column.getValue();

                Object value = null;
                if (oldRow.get(column.getValue()) != null) {
                    value =  oldRow.get(column.getValue());
                } else {
                    value = getColumnValue(column.getValue());
                }

                newRow.put(key,value);

            }

            resultList.add(newRow);
        }

        return resultList;
    }

    Binding binding = new Binding();

    private void setBinding(Map<String, Object> bindingMap) {
        for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }
    }

    private Boolean validateRow() {

        GroovyShell gs = new GroovyShell(binding);
        Object evaluate = gs.evaluate("return " + whereGrooyExpression);
        return Boolean.valueOf(evaluate.toString());
    }

    private Object getColumnValue(String expression) {

        GroovyShell gs = new GroovyShell(binding);
        Object evaluate = gs.evaluate("return " + expression);
        return evaluate;
    }

}

