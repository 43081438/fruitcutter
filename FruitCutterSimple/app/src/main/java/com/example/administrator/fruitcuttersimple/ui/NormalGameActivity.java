package com.example.administrator.fruitcuttersimple.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.fruitcuttersimple.R;
import com.example.administrator.fruitcuttersimple.bean.GameResultEntity;
import com.example.administrator.fruitcuttersimple.fruitcutter.AnySurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/21. 14:28
 * 版本：
 */
public class NormalGameActivity extends Activity {
    private GameResultEntity.GameEntity gameEntity;
    private AnySurfaceView any_surface_view;
    private RelativeLayout rl_fruit_cutter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(any_surface_view);
        setContentView(R.layout.activity_normal_game);
        initFindViewByID();
        initData();


    }

    public void initFindViewByID(){
        //any_surface_view = (AnySurfaceView) findViewById(R.id.any_surface_view);
        rl_fruit_cutter = (RelativeLayout) findViewById(R.id.rl_fruit_cutter);
    }
    public void initData(){
        //Bundle bundle = getIntent().getBundleExtra("INTENT_BUNDLE");
        //gameEntity = (GameResultEntity.GameEntity) bundle.getSerializable("INTENT_OBJECT");
        GameResultEntity.GameEntity gameEntity = new GameResultEntity.GameEntity();
        List<GameResultEntity.GameEntity.GameItemEntity> gameItemEntityList = new ArrayList<GameResultEntity.GameEntity.GameItemEntity>();
        //初始化Spirites链表
        gameEntity.setType("1");
        gameEntity.setOver_type(1);
        gameEntity.setInterval(1);
        gameEntity.setBack_img("http://imgcdn.xuxian.com/upload/2016/09/22/20160922054447804.jpg");
        gameEntity.setDesc("切水果");
        for(int index = 0 ; index <20 ;index++){
            if(index%4==0){//炸弹
                GameResultEntity.GameEntity.GameItemEntity gameItemEntity = new GameResultEntity.GameEntity.GameItemEntity();
                gameItemEntity.setImg("http://imgcdn.xuxian.com/upload/2016/09/22/20160922032631801.png");
                gameItemEntity.setClick_img("http://imgcdn.xuxian.com/upload/2016/09/22/20160922032638901.png");
                gameItemEntity.setWidth(150);
                gameItemEntity.setHeight(150);
                gameItemEntity.setIs_bomb(1);
                gameItemEntity.setSpeed(3);
                gameItemEntity.setReward_type("coupon");
                gameItemEntity.setReward_value("183");
                gameItemEntityList.add(gameItemEntity);
            }else{//红包
                GameResultEntity.GameEntity.GameItemEntity gameItemEntity = new GameResultEntity.GameEntity.GameItemEntity();
                gameItemEntity.setImg("http://imgcdn.xuxian.com/upload/2016/09/22/20160922032615957.png");
                gameItemEntity.setClick_img("http://imgcdn.xuxian.com/upload/2016/09/22/20160922032620571.png");
                gameItemEntity.setWidth(150);
                gameItemEntity.setHeight(150);
                gameItemEntity.setIs_bomb(0);
                gameItemEntity.setSpeed(3);
                gameItemEntity.setReward_type("coupon");
                gameItemEntity.setReward_value("183");
                gameItemEntityList.add(gameItemEntity);
            }
        }
        gameEntity.setItem(gameItemEntityList);
        //((AnySurfaceView)any_surface_view).setData(gameEntity);
        any_surface_view = new AnySurfaceView(this,gameEntity);
        rl_fruit_cutter.addView(any_surface_view);
    }
}
