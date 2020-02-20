package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.Game;

import java.util.ArrayList;

public class Meter {

    static Vector2 pos;
    int dots = 8;
    int balls = 8;

    int balls_small;
    int balls_medium;
    int balls_large;

    ArrayList<Particle> particles = new ArrayList<Particle>();

    public Meter() {
        pos = new Vector2(97, 2);
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
        for (Particle particle : particles) {
            particle.update();
        }
    }

    public void loseBall() {
        particles.add(new Particle(balls - 1));
    }

    public void render(SpriteBatch batch) {

        for (int i = 0; i < balls; i++) {
            batch.draw(Res.tex_meter_ball, pos.x + 1, pos.y + 1 + i * 6);
        }

        int height = 0;
        for (int i = 0; i < balls_large; i++) {
            batch.draw(Res.tex_meter_dot[2], pos.x + 1, pos.y + 2 + height);
            height += 24;
        }
        for (int i = 0; i < balls_medium; i++) {
            batch.draw(Res.tex_meter_dot[1], pos.x + 1, pos.y + 2 + height);
            height += 12;
        }
        for (int i = 0; i < balls_small; i++) {
            batch.draw(Res.tex_meter_dot[0], pos.x + 1, pos.y + 2 + height);
            height += 6;
        }
        batch.draw(Res.tex_meter_case, pos.x, pos.y);
        for (Particle particle : particles) {
            particle.render(batch);
        }
    }

    private class Particle {
        Sprite sprite;
        Vector2 pos;
        Vector2 vel;
        float ang;

        Particle(int height) {
            sprite = new Sprite(Res.tex_meterDot);
            pos = new Vector2(Meter.pos.x + 1 + sprite.getRegionWidth() / 2, Meter.pos.y + 3 + sprite.getHeight() / 2 + 6 * height);
            vel = new Vector2(-1, 1.5f + (float)Math.random());
            sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y - sprite.getHeight() / 2);
        }

        public void update() {
            ang += Main.dt_one*3;
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
