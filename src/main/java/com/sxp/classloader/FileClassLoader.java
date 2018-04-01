package com.sxp.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2018/4/1.
 */
public class FileClassLoader extends ClassLoader {

    String dir;

    FileClassLoader(String dir){
        this.dir=dir;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(dir+name.replace('.','\\')+".class");
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            int available = fileInputStream.available();

            byte[] bytes=new byte[available];
            fileInputStream.read(bytes);

            Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
            return aClass;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        String dir="E:\\javacode\\demo\\target\\classes\\";

        FileClassLoader fileClassLoader=new FileClassLoader(dir);
        FileClassLoader fileClassLoader1=new FileClassLoader(dir);

        Class<?> aClass = fileClassLoader.loadClass("com.example.Test");
        Class<?> bClass = fileClassLoader1.loadClass("com.example.Test");

        System.out.println(aClass.hashCode()+"\n"+bClass.hashCode());

    }

}
