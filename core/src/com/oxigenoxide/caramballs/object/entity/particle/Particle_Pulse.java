package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_Pulse extends Particle {
    float countMax = 30;
    static int number;
    int id;
    float size;
    static final int TEXWIDTH = 30;
    static final float MAXRADIUS = TEXWIDTH / 2;
    TextureRegion tex;
    Animation animation;

    public Particle_Pulse(float x, float y, float size) {
        super(x, y);
        size = Math.min(size, MAXRADIUS * 2);
        this.size = size;
        Main.particles_batch.add(this);
        number++;
        id = number;
        tex = Res.tex_pulse[0];
        animation = new Animation((int) countMax, Res.tex_pulse, new float[]{1, 1, 1, 1, 1, 1, 1, 1}, false);
    }

    public void update() {
        animation.update(Main.dt_one);
        tex = animation.getTexture();
        if (animation.ended)
            dispose();
    }

    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        batch.draw(tex, (int)(pos.x - size / 2), (int)(pos.y - size / 2), size, size);
    }

    // PERF: 100 fps
    public void render(ShapeRenderer sr) {
    }
}
