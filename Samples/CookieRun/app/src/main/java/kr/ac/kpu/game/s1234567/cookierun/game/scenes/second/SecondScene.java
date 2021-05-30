package kr.ac.kpu.game.s1234567.cookierun.game.scenes.second;

import android.view.MotionEvent;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.Scene;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Player;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.main.MainGame;

public class SecondScene extends Scene {
    enum Layer {
        bg, player, COUNT
    }
    public static SecondScene scene;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    @Override
    public void start() {
        super.start();
        transparent = true;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        initLayers(Layer.COUNT.ordinal());

        add(Layer.bg, new ImageObject(R.mipmap.bg_city, w/2, h/2));
        add(Layer.player, new Player(w/2, h/2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            MainGame.get().popScene();
        }
        return super.onTouchEvent(e);
    }
}
