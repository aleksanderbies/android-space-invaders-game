package com.abies.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


import static com.abies.spaceinvaders.GameView.screenRatioX;
import static com.abies.spaceinvaders.GameView.screenRatioY;

public class Enemies {
    public int speed = 20;
    int x, y, width, height;
    Bitmap enemy;

    Enemies(Resources res) {
        enemy = BitmapFactory.decodeResource(res, R.drawable.es);

        width = enemy.getWidth();
        height = enemy.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        enemy = Bitmap.createScaledBitmap(enemy, width, height, false);

        y = -height;
    }
    Bitmap getBird(){
        return enemy;
    }
    Rect getCollisonShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
