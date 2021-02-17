package com.abies.spaceinvaders;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    Rocket rocket;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioY = 2400f/screenY;
        screenRatioX = 1080f/screenX;
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        rocket = new Rocket(screenX,screenY, getResources());

        background2.y = -1* (background2.backgroud.getHeight());
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
        background1.y += 10 * screenRatioX;
        background2.y += 10 * screenRatioX;
        if (background1.y > background1.backgroud.getHeight()){
            background1.y = 0 - background1.backgroud.getHeight();
        }
        if (background2.y > background2.backgroud.getHeight()){
            background2.y = 0 - background2.backgroud.getHeight();
        }
        if (rocket.isGoingLeft){
            rocket.x -=15 * screenRatioX;
        }else{
            rocket.x +=15 * screenRatioX;
        }
        if(rocket.x < 0){
            rocket.x = 0;
        }
        if (rocket.x > screenX - rocket.width){
            rocket.x = screenX - rocket.width;
        }
    }

    private void draw(){
        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.backgroud, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.backgroud, background2.x, background2.y, paint);

            canvas.drawBitmap(rocket.getRocket(), rocket.x, rocket.y, paint);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX/2){
                    rocket.isGoingLeft = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                rocket.isGoingLeft = false;
                break;
        }


        return true;
    }
}
