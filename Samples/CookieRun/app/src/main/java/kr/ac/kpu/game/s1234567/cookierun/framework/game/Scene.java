package kr.ac.kpu.game.s1234567.cookierun.framework.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.Recyclable;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class Scene {
    protected ArrayList<ArrayList<GameObject>> layers;
    public ArrayList<ArrayList<GameObject>> getLayers() { return layers; }

    public boolean isTransparent() {
        return transparent;
    }

    protected boolean transparent;

    protected void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }
    public void add(int layerIndex, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layerIndex);
                objects.add(gameObject);
            }
        });
//        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        remove(gameObject, true);
    }
    public void remove(GameObject gameObject, boolean delayed) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BaseGame game = BaseGame.get();
                for (ArrayList<GameObject> objects: layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recycle();
                            game.recycle(gameObject);
                        }
                        //Log.d(TAG, "Removed: " + gameObject);
                        break;
                    }
                }
            }
        };
        if (delayed) {
            GameView.view.post(runnable);
        } else {
            runnable.run();
        }
    }

    public ArrayList<GameObject> objectsAt(int layerIndex) {
        return layers.get(layerIndex);
    }
    public void start() {}
    public void end() {}
    public void pause() {}
    public void resume() {}
    public boolean onTouchEvent(MotionEvent e) { return false; }
}
