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
    float stretched;
    float count_enoughStretched;

    boolean doTriangle;

    final float DISTANCEMAX = 50;
    final float MAXSPEED = 12;
    final float MINSPEED = 1;
    //final float MINSPEED = 15;
    final float MINSTRETCH = .2f;

    public BallSelector() {
        v0 = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2();
        v_pointer = new Vector2();
    }

    public void update() {
        active = ball_selected != null && Gdx.input.isTouched(0) && !ball_selected.isDisposed && !Game.doGameOverCue;
        if (!active) {
            Game.slowdown_effect = Math.max(Game.slowdown_effect - Main.dt_one * .05f, 0);
        }
        if (active) {
            if (Main.isInScene(Game.class)) {
                Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .9f);
                Main.slowdown = Game.slowdown_effect;
            }

            cang = MathFuncs.angleBetweenPoints(ball_selected.pos, tap[0]);
            v0.set(ball_selected.radius + 2, 0);
            v0.rotateRad(cang);
            v0.add(ball_selected.pos);

            float distanceToPointer = MathFuncs.distanceBetweenPoints(ball_selected.pos, tap[0]);
            doTriangle = distanceToPointer >= ball_selected.radius + 2;
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

            if (enoughStretched())
                count_enoughStretched = Math.min(count_enoughStretched + Main.dt * 5, 1);
            else
                count_enoughStretched = Math.max(count_enoughStretched - Main.dt * 5, 0);

            radius = count_enoughStretched * radiusMax / (1 + 1.5f * Math.min(DISTANCEMAX, MathFuncs.distanceBetweenPoints(ball_selected.pos, tap[0])) / DISTANCEMAX);
        }
    }

    public void onRelease() { // apparently onRelease sometimes is called after Gdx.input.isTouched is changed on android. This is always the contrary on desktop.
        if (enoughStretched() && ball_selected != null && !ball_selected.isDisposed) {
            ball_selected.hit(cang, MINSPEED + (MAXSPEED - MINSPEED) * (stretched - MINSTRETCH) / (1 - MINSTRETCH)); // stretch mapped to [MINSPEED, MAXSPEED]
        }
        ball_selected = null;
        Main.game.onBallRelease();
    }

    boolean enoughStretched() {
        return stretched > MINSTRETCH * Main.test_float;
    }

    public void setSelected(Ball ball) {
        ball_selected = ball;
        ball_selected.onSelect();
        count_enoughStretched = 0;
    }

    public void render(ShapeRenderer sr) {

        //if (enoughStretched())
        if (active && radius > 0) {
            sr.setColor(1, 1, 1, 1);
            sr.circle(v_pointer.x, v_pointer.y, radius);
            if (doTriangle)
                sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
        }
        //sr.triangle(0,0,0,20,20,0);
    }

    public Ball getSelectedBall() {
        return ball_selected;
    }

    public boolean isBallSelected(Ball ball) {
        return ball_selected == ball;
    }
}
