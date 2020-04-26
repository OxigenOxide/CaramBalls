package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_OrbPickup extends Particle {
    Animation animation;
    Sprite sprite;

    public Particle_OrbPickup(float x, float y) {
        super(x, y);

        Main.particles_batch.add(this);

        sprite = new Sprite(Res.tex_orbPickup[0]);
        sprite.setPosition(x - sprite.getWidth() / 2, y - 3);

        animation = new Animation((int) (50 * Main.test_float), Res.tex_orbPickup, new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, false);
    }

    public void update() {
        animation.update();
        if (animation.ended)
            dispose();
        sprite.setRegion(animation.getTexture());
    }

    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        sprite.draw(batch);
    }
}
