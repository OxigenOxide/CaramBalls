package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Particle_Pulse extends Particle {
    float radius;
    float lineWidth = 4;
    float count;
    float countMax = 30;
    float progress;
    static int number;
    int id;
    float size;
    static final int TEXWIDTH = 30;
    //static final float MAXRADIUS = TEXWIDTH * 1 / 2f * (float) Math.sqrt(2) * 1 / 2f;
    static final float MAXRADIUS = TEXWIDTH/2;
    static final float MAXLINEWIDTH = 5f;
    static Texture texture = new Texture(new Pixmap(TEXWIDTH, TEXWIDTH, Pixmap.Format.RGBA8888));

    public Particle_Pulse(float x, float y, float size) {
        super(x, y);
        size = Math.min(size, MAXRADIUS);
        this.size = size;
        Main.particles_batch.add(this);
        number++;
        id = number;
        //countMax*=size/10;
    }

    public void update() {
        lineWidth = MAXLINEWIDTH - progress * MAXLINEWIDTH;
        radius = size * progress;
        progress = count / countMax;
        count = Math.min(count + Main.dt_one, countMax);

        if (progress >= 1)
            dispose();
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_circle);
        Res.shader_circle.setUniformf("r", radius / TEXWIDTH);
        Res.shader_circle.setUniformf("width", lineWidth / TEXWIDTH);
        batch.draw(texture, pos.x - TEXWIDTH / 2, pos.y - TEXWIDTH / 2);
        batch.setShader(null);
    }

    public void render(ShapeRenderer sr) {
    }
}
