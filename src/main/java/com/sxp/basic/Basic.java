package com.sxp.basic;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/30.
 */
public class Basic {

    final  int a;

    public Basic(){
        a=5;
    }

    @Test
   public void test(){

        Basic b=new Basic();
       // Class<b.Inner> innerClass = b.Inner.class;
        b.new Inner();

        Object o=new Object();

        List<Integer> list=new ArrayList<Integer>();
        List list1=list;
        list1.add(new File("hello"));

        List<? extends  Number> list2=list;
       // list2.add(new Integer(2));

        BigDecimal bigDecimal=new BigDecimal(5);
        bigDecimal.add(new BigDecimal(6));

    }

   private  class  Inner{


    }

    protected  void hello(Basic.Inner inner){

        Basic.Inner inner1 = new Basic().new Inner();

    }

}

 class  Com extends  Basic{

   public void  test(){

        Basic b=new Basic();
        //b.hello();

        //System.identityHashCode()   ==  //reference address
        // new Thread()
       new Thread(new Runnable() {
           public void run() {

           }
       });
    }

    public class Inner{

    }



    public  static  <T extends Comparable<? super T>> T min(T t){

        return  t;
    }
}

   interface  iHello{


}









