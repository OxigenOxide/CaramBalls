package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class TutorialSwipeExample {
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
    float alpha_hand = 1;
    int hand_index;
    boolean selectorActive;
    float alpha_ball=1;

    public TutorialSwipeExample() {
        pos = new Vector2(85, Main.height/2-30);
        v0 = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2();
        pos_hand = new Vector2();
        pos_ball = new Vector2();
        v_pointer = new Vector2();
        v0.set(pos.x, pos.y + 7 + 1);
        pos_virtualTap = new Vector2(pos.x + 0, pos.y + 30);
        v_handOffset = new Vector2(-4, -17);
        sprite_hand = new Sprite(Res.tex_hand[0]);
    }

    public void update() {
        loop = (loop + .005f) % 1;
        if (loop < .25) {
            pos_hand.set(pos.x + 40 - 40 * 1 / .25f * (loop), pos.y);
            alpha_hand = 1;
            alpha_ball=1;
            sprite_hand.setAlpha(alpha_hand);
            selectorActive = false;
            hand_index = 0;
            pos_ball.set(pos);
        }
        if (loop > .25 && loop < .75) {
            hand_index = 1;
            stretch = (loop - .25f) * 2;
            pos_virtualTap.set(pos.x, pos.y + stretch * 50);
            pos_hand.set(pos.x, pos.y + stretch * 50);
            selectorActive = true;
        }
        if (loop > .75) {
            hand_index = 0;
            //pos_virtualTap.set(pos_shop.x, pos_shop.y + stretch * 50);
            alpha_hand =Math.max(0,alpha_hand-.05f);
            sprite_hand.setAlpha(alpha_hand);
            pos_hand.set(pos.x, pos.y + stretch * 50);
            selectorActive = false;
            pos_ball.y++;
            alpha_ball=1-4*(loop-.75f);
        }

        pos_hand.add(v_handOffset);
        sprite_hand.setPosition(pos_hand.x, pos_hand.y);
        sprite_hand.setRegion(Res.tex_hand[hand_index]);

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

    public void render(SpriteBatch batch) {
        sprite_hand.draw(batch);
    }

    public void render(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);
        if (selectorActive) {
            sr.circle(v_pointer.x + .5f, v_pointer.y, radius);
            sr.triangle(v0.x + .5f, v0.y, v1.x + .5f, v1.y, v2.x + .5f, v2.y);
        }
        sr.setColor(1, 1, 1, alpha_ball);
        sr.circle(pos_ball.x, pos_ball.y, 7);
    }
}
