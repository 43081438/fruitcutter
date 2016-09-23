package com.example.administrator.fruitcuttersimple.fruitcutter;

/**
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 14:49
 * 版本：
 */
public class RandomTimer {
    private long timeSet;
    private int internal;
    private RandomGenerator random;
    private long randomDuration;
    private long duration = 500;
    private boolean currentTimneGeted = false;
    private int fastInternal;
    private long fastTimeSet;

    public RandomTimer(){
        random = new RandomGenerator();

        timeSet = 0;
        internal = 0;

        randomDuration = random.bombInternal() + System.currentTimeMillis();
    }

    private void resetTimer(){
        internal = random.getTimeInternal();
        timeSet = internal + System.currentTimeMillis();
    }

    public boolean isTimeUp(){
        if( System.currentTimeMillis() > timeSet ){
            resetTimer();

            return true;
        }
        return false;
    }

    public boolean fastTimeUp(){
        if( System.currentTimeMillis() > fastTimeSet ){
            fastResetTimer();
            return true;
        }
        return false;
    }

    public void fastResetTimer(){
        fastInternal = random.getShortInternal();
        fastTimeSet = fastInternal + System.currentTimeMillis();
    }

    public boolean isBombThrown(){

        if( System.currentTimeMillis()> randomDuration){
            randomDuration = random.bombInternal() + System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public boolean fastBombThrown(){

        if( System.currentTimeMillis()> randomDuration){
            randomDuration = random.fastBombInternal() + System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public boolean isBombTimeUp(){
        if( !currentTimneGeted ){
            setDuration();;
            currentTimneGeted = true;
        }
        if( System.currentTimeMillis()<duration ){
            return true;
        } else {
            return false;
        }
    }

    private void setDuration(){
        duration = System.currentTimeMillis() + duration;
    }
}
