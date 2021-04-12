package kr.ac.kpu.game.s1234567.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s1234567.samplegame.R;
import kr.ac.kpu.game.s1234567.samplegame.framework.GameObject;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class Bullet implements GameObject {
    private static int imageWidth;
    private static int imageHeight;
    private final float radius;
    private final float angle;
    private float x, y;
    private float dx, dy;

    private final long createdOn;
    private int frameIndex;
    private static Bitmap bitmap;
    private static float FRAME_RATE = 8.5f;

//    Paint paint = new Paint();

    public Bullet(float x, float y, float tx, float ty) {
        this.x = x;
        this.y = y;
        this.radius = 10.0f;

        //float speed = 1000;
        float delta_x = tx - this.x;
        float delta_y = ty - this.y;
        angle = (float) Math.atan2(delta_y, delta_x);
        float move_dist = 100;
        this.dx = (float) (move_dist * Math.cos(angle));
        this.dy = (float) (move_dist * Math.sin(angle));

//        paint.setColor(0xFFFF0000);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.laser_light);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
        createdOn = System.currentTimeMillis();
    }

    public void update() {
        MainGame game = MainGame.get();
        this.x += this.dx * game.frameTime;
        this.y += this.dy * game.frameTime;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        int frameWidth = w / 10;
        boolean toBeDeleted = false;
        if (x < 0 || x > w - frameWidth) {
            toBeDeleted = true;
        }
        if (y < 0 || y > h - imageHeight) {
            toBeDeleted = true;
        }
        if (toBeDeleted) {
            game.remove(this);
        }

        int elapsed = (int)(System.currentTimeMillis() - createdOn);
        frameIndex = Math.round(elapsed * 0.001f * FRAME_RATE) % 10;
    }

    public void draw(Canvas canvas) {
//        canvas.drawCircle(x, y, radius, paint);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int fw = w / 10;
//        int fw = h;
//        int ballRadius = 400;
        int hw = 100;
        int hh = 124;
        Rect src = new Rect(fw * frameIndex, 0, fw * frameIndex + fw, h);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);


        float degree = (float) (angle * 180 / Math.PI) + 90;
        canvas.save();
        canvas.rotate(degree, x, y);
        canvas.drawBitmap(bitmap, src, dst, null);
        canvas.restore();
    }
}
