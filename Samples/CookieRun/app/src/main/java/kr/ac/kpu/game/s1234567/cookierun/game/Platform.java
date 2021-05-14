package kr.ac.kpu.game.s1234567.cookierun.game;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class Platform extends ImageObject {
    public enum Type {
        T_10x2, T_2x2, T_3x1
    }
    public Platform(Type type, float x, float y) {
//        int resId = type == ?? ......;
        super(R.mipmap.cookierun_platform_480x48, x, y);
        final float UNIT = 40 * GameView.MULTIPLIER;
        float w = UNIT * 10;
        float h = UNIT * 2;
        dstRect.set(x, y, x + w, y + h);
    }
}
