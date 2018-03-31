package org.smart4j.chapter1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/16.
 */
public class Test {

    public static void main(String[] args) {
        ThreadLocal<Integer> container=new ThreadLocal<Integer>();

        test();

        Subject subject = SecurityUtils.getSubject();
        //subject.login();
        //com.sun.jersey.spi.container;
        //   .servlet.ServletContainer;

        throw new RuntimeException("hell");
    }


    public static void  test(Integer...integers){

    }




}
