package kr.ac.kpu.game.s1234567.cookierun.game.main;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.Scene;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.HorizontalScrollBackground;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.ctrl.CollisionChecker;
import kr.ac.kpu.game.s1234567.cookierun.game.ctrl.StageMap;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Platform;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Player;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Score;

public class MainScene extends Scene {
    private Player player;
    private Score score;
    public enum Layer {
        bg, platform, item, obstacle, player, ui, controller, LAYER_COUNT
    }

    public static MainScene scene;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public void start() {
        scene = this;
        super.start();
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        add(Layer.controller, new StageMap("stage_01.txt"));

        player = new Player(Platform.Type.T_2x2.width(), h / 2);
        add(Layer.player, player);

        add(Layer.controller, new CollisionChecker(player));

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -10));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -30));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
        if (action == MotionEvent.ACTION_DOWN) {
//            player.moveTo(event.getX(), event.getY());
            if (event.getX() < GameView.view.getWidth() / 2) {
                player.jump();
            } else {
                player.startSliding();
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            player.endSliding();
        }
        return false;
    }
}
