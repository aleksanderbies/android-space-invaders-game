package com.abies.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.abies.spaceinvaders.GameView.screenRatioX;
import static com.abies.spaceinvaders.GameView.screenRatioY;

public class Rocket {
    public boolean isGoingLeft = false;
    int x, y, width, height;
    Bitmap rocket;

    Rocket (int screenX, int screenY, Resources res){
        rocket = BitmapFactory.decodeResource(res, R.drawable.ps);

        width = rocket.getWidth();
        height = rocket.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        rocket = Bitmap.createScaledBitmap(rocket, width, height, false);

        y = (int) screenRatioY * (screenY - rocket.getHeight() - 50);
        x = (screenX /2) - (rocket.getWidth()/2);
    }
    Bitmap getRocket(){
        return rocket;
    }
}
