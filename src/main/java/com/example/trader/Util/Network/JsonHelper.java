package com.example.trader.Util.Network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class JsonHelper {
    public static <T> T jsonStringToObject(String json, Class<T> type){
        return JSON.toJavaObject(JSONObject.parseObject(json), type);
    }

    public static <T> T jsonObjectToObject(JSONObject json, Class<T> type){
        return JSON.toJavaObject(json, type);
    }

    public static <T> List<T> jsonStringToList(String json, Class<T> type){
        return JSONObject.parseArray(json, type);
    }
}
