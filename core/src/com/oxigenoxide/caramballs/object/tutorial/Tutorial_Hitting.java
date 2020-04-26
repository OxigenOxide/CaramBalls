package com.oxigenoxide.caramballs.object.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Tutorial_Hitting extends Tutorial {
    Vector2 v0;
    Vector2 v1;
    Vector2 v2;
    Vector2 v_pointer;
    Vector2 pos;
    Vector2 pos_hand;
    Vector2 pos_virtualTap;
    Vector2 pos_ball;
    Vector2 v_handOffset;
    Sprite sprite_hand;
    float radius;
    float distanceMax = 50;
    float radiusMax = 10;
    float stretch;
    float loop;
    float ang = (float) Math.PI * 1 / 2f;
    float alpha_hand = 1;
    int hand_index;
    boolean selectorActive;
    float alpha_ball = 1;
    Ball_Main ball_subject;
    Ball_Main ball_target;

    float count_idle;
    boolean visible;

    public Tutorial_Hitting() {
        pos = new Vector2(85, Main.height / 2 - 30);
        v0 = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2();
        pos_hand = new Vector2();
        pos_ball = new Vector2();
        v_pointer = new Vector2();
        v_handOffset = new Vector2(-4, -17);
        sprite_hand = new Sprite(Res.tex_hand[0]);
    }

    public void update() {

        if (Main.mainBalls.size() > 0)
            ball_subject = Main.mainBalls.get(0);
        else
            ball_subject = null;

        if (Main.mainBalls.size() > 1)
            ball_target = Main.mainBalls.get(1);
        else
            ball_target = null;

        count_idle = Math.max(0, count_idle - Main.dt);

        if (count_idle > 0 || ball_subject == null || Main.ballSelector.getSelectedBall() != null) {
            visible = false;
            loop = 0;
            return;
        }

        visible = true;

        pos.set(ball_subject.pos);

        pos_virtualTap = new Vector2(pos.x + (float) Math.cos(ang) * 30, pos.y + (float) Math.sin(ang) * 30);

        v0.set(pos.x + (float) Math.cos(ang) * 8, pos.y + (float) Math.sin(ang) * 8);

        loop = (loop + .005f * Main.dt_one);
        if (loop > 1)
            newLoop();

        if (loop < .25) {
            pos_hand.set(pos.x + 40 - 40 * 1 / .25f * (loop), pos.y);
            alpha_hand = 1;
            alpha_ball = 1;
            sprite_hand.setAlpha(alpha_hand);
            selectorActive = false;
            hand_index = 0;
            pos_ball.set(pos);
        }

        if (loop > .25 && loop < .75) {
            hand_index = 1;
            stretch = (loop - .25f) * 2;
            pos_virtualTap.set(pos.x + (float) Math.cos(ang) * 50 * stretch, pos.y + (float) Math.sin(ang) * stretch * 50);
            pos_hand.set(pos.x + (float) Math.cos(ang) * 50 * stretch, pos.y + (float) Math.sin(ang) * stretch * 50);
            selectorActive = true;
        }

        if (loop > .75) {
            hand_index = 0;
            //pos_virtualTap.set(pos.x, pos.y + stretch * 50);
            alpha_hand = Math.max(0, alpha_hand - .05f);
            sprite_hand.setAlpha(alpha_hand);
            pos_hand.set(pos.x + (float) Math.cos(ang) * 50 * stretch, pos.y + (float) Math.sin(ang) * stretch * 50);
            selectorActive = false;
            pos_ball.y++;
            alpha_ball = 1 - 4 * (loop - .75f);
        }

        pos_hand.add(v_handOffset);
        sprite_hand.setPosition(pos_hand.x, pos_hand.y);
        sprite_hand.setRegion(Res.tex_hand[hand_index]);
        sprite_hand.setSize(sprite_hand.getRegionWidth(), sprite_hand.getRegionHeight());

        float cang = MathFuncs.angleBetweenPoints(pos, pos_virtualTap);
        float distanceToPointer = MathFuncs.distanceBetweenPoints(pos, pos_virtualTap);
        if (distanceToPointer < distanceMax) {
            v_pointer.set(pos_virtualTap);
        } else {
            v_pointer.set(distanceMax, 0);
            v_pointer.rotateRad(cang);
            v_pointer.add(pos);
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
        radius = radiusMax / (1 + (distanceToPointer) / distanceMax);
    }

    void newLoop() {
        loop = 0;
        if (ball_target != null)
            ang = MathFuncs.angleBetweenPoints(ball_subject.pos, ball_target.pos);
    }

    public void onBallCombined() {
        finish();
    }

    public void onBallRelease() {
        count_idle = 1;

    }

    public void render(SpriteBatch batch, ShapeRenderer sr) {
        if (visible) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(1, 1, 1, 1);
            if (selectorActive) {
                sr.circle(v_pointer.x, v_pointer.y, radius);
                sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
            }

            sr.end();

            batch.begin();
            sprite_hand.draw(batch);
            batch.end();
        }

    }
}

