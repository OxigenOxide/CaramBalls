package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.tap;

public class BallSelector {

    Vector2 v0;
    Vector2 v1;
    Vector2 v2;
    Vector2 v_pointer;
    float radiusMax = 10;
    float radius;
    Ball ball_selected;
    boolean active;
    float cang;
    final float DISTANCEMAX = 50;
    float distanceToSelect = 40;
    float stretched;

    public BallSelector() {
        v0 = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2();
        v_pointer = new Vector2();
    }

    public void update() {
        active = ball_selected != null && Gdx.input.isTouched(0) && !ball_selected.isDisposed && !Game.doGameOverCue;
        if (!active) {
            ball_selected = null;
            //Game.slowdown = Math.max(Game.slowdown - Main.dt_one * .05f, 0);
            Game.slowdown_effect = Math.max(Game.slowdown_effect - Main.dt_one * .05f, 0);
        }
        if (active) {
            if (Main.noFlow)
                Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .8f);
            else
                Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .9f);

            Main.slowdown = Game.slowdown_effect;

            cang = MathFuncs.angleBetweenPoints(ball_selected.pos, tap[0]);
            v0.set(ball_selected.radius + 2, 0);
            v0.rotateRad(cang);
            v0.add(ball_selected.pos);

            float distanceToPointer = MathFuncs.distanceBetweenPoints(ball_selected.pos, tap[0]);
            stretched = Math.min(DISTANCEMAX, distanceToPointer) / DISTANCEMAX;
            if (distanceToPointer < DISTANCEMAX) {
                v_pointer.set(tap[0]);
            } else {
                v_pointer.set(DISTANCEMAX, 0);
                v_pointer.rotateRad(cang);
                v_pointer.add(ball_selected.pos);
            }
            float a = MathFuncs.distanceBetweenPoints(v0, v_pointer);
            float ang = MathFuncs.angleBetweenPoints(v0, v_pointer);
            float c = (float) Math.sqrt(-Math.pow(radius, 2) + Math.pow(a, 2));
            float bang = (float) Math.acos(c / a);
            v1.set(c, 0);
            v1.rotateRad(ang + bang);
            v1.add(v0);
            v2.set(c, 0);
            v2.rotateRad(ang - bang);
            v2.add(v0);
            radius = radiusMax / (1 + (MathFuncs.distanceBetweenPoints(ball_selected.pos, tap[0])) / DISTANCEMAX);
        }
    }

    public void onRelease() {
        if (active && !ball_selected.isDisposed)
            ball_selected.hit(cang, 15 * stretched);
    }

    public void setSelected(Ball ball) {
        ball_selected = ball;
    }

    public void render(ShapeRenderer sr) {

        if (active) {
            sr.setColor(1, 1, 1, 1);
            sr.circle(v_pointer.x, v_pointer.y, radius);
            sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
        }
        //sr.triangle(0,0,0,20,20,0);
    }

    public boolean isBallSelected(Ball ball) {
        return ball_selected == ball;
    }
}
