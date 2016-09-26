package com.example.administrator.fruitcuttersimple.timeoutgame;

import android.graphics.Bitmap;

import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.RandomGenerator;

/**
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 14:12
 * 版本：
 */
public class BitmapGroup {

    //水果Bitmap数组
    private Bitmap[] fruitBitmap;
    //切后水果Bitmap数组
    private Bitmap[] fruitAfterCutBitmap;
    //水果种类数量
    private int numOfFruitTypes;
    //随机生成器
    private RandomGenerator randomGen;
    //生成的随机数
    private int index;


    public Bitmap getFruitBitmap() {
        index = randomGen.getFruitIndex();
        return fruitBitmap[ index ];
    }

    public Bitmap getFruitBitmapOne() {
        return fruitAfterCutBitmap[ index*2 ];
    }

    public Bitmap getFruitBitmapTwo() {
        return fruitAfterCutBitmap[ index*2+1 ];
    }


    public BitmapGroup(Bitmap[] fruitBitmap, Bitmap[] fruitAfterCutBitmap) {

        this.fruitBitmap = fruitBitmap;
        this.fruitAfterCutBitmap = fruitAfterCutBitmap;

        numOfFruitTypes = fruitBitmap.length;

        randomGen = new RandomGenerator( numOfFruitTypes );

        System.out.println( fruitAfterCutBitmap.length );
    }
}