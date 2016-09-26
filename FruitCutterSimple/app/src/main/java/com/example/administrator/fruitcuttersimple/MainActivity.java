package com.example.administrator.fruitcuttersimple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.fruitcuttersimple.bean.GameResultEntity;
import com.example.administrator.fruitcuttersimple.client.NetParams;
import com.example.administrator.fruitcuttersimple.client.RetrofitService;
import com.example.administrator.fruitcuttersimple.ui.BloodGameActivity;
import com.example.administrator.fruitcuttersimple.ui.NormalGameActivity;
import com.example.administrator.fruitcuttersimple.ui.TimeModelActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_normal_game;
    private Button btn_blood_game;
    private Button btn_time_game;
    private GameResultEntity.GameEntity gameResultEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*Retrofit retrofit = new Retrofit.Builder().baseUrl("http://mobile.xuxian.com").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<GameResultEntity> call =  service.getQuestion(NetParams.getInstance().getQuestion());
        call.enqueue(new Callback<GameResultEntity>() {
            @Override
            public void onResponse(Response<GameResultEntity> response, Retrofit retrofit) {
                if(response.body().getStatus().getCode() == 0){
                    Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
                    gameResultEntity = response.body().getData();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void initView(){
        btn_blood_game = (Button) findViewById(R.id.btn_blood_game);
        btn_time_game = (Button) findViewById(R.id.btn_time_game);
        btn_normal_game = (Button) findViewById(R.id.btn_normal_game);
        btn_blood_game.setOnClickListener(this);
        btn_normal_game.setOnClickListener(this);
        btn_time_game.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_normal_game://普通模式
                Intent normal_intent = new Intent(getActivity(), NormalGameActivity.class);
               /* Bundle normal_bundle = new Bundle();
                normal_bundle.putSerializable("INTENT_OBJECT", gameResultEntity);
                normal_intent.putExtra("INTENT_BUNDLE", normal_bundle);*/
                startActivity(normal_intent);
                break;
            case R.id.btn_blood_game://血量模式
                startActivity(new Intent(MainActivity.this, BloodGameActivity.class));
                break;
            case R.id.btn_time_game://时间模式
                Intent timeIntent = new Intent(getActivity(), TimeModelActivity.class);
                /*Bundle timeBundle = new Bundle();
                timeBundle.putSerializable("INTENT_OBJECT", gameResultEntity);
                timeIntent.putExtra("INTENT_BUNDLE", timeBundle);*/
                startActivity(timeIntent);
                break;

        }
    }

    public Activity getActivity(){
        return this;
    }

}
