package com.softtek.lai.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jerry.guan on 4/5/2016.
 */
public class MD5 {

    public static String md5WithEncoder(String text){
        String res="";
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            res=new String(md5.digest(text.getBytes("utf-8")));
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

}
