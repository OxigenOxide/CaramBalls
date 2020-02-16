package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Particle_Confetti extends Particle {
    float count_nextRotation;

    public Particle_Confetti(float x, float y, float vx, float vy) {
        super(x, y);
        velY = (float) (Math.random() * 3 + 2);
        resistance = .2f;
        fallResistance = .6f;
        sprite = new Sprite(Res.tex_confetti[(int) (Math.random() * 3)]);
        vel.set(vx, vy);
        minVelY = -.5f;
        Main.particles_batch.add(this);
        sprite.setRotation((int) (Math.random() * 4) * 90);
        hasMinVelY=true;
    }

    @Override
    public void update() {
        super.update();
        count_nextRotation -= Main.dt_one;
        if (count_nextRotation <= 0 && velY == minVelY) {
            count_nextRotation = 10;
            sprite.setRotation(sprite.getRotation() + 90);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
