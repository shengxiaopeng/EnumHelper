package com.sxp.script;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/11/25.
 */
public class GroovyTest {

    public static void main(String[] args) throws Exception {

        // TODO Auto-generated method stub

        groovyShell();


       // groovyFile();


    }

    private static void groovyFile() throws Exception {
        ClassLoader parent = ClassLoader.getSystemClassLoader();

        GroovyClassLoader loader = new GroovyClassLoader(parent);

        Class groovyClass = loader.parseClass(new File("C:\\normandy_workspace\\groovy\\src\\groovy\\GroovyDemo.groovy"));

        GroovyObject groovyObject = (GroovyObject)groovyClass.newInstance();

        Object[] param = {123,321};

        int res = (Integer) groovyObject.invokeMethod("add", param);

        System.out.println("res="+res);
    }

    private static void groovyShell() {
        Map<String,String> varMap=new HashMap<String, String>(8);
        varMap.put("timestamp","timestamp/1000");
        varMap.put("var","var*2");


        Binding binding = new Binding();
        binding.setVariable("var", 5);
        binding.setVariable("timestamp",1501234567000L);

        GroovyShell gs = new GroovyShell(binding);
        Map<String,String> valMap=new HashMap<String, String>();
        for(Map.Entry<String,String> entry:varMap.entrySet()){
            Object evaluate = gs.evaluate("return " + entry.getValue());
            System.out.println(evaluate);

            varMap.put(entry.getKey(),evaluate.toString());
        }

        //Object value = gs.evaluate("return var*10");//执行groovyshell脚本

       // System.out.println(value.equals(50));
        //System.out.println(binding.getVariable("abc").equals(123));
    }


}
