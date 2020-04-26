package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;

public class Particle_BulletExplosion extends Particle {

    Animation animation;
    Sprite sprite;

    public Particle_BulletExplosion(float x, float y, float angle) {
        super(x, y);

        Main.particles_batch.add(this);

        sprite = new Sprite(Res.tex_bulletExplosion[0]);
        sprite.setPosition(x - sprite.getWidth(), y - sprite.getHeight() / 2);
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(angle));

        animation = new Animation(15, Res.tex_bulletExplosion, new float[]{1, 1, 1, 1, 1}, false);
    }

    public void update() {
        animation.update();
        if (animation.ended)
            dispose();
        sprite.setRegion(animation.getTexture());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
