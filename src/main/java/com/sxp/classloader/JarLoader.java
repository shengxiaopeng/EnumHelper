package com.sxp.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.Thread.currentThread;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2018/3/31.
 */
public class JarLoader {

    static class MyJarLoader extends URLClassLoader {

        public MyJarLoader(URL[] urls) {
            super(urls);
        }

        public MyJarLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public void addJar(URL url) {
            this.addURL(url);
        }

    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        //
        //loadJarByURLClassLoader();
        loadJarByMyJarLoader();


    }

    private static void loadJarByMyJarLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MyJarLoader jarLoader=new MyJarLoader(new URL[]{},Thread.currentThread().getContextClassLoader());
        jarLoader.addJar(new File("C:/Users/sxp/Desktop/hello.jar").toURI().toURL());
        Class<?> clazz= jarLoader.loadClass("com.sxp.basic.NetTest");
        Object object=  clazz.newInstance();
        Method method= clazz.getDeclaredMethod("testNet");
        method.setAccessible(true);
        method.invoke(object);
    }

    private static void loadJarByURLClassLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        URL jarUrl=new URL("file:/C:/Users/sxp/Desktop/hello.jar");
        URL[] urls=new URL[]{jarUrl};
        URLClassLoader jarLoader=new URLClassLoader(urls, currentThread().getContextClassLoader());
        Class<?> clazz= jarLoader.loadClass("com.sxp.basic.NetTest");
        Object object=  clazz.newInstance();
        Method method= clazz.getDeclaredMethod("testNet");
        method.setAccessible(true);
        method.invoke(object);
    }

}
