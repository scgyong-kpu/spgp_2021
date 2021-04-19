package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.framework.Sound;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class Player implements GameObject {
    private static final String TAG = Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private int imageWidth;
    private int imageHeight;
    private float x, y;
    private float tx, ty;
    private float speed;
    private Bitmap bitmap;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.bitmap = GameBitmap.load(R.mipmap.fighter);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        //this.ty = this.y;
        Bullet bullet = new Bullet(this.x, this.y, BULLET_SPEED);
        MainGame game = MainGame.get();
        game.add(bullet);
    }

    public void update() {
        MainGame game = MainGame.get();
        float dx = speed * game.frameTime;
        if (tx < x) { // move left
            dx = -dx;
        }
        x += dx;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx;
        }
    }

    public void draw(Canvas canvas) {
        float sr = x - imageWidth / 2;
        float st = y - imageHeight / 2;

        int hw = imageWidth / 2;
        int hh = imageHeight / 2;
        //Rect srcRect = new Rect(left, )
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        RectF dstRect = new RectF(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
