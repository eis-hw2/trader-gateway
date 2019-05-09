package com.example.trader.Util.Network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    public static String METHOD_GET = "GET";
    public static String METHOD_POST = "POST";
    public static String METHOD_PUT = "PUT";
    public static String METHOD_DELETE = "DELETE";
    public static String ERROR_VALUE = "ERROR";
    public static String NULL_VALUE = "NULL";

    private static String getResponse (HttpURLConnection conn) throws IOException{
        StringBuffer sb = new StringBuffer();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream in1 = conn.getInputStream();
            try {
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                while((readLine = responseReader.readLine()) != null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
            } catch (Exception e1) {
                e1.printStackTrace();
                return ERROR_VALUE;
            }
        }
        else if (responseCode == HttpURLConnection.HTTP_ACCEPTED){
            return NULL_VALUE;
        }
        else {
            System.out.println("[HttpRequest.getResponse][error]:"+conn.getResponseMessage());
            return ERROR_VALUE;
        }
        return sb.toString();
    }

    public static String request(String method, String urls) {
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            conn.connect();
            return getResponse(conn);
        }
        catch (Exception e) {
            return NULL_VALUE;
        }
    }
    public static String requestWithBody(String method, Object jsonParam, String urls) {

        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            byte[] data = (jsonParam.toString()).getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();
            return getResponse(conn);
        }
        catch (Exception e) {
            return ERROR_VALUE;
        }
    }
}
