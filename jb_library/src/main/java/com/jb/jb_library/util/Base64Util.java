package com.jb.jb_library.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/15 17:53
 * @描述： ${TODO}
 */

public class Base64Util {
  

    private static final int CACHE_SIZE = 1024;  
      

    public static byte[] decode(String base64) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(base64);
    }  
      

    public static String encode(byte[] bytes) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(bytes);
    }  
      

    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);  
        return encode(bytes);  
    }  
      

    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);  
        byteArrayToFile(bytes, filePath);  
    }  

    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];  
        File file = new File(filePath);
        if (file.exists()) {  
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];  
            int nRead = 0;  
            while ((nRead = in.read(cache)) != -1) {  
                out.write(cache, 0, nRead);  
                out.flush();  
            }  
            out.close();  
            in.close();  
            data = out.toByteArray();  
         }  
        return data;  
    }  
      

    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {  
            destFile.getParentFile().mkdirs();  
        }  
        destFile.createNewFile();  
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];  
        int nRead = 0;  
        while ((nRead = in.read(cache)) != -1) {     
            out.write(cache, 0, nRead);  
            out.flush();  
        }  
        out.close();  
        in.close();  
    }  
      
      
}  