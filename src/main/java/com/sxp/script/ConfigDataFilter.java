package com.sxp.script;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/11/25.
 */
public class ConfigDataFilter {

    public static void main(String[] args) {
        //String alaisConfig="{'real':'alias'}";

        Map<String, Object> row = new HashMap<String, Object>();
        row.put("real", 3);
        row.put("normal", "normal");
        row.put("ignore", "ignore");
        row.put("operate",6);

        Map<String, Object> row2 = new HashMap<String, Object>();
        row2.put("real", 3);
        row2.put("normal", null);
        row2.put("ignore", "ignore");
        row2.put("operate", 5);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(row);
        list.add(row2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            List<Map<String, Object>> filter = new ConfigDataFilter().filter(list);
            System.out.println(JSON.toJSONString(filter));
        }
        long period = System.currentTimeMillis() - start;
        System.out.println("execute 1,000 times takes:" + period + " ms");

        // Integer.valueOf()
    }

    static String[] ignores = new String[]{"ignore"};
    static List<String> ignoreList = Arrays.asList(ignores);


    static String alaisConfig = "{'real':'alias'}";
    JSONObject alaisMap = JSON.parseObject(alaisConfig);

    static String addConfig = "{'add':'real+2'}";
    JSONObject addMap = JSON.parseObject(addConfig);

    static String operateConfig = "{'operate':'(Integer.valueOf(real+operate))*100'}";
    static JSONObject operateMap = JSON.parseObject(operateConfig);

    static String filterRowGroovyStr="normal != null && real >= 3 && !(operate  in [1, 2, 3,5])";

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

            setBinding(oldRow);
            //对每行进行操作
            //过滤行
            if(!validateRow()){
                System.out.println("row validate is not pass"+JSON.toJSONString(oldRow));
                continue;
            }

            Map<String, Object> newRow = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : oldRow.entrySet()) {
                //ignore
                if (ignoreList.contains(entry.getKey()))
                    continue;

                //alais
                if (alaisMap.containsKey(entry.getKey()))
                    newRow.put((String) alaisMap.get(entry.getKey()), entry.getValue());
                    //operate
                else if (operateMap.containsKey(entry.getKey()))
                    newRow.put(entry.getKey(), getOperateValue(entry.getKey(), operateMap));

                else
                    newRow.put(entry.getKey(), entry.getValue());

            }

            //add
            for (Map.Entry<String, Object> addItem : addMap.entrySet()) {
                newRow.put(addItem.getKey(), getOperateValue(addItem.getKey(), addMap));
            }

            if (newRow.size() > 0)
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

    private String getOperateValue(String key, Map opMap) {

        GroovyShell gs = new GroovyShell(binding);
        Object evaluate = gs.evaluate("return " + opMap.get(key));
        return evaluate.toString();
    }

    private Boolean validateRow() {

        GroovyShell gs = new GroovyShell(binding);
        Object evaluate = gs.evaluate("return " + filterRowGroovyStr);
        return Boolean.valueOf(evaluate.toString());
    }


}
