package com.example.administrator.fruitcuttersimple.client;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：请求参数封装类
 * 创建人：quzongyang
 * 创建时间：2016/7/28. 18:58
 * 版本：
 */
public class NetParams {
    private static NetParams params = new NetParams();

    private NetParams() {

    }

    public static NetParams getInstance() {
        return params;
    }

    /**
     * @return
     */
    public Map<String, String> getQuestion() {
        Map<String, String> params = new HashMap<>();
        params.put("city_id", "110000");
        params.put("user_id", "609412");
        params.put("store_id", "7");
        params.put("token","2704455a68f94aa3492bae8dc9745c57");
        return params;
    }
}
