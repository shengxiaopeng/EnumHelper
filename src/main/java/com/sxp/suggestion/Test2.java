package com.sxp.suggestion;

import java.text.Collator;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/3.
 */
public class Test2 {

    public static void main(String[] args) throws MyException {
        /*int i = Integer.MAX_VALUE / Integer.MIN_VALUE;
        System.out.println(i);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println((int)-0.99);*/

        //test();

        //AccessibleObject.setAccessible();

       /* String name = String[].class.getName();
        System.out.println(name);


        Object o=new Object();
        Integer o1 = (Integer) o;*/

        //int i=5;
        //long l=1;
       // Long l1=1;  编译有问题

        //System.out.println(null instanceof  Integer);
        //new HashCodeBuilder().append()

        Random random=new Random(1000);
        Random random1=new Random(1000);

        int i = random.nextInt();
        int i1 = random1.nextInt();
        System.out.println("i:"+i);
        System.out.println("i1:"+i1);

        //SerializationUtils.serialize();


        String str="$2$";
        //str.replaceAll();
        String $ = str.replace("$", "");
        System.out.println($);

        String s = str + new ArrayList<Integer>() + new HashMap();
        System.nanoTime();

        new StringBuilder().toString();

        char a='张';   // java中一个字符两个字节

        Comparator instance = Collator.getInstance(Locale.CHINA);

        long a1=1L;
        //int a2=0x1;

        System.out.println(Constant.RAND);
        System.out.println(Constant.RAND); System.out.println(Constant.RAND); System.out.println(Constant.RAND); System.out.println(Constant.RAND);

        int[] at=null;
        //Integer[] bb=at;
        //SimpleJavaFileObject
        String s1 =(String) null;
        System.out.println("--- "+s1);

        //new Date() instanceof  String
        boolean b = s1 instanceof String;
        System.out.println(b);

        try {
            assert 1<0;


        }catch (Error error){
            System.out.println("--error--");
            //Integer.valueOf()

        }


    }

    public  static void  test() throws MyException {
        throw  new MyException("hello exception");

    }

    interface Constant{

        public  static final int RAND=new Random().nextInt();
    }
}





class  MyException extends  Exception{

    public MyException(String message){
        super(message);
    }

    @Override
    public Throwable fillInStackTrace(){
        return  this;
    }


    void fun(int[] para){

    }
    /*void fun(int... para){

    }*/


}