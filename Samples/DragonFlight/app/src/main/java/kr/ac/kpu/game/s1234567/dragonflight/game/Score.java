package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s1234567.dragonflight.R;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

    public Score(int right, int top) {
        bitmap = GameBitmap.load(R.mipmap.number_24x32);
        this.right = right;
        this.top = top;
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.score;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right;
        int dw = (int) (nw * GameView.MULTIPLIER);
        int dh = (int) (nh * GameView.MULTIPLIER);
        Rect src = new Rect();
        RectF dst = new RectF();
        while (value > 0) {
            int digit = value % 10;
            src.set(digit * nw, 0, (digit + 1) * nw, nh);
            x -= dw;
            dst.set(x, top, x + dw, top + dh);
            canvas.drawBitmap(bitmap, src, dst, null);
            value /= 10;
        }
    }
}
