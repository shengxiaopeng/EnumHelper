package com.sxp.basic;

import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2017/9/10.
 */
public class NetTest {

    public static void main(String[] args) {

        try {
            testNet();
           // testEncoder();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void testNet() throws IOException {
        URL url=new URL("http://www.baidu.com");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
       // System.out.println(urlConnection.getContent());
        OutputStream outputStream=new BufferedOutputStream(System.out);
        //IOUtils.copy(inputStream,System.out);
        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
    }

    static void testEncoder() throws Exception {
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharsetDecoder charsetDecoder = charset.newDecoder();

       // ByteBuffer byteBuffer = ByteBuffer.wrap("盛小朋".getBytes());
        CharBuffer charBuffer=CharBuffer.wrap("盛小朋");
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        CharBuffer decode = charsetDecoder.decode(encode);

        System.out.println(encode);
        System.out.println(decode);


    }


}
