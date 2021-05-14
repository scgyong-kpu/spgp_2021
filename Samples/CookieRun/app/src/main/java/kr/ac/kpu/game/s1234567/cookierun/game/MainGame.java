package kr.ac.kpu.game.s1234567.cookierun.game;

import android.view.MotionEvent;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.HorizontalScrollBackground;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class MainGame extends BaseGame {
    private boolean initialized;
    private Player player;
    private Score score;

    public enum Layer {
        bg1, enemy, bullet, player, bg2, ui, controller, ENEMY_COUNT
    }

    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }

    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.ENEMY_COUNT.ordinal());

        player = new Player(w/2, h - 300);
        //layers.get(Layer.player.ordinal()).add(player);
        add(Layer.player, player);
//        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        add(Layer.bg1, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -10));
        add(Layer.bg1, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg1, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -30));
//
//        VerticalScrollBackground clouds = new VerticalScrollBackground(R.mipmap.clouds, 20);
//        add(Layer.bg2, clouds);

        initialized = true;
        return true;

    }

    @Override
    public void update() {
        super.update();

        // collision check
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
//            int li = 0;
//            for (ArrayList<GameObject> objects: layers) {
//                for (GameObject o : objects) {
//                    Log.d(TAG, "L:" + li + " " + o);
//                }
//                li++;
//            }
            return true;
        }
        return false;
    }
}
