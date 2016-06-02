package com.softtek.lai.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jarvis.liu on 6/1/2016.
 */
public class WheatherUtil {
    static String httpUrl = "http://apis.baidu.com/apistore/aqiservice/aqi";
    /**
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public String request( String httpArg) {
        BufferedReader reader = null;
        String result = "";
        StringBuffer sbf = new StringBuffer();
        httpUrl =httpArg;
        System.out.println("httpUrl:"+httpUrl);
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "0a507eff1ce119c32987a147a21fe308");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
