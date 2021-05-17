package kr.ac.kpu.game.s1234567.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.IndexedGameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;

public class Jelly extends ImageObject {
    private static final String TAG = Jelly.class.getSimpleName();
    private final IndexedGameBitmap ibmp;

    public Jelly(int index, float x, float y) {
        super(R.mipmap.jelly, x, y);
        ibmp = new IndexedGameBitmap(R.mipmap.jelly, 66, 66, 30, 2, 2);
        ibmp.setIndex(index);
        Log.d(TAG, "index = " + index + " Rect = " + dstRect);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float dx = Platform.SPEED * game.frameTime;
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
