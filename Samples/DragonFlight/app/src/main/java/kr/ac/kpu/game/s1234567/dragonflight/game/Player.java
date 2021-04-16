package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.framework.Sound;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class Player implements GameObject {
    private static final String TAG = Player.class.getSimpleName();
    private int imageWidth;
    private int imageHeight;
    private float x, y;
    private float dx, dy;
    private float tx, ty;
    private float speed;
    private Bitmap bitmap;
    private float angle = 0;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.bitmap = GameBitmap.load(R.mipmap.plane_240);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        //this.ty = this.y;
//        this.ty = y;
//        MainGame game = MainGame.get();
//        float move_dist = speed * game.frameTime;
//        this.dx = (float) (move_dist * Math.cos(angle));
//        this.dy = (float) (move_dist * Math.sin(angle));

//        Sound.play(R.raw.hadouken);
//        Bullet bullet = new Bullet(this.x, this.y, x, y);
//        MainGame game = MainGame.get();
//        game.add(bullet);
//        float delta_x = x - this.x;
//        float delta_y = y - this.y;
//        this.angle = (float) Math.atan2(delta_y, delta_x);
//        Log.d(TAG, "Angle = " + angle);
//        MainGame game = MainGame.get();
//        float move_dist = speed * game.frameTime;
//        this.dx = (float) (move_dist * Math.cos(angle));
//        this.dy = (float) (move_dist * Math.sin(angle));
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
//        y += dy;
//        if ((dy > 0 && y > ty) || (dy < 0 && y < ty)) {
//            y = ty;
//        }
    }

    public void draw(Canvas canvas) {
        float left = x - imageWidth / 2;
        float top = y - imageWidth / 2;
//        float degree = (float) (angle * 180 / Math.PI) + 90;
//        canvas.save();
//        canvas.rotate(degree, x, y);
        canvas.drawBitmap(bitmap, left, top, null);
//        canvas.restore();
    }
}
