package com.sxp.enumTest;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/6.
 */
public enum  Helper {

    CREDIT(Credit.class),
    FROZEN(Frozen.class);

    private Helper(Class<?> clazz){
        this.clazz=clazz;
    }

    private Class<?>  clazz;

    public Object[] getAll(){

        //Credit[] enumConstants = Credit.class.getEnumConstants();
        Object[] arr= clazz.getEnumConstants();

        return arr;

    }

    public  static  String getNameByField(String status,String fieldName,Object fieldValue) throws NoSuchFieldException, IllegalAccessException {

        Helper credit = Helper.valueOf(status);
        Object[] all = credit.getAll();

        Class<?> aClass = all[0].getClass();
        Field field = aClass.getDeclaredField(fieldName);
        Field nameField= aClass.getDeclaredField("name");
        field.setAccessible(true);
        nameField.setAccessible(true);

        for(Object o:all){

            Object o1 = field.get(o);
            //Integer i = (Integer) o1;

            if(o1.equals(fieldValue))
                return nameField.get(o).toString();

        }




        return null;
    }

    public static List<String> getAllNames(String status,String fieldName) throws Exception {

        Helper credit = Helper.valueOf(status);
        Object[] all = credit.getAll();

        Class<?> aClass = all[0].getClass();
        Field field = aClass.getDeclaredField(fieldName);

        List<String> list=new ArrayList<String>();
        for(Object o:all){
            field.setAccessible(true);
            Object o1 = field.get(o);

            //System.out.println(o1.toString());
            list.add(o1.toString());
        }

        return  list;
    }

    public static <T extends Enum<T>> List<T> getAllEntity(Class<T> tClass) throws Exception {

        Helper helper = Helper.valueOf(tClass.getSimpleName().toUpperCase());
        Object[] all = helper.clazz.getEnumConstants();

        List<T> list=new ArrayList<T>();

        for(Object o: all){
            T t=(T)o;
            list.add(t);
        }

        return  list;
    }

    public  static List getAllBean(String className)  {

        List list=null;
        try {
            Class aClass = Class.forName("com.sxp.enumTest."+className);
             list = Helper.getAllEntity(aClass);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<Map<String,Object>> getAllEntity(String status) throws Exception {

        Helper helper = Helper.valueOf(status);
        Object[] all = helper.clazz.getEnumConstants();

        Class<?> aClass = all[0].getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields,true);

        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

        for(Object o:all){

            Map<String,Object> map=new HashMap<String,Object>();

            for(Field field:declaredFields){
                //if(Modifier.isFinal())
                int modifiers = field.getModifiers();
                if(Modifier.isFinal(modifiers))
                    continue;

                Object o1 = field.get(o);
                map.put(field.getName(),o1);
            }

            list.add(map);
        }

        return  list;
    }




}
