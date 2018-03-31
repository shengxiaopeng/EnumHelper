package com.sxp.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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
            /**
             * Appends the specified URL to the list of URLs to search for
             * classes and resources.
             * <p>
             * If the URL specified is <code>null</code> or is already in the
             * list of URLs, or if this loader is closed, then invoking this
             * method has no effect.
             *
             * @param url the URL to be added to the search path of URLs
             */
            this.addURL(url);
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        //method 1
        loadJarByURLClassLoader();
        //method 2
       // loadJarByMyJarLoader();


    }

    private static void loadJarByMyJarLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MyJarLoader jarLoader=new MyJarLoader(new URL[]{},Thread.currentThread().getContextClassLoader());
        jarLoader.addJar(new File("C:/Users/sxp/Desktop/hello1.jar").toURI().toURL());
        Class<?> clazz= jarLoader.loadClass("com.example.Test");
        Object object=  clazz.newInstance();
        Method method= clazz.getDeclaredMethod("test");
        method.setAccessible(true);
        method.invoke(object);
    }

    private static void loadJarByURLClassLoader() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        URL jarUrl=new URL("file:///C:/Users/sxp/Desktop/hello1.jar");
      /*  URLConnection urlConnection = jarUrl.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        IOUtils.copy(inputStream,System.out);*/

       // URL jarUrl = new File("C:/Users/sxp/Desktop/hello1.jar").toURI().toURL();

        URL[] urls=new URL[]{jarUrl};
        URLClassLoader jarLoader=new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
        Class<?> clazz= jarLoader.loadClass("com.example.Test");
        Object object=  clazz.newInstance();
        Method method= clazz.getDeclaredMethod("test");
        method.setAccessible(true);
        method.invoke(object);
    }

}
