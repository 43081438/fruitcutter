package com.example.administrator.fruitcuttersimple;

import android.app.Application;

import android.os.Handler;


/**
 * 类名称：
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 16:05
 * 版本：
 */
public class MyApplication extends Application {


    /**
     * 全局context
     */
    private static MyApplication myAppLication;

    private static Handler handler;


    @Override
    public void onCreate() {
        super.onCreate();
        myAppLication = (MyApplication) getApplicationContext();
        handler = new Handler();
    }


    /**
     * 创建一个handler对象
     */
    public static Handler getUIHandler() {
        return handler;
    }


    // 单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        return myAppLication;
    }


}
