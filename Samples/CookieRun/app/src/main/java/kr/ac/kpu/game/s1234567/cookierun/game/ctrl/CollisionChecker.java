package kr.ac.kpu.game.s1234567.cookierun.game.ctrl;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.utils.CollisionHelper;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Jelly;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.main.MainGame;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.main.MainScene;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Player;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.second.SecondScene;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    int count;
    @Override
    public void update() {
        MainGame game = MainGame.get();
        ArrayList<GameObject> items = MainScene.scene.objectsAt(MainScene.Layer.item);
        for (GameObject item: items) {
            if (!(item instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) item)) {
                //Log.d(TAG, "Collision: " + item);
                MainScene.scene.remove(item);
                if (++count == 10) {
                    count = 0;
                    game.push(new SecondScene());
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
