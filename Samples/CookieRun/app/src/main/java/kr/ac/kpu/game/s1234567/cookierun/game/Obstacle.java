package kr.ac.kpu.game.s1234567.cookierun.game;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;

public class Obstacle extends ImageObject {
    private static final String TAG = Obstacle.class.getSimpleName();

    public Obstacle(char ch, float x, float y) {
        dstRect.set(x, y, x, y);
        switch (ch) {
            case 'X':
                loadBitmaps(R.mipmap.epn01_tm01_jp1a);
                frameTime = 1.0f / 1.0f;
                delay = 0.0f;
                bbOffset = new Rect(-15, -45, 15, 50);
                break;
            case 'Y':
                loadBitmaps(
                        R.mipmap.transparent,
                        R.mipmap.epn01_tm01_jp2up_01,
                        R.mipmap.epn01_tm01_jp2up_02,
                        R.mipmap.epn01_tm01_jp2up_03,
                        R.mipmap.epn01_tm01_jp2up_04,
                        R.mipmap.epn01_tm01_jp2up_05
                );
                frameTime = 1.0f / 5.0f;
                delay = 1.0f;
                bbOffset = new Rect(-30, -50, 30, 111);
                break;
            case 'Z':
                loadBitmaps(
                        R.mipmap.transparent,
                        R.mipmap.epn01_tm01_jp1up_01,
                        R.mipmap.epn01_tm01_jp1up_02,
                        R.mipmap.epn01_tm01_jp1up_03,
                        R.mipmap.epn01_tm01_jp1up_04
                );
                frameTime = 1.0f / 10.0f;
                delay = 1.0f;
                bbOffset = new Rect(-30, -10, 30, 75);
                break;
        }
    }

    protected boolean hit = false;
    protected boolean loops = false;
    protected float frameTime, delay;
    protected float time;
    protected Rect bbOffset;
    protected ArrayList<Bitmap> bitmaps;
    protected int bitmapIndex;
    protected void loadBitmaps(int... resIds) {
        bitmaps = new ArrayList<>();
        for (int resId: resIds) {
            bitmaps.add(GameBitmap.load(resId));
        }
        setBitmapIndex(0);
    }

    protected void setBitmapIndex(int index) {
        bitmapIndex = index;
        bitmap = bitmaps.get(bitmapIndex);
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        dstRect.right = dstRect.left + w * GameView.MULTIPLIER;
        dstRect.top = dstRect.bottom - h * GameView.MULTIPLIER;
    }

    @Override
    public void update() {
        super.update();
        MainGame game = MainGame.get();
        if (delay > 0) {
            delay -= game.frameTime;
            return;
        }
        int count = bitmaps.size();
        if (loops || bitmapIndex < count - 1) {
            time += game.frameTime;
            if (time >= frameTime) {
                int index = (bitmapIndex + 1) % count;
                setBitmapIndex(index);
                time -= frameTime;
            }
        }

        float dx = Platform.SPEED * GameView.MULTIPLIER * game.frameTime;
        dstRect.offset(-dx, 0);
        if (getRight() < 0) {
            game.remove(this);
        }
    }

//    @Override
//    public void getBoundingRect(RectF rect) {
//        rect.set(
//                dstRect.left - bbOffset.left,
//                dstRect.top - bbOffset.top,
//                dstRect.right - bbOffset.right,
//                dstRect.bottom - bbOffset.bottom
//        );
//    }
}
