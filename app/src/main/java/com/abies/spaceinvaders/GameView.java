package com.abies.spaceinvaders;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX =1080f / screenX;
        screenRatioY =2400f / screenY;
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.y = screenY;
        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        background1.y -= 10 * screenRatioY;
        background2.y -= 10 * screenRatioY;
        if (background1.y + background1.backgroud.getHeight()<0){
            background1.y = screenY;
        }
        if (background2.y + background2.backgroud.getHeight()<0){
            background2.y = screenY;
        }
    }

    private void draw(){
        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.backgroud, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.backgroud, background2.x, background2.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep(){
        try {
            thread.sleep(17);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
