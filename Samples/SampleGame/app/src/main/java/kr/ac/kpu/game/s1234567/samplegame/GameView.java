package kr.ac.kpu.game.s1234567.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap bitmap;

    private float x;
    private float y;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

    Handler handler = new Handler();

    private void doGameFrame() {
//        update();
        x += 1;
        y += 2;

//        draw();
        invalidate();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doGameFrame();
            }
        }, 15);
//        doGameFrame();
    }

    private void initResources() {
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        x = 100;
        y = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        Log.d(TAG, "Drawing at: " + x + "," + y);
    }
}













