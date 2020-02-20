package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Particle_Eye extends Particle {
    int level;

    public Particle_Eye(float x, float y, float vx, float vy, int type) {
        super(x, y);
        this.level=level;
        Main.particles_batch.add(this);
        vel.set(vx,vy);
        sprite=new Sprite(Res.tex_eye[type]);
        resistance=.05f;
        lifeTime=100;
        velY=4f;
        setSpritePosition();
    }

    public void update() {
        super.update();
        float progress = (lifeTime-count_life)/lifeTime;
        float sizeFactor = Math.min(1,progress*2);
        sprite.setSize(sprite.getRegionWidth()*sizeFactor,sprite.getRegionHeight()*sizeFactor);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
