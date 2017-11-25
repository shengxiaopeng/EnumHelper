package com.sxp.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/8/27.
 */
public class EnumTest {

    public static void main(String[] args) {
        Object[] o=new Object[5];
        String[] s=(String[])o;
    }

    public  Object getDesc(){
     // return new Object();

        Object[] o=new Object[5];
        String[] s=(String[])o;

        return null;
    }

//              n

}

@interface Desc{
    enum Color{
        White,Red,EnumTest;

        public EnumTest getColor(){
            return new EnumTest();
        }
    }




}

interface iDesc{
    enum Color{
        White,Red;
    }

    class Hello{

    }

    void hello();
}

enum EDesc implements iDesc{
    ;

    public void hello() {
        //Method m;

    }
}

class Foo<T>{
//    private T t=new T();
//      private T[] tArr=new T[5];
      private List<T> list=new ArrayList<T>();
}


