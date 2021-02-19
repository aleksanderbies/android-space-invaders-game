package com.abies.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.abies.spaceinvaders.GameView.screenRatioX;
import static com.abies.spaceinvaders.GameView.screenRatioY;

public class Rocket {
    boolean isGoingLeft = false;
    int shoot = 0;
    int x, y, width, height, flyController = 0, shootCounter = 1;
    Bitmap rocket, bullet1, bullet2, bullet3, bullet4, bullet5, explosion;
    private GameView gameView;
    Rocket (GameView gameView, int screenX, int screenY, Resources res){

        this.gameView = gameView;

        rocket = BitmapFactory.decodeResource(res, R.drawable.ps);

        width = rocket.getWidth();
        height = rocket.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        rocket = Bitmap.createScaledBitmap(rocket, width, height, false);
        rocket = Bitmap.createScaledBitmap(rocket, width, height, false);

        y = (int) screenRatioY * (screenY - rocket.getHeight() - 50);
        x = (screenX /2) - (rocket.getWidth()/2);

        bullet1 = BitmapFactory.decodeResource(res, R.drawable.ps);
        bullet2 = BitmapFactory.decodeResource(res, R.drawable.ps);
        bullet3 = BitmapFactory.decodeResource(res, R.drawable.ps);
        bullet4 = BitmapFactory.decodeResource(res, R.drawable.ps);
        bullet5 = BitmapFactory.decodeResource(res, R.drawable.ps);

        bullet1 = Bitmap.createScaledBitmap(bullet1, width, height, false);
        bullet2 = Bitmap.createScaledBitmap(bullet2, width, height, false);
        bullet3 = Bitmap.createScaledBitmap(bullet3, width, height, false);
        bullet4 = Bitmap.createScaledBitmap(bullet4, width, height, false);
        bullet5 = Bitmap.createScaledBitmap(bullet5, width, height, false);

        explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
    }
    Bitmap getRocket(){
        if (shoot!=0){
            if(shootCounter == 1){
                shootCounter++;
                return bullet1;
            }
            if(shootCounter == 2){
                shootCounter++;
                return bullet2;
            }
            if(shootCounter == 3){
                shootCounter++;
                return bullet3;
            }
            if(shootCounter == 4){
                shootCounter++;
                return bullet4;
            }
            shootCounter = 1;
            shoot--;
            gameView.newBullet();
            return bullet5;
        }
        return rocket;
    }
    Rect getCollisonShape(){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getExplosion(){
        return explosion;
    }
}
