package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.AnimationGameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class Enemy implements GameObject {
    private static final float FRAMES_PER_SECOND = 8.0f;
    private final float x;
    private final GameBitmap bitmap;
    private float y;
    private final int speed;

    public Enemy(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.bitmap = new AnimationGameBitmap(R.mipmap.enemy_01, FRAMES_PER_SECOND, 0);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if (y > GameView.view.getHeight()) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
