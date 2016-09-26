package com.example.administrator.fruitcuttersimple.normalgamefruitcutted;

import android.graphics.Point;

import java.util.Random;

/**
 * 类描述：生成随机水果位置工具类
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 14:12
 * 版本：
 */
public class RandomGenerator {

    //private final static boolean LEFT = true;
    //private final static boolean RIGHT = false;

    private static int numOfFruitTypes;
    private static boolean left;

    //随机产生水果编号
    public int getFruitIndex(){
        return new Random().nextInt( numOfFruitTypes );
    }

    //随机产生左右
    public boolean isLeft(){
        return new Random().nextBoolean();
    }

    //随机产生抛出位置坐标
    public Point getPos(){
        if( left ){
            int x = -20 + new Random().nextInt(21);
            int y = 790 + new Random().nextInt(21);

            return new Point( x, y );
        }else {
            int x = 1070 + new Random().nextInt(200);
            int y = 790 + new Random().nextInt(21);

            return new Point( x, y );
        }
    }
    //随机产生抛出速度
    public Point getVelocity(){
        if( left ){
            int x = 5 + new Random().nextInt(11);
            int y = -50 - new Random().nextInt(21);

            return new Point( x, y );
        }else {
            int x = -5 - new Random().nextInt(11);
            int y = -40 - new Random().nextInt(21);

            return new Point( x, y );
        }
    }

    //随机生成时间间隔
    public int getTimeInternal(){
        return 200 + 100*new Random().nextInt(8);
    }
    //随机生成短时间时间间隔
    public int getShortInternal(){
        return 50 + 100*new Random().nextInt(3);
    }

    public RandomGenerator( int numOfFruitTypes ){
        this.numOfFruitTypes = numOfFruitTypes;

    }

    //随机生成切水果声音
    public int getCutSoundInt(){
        return new Random().nextInt(6);
    }

    //随机生成炸弹时间
    public int bombInternal(){
        return new Random().nextInt(5)*1000 + 3000;
    }

    //
    public int fastBombInternal(){
        return new Random().nextInt(2)*500 + 500;
    }


    public RandomGenerator(){
        left = isLeft();
    }
}

