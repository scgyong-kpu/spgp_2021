package kr.ac.kpu.game.s1234567.cookierun.game.objs;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.main.MainGame;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final float GRAVITY = 2500;
    private static final float JUMP_POWER = 1200;
    private final IndexedAnimationGameBitmap charBitmap;
    private float x, y;
    private float vertSpeed;
    private int[] ANIM_INDICES_RUNNING = { 100, 101, 102, 103 };
    private int[] ANIM_INDICES_JUMP = { 7, 8 };
    private int[] ANIM_INDICES_DOUBLE_JUMP = { 1, 2, 3, 4 };
    private int[] ANIM_INDICES_FALLING = { 0 };
    private int[] ANIM_INDICES_SLIDE = { 9, 10 };
    private Rect COL_BOX_OFFSETS_RUNNING = new Rect(-60, 0, 60, 135);
    private Rect COL_BOX_OFFSETS_JUMP = new Rect(-60,20,60,135);
    private Rect COL_BOX_OFFSETS_DOUBLE_JUMP = new Rect(-60,-10,60,135);
    private Rect COL_BOX_OFFSETS_FALLING = new Rect(-60,20,60,135);
    private Rect COL_BOX_OFFSETS_SLIDE = new Rect(-80,68,80,135);
    private Rect collisionOffsetRect = COL_BOX_OFFSETS_RUNNING;

    private enum State {
        running, jump, doubleJump, falling, slide, hit
    }

    public void setState(State state) {
        this.state = state;
        Log.d(TAG, "state = " + state);
        int[] indices = ANIM_INDICES_RUNNING;
        switch (state) {
            case running:
                indices = ANIM_INDICES_RUNNING;
                collisionOffsetRect = COL_BOX_OFFSETS_RUNNING;
                break;
            case jump:
                indices = ANIM_INDICES_JUMP;
                collisionOffsetRect = COL_BOX_OFFSETS_JUMP;
                break;
            case doubleJump:
                indices = ANIM_INDICES_DOUBLE_JUMP;
                collisionOffsetRect = COL_BOX_OFFSETS_DOUBLE_JUMP;
                break;
            case falling:
                indices = ANIM_INDICES_FALLING;
                collisionOffsetRect = COL_BOX_OFFSETS_FALLING;
                break;
            case slide:
                indices = ANIM_INDICES_SLIDE;
                collisionOffsetRect = COL_BOX_OFFSETS_SLIDE;
                break;
        }
        charBitmap.setIndices(indices);
    }

    private State state = State.falling;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.charBitmap = new IndexedAnimationGameBitmap(
                R.mipmap.cookie, 270, 270, 2, 2, 10.f);
        setState(State.running);
    }

    public void update() {
        BaseGame game = BaseGame.get();
        float foot = y + collisionOffsetRect.bottom * GameView.MULTIPLIER;
        if (state == State.jump || state == State.doubleJump || state == State.falling) {
            float dy = vertSpeed * game.frameTime;
            float platformTop = findNearestPlatformTop();
            if (vertSpeed >= 0) {
                if (foot + dy >= platformTop) {
                    dy = platformTop - foot;
                    setState(State.running);
                }
            }
            this.y = y + dy;
            vertSpeed += GRAVITY * game.frameTime;
        } else if (state == State.running || state == State.slide) {
            float platformTop = findNearestPlatformTop();
            if (foot < platformTop) {
                setState(State.falling);
                vertSpeed = 0;
                //this.y += 0.01;
            }
        }
    }

    private float findNearestPlatformTop() {
        float foot = y + collisionOffsetRect.bottom * GameView.MULTIPLIER;
        MainGame game = (MainGame)BaseGame.get();
        ArrayList<GameObject> platforms = game.objectsAt(MainGame.Layer.platform);
        float top = GameView.view.getHeight();
        for (GameObject obj: platforms) {
            Platform platform = (Platform) obj;
            RectF rect = platform.getBoundingRect();
            if (rect.left > x || x > rect.right) {
                continue;
            }
            if (rect.top < foot) {
                continue;
            }
            if (top > rect.top) {
                top = rect.top;
            }
        }
        //Log.d(TAG, "foot="+foot+" top="+top);
        return top;
    }

    public void draw(Canvas canvas) {
        charBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        float mult = GameView.MULTIPLIER;
        rect.set(
                x + collisionOffsetRect.left * mult,
                y + collisionOffsetRect.top * mult,
                x + collisionOffsetRect.right * mult,
                y + collisionOffsetRect.bottom * mult
        );
        //planeBitmap.getBoundingRect(x, y, rect);
    }

    public void jump() {
        //if (state != State.running && state != State.jump && state != State.slide) {
        if (state == State.running || state == State.slide) {
            setState(State.jump);
            vertSpeed = -JUMP_POWER;
        } else if (state == State.jump) {
            setState(State.doubleJump);
            vertSpeed = -JUMP_POWER;
        } else {
            Log.d(TAG, "Not in a state that can jump: " + state);
            return;
        }
    }

    public void startSliding() {
        if (state != State.running) {
            return;
        }
        setState(State.slide);
    }
    public void endSliding() {
        if (state != State.slide) {
            return;
        }
        setState(State.running);
    }
}
