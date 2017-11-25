package com.sxp.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/15.
 */
public class TestHello {

   public Logger logger=Logger.getLogger(TestHello.class);

    private final List i;

    public TestHello(){
        i=new ArrayList() ;
    }

    public static void main(String[] args) {
        //System.out.println("hello test");
        JedisPool pool = new JedisPool("192.168.130.128",6379);
        Jedis jedis = pool.getResource();
       // Jedis jedis=new Jedis("192.168.130.128",6379);
        jedis.set("hello","hello jedis");
        String hello = jedis.get("hello");

        System.out.println(hello);
        jedis.close();


    }

    @Before
    public  void  init(){
        System.out.println("--before");
    }

    @Test
    public  void test11(){
       // Assert.assertNotNull(null);
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isBlank("  "));

        InputStream fileName = Thread.currentThread().getContextClassLoader().getResourceAsStream("fileName");

        logger.info("hello");

        //URLEncoder.encode()
       // new String(new byte[5],"")
       // InputStream is=new InputStreamReader();

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = contextClassLoader.getResources("org.smart4j".replace(".","/"));

            while (urls.hasMoreElements()){

                URL url = urls.nextElement();

                System.out.println(url.getPath()+url.getProtocol());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public  void  test22(){
        System.out.println("hello 2");

        logger.info("nihao");
    }
}
