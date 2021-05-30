package kr.ac.kpu.game.s1234567.cookierun.game.objs;

import android.graphics.Canvas;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.IndexedGameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.ctrl.StageMap;

public class Jelly extends ImageObject {
    private static final String TAG = Jelly.class.getSimpleName();
    public static final int JELLY_WIDTH = 66;
    public static final int JELLY_HEIGHT = 66;
    public static final int JELLIES_IN_A_ROW = 30;
    public static final int BORDER_WIDTH = 2;
    public static final int SPACING_WIDTH = 2;
    private final IndexedGameBitmap ibmp;

    public Jelly(int index, float x, float y) {
        ibmp = new IndexedGameBitmap(R.mipmap.jelly, JELLY_WIDTH, JELLY_HEIGHT, JELLIES_IN_A_ROW, BORDER_WIDTH, SPACING_WIDTH);
        ibmp.setIndex(index);
        float l = x;
        float t = y;
        float r = x + StageMap.UNIT_SIZE * GameView.MULTIPLIER;
        float b = y + StageMap.UNIT_SIZE * GameView.MULTIPLIER;
        dstRect.set(l, t, r, b);
        //Log.d(TAG, "index = " + index + " Rect = " + dstRect);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float dx = Platform.SPEED * GameView.MULTIPLIER * game.frameTime;
        dstRect.offset(-dx, 0);
        if (getRight() < 0) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        ibmp.draw(canvas, dstRect);
    }
}
