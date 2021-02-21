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
    public boolean wasShot = false;
    private Random random;
    int x, y, width, height, enemiesCounter = 1;
    Bitmap enemy1,enemy2,enemy3,enemy4;
    Enemies(Resources res) {
        enemy1 = BitmapFactory.decodeResource(res, R.drawable.es);
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.es);
        enemy3 = BitmapFactory.decodeResource(res, R.drawable.es);
        enemy4 = BitmapFactory.decodeResource(res, R.drawable.es);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 2;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false);
        enemy4 = Bitmap.createScaledBitmap(enemy4, width, height, false);

        random = new Random();

        x = random.nextInt(screenX - width);
        y = -height;
    }
    Bitmap getEnemy(){
        if(enemiesCounter == 1){
            enemiesCounter++;
            return enemy1;
        }
        if(enemiesCounter == 2){
            enemiesCounter++;
            return enemy2;
        }
        if(enemiesCounter == 3){
            enemiesCounter++;
            return enemy3;
        }
        enemiesCounter = 1;

        return  enemy4;
    }
    Rect getCollisonShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
