package kr.ac.kpu.game.s1234567.cookierun.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s1234567.cookierun.R;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.bitmap.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private static final float GRAVITY = 2500;
    private static final float JUMP_POWER = 1200;
    private final IndexedAnimationGameBitmap charBitmap;
    private final float ground_y;
    private float fireTime;
    private float x, y;
    private float tx, ty;
    private float vertSpeed;
    private float speed;

    private enum State {
        running, jump, doubleJump, slide, hit
    }
    private State state = State.running;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.ground_y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        this.charBitmap = new IndexedAnimationGameBitmap(R.mipmap.cookie, 4.5f, 0);
        this.charBitmap.setIndices(100, 101, 102, 103);
//        this.planeBitmap = new GameBitmap(R.mipmap.fighter);
//        this.fireBitmap = new GameBitmap(R.mipmap.laser_0);
        this.fireTime = 0.0f;
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        //this.ty = this.y;
    }

    public void update() {
        BaseGame game = BaseGame.get();
        if (state == State.jump) {
            float y = this.y + vertSpeed * game.frameTime;
//            charBitmap.move(0, y - this.y);
            vertSpeed += GRAVITY * game.frameTime;
            if (y >= ground_y) {
                y = ground_y;
                state = State.running;
                this.charBitmap.setIndices(100, 101, 102, 103);
            }
            this.y = y;
        }
    }

    public void draw(Canvas canvas) {
        charBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        //planeBitmap.getBoundingRect(x, y, rect);
    }

    public void jump() {
        //if (state != State.running && state != State.jump && state != State.slide) {
        if (state != State.running) {
            Log.d(TAG, "Not in a state that can jump: " + state);
            return;
        }
        state = State.jump;
        charBitmap.setIndices(7, 8);
        vertSpeed = -JUMP_POWER;
    }
}
