package com.sxp.suggestion;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/3.
 */
public class ApacheUtil {

    public static void main(String[] args)  {

        User user=new User(1,"sxp",new BigDecimal(1111111111111111111.1));
        User user1=new User();

       /* Map<String,String> map=new HashMap<String, String>();
        map.put("name","sxp11");
        map.put("id","2");*/
        try {
           // BeanUtils.copyProperties(user,map);
            BeanUtils.copyProperties(user1,user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BasicDynaBean dynaBean=new BasicDynaBean();
        //BasicDynaClass basicDynaClass=new BasicDynaClass();

        Arrays.asList("hello","hello2");

        DynaBean bean=new LazyDynaBean();
        bean.set("name","sxp");
        bean.set("age",5);
        System.out.println(bean);

        ObjectMapper mapper=new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(bean);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(user);
        System.out.println(user1);

    }


}



