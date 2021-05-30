package kr.ac.kpu.game.s1234567.cookierun.game.main;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.HorizontalScrollBackground;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.ctrl.CollisionChecker;
import kr.ac.kpu.game.s1234567.cookierun.game.ctrl.StageMap;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Platform;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Player;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Score;

public class MainGame extends BaseGame {
    private boolean initialized;

    public static MainGame get() {
        return (MainGame) instance;
    }


    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        push(new MainScene());

        initialized = true;
        return true;

    }

}
