package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_Split extends Particle {

    Animation animation;
    Sprite sprite;

    public Particle_Split(float x, float y, float angle, int size) {
        super(x, y);

        Main.particles_batch.add(this);

        if (size == 1) {
            sprite = new Sprite(Res.tex_mediumSplit[0]);
            animation = new Animation(15, Res.tex_mediumSplit, new float[]{1, 1, 1, 1, 1, 1, 1}, false);
        } else if (size == 2) {
            sprite = new Sprite(Res.tex_largeSplit[0]);
            animation = new Animation(15, Res.tex_largeSplit, new float[]{1, 1, 1, 1, 1, 1, 1, 1}, false);
        }

        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(angle));

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
