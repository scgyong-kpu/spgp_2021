package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;
import kr.ac.kpu.game.s1234567.dragonflight.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    private Player player;

    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private boolean initialized;

//    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        player = new Player(w/2, h - 300);
        objects.add(player);

        objects.add(new EnemyGenerator());

        initialized = true;
        return true;
    }

    public void update() {
        //if (!initialized) return;
        for (GameObject o : objects) {
            o.update();
        }

        for (GameObject o1 : objects) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            boolean removed = false;
            for (GameObject o2 : objects) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;

                if (CollisionHelper.collides(enemy, bullet)) {
                    //Log.d(TAG, "Collision!" + o1 + " - " + o2);
                    remove(enemy);
                    remove(bullet);
                    bullet.recycle();
                    removed = true;
                    break;
                }
            }
            if (removed) {
                continue;
            }
            if (CollisionHelper.collides(enemy, player)) {
                Log.d(TAG, "Collision: Enemy - Player");
            }
        }
    }

    public void draw(Canvas canvas) {
        //if (!initialized) return;
        for (GameObject o: objects) {
            o.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.add(gameObject);
            }
        });
//        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
//                Log.d(TAG, "<R> object count = " + objects.size());
            }
        });
    }
}
