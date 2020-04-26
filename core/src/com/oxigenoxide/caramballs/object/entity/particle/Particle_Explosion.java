package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

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

        if(animation.ended)
            dispose();

        sprite.setRegion(animation.getTexture());
    }

    @Override
    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        sprite.draw(batch);
    }
}
