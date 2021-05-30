package kr.ac.kpu.game.s1234567.cookierun.game.ctrl;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.utils.CollisionHelper;
import kr.ac.kpu.game.s1234567.cookierun.game.main.MainGame;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Player;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
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