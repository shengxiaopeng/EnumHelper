package com.sxp.suggestion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/3.
 */
public class StaticClass {

    public static void main(String[] args) {
        A a = new A();
        A a1= new  StaticClass.A();

//        new ArrayList<Integer>().indexOf()

       // RuntimeException//
    }

    static class  A{
        //ArrayBlockingQueue

        //ReentrantLock
       // TimeUnit.MILLISECONDS

        void  test() throws IOException,FileNotFoundException{

        }

    }

    static class B{

    }
}

/**
 * callable 有返回值 且抛出异常
 */
class A implements Callable<Integer>{

    public Integer call() throws Exception {
        return null;
    }
}

class Base{

    static {
        i=100;
    }
    public  static int i=0;

    public Base() throws IOException {
        throw new IOException();
    }
}

interface Itest{

    class Hello{

    }
    Hello b=new Hello();


}

class Sub extends Base{

    public Sub() throws Exception {

        try{
           // Type
           // getClass()

           // SerializationUtils.clone()
        }
        catch (Error error){

        }
    }
}