package com.abies.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.abies.spaceinvaders.GameView.screenRatioX;
import static com.abies.spaceinvaders.GameView.screenRatioY;
import static com.abies.spaceinvaders.GameView.screenX;

public class Enemies {
    public int speed = 20;
    private Random random;
    int x, y, width, height;
    Bitmap enemy;
    Enemies(Resources res) {
        enemy = BitmapFactory.decodeResource(res, R.drawable.es);

        width = enemy.getWidth();
        height = enemy.getHeight();

        width /= 2;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        enemy = Bitmap.createScaledBitmap(enemy, width, height, false);

        random = new Random();

        x = random.nextInt(screenX - width);
        y = -height;
    }
    Bitmap getEnemy(){
        return  enemy;
    }
    Rect getCollisonShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
