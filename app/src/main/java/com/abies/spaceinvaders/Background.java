package com.abies.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x = 0, y= 0;
    Bitmap backgroud;

    Background(int screenX, int screenY, Resources res){ //create scaled bitmap for background
        backgroud = BitmapFactory.decodeResource(res,R.drawable.background);
        backgroud = Bitmap.createScaledBitmap(backgroud, screenX, screenY, false );
    }
}
