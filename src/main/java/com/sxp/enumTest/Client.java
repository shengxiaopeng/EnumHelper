package com.sxp.enumTest;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/6.
 */
public class Client {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

               /* Credit[] credits = (Credit[]) all;
        for(Credit c:credits){
            System.out.println(c.getName());
        }*/

      /*  Helper credit = Helper.valueOf("CREDIT");
        Object[] all = credit.getAll();

        Class<?> aClass = all[0].getClass();
        Field field = aClass.getDeclaredField("name");

        for(Object o:all){
            field.setAccessible(true);
            Object o1 = field.get(o);
            System.out.println(o1.toString());
        }*/

        try {
            //get name by specific field
            String nameByField = Helper.getNameByField("CREDIT", "type", 11);
            System.out.println("name field:"+nameByField);

            List<String> allNames = Helper.getAllNames("FROZEN", "name");
            for(String name:allNames){
                System.out.println(name);
            }

            //get map list
            List<Map<String, Object>> frozens = Helper.getAllEntity("FROZEN");
            for(Map<String,Object> item :frozens){
                for(Map.Entry entry: item.entrySet()){

                    System.out.print(entry.getKey()+"--"+entry.getValue()+"**");
                }
                System.out.println();
            }

            //get entity list
            System.out.println("the result of entity list");
            List<Credit> allEntity = Helper.getAllEntity(Credit.class);
            for(Credit credit:allEntity){
                System.out.println(credit);
            }

            //此处不能用 Class<?>   不论T是什么类型 Class<?> 都是 Class<T>的父类
           /* Class aClass = Class.forName("com.sxp.enumTest.Frozen");
            List allEntity1 = Helper.getAllEntity(aClass);*/
            List froz = Helper.getAllBean("Frozen");

            for(Object o:froz){
                System.out.println(o);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
