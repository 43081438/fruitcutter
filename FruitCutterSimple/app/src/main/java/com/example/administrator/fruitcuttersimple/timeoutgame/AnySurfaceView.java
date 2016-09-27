package com.example.administrator.fruitcuttersimple.timeoutgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.fruitcuttersimple.MyApplication;
import com.example.administrator.fruitcuttersimple.R;
import com.example.administrator.fruitcuttersimple.bean.GameResultEntity;

import java.util.ArrayList;


/**
 * 类名称：
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/21. 14:33
 * 版本：
 */
public class AnySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private int screenWidth,screenHeight;//屏幕宽高
    private SurfaceHolder holder;
    private Bitmap modeOneBG;//游戏背景
    private RectF rectF;
    private InplementThread inplementThread;
    private Thread mThread;
    //声明切中计数器
    public static int cutCount = 0;
    //记分板画笔
    private Paint counterPaint;
    //声明计时器
    private Timer timer;

    private Canvas canvas;


    private ArrayList<PointF> mBladeTrack;//刀光点集合
    private Paint mPaint;//刀光画笔
    //鼠标当前位置
    private PointF mousePos;
    //鼠标放开！
    private boolean isMouseUp = false;
    //声明存放Bitmap的对象
    //private BitmapGroup bitmapGroup;
    //水果和被切后水果存放的数组
    private Bitmap[] fruitBitmap;
    private Bitmap[] fruitAfterCutBitmap;
    //水果以及被切后种类的数目
    private int numOfFruitType;
    private int numOfFruitAfterCutType;
    private GameResultEntity.GameEntity gameEntity;
    //随机生成器
    private RandomGenerator randomGenerator;
    //声明存放Bitmap的对象
    private BitmapGroup bitmapGroup;

    private RandomTimer randomTimer;
    //声明Spirite对象
    private Spirite spirite;
    //声明Spirite链表
    private ArrayList<Spirite> spiritesList;
    private int currentPosition = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Spirite barrage = (Spirite) msg.getData().getSerializable("currentFruit");
            Toast.makeText(mContext,barrage.position+"",Toast.LENGTH_SHORT).show();
        }
    };

    public AnySurfaceView(Context context,GameResultEntity.GameEntity gameEntity,Bitmap[] fruitBitmap,Bitmap[] fruitAfterCutBitmap) {
        super(context);
        this.mContext = context;
        this.numOfFruitType = gameEntity.getItem().size();
        this.numOfFruitAfterCutType = gameEntity.getItem().size()*2;
        this.gameEntity = gameEntity;
        this.fruitBitmap = fruitBitmap;
        this.fruitAfterCutBitmap = fruitAfterCutBitmap;
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        //holder things
        holder = this.getHolder();
        holder.addCallback(this);
        modeOneBG = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.redpackage_pop_false);
        //初始化rectF
        rectF = new RectF(0, 0, screenWidth, screenHeight);
        Glide.with(MyApplication.getInstance()).load("http://10.20.1.1/upload/2016/09/21/20160921120515947.jpg")
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(screenWidth, screenHeight) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        modeOneBG = resource;
                        if(canvas != null){
                            canvas.drawBitmap(modeOneBG, null, rectF, null);
                        }
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        //modeOneBG = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.redpackage_pop_false);
                    }
                });
        //初始化inplementThread
        inplementThread = new InplementThread();
        mThread = new Thread(inplementThread);
        //初始化BitmapGroup
        bitmapGroup = new BitmapGroup( fruitBitmap, fruitAfterCutBitmap );
        //初始化Spirites链表
        spiritesList = new ArrayList<Spirite>();

        //初始化链表
        mBladeTrack = new ArrayList<PointF>();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
        //初始化mousePos
        mousePos = new PointF();
        //初始化计时器
        timer = new Timer();
        randomTimer = new RandomTimer();
        //记分板画笔初始化
        counterPaint = new Paint();
        counterPaint.setColor( Color.YELLOW );
        counterPaint.setTextSize( 40 );
        //随机生成器初始化
        randomGenerator = new RandomGenerator();
    }

    //生成水果
    private void genSpirite(){
        if( randomTimer.isTimeUp() ){
            spirite = new Spirite( bitmapGroup );
            spirite.position = currentPosition;
            currentPosition++;
            spiritesList.add( spirite );
            if( spiritesList.size() > 10 ){
                spiritesList.remove( 0 );
            }
        }
    }

    //画水果
    private void drawSpirites( Canvas canvas ){

        for( int i=0; i<spiritesList.size(); i++ ){
            Spirite tempSpirite = spiritesList.get(i);

            isCut( tempSpirite );
            tempSpirite.drawBeforeCut(canvas);
        }
    }



    //
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            pressDown(event);

        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            pressMove(event);

            mousePos.x = event.getX();
            mousePos.y = event.getY();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            pressUp(event);

            mousePos.x = 0;
            mousePos.y = -100;

            isMouseUp = true;
        }

        return true;
    }

    //按下以及按下滑动的坐标记录
    private void pressDown ( MotionEvent event ){
        PointF position = new PointF(event.getX(), event.getY());
        //加锁
        synchronized (mBladeTrack){
            mBladeTrack.add(position);
        }
        isMouseUp = false;
    }

    private void pressMove ( MotionEvent event ){
        PointF position = new PointF(event.getX(), event.getY());
        synchronized (mBladeTrack){
            if( mBladeTrack.size() > 15){
                mBladeTrack.remove(0);
            }
            mBladeTrack.add(position);
            isMouseUp = false;
        }
    }

    private void pressUp ( MotionEvent event ){
        synchronized (mBladeTrack){
            mBladeTrack.clear();
        }
    }

    //判断是否切到
    private boolean isCut( Spirite spirite ){

        Point pos = spirite.getBitmapPos();
        Point size = spirite.getBitmapSize();
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;
        int objectLeft = pos.x;
        int objectRight = pos.x + size.x;
        int objectUp = pos.y;
        int objectBottom = pos.y + size.y;

        if ( mouseX>objectLeft && mouseX<objectRight ){
            if ( mouseY>objectUp && mouseY<objectBottom ){
                if( !spirite.getCUT() ){
                    //soundPool.play( cutSound[ randomGenerator.getCutSoundInt() ], 1, 1, 2, 0, 1 );
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    //GameResultEntity.GameEntity.GameItemEntity gameEntity = date.get(nowIndex);
                    //gameEntity.setIndex(nowIndex);
                    bundle.putSerializable("currentFruit",spirite);
                    //nowIndex ++;
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                spirite.setCUT( true );
                return true;
            }
        }
        return false;
    }

    //线程
    class InplementThread implements Runnable{
        private boolean isRun = true;
        @Override
        public void run() {
            while(isRun){
                //锁住线程
                Canvas canvas = holder.lockCanvas();
                if(modeOneBG == null){
                    modeOneBG = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.redpackage_pop_false);
                }else{
                    //1 常规模式
                    canvas.drawBitmap(modeOneBG, null, rectF, null);
                }
                //1 常规模式
                canvas.drawBitmap(modeOneBG, null, rectF, null);
                    if ( !timer.isGameOver() ) {
                        //随机画水果
                        genSpirite();
                        drawSpirites( canvas );
                        //画记分板
                        drawCount(canvas);
                        //画倒计时板
                        timer.draw( canvas );
                    }
                //画刀光
                drawBladeTrack(canvas);
                //线程解锁
                holder.unlockCanvasAndPost(canvas);
                //间隔
                try{
                    Thread.sleep(10);
                }
                catch (InterruptedException e){
                }
            }
        }

        //滑动轨迹重绘
        private void drawBladeTrack(Canvas canvas){
            synchronized (mBladeTrack){
                if(mBladeTrack.size() > 1 ){
                    for(int i=0; i<mBladeTrack.size()-1; i++ ){
                        mPaint.setStrokeWidth(i+1);
                        canvas.drawLine(mBladeTrack.get(i).x, mBladeTrack.get(i).y, mBladeTrack.get(i+1).x, mBladeTrack.get(i+1).y, mPaint);
                    }
                    mBladeTrack.remove(0);
                }
            }
        }

        //绘制记分板
        private void drawCount( Canvas canvas ){
            canvas.drawText( ""+cutCount, 20, 70, counterPaint );
        }

        //中断线程
        private void stopThread(){
            isRun = false;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //缓冲区准备好后才之行
        mThread.start();
        //mediaPlayer.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        inplementThread.stopThread();
    }
}