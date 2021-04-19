package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class Bullet implements GameObject {
    private final float x;
    private final Bitmap bitmap;
    private final int halfWidth;
    private final int halfHeight;
    private float y;
    private final int speed;

    public Bullet(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.bitmap = GameBitmap.load(R.mipmap.laser_1);
        halfWidth = bitmap.getWidth() / 2;
        halfHeight = bitmap.getHeight() / 2;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y -= speed * game.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
//        float left = x - halfWidth;
//        float top = y - halfHeight;
        int hw = halfWidth;
        int hh = halfHeight;
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        RectF dstRect = new RectF(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
//        canvas.drawBitmap(bitmap, left, top, null);
    }
}
