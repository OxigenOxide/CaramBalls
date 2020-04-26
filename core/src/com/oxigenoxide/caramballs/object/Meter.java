package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;

import java.util.ArrayList;

public class Meter {

    static Vector2 pos;
    static Vector2 pos_number;
    int dots = 8;
    int balls = 8;

    int balls_small;
    int balls_medium;
    int balls_large;

    int ballsMax;
    int ballsMax_small;
    int ballsMax_medium;
    int ballsMax_large;

    int ballsCombined_medium;
    int ballsCombined_large;

    int ballsCombinedMax_medium;
    int ballsCombinedMax_large;

    int rewardsLeft = 8;

    float count_vibrate;
    float vibrate;

    static final boolean REMOVECONECTIONS = false;

    ArrayList<Particle> particles = new ArrayList<Particle>();

    public Meter() {
        pos = new Vector2(97, 2);
        pos_number = new Vector2(pos.x + 3, pos.y + 54);
    }

    public void update() {
        balls = 0;
        balls_small = 0;
        balls_medium = 0;
        balls_large = 0;
        for (Ball_Main ball : Main.mainBalls) {
            int size = ball.getSize();
            if (!ball.isUnderGround && !ball.doDispose) {
                balls += Math.pow(2, size);
                if (size == 0)
                    balls_small++;
                else if (size == 1)
                    balls_medium++;
                else
                    balls_large++;
            }
        }

        if (balls_small > ballsMax_small) {
            ballsMax_small = balls_small;
        }
        if (balls_medium > ballsMax_medium) {
            ballsMax_medium = balls_medium;
            // medium reward
        }
        if (balls_large > ballsMax_large) {
            ballsMax_large = balls_large;
            // big reward
        }

        if (balls > ballsMax) {
            ballsMax = balls;
        }
        //
//        int spaceLeft = ballsMax;
//        balls_large = ballsMax_large;
//        spaceLeft -= 4 * balls_large;
//        //balls_medium = Math.min(spaceLeft / 2, ballsMax_medium);
//        balls_medium = Math.max(0, ballsMax_medium - 2 * balls_large);
//        spaceLeft -= 2 * balls_medium;
//        balls_small = Math.min(spaceLeft, ballsMax_small);
//        //balls_small = Math.max(0, ballsMax_small - 2 * balls_medium);

        balls = ballsMax;

        balls_large = ballsCombinedMax_large;
        balls_medium = ballsCombinedMax_medium - balls_large * 2;
        balls_small = balls - balls_medium * 2 - balls_large * 4;
        //System.out.println("max comb: "+ ballsCombinedMax_medium + " "+ballsCombined_large);

        count_vibrate = Math.max(0, count_vibrate - Main.dt * 30);
        vibrate = count_vibrate / 2 * (float) Math.sin(count_vibrate);

        for (Particle particle : particles) {
            particle.update();
        }
    }

    public void loseBall() {
        //particles.add(new Particle(balls - 1));
    }

    public void vibrate() {
        count_vibrate = 10;
    }

    public void onCombine(Ball_Main ball_combined) {
        if (ball_combined.size == 1) {
            ballsCombined_medium++;
            if(ballsCombined_medium > ballsCombinedMax_medium){
                rewardsLeft--;
                vibrate();
            }
            ballsCombinedMax_medium = Math.max(ballsCombinedMax_medium, ballsCombined_medium);
        } else if (ball_combined.size == 2) {
            ballsCombined_large++;
            if(ballsCombined_large > ballsCombinedMax_large){
                rewardsLeft -= 2;
                vibrate();
            }
            ballsCombinedMax_large = Math.max(ballsCombinedMax_large, ballsCombined_large);
        }
    }

    public void onSplit(Ball_Main ball_split) {
        if (ball_split.size == 1)
            ballsCombined_medium--;
        else if (ball_split.size == 2)
            ballsCombined_large--;
    }

    public void reset() {
        rewardsLeft = 8;
        ballsMax_small = 0;
        ballsMax_medium = 0;
        ballsMax_large = 0;
        ballsMax = 0;

        ballsCombinedMax_medium = 0;
        ballsCombinedMax_large = 0;

        ballsCombined_medium = 0;
        ballsCombined_large = 0;
    }

    public void render(SpriteBatch batch) {

        for (int i = 0; i < balls; i++) {
            batch.draw(Res.tex_meter_ball, pos.x + 1 + vibrate, pos.y + 1 + i * 6);
        }

        int height = 0;
        for (int i = 0; i < balls_large; i++) {
            batch.draw(Res.tex_meter_dot[2], pos.x + 1 + vibrate, pos.y + 2 + height);
            height += 24;
        }
        for (int i = 0; i < balls_medium; i++) {
            batch.draw(Res.tex_meter_dot[1], pos.x + 1 + vibrate, pos.y + 2 + height);
            height += 12;
        }
        for (int i = 0; i < balls_small; i++) {
            batch.draw(Res.tex_meter_dot[0], pos.x + 1 + vibrate, pos.y + 2 + height);
            height += 6;
        }
        batch.draw(Res.tex_meter_case, pos.x + vibrate, pos.y);
        for (Particle particle : particles) {
            particle.render(batch);
        }

        if(rewardsLeft>0) {
            batch.draw(Res.tex_meter_fruit, pos_number.x - Res.tex_meter_fruit.getRegionWidth() / 2, pos_number.y - 2);
            Funcs.drawNumber(batch, rewardsLeft, pos_number, ID.Font.SMALL);
        }
        else batch.draw(Res.tex_smallFruit[Game.nextFruit], pos_number.x - Res.tex_smallFruit[Game.nextFruit].getRegionWidth() / 2, pos_number.y - 2);

    }

    private class Particle {
        Sprite sprite;
        Vector2 pos;
        Vector2 vel;
        float ang;

        Particle(int height) {
            sprite = new Sprite(Res.tex_meterDot);
            pos = new Vector2(Meter.pos.x + 1 + sprite.getRegionWidth() / 2, Meter.pos.y + 3 + sprite.getHeight() / 2 + 6 * height);
            vel = new Vector2(-1, 1.5f + (float) Math.random());
            sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y - sprite.getHeight() / 2);
        }

        public void update() {
            ang += Main.dt_one * 3;
            sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y - sprite.getHeight() / 2);
            sprite.setRotation(ang);
            vel.add(0, Game.GRAVITY_PIXELS * Main.dt_one * .3f);
            pos.add(vel);
        }

        public void render(SpriteBatch batch) {
            sprite.draw(batch);
        }
    }
}
