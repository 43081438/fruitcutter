package com.example.administrator.fruitcuttersimple.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.fruitcuttersimple.MyApplication;
import com.example.administrator.fruitcuttersimple.R;
import com.example.administrator.fruitcuttersimple.bean.GameResultEntity;
import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.AnySurfaceView;
import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.BitmapGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：DEV  普通模式（水果全部切完游戏结束）
 * 创建人：quzongyang
 * 创建时间：2016/9/21. 14:28
 * 版本：
 */
public class NormalGameActivity extends Activity {
    private GameResultEntity.GameEntity gameEntity;
    private AnySurfaceView any_surface_view;
    private RelativeLayout rl_fruit_cutter;
    private TextView tv_count;
    private int totalTime = 3;
    //水果和被切后水果存放的数组
    private Bitmap[] fruitBitmap;
    private Bitmap[] fruitAfterCutBitmap;
    //声明存放Bitmap的对象
    private BitmapGroup bitmapGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(any_surface_view);
        setContentView(R.layout.activity_normal_game);
        initFindViewByID();

        initData();
        init();


    }

    public void init(){
        MyApplication.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (totalTime > 1) {
                    totalTime--;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_count.setText(totalTime + "");
                        }
                    });
                    MyApplication.getUIHandler().postDelayed(this, 1000);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_count.setVisibility(View.GONE);
                            totalTime = 3;
                            any_surface_view = new AnySurfaceView(getActivity(),gameEntity,fruitBitmap,fruitAfterCutBitmap);
                            rl_fruit_cutter.addView(any_surface_view);
                            //initView();
                        }
                    });
                }
            }
        }, 1000);
    }

    public Activity getActivity(){
        return this;
    }

    public void initFindViewByID(){
        //any_surface_view = (AnySurfaceView) findViewById(R.id.any_surface_view);
        rl_fruit_cutter = (RelativeLayout) findViewById(R.id.rl_fruit_cutter);
        tv_count = (TextView) findViewById(R.id.tv_count);
    }
    public void initData(){
        //Bundle bundle = getIntent().getBundleExtra("INTENT_BUNDLE");
        //gameEntity = (GameResultEntity.GameEntity) bundle.getSerializable("INTENT_OBJECT");
        gameEntity = new GameResultEntity.GameEntity();
        List<GameResultEntity.GameEntity.GameItemEntity> gameItemEntityList = new ArrayList<GameResultEntity.GameEntity.GameItemEntity>();
        //初始化Spirites链表
        gameEntity.setType("1");
        gameEntity.setOver_type(1);
        gameEntity.setInterval(1);
        gameEntity.setBack_img("http://imgcdn.xuxian.com/upload/2016/09/22/20160922054447804.jpg");
        gameEntity.setDesc("切水果");
        for(int index = 0 ; index <20 ;index++){
            GameResultEntity.GameEntity.GameItemEntity gameItemEntity = new GameResultEntity.GameEntity.GameItemEntity();
            gameItemEntity.setIndex(index);
            if(index%4==0){//炸弹
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
        /*any_surface_view = new AnySurfaceView(getActivity(),gameEntity);
        rl_fruit_cutter.addView(any_surface_view);*/
        //((AnySurfaceView)any_surface_view).setData(gameEntity);
        //初始化水果Bitmap数组以及被切后的水果Bitmap数组
        fruitBitmap = new Bitmap[ gameItemEntityList.size() ];
        fruitAfterCutBitmap = new Bitmap[ gameItemEntityList.size()*2 ];
        initFruitBitmapArray( getActivity() );
    }

    /**
     * 初始化水果图片背景
     * @param context
     */
    private void initFruitBitmapArray( Context context){
        initFruitBitmapDefault();
        for(int index = 0 ;index<gameEntity.getItem().size();index++){
            GameResultEntity.GameEntity.GameItemEntity gameItemEntity = gameEntity.getItem().get(index);
            initFruitBitmap( context, index, gameItemEntity.getImg(),gameItemEntity.getClick_img(),gameItemEntity.getClick_img());
        }
    }

    private void initFruitBitmapDefault(){
        for(int index = 0 ;index<gameEntity.getItem().size();index++){
            fruitBitmap[ index ] = BitmapFactory.decodeResource( getActivity().getResources(), R.drawable.apple);
            fruitAfterCutBitmap[ index*2 ] = BitmapFactory.decodeResource( getActivity().getResources(), R.drawable.applep1);
            fruitAfterCutBitmap[ index*2+1 ] = BitmapFactory.decodeResource( getActivity().getResources(), R.drawable.applep2);
        }
    }

    /**
     * 初始化Bitmap方法
     */
    private void initFruitBitmap(final Context context, final int index, String fruitURL, String fruitAfterURL_1, String fruitAfterURL_2 ){
        //初始化正常水果
        Glide.with(MyApplication.getInstance()).load(fruitURL)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(150, 150) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        fruitBitmap[ index ] = resource;
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        fruitBitmap[ index ] = BitmapFactory.decodeResource( context.getResources(), R.drawable.apple);
                    }
                });
        //初始化被切开水果
        Glide.with(MyApplication.getInstance()).load(fruitAfterURL_1)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(150, 150) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        fruitAfterCutBitmap[ index*2 ] = resource;
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        fruitAfterCutBitmap[ index*2 ] = BitmapFactory.decodeResource( context.getResources(), R.drawable.applep1);
                    }
                });
        //初始化被切开水果
        Glide.with(MyApplication.getInstance()).load(fruitAfterURL_2)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(150, 150) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        fruitAfterCutBitmap[ index*2+1 ] = resource;
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        fruitAfterCutBitmap[ index*2+1 ] = BitmapFactory.decodeResource( context.getResources(), R.drawable.applep2);;
                    }
                });
    }
}
