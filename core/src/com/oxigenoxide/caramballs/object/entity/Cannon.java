package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Cannon extends Entity {
    Vector2 pos_center;
    float ang;
    Sprite gun;
    float angDest;
    Ball_Main ball_target;
    float count_nextBullet;
    float size = 0;
    Body body;
    boolean disappear;
    float count;
    float lifetime = 30;

    public static final int DETECTDISTANCE = 50;

    public Cannon(float x, float y) {
        x = (int) x;
        y = (int) y;
        pos = new Vector2(x, y);
        pos_center = new Vector2(pos.x, pos.y + 10.5f);
        gun = new Sprite(Res.tex_cannon_gun);
        gun.setOrigin(6.5f, 6.5f);
        gun.setPosition(x - 6, y - 6.5f + 4);
        createBody();
        body.setTransform(pos.x * Main.MPP, (pos.y + 4.5f) * Main.MPP, 0);
        radius_spawn = 10; // not perfect
        radius_spawn_danger = DETECTDISTANCE + 10;
    }

    public Cannon() {
        pos = Game.getFreePosOnTable(9, 9);
        pos_center = new Vector2(pos.x, pos.y + 10.5f);
        gun = new Sprite(Res.tex_cannon_gun);
        gun.setOrigin(6.5f, 6.5f);
        gun.setPosition(pos.x - 6, pos.y + 4);
        createBody();
        body.setTransform(pos.x * Main.MPP, (pos.y + 4.5f) * Main.MPP, 0);
        radius_spawn = 10; // not perfect
        radius_spawn_danger = DETECTDISTANCE + 10;
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(4.5f * Main.MPP);
        body.createFixture(Res.fixtureDef_circle);
        body.setUserData(this);
    }

    public void update() {

        if (disappear) {
            size = Math.max(0, size - .05f * Main.dt_one_slowed);
            gun.setSize(gun.getRegionWidth() * size, gun.getRegionHeight() * size);
            gun.setPosition(pos.x - 6 * size, pos.y + 4 * size);
            if (size == 0) dispose();
        } else if (size < 1) {
            size = Math.min(1, size + .05f * Main.dt_one_slowed);
            gun.setSize(gun.getRegionWidth() * size, gun.getRegionHeight() * size);
            gun.setPosition(pos.x - 6 * size, pos.y + 4 * size);
        }

        boolean isBallInRange = false;
        float closestDistance = DETECTDISTANCE;
        for (Ball_Main ball : Main.mainBalls) {
            float distance = MathFuncs.distanceBetweenPoints(ball.pos, pos_center);
            if (distance < closestDistance) {
                closestDistance = distance;
                angDest = MathFuncs.angleBetweenPoints(pos_center, ball.pos);
                isBallInRange = true;
            }
        }
        float shortest_angle = ((((angDest - ang) % ((float) Math.PI * 2)) + ((float) Math.PI * 3)) % ((float) Math.PI * 2)) - ((float) Math.PI);
        ang = ang + (shortest_angle * .1f) % ((float) Math.PI * 2);
        gun.setRotation((float) Math.toDegrees(ang));
        if (isBallInRange) {
            count_nextBullet -= Main.dt_one_slowed;
            if (count_nextBullet <= 0) {
                count_nextBullet = 50;
                Main.bullets.add(new Bullet(pos.x + 15.5f * (float) Math.cos(ang), pos.y + 10.5f + 15.5f * (float) Math.sin(ang), ang));
            }
        }
        count += Main.dt;
        if (count >= lifetime && !disappear)
            disappear();
    }

    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        batch.draw(Res.tex_cannon_base, pos.x - Res.tex_cannon_base.getRegionWidth() / 2 * size, pos.y, size * Res.tex_cannon_base.getRegionWidth(), size * Res.tex_cannon_base.getRegionWidth());
        gun.draw(batch);
        batch.draw(Res.tex_cannon_shine, pos.x - Res.tex_cannon_shine.getRegionWidth() / 2 * size, pos.y + 11 * size, size * Res.tex_cannon_shine.getRegionWidth(), size * Res.tex_cannon_shine.getRegionWidth());
    }

    public void disappear() {
        disappear = true;
    }

    public void dispose() {
        Main.cannonsToRemove.add(this);
        body = Main.destroyBody(body);
    }
}
