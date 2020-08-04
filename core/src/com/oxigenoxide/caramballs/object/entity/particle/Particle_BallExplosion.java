package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;

public class Particle_BallExplosion extends Particle {

    Animation animation;
    Sprite sprite;

    public Particle_BallExplosion(float x, float y) {
        super(x, y);

        Main.particles_batch.add(this);

        sprite = new Sprite(Res.tex_ballExplosion[0]);
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);

        animation = new Animation((int)(20), Res.tex_ballExplosion, new float[]{1, 1, 1, 1, 1, 1}, false);
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