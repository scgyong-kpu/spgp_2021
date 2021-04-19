package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float time;
    private float spawnInterval;

    public EnemyGenerator() {
        time = 0.0f;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        time += game.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        Log.d(TAG, "Generate now !!");
        Enemy enemy = new Enemy(500, 0, 700);
        MainGame game = MainGame.get();
        game.add(enemy);
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}
