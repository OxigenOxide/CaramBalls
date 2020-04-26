package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Particle_Ball extends Particle {
    int level;
    Color[] palette;

    public Particle_Ball(float x, float y, float angle, float speed, int level) {
        super(x, y);
        this.level = level;
        palette = Res.palette_mainBall[level];
        Main.particles_batch.add(this);
        vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        sprite = new Sprite(Res.tex_particle_ball[(int) (Math.random() * 2)]);
        resistance = .06f;
        lifeTime = 100;
        velY = 3f;
        sprite.setRotation((int) (Math.random() * 4) * 90);
        setSpritePosition();
    }

    public Particle_Ball(float x, float y, float angle, float speed, Color[] palette) {
        super(x, y);
        this.palette = palette;
        Main.particles_batch.add(this);
        vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        sprite = new Sprite(Res.tex_particle_ball[(int) (Math.random() * 2)]);
        resistance = .06f;
        lifeTime = 100;
        velY = 2f;
        sprite.setRotation((int) (Math.random() * 4) * 90);
        setSpritePosition();
    }

    public Particle_Ball(float x, float y, float angle, float speed, Color color) {
        super(x, y);
        this.palette = new Color[]{null, null, color, null};
        Main.particles_batch.add(this);
        vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        sprite = new Sprite(Res.tex_particle_ball[(int) (Math.random() * 2)]);
        resistance = .06f;
        lifeTime = 100;
        velY = 2f;
        sprite.setRotation((int) (Math.random() * 4) * 90);
        setSpritePosition();
    }

    public void update() {
        super.update();
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);

        //Main.setPalette(palette[0], palette[1], palette[2], null);
        Main.setPalette(palette);

        super.render(batch);
        batch.setShader(null);
    }
}
