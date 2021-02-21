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
    int x, y, width, height, shootCounter = 1;
    Bitmap rocket, explosion;
    private GameView gameView;
    Rocket (GameView gameView, int screenX, int screenY, Resources res){

        this.gameView = gameView;

        rocket = BitmapFactory.decodeResource(res, R.drawable.ps);

        width = rocket.getWidth();
        height = rocket.getHeight();

        width /= 2;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        rocket = Bitmap.createScaledBitmap(rocket, width, height, false);
        rocket = Bitmap.createScaledBitmap(rocket, width, height, false);

        y = (int) screenRatioY * (screenY - rocket.getHeight() - 50);
        x = (screenX /2) - (rocket.getWidth()/2);

        explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
        explosion = Bitmap.createScaledBitmap(explosion, width, height, false);
    }
    Bitmap getRocket(){
        //get rocket image and creating bullets
        if (shoot!=0){

            if(shootCounter >=1 && shootCounter<=4){
                shootCounter++;
                return rocket;
            }
            shootCounter = 1;
            shoot--;
            gameView.newBullet();
            return rocket;
        }
        return rocket;
    }
    Rect getCollisonShape(){
        //get Rectangle to check intersections
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getExplosion(){
        //get explosion image when rocket have collision with enemies
        return explosion;
    }
}
