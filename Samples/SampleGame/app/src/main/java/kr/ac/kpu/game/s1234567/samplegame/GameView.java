package kr.ac.kpu.game.s1234567.samplegame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap bitmap;

    private float x;
    private float y;
    private float x2;
    private float y2;
    private long lastFrame;
    private float frameTime;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

//    Handler handler = new Handler();

    private void doGameFrame() {
//        update();
        x += 100 * frameTime;
        y += 200 * frameTime;

        x2 += -50 * frameTime;
        y2 += 150 * frameTime;

//        draw();
        invalidate();

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long time) {
                if (lastFrame == 0) {
                    lastFrame = time;
                }
                frameTime = (float) (time - lastFrame) / 1_000_000_000;
                doGameFrame();
                lastFrame = time;
            }
        });
    }

    private void initResources() {
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        x = 100;
        y = 100;
        x2 = 1000;
        y2 = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        canvas.drawBitmap(bitmap, x2, y2, null);
        Log.d(TAG, "Drawing at: " + x + "," + y + " ft=" + frameTime);
    }
}













