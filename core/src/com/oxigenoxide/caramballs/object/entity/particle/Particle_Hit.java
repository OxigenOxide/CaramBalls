package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;

public class Particle_Hit extends Particle {

    Animation animation;
    Sprite sprite;

    public Particle_Hit(float x, float y, float angle, float radius_ball) {
        super(x, y);

        Main.particles_batch.add(this);

        sprite = new Sprite(Res.tex_particle_hit[0]);
        sprite.setPosition(x + radius_ball + 1, y-sprite.getHeight()/2);
        sprite.setOrigin(-radius_ball - 1, sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(angle));

        animation=new Animation(10,Res.tex_particle_hit,new float[]{1,1,1,1,1,1},false);
        //countMax*=size/10;
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
