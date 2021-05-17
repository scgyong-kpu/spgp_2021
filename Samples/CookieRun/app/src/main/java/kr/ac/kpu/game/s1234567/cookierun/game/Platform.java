package kr.ac.kpu.game.s1234567.cookierun.game;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class Platform extends ImageObject {
    public static int UNIT_SIZE = 40;
    public static int SPEED = 150;

    public float getRight() {
        return dstRect.right;
    }

    public enum Type {
        T_10x2, T_2x2, T_3x1;
        float width() {
            int w = 1;
            switch (this) {
                case T_10x2: w = 10; break;
                case T_2x2: w = 2; break;
                case T_3x1: w = 3; break;
            }
            return w * UNIT_SIZE * GameView.MULTIPLIER;
        }
        float height() {
            int h = 1;
            switch (this) {
                case T_10x2: case T_2x2: h = 2; break;
                case T_3x1: h = 1; break;
            }
            return h * UNIT_SIZE * GameView.MULTIPLIER;
        }
    }
    public Platform(Type type, float x, float y) {
//        int resId = type == ?? ......;
        super(R.mipmap.cookierun_platform_480x48, x, y);
        float w = type.width();
        float h = type.height();
        dstRect.set(x, y, x + w, y + h);
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float dx = SPEED * game.frameTime;
        dstRect.offset(-dx, 0);
    }
}
