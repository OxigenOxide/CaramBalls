package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.Entity;

public class Particle extends Entity {
    Vector2 vel;
    Sprite sprite;
    float resistance;
    int blinks;
    int lifeTime = 240;
    float count_life = 0;
    boolean isVisible = true;
    float height = 0;
    float velY;
    float minVelY;
    float fallResistance;
    float absVel;
    boolean hasMinVelY;
    boolean doLinearResistance = true;

    Particle(float x, float y) {
        pos = new Vector2(x, y);
        vel = new Vector2();
    }

    public void update() {
        pos.add(vel);

        if (doLinearResistance) {
            absVel = Math.abs(vel.len());
            vel.sub(MathUtils.clamp(resistance * (float) Math.cos(vel.angleRad()), -absVel, absVel), MathUtils.clamp(resistance * (float) Math.sin(vel.angleRad()), -absVel, absVel));
        } else vel.scl((1 - resistance) * Main.dt_one);

        height += velY;
        height = Math.max(0, height);

        if (height == 0) {
            if (velY < 0) {
                velY = -velY * .56f;
                if (velY < .5f)
                    velY = 0;
            }
        } else velY += Game.GRAVITY_PIXELS * .2f * (1 - fallResistance);

        if (hasMinVelY) velY = Math.max(velY, minVelY);

        if (sprite != null) setSpritePosition();

        count_life += Main.dt_one;

        if (count_life > lifeTime / 2) isVisible = (int) ((count_life - lifeTime / 2) / 5) % 2 == 0;
        if (count_life > lifeTime) dispose();
    }

    public void render(SpriteBatch batch) {
        if (isVisible) sprite.draw(batch);
    }

    public void render(ShapeRenderer sr) { }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr){ // only called when in particles_batch
        render(batch);
    }

    public void setSpritePosition() {
        sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y + height - sprite.getHeight() / 2));
    }

    public void dispose() {
        Main.particlesToRemove.add(this);
        Main.particles_batch.remove(this);
        Main.particles_sr.remove(this);
    }
}
