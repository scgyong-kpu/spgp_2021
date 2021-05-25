package kr.ac.kpu.game.s1234567.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.utils.CollisionHelper;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainGame game = MainGame.get();
        Player player = (Player) game.objectsAt(MainGame.Layer.player).get(0);
        ArrayList<GameObject> items = game.objectsAt(MainGame.Layer.item);
        for (GameObject item: items) {
            if (!(item instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) item)) {
                //Log.d(TAG, "Collision: " + item);
                game.remove(item);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
