package com.sxp.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/2.
 */
public class CollectionTest {


    void  callTest(){

        test(new Integer[]{1,2});
        test(1,2,3);

        List<String> list=new ArrayList<String>();

        list.toArray(new String[0]);

       // AbstractList
    }

    void test(Integer...integers){


    }

}

class  Parent{

    Integer hello(){
        return null;

        //Thread.UncaughtExceptionHandler
    }
}

class  Child extends  Parent{

    Integer hello(){
        return null;
    }

}