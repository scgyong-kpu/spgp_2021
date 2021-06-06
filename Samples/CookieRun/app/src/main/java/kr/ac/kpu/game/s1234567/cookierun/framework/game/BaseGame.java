package kr.ac.kpu.game.s1234567.cookierun.framework.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.game.s1234567.cookierun.BuildConfig;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class BaseGame {
    private static final String TAG = BaseGame.class.getSimpleName();
    // singleton
    protected static BaseGame instance;
    private RectF collisionRect;
    private Paint collisionPaint;

    public static BaseGame get() {
        return instance;
    }
    public float frameTime;

    protected BaseGame() {
        instance = this;

        if (BuildConfig.showsCollisionBox) {
            collisionRect = new RectF();
            collisionPaint = new Paint();
            collisionPaint.setStyle(Paint.Style.STROKE);
            collisionPaint.setColor(Color.RED);
        }
    }
//    Player player;
    //ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

    ArrayList<Scene> sceneStack = new ArrayList<>();
    public Scene getTopScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex < 0) return null;
        return sceneStack.get(lastIndex);
    }
    public void start(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in start): " + top);
            top.end();
            sceneStack.set(lastIndex, scene);
        } else {
            sceneStack.add(scene);
        }
        Log.d(TAG, "Starting(in start): " + scene);
        scene.start();
    }
    public void push(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Pausing: " + top);
            top.pause();
        }
        sceneStack.add(scene);
        Log.d(TAG, "Starting(in push): " + scene);
        scene.start();
    }
    public void popScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in pop): " + top);
            top.end();
        }
        lastIndex--;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Resuming: " + top);
            top.resume();
        } else {
            Log.e(TAG, "should end app in popScene()");
            GameView.view.finishActivity();
        }
    }
    public void recycle(GameObject object) {
        Class clazz = object.getClass();
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null) {
            array = new ArrayList<>();
            recycleBin.put(clazz, array);
        }
        array.add(object);
    }
    public GameObject get(Class clazz) {
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null || array.isEmpty()) return null;
        return array.remove(0);
    }

    public boolean initResources() {
        // prints this is error
        return false;
    }

    public void update() {
        if (sceneStack.size() == 0) {
            return;
        }
        ArrayList<ArrayList<GameObject>> layers = getTopScene().getLayers();
        //if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        if (sceneStack.size() == 0) {
            return;
        }
        draw(canvas, sceneStack.size() - 1);
    }
    protected void draw(Canvas canvas, int index) {
        Scene scene = sceneStack.get(index);
        if (scene.isTransparent() && index > 0) {
            draw(canvas, index - 1);
        }
        ArrayList<ArrayList<GameObject>> layers = scene.getLayers();
        //if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
        if (BuildConfig.showsCollisionBox) {
            for (ArrayList<GameObject> objects: layers) {
                for (GameObject o : objects) {
                    if (!(o instanceof BoxCollidable)) {
                        continue;
                    }
                    ((BoxCollidable) o).getBoundingRect(collisionRect);
                    canvas.drawRect(collisionRect, collisionPaint);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return getTopScene().onTouchEvent(event);
    }

    public boolean handleBackKey() {
        Scene scene = getTopScene();
        if (scene.handleBackKey()) {
            return true;
        }
        popScene();
        return true;
    }
}
