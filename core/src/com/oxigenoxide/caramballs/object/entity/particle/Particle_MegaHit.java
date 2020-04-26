package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_MegaHit extends Particle {
    Animation animation;
    Sprite sprite;

    public Particle_MegaHit(float x, float y, float angle, float radius_ball) {
        super(x, y);

        Main.particles_batch.add(this);

        sprite = new Sprite(Res.tex_particle_megaHit[0]);
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() - radius_ball);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() + radius_ball);
        sprite.setRotation((float) Math.toDegrees(angle) + 90);

        animation = new Animation(28, Res.tex_particle_megaHit, new float[]{.3f, .5f, 1, 1, 1, 1, 1, 1, 1}, false);
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
