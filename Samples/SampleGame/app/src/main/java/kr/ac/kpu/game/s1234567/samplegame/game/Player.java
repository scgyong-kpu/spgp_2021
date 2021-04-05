package kr.ac.kpu.game.s1234567.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.game.s1234567.samplegame.R;
import kr.ac.kpu.game.s1234567.samplegame.framework.GameObject;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class Player implements GameObject {
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private float tx, ty;
    private float speed;
    private static Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = 0;
        this.ty = 0;
        this.speed = 800;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        this.ty = y;
    }

    public void update() {
        MainGame game = MainGame.get();
        float delta_x = tx - x;
        float delta_y = ty - y;
        float distance = (float) Math.sqrt(delta_x * delta_x + delta_y * delta_y);
        float move_dist = speed * game.frameTime;
        if (distance < move_dist) {
            x = tx;
            y = ty;
        } else {
            float angle = (float) Math.atan2(delta_y, delta_x);
            float mx = (float) (move_dist * Math.cos(angle));
            float my = (float) (move_dist * Math.sin(angle));
            x += mx;
            y += my;
        }
    }

    public void draw(Canvas canvas) {
        float left = x - imageWidth / 2;
        float top = y - imageWidth / 2;
        canvas.drawBitmap(bitmap, left, top, null);
    }
}
