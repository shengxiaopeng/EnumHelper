package com.sxp.script;

import com.alibaba.fastjson.JSON;
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
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/11/26.
 */
public class JSqlParserTest {

    public static void main(String[] args) throws JSQLParserException {

        testWhere();


    }


    static void testWhere() throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        String sql = "select normal,real as alias,real+2 as add1,operate*4 as  operate from transfer where normal is not null and real >3 and add1 not in (1,2,3) and hello!=3 ";
        net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(sql));
        Select selectStatement = (Select) statement;
        PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
        //提取where条件
        Expression where = selectBody.getWhere();
        extractExpression(where);
        //System.out.println(where.getClass().getName());
         /* AndExpression andExpression = (AndExpression) where;
          System.out.println(andExpression);
          String stringExpression = andExpression.getStringExpression();
          Expression leftExpression = andExpression.getLeftExpression();
          Expression rightExpression = andExpression.getRightExpression();

          InExpression inExpression = (InExpression) rightExpression;
            *//*System.out.println(inExpression.getLeftExpression());
            System.out.println(inExpression.);*//*
          System.out.println(inExpression.toString().toLowerCase());*/
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
            System.out.println(item.getClass().getName());

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
                System.out.println(express);
            } else if (item instanceof EqualsTo) {
                String s = item.toString();
                if(!((EqualsTo) item).isNot())
                   s= s.replace("=","==");

                listExpStr.add(s);

            } else {
                listExpStr.add(item.toString());
            }
            String join = StringUtils.join(listExpStr, " && ");
            System.out.println(join);

        }

        return null;
    }


    static void testSelect() throws JSQLParserException {
        Map<String, String> resultMap = new HashMap<String, String>();

        CCJSqlParserManager pm = new CCJSqlParserManager();
        String sql = "select normal,real as alias,real+2 as add1,operate*4 as  operate from transfer where normal is not null and real >3 and add1 in(1,2,3)";
        net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(sql));
/*
now you should use a class that implements StatementVisitor to decide what to do
based on the kind of the statement, that is SELECT or INSERT etc. but here we are only
interested in SELECTS
*/
        if (statement instanceof Select) {
            Select selectStatement = (Select) statement;
            /*TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List tableList = tablesNamesFinder.getTableList(selectStatement);
            for (Iterator iter = tableList.iterator(); iter.hasNext();) {
                System.out.println(tableName);
            }*/
            PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();

            List<SelectItem> selectItems = selectBody.getSelectItems();
            for (SelectItem item : selectItems) {
                //item.accept(new SelectDeParser());
                //System.out.println(item.getClass().getName());
                // System.out.println(item.toString());
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) item;
                Expression expression = selectExpressionItem.getExpression();
                Alias alias = selectExpressionItem.getAlias();
                String aliasName = alias == null ? null : alias.getName();

                resultMap.put(aliasName, expression.toString());

                /*item.accept(new SelectItemVisitor() {
                    public void visit(AllColumns allColumns) {
                    }

                    public void visit(AllTableColumns allTableColumns) {
                    }

                    public void visit(SelectExpressionItem selectExpressionItem) {
                        Expression expression = selectExpressionItem.getExpression();
                        System.out.println(expression);
                        //System.out.println(expression.getClass().getName());
                        Alias alias = selectExpressionItem.getAlias();
                        System.out.println(alias==null?"null":alias.getName());

                    }
                });*/

            }

            System.out.println(JSON.toJSONString(resultMap));
        }
    }


}
