package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.particle.Particle;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_BallExplosion;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_BulletExplosion;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Bullet extends Entity {
    Sprite sprite;
    Body body;
    public boolean doDispose;
    float count;
    final float SPEED = 1.9f; // m/s

    public Bullet(float x, float y, float ang) {
        sprite = new Sprite(Res.tex_bullet[1]);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees(ang));
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.shape_bullet, 1);
        body.setTransform(x * Main.MPP, y * Main.MPP, ang);
        body.setLinearVelocity(SPEED * (float) Math.cos(ang), SPEED * (float) Math.sin(ang));
        body.setFixedRotation(true);
        body.setUserData(this);
        sprite.setPosition(body.getPosition().x * Main.PPM - sprite.getRegionWidth() / 2f, body.getPosition().y * Main.PPM - sprite.getHeight() / 2);
        pos = new Vector2();
    }

    public void update() {
        count = (count + Main.dt * 20) % 2;
        sprite.setRegion(Res.tex_bullet[(int) count]);
        pos.set(body.getPosition().x * Main.PPM, body.getPosition().y * Main.PPM);
        sprite.setPosition(pos.x - sprite.getWidth() / 2f, pos.y - sprite.getHeight() / 2);
        if (doDispose)
            dispose();
    }

    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        sprite.draw(batch);
    }

    public void hit(Ball ball) {
        ball.destroy(body.getAngle(), 5, new Vector2(pos));
    }

    public void dispose() {
        Main.particles.add(new Particle_BulletExplosion(pos.x + (float) Math.cos(body.getAngle()) * sprite.getWidth() / 2f, pos.y + (float) Math.sin(body.getAngle()) * sprite.getWidth() / 2f, body.getAngle()));
        Main.bulletsToRemove.add(this);
        body = Main.destroyBody(body);
    }
}
