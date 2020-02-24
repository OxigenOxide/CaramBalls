
package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Particle_Cross extends Particle {


    public Particle_Cross(float x, float y) {
        super(x, y);
        Main.particles_batch.add(this);
        vel.set(0, 0);
        sprite = new Sprite(Res.tex_indication_cross);
        resistance = .05f;
        lifeTime = 100;
        velY = 2f;
        setSpritePosition();
    }

    public void update() {
        super.update();
        float progress = (lifeTime - count_life) / lifeTime;
        float sizeFactor = Math.min(1, progress * 2);
        sprite.setSize(sprite.getRegionWidth() * sizeFactor, sprite.getRegionHeight() * sizeFactor);
        setSpritePosition();
        //sprite.setPosition((int) sprite.getX(), (int) sprite.getY());
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
