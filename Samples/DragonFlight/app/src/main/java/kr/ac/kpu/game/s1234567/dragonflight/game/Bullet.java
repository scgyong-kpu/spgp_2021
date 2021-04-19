package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;

public class Bullet implements GameObject {
    private final float x;
    private final GameBitmap bitmap;
    private float y;
    private final int speed;

    public Bullet(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.bitmap = new GameBitmap(R.mipmap.laser_1);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y -= speed * game.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
