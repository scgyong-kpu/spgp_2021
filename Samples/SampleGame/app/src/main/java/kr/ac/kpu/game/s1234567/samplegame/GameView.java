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
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private static final int BALL_COUNT = 10;

    //    private Ball b1, b2;
    Player player;
    ArrayList<Ball> balls = new ArrayList<>();

    private long lastFrame;
    public static float frameTime;
    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        GameView.view = this;
        initResources();
        startUpdating();
    }

    private void startUpdating() {
        doGameFrame();
    }

//    Handler handler = new Handler();

    private void doGameFrame() {
//        update();
        for (Ball b: balls) {
            b.update();
        }
        player.update();
//        b1.update();
//        b2.update();

//        b1.x += b1.dx * frameTime;
//        b1.y += b1.dy * frameTime;
//
//        b2.x += b2.dx * frameTime;
//        b2.y += b2.dy * frameTime;
//
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
        player = new Player(100, 100, 0, 0);
        Random rand = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            float x = rand.nextInt(1000);
            float y = rand.nextInt(1000);
            float dx = rand.nextFloat() * 1000 - 500;
            float dy = rand.nextFloat() * 1000 - 500;
            Ball b = new Ball(x, y, dx, dy);
            balls.add(b);
        }
//        b1 = new Ball(100, 100, 200, 300);
//        b2 = new Ball(1000, 100, -250, 350);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Ball b: balls) {
            b.draw(canvas);
        }
        player.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }
}













