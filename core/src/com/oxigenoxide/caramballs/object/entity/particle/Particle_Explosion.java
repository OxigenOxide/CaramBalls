package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;

public class Particle_Explosion extends Particle {
    Animation animation;

    public Particle_Explosion(float x, float y) {
        super(x, y);
        Main.particles_batch.add(this);
        sprite = new Sprite(Res.tex_explosion[0]);
        sprite.setSize(58,50);
        animation = new Animation(60, Res.tex_explosion, new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, false);
    }

    @Override
    public void update() {
        super.update();
        animation.update();
        sprite.setTexture(animation.getTexture());
        if(animation.ended)
            dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        sprite.draw(batch);
    }
}
