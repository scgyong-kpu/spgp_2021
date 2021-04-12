package kr.ac.kpu.game.s1234567.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s1234567.samplegame.framework.GameObject;
import kr.ac.kpu.game.s1234567.samplegame.R;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class Ball implements GameObject {
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private int frameIndex;
    private static Bitmap bitmap;

    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.fireball_128_24f);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void update() {
        MainGame game = MainGame.get();
        this.x += this.dx * game.frameTime;
        this.y += this.dy * game.frameTime;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        int frameWidth = imageHeight;
        if (x < 0 || x > w - frameWidth) {
            dx *= -1;
        }
        if (y < 0 || y > h - imageHeight) {
            dy = -dy;
        }

        frameIndex = (frameIndex + 1) % 24;
    }

    public void draw(Canvas canvas) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        int fw = w / 24;
        int fw = h;
        int ballRadius = 100;
        Rect src = new Rect(fw * frameIndex, 0, fw * frameIndex + fw, h);
        RectF dst = new RectF(x - ballRadius, y - ballRadius, x + ballRadius, y + ballRadius);
        canvas.drawBitmap(bitmap, src, dst, null);
//        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}



















