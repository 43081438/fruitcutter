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

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button btn_normal_game;
    private Button btn_blood_game;
    private GameResultEntity.GameEntity gameResultEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_blood_game = (Button) findViewById(R.id.btn_blood_game);
        btn_normal_game = (Button) findViewById(R.id.btn_normal_game);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://mobile.xuxian.com").addConverterFactory(GsonConverterFactory.create()).build();
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
        });
        btn_blood_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BloodGameActivity.class));
            }
        });

        btn_normal_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NormalGameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("INTENT_OBJECT", gameResultEntity);
                intent.putExtra("INTENT_BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    public Activity getActivity(){
        return this;
    }

}
