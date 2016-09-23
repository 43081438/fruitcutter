package com.example.administrator.fruitcuttersimple.fruitcutter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * 类名称：
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 14:50
 * 版本：
 */
public class Spirite {

    //加速度
    private final static float accelerateY = 3;
    //切前的水果Bitmap
    private Bitmap fruitBitmap;
    //切后的水果Bitmap
    private Bitmap fruitBitmapOne;
    private Bitmap fruitBitmapTwo;
    //声明随机数生成器
    private RandomGenerator random;
    //水果位置
    private Point pos;
    //水果速度
    private Point velocity;
    //获得水果bitmap的大小
    private Point size;
    //水果是否被切中
    private boolean isCUT = false;
    //被切后执行一次的状态参数
    private boolean executeOnceStatus = false;
    //被切后另一半水果的速度和位置
    private Point posTwo;
    private Point velocityTwo;


    //切中之前 -- 改变图片坐标（移动）
    private void resetPos(){
        pos.x += velocity.x;
        pos.y += velocity.y;

        velocity.y += accelerateY;
    }

    //切中之后 -- 改变图片坐标
    public void resetPosAfterCut(){
        posTwo.x += velocityTwo.x;
        posTwo.y += velocityTwo.y;

        velocityTwo.y += accelerateY;
    }

    //绘制
    public void drawBeforeCut( Canvas canvas ){
        //切中之前
        if( !isCUT ){
            resetPos();

            canvas.drawBitmap( fruitBitmap, pos.x, pos.y, null );
        } else {
            if( !executeOnceStatus ){
                executeOnce();
            }
            resetPos();
            resetPosAfterCut();

            canvas.drawBitmap( fruitBitmapOne, pos.x, pos.y, null );
            canvas.drawBitmap( fruitBitmapTwo, posTwo.x, posTwo.y, null );
        }

    }

    //被切后执行一次
    private void executeOnce(){
        AnySurfaceView.cutCount ++;

        if( velocity.x>0 ){
            velocity.x -= 3;
        } else {
            velocity.x += 3;
        }

        if( velocity.y<0 ){
            velocity.y += 3;
        }

        posTwo = new Point( pos.x, pos.y + 5 );
        velocityTwo = new Point( velocity.x, velocity.y + 1 );

        executeOnceStatus = true;
    }

    public void setCUT( boolean isCUT ){
        this.isCUT = isCUT;
    }

    public boolean getCUT(){
        return isCUT;
    }

    //返回当前位置
    public Point getBitmapPos(){
        return pos;
    }

    public Point getBitmapSize(){
        return size;
    }

    //构造方法
    public Spirite( BitmapGroup bitmapGroup ){

        fruitBitmap = bitmapGroup.getFruitBitmap();

        fruitBitmapOne = bitmapGroup.getFruitBitmapOne();
        fruitBitmapTwo = bitmapGroup.getFruitBitmapTwo();

        random = new RandomGenerator();

        pos = random.getPos();

        velocity = random.getVelocity();

        size = new Point( fruitBitmap.getWidth(), fruitBitmap.getHeight() );

        // System.out.println( "=========" + numOfFruitType + "============" + currentFruitIndex );
    }
}