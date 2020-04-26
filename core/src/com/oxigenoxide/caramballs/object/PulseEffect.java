package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;

public class PulseEffect {
    float countMax = 32;
    static int number;
    int id;
    float size;
    static final int TEXWIDTH = 30;
    static final float MAXRADIUS = TEXWIDTH / 2;
    TextureRegion tex;
    Animation animation;
    Vector2 pos;

    public PulseEffect(float x, float y, float size) {
        pos = new Vector2(x, y);
        size = Math.min(size, MAXRADIUS * 2);
        this.size = size;
        number++;
        id = number;
        tex = Res.tex_pulse[0];
        animation = new Animation((int) countMax, Res.tex_pulse, new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, false);
    }

    public void update() {
        animation.update(Main.dt_one);
        tex = animation.getTexture();
        if (animation.ended)
            dispose();
    }

    public void render(SpriteBatch batch) {
        //Funcs.setShaderNull(batch);
        batch.draw(tex, (int) (pos.x - size / 2), (int) (pos.y - size / 2), size, size);
    }

    // PERF: 100 fps

    public void dispose() {
        Main.pulseEffectsToRemove.add(this);
    }
}
