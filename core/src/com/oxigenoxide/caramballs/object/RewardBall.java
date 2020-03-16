package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
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
    static final float HEIGHT = 30;
    int level;
    float count_inFarm;
    float count_shine;

    public RewardBall(float x, float y, int level) {
        this.level = level;
        palette = Ball_Main.getBallPalette(level);
        pos = new Vector2(x, y);
        pos_target = new Vector2(pos);
        sprite = new Sprite(Res.tex_ball[Game.ballType][0]);
    }

    public void update() {
        pos.lerp(pos_target, .1f);
        if(MathFuncs.distanceBetweenPoints(pos,pos_target)<.1f){
            pos.set(pos_target);
        }
        sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y - sprite.getHeight() / 2));
        count_shine = MathFuncs.loopOne(count_shine, Main.dt*.1f);
        if (Main.isInScene(Farm.class)) {
            count_inFarm += Main.dt;
            if (count_inFarm > 1) {
                drop();
            }
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

    public void onSetScene(Scene scene) {
        if (Funcs.getClass(scene) == GameOver.class)
            pos_target = Main.gameOver.getBallPos();

        if (Funcs.getClass(scene) == Farm.class)
            pos_target = new Vector2(Main.width / 2, Main.farm.pos_field.y + Farm.FIELDWIDTH / 2 + HEIGHT);
    }
}
