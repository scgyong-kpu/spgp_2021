package kr.ac.kpu.game.s1234567.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player {
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private static Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
//        this.x += this.dx * GameView.frameTime;
//        this.y += this.dy * GameView.frameTime;
//        int w = GameView.view.getWidth();
//        int h = GameView.view.getHeight();
//        if (x < 0 || x > w - imageWidth) {
//            dx *= -1;
//        }
//        if (y < 0 || y > h - imageHeight) {
//            dy = -dy;
//        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}
