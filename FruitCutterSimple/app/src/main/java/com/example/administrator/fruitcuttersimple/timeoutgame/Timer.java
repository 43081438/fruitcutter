package com.example.administrator.fruitcuttersimple.timeoutgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Cheung on 2014/9/23.
 */
public class Timer {
    private long timeSet;
    private static final int regularInternal = 1000;
    private int timer = 15;

    private Paint paint;

    public Timer(){
        resetTimer();

        paint = new Paint();
        paint.setColor( Color.YELLOW );
        paint.setTextSize( 40 );

    }
    public Timer(int timer ){
        this.timer = timer;

        resetTimer();
        paint = new Paint();
        paint.setColor( Color.YELLOW );
        paint.setTextSize( 40 );
    }

    private void resetTimer(){
        timeSet = System.currentTimeMillis() + regularInternal;
    }

    public boolean isTimeUp(){
         if( System.currentTimeMillis() > timeSet ){
             resetTimer();
            timer--;

             return true;
         }
        return false;
    }

    public boolean isGameOver(){
        if ( timer<0 || timer == 0 ){
            return true;
        }
        return false;
    }

    public void draw( Canvas canvas ){

        canvas.drawText( ""+timer, 20, 30, paint );

        isTimeUp();
    }

    public void restartTimer(){
        timer = 60;
    }

    public void fastResetTimer(){
        timer = 30;
    }
}
