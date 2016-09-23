package com.example.administrator.fruitcuttersimple.client;

import com.example.administrator.fruitcuttersimple.bean.GameResultEntity;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;


/**
 * 类名称：
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/7/21. 19:14
 * 版本：
 */
public interface RetrofitService {


    @FormUrlEncoded
    @POST("/game/get_data?ver=2016092201")
    Call<GameResultEntity> getQuestion(@FieldMap Map<String, String> MapPar);

}
