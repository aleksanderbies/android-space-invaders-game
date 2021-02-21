package com.abies.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private Background background1, background2;
    public static int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Enemies [] enemies;
    private SharedPreferences preferences;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private GameActivity activity;
    Rocket rocket;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        preferences = activity.getSharedPreferences("game",Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            AudioAttributes audioAttributes= new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();


        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        }

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioY = 2400f/screenY;
        screenRatioX = 1080f/screenX;
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        rocket = new Rocket(this, screenX,screenY, getResources());

        bullets = new ArrayList<>();

        background2.y = -1* (background2.backgroud.getHeight());

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        enemies = new Enemies[4];

        for (int i = 0; i < 4; i++){
            Enemies enemy = new Enemies(getResources());
            enemies[i] = enemy;
        }
        for (int i = 0; i < 4; i++){
            if (i==0){
                enemies[i].y = -enemies[i].height;
            }else if(i>=1 && i<4){
                enemies[i].y = enemies[i-1].y - enemies[i].height;
            }
        }
        random = new Random();
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
        List<Bullet> trash = new ArrayList<>();
        for (Bullet bullet: bullets) {
            if (bullet.y < 0){
                trash.add(bullet);
            }
            bullet.y -= 50 * screenRatioY;

            for (Enemies enemy: enemies) {
                if (Rect.intersects(enemy.getCollisonShape(), bullet.getCollisonShape())){
                    enemy.y = -200-enemy.height;
                    bullet.y = -500;
                    score++;
                    enemy.x = random.nextInt(screenX- enemy.width);
                }
            }
        }
        for (Bullet bullet: trash) {
            bullets.remove(bullet);
        }
        for (int i = 0; i<4; i++){
            for (int j = i+1; j<4; j++){
                if(Rect.intersects(enemies[i].getCollisonShape(), enemies[j].getCollisonShape())){
                enemies[i].x = random.nextInt(screenX- enemies[i].width);
                }
            }
        }
        for (Enemies enemy : enemies){

            enemy.y += enemy.speed;

            if (enemy.y>screenY){
                enemy.y = -200-enemy.height;
                enemy.x = random.nextInt(screenX- enemy.width);
                score -=2;
            }

            if (enemy.x + enemy.width < 0){

                int bound = (int) (30 * screenRatioY);
                enemy.speed = random.nextInt(bound);
                if (enemy.speed < 10 * screenRatioY){
                    enemy.speed = (int) (10 * screenRatioY);
                }
                enemy.y = -200-enemy.height;
                enemy.x = random.nextInt(screenX- enemy.width);
            }
            if (Rect.intersects(enemy.getCollisonShape(),rocket.getCollisonShape())){
                isGameOver = true;
                return;
            }
        }
    }

    private void draw(){
        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.backgroud, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.backgroud, background2.x, background2.y, paint);

            for (Enemies enemy : enemies){
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);
            }

            canvas.drawText(String.valueOf(score), 890 * screenRatioX, 150 * screenRatioY, paint );

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(rocket.getExplosion(), rocket.x, rocket.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                score = 0;
                waitBeforeExit();
                return;
            }

            for (Enemies enemy : enemies){
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);
            }

            for (Bullet bullet: bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            canvas.drawBitmap(rocket.getRocket(), rocket.x, rocket.y, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExit() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {

        if (preferences.getInt("highscore", 0)<score) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("highscore",score);
            editor.apply();
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
                if(event.getX() > screenX/2){
                    rocket.shoot++;
                }
                break;
        }


        return true;
    }

    public void newBullet() {

        if (!preferences.getBoolean("isMute", false)){
            soundPool.play(sound, 1, 1, 0, 0, 1 );
        }

        Bullet bullet = new Bullet(getResources());
        bullet.x = rocket.x + (rocket.width/2)- (bullet.width/2);
        bullet.y = rocket.y;
        bullets.add(bullet);
    }
}
