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
        //Res.shader_palette.setUniformf("color0", Res.palette_mainBall[level][0].r, Res.palette_mainBall[level][0].g, Res.palette_mainBall[level][0].b, 1);
        {
            Res.shader_palette.setUniformf("color0", palette[0].r, palette[0].g, palette[0].b, 1);
            Res.shader_palette.setUniformf("color1", palette[1].r, palette[1].g, palette[1].b, 1);
            Res.shader_palette.setUniformf("color2", palette[2].r, palette[2].g, palette[2].b, 1);
        }
        //Res.shader_palette.setUniformf("color3", Res.palette_mainBall[level][3].r, Res.palette_mainBall[level][3].g, Res.palette_mainBall[level][3].b, 1);
        super.render(batch);
        batch.setShader(null);
    }
}
