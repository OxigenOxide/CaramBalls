package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.DownGrading;
import com.oxigenoxide.caramballs.scene.Farm;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.scene.GameOver;
import com.oxigenoxide.caramballs.scene.Scene;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class RewardBall {
    Sprite sprite;
    Color[] palette;
    Vector2 pos;
    Vector2 pos_target;
    boolean hasReachedTarget;
    static final float HEIGHT = 30;
    int level;
    float count_inFarm;
    float count_shine;

    static final float LERP = .999f;

    public RewardBall(float x, float y, int level) {
        this.level = level;
        palette = Ball_Main.getBallPalette(level);
        pos = new Vector2(x, y);
        pos_target = new Vector2(pos);
        sprite = new Sprite(Res.tex_ball[Game.selectedSkin][0]);
    }

    public void update() {
        if (!hasReachedTarget) {
            pos.lerp(pos_target, 1 - (float) Math.pow(1 - LERP, Main.dt));

            if (pos.dst(pos_target) < .25f) {
                pos.set(pos_target);
                hasReachedTarget = true;
                onReachTarget();
            }

            sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y - sprite.getHeight() / 2));
        }
        count_shine = MathFuncs.loop(count_shine, Main.dt * .2f, 4);
        if (Main.isInScene(Farm.class)) {
            count_inFarm += Main.dt;
            if (count_inFarm > 1)
                drop();
        }
    }

    void drop() {
        Main.rewardBall = null;
        Ball_Main ball_new = new Ball_Main(pos.x, pos.y - HEIGHT, HEIGHT, 0, level);
        ball_new.velY = 1;
        Main.balls.add(ball_new);
    }

    public void render(ShapeRenderer sr) {
        Funcs.drawShine(sr, pos, 20, count_shine);
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        Main.setPalette(palette);
        sprite.draw(batch);
        batch.setShader(null);
    }

    public void setTarget(float x, float y) {
        pos_target = new Vector2(x, y);
        hasReachedTarget = false;
    }

    public void setTarget(Vector2 v) {
        setTarget(v.x, v.y);
    }

    void onReachTarget(){
        if (Main.isInScene(DownGrading.class) && !Main.downGrading.wheel.finished)
            Main.rewardBall=null;
    }

    public int getLevel() {
        return level;
    }

    public void onSetScene(Scene scene) {
        if (Funcs.getClass(scene) == GameOver.class)
            setTarget(Main.gameOver.getBallPos());

        if (Funcs.getClass(scene) == Farm.class)
            setTarget(new Vector2(Main.width / 2, Main.farm.pos_field.y + Farm.FIELDWIDTH / 2 + HEIGHT));
    }
}
