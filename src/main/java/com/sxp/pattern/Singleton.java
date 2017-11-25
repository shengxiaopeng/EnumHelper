package com.sxp.pattern;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/11/20.
 */
public class Singleton implements Serializable {

    private Singleton(){

    }

    private static class SingletonHolder{
        private static Singleton singleton=new Singleton();
    }

    public static Singleton getInstance(){
        return  SingletonHolder.singleton;
    }

}

