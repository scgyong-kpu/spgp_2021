package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;

public class Bullet implements GameObject, BoxCollidable {
    private static final String TAG = Bullet.class.getSimpleName();
    private float x;
    private final GameBitmap bitmap;
    private float y;
    private int speed;

    private Bullet(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;

        Log.d(TAG, "loading bitmap for bullet");
        this.bitmap = new GameBitmap(R.mipmap.laser_1);
    }

    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y, int speed) {
        if (recycleBin.isEmpty()) {
            return new Bullet(x, y, speed);
        }
        Bullet bullet = recycleBin.remove(0);
        bullet.init(x, y, speed);
        return bullet;
    }

    private void init(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if (y < 0) {
            game.remove(this);
            recycle();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x, y, rect);
    }

    public void recycle() {
        recycleBin.add(this);
    }
}
