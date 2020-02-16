package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.Funcs;

import static com.oxigenoxide.caramballs.Res.COLOR_HOT;

public class Point {
    float count_particleCooldown;
    static final float COUNTMAX_PARTICLECOOLDOWN = 5;
    Vector2 pos;
    float distanceMade;
    public Color color = new Color();
    float colorIntensity;
    public boolean isActive;
    float slowdown;

    public Point() {
        pos = new Vector2();
    }

    public void update() {
        if (Funcs.justTouched())
            onTouch();

        if (isActive) {
            pos.set(Main.tap[0]);

            Main.slowdown = Math.min(Main.slowdown + Main.dt_one * .05f, .8f);

            count_particleCooldown -= Main.dt_one;
            if (count_particleCooldown <= 0) {

            }

            if (distanceMade >= 5) {
                Main.tracers.add(new Tracer(pos.x, pos.y, Math.min(Main.tap_speed / 2, 5)));
                count_particleCooldown = COUNTMAX_PARTICLECOOLDOWN;
                distanceMade %= 5;
            }

            distanceMade += Main.tap_distance[0];

            for (Ball ball : Main.balls) {
                if(!ball.isUnderGround) {
                    if(ball.testHit())
                        break;
                }
            }
        }
        if (!isActive) {
            Main.slowdown = Math.max(Main.slowdown - Main.dt_one * .05f, 0);
        }

        //Main.cam.zoom = 1 - Game.slowdown * 2 / 40f;

        colorIntensity = getColorIntensity();

        color.set(COLOR_HOT.r * colorIntensity, COLOR_HOT.g * colorIntensity, COLOR_HOT.b * colorIntensity, 1);
    }

    public void render(SpriteBatch batch) {

    }

    public void onRelease() {
        if(isActive) {
            isActive = false;
            Res.sound_speedup.play(.5f, 2, 0);
            Res.sound_slowdown.stop();
        }
    }

    public void onTouch() {
        if(!isActive) {
            isActive = true;
            Res.sound_slowdown.play(1f, 2, 0);
            Res.sound_speedup.stop();
        }
    }

    public void onHit() {
        onRelease();
    }

    float getColorIntensity() {
        float intensity = 0;
        if (Main.tap_speed > Game.HITSPEEDTHRESHOLD) {
            intensity = Math.min((Main.tap_speed - Game.HITSPEEDTHRESHOLD) / 2, 1);

        }
        return intensity;
    }
}
