package com.oxigenoxide.caramballs.object.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Video {
    Animation animation;
    TextureRegion[] frames;
    TextureRegion currentFrame;
    int id;

    float progress;
    float progress_continue;

    boolean show = true;
    float radius;
    float count_rad;
    Vector2 pos_circle;
    Vector2 pos_text;

    static final float TIMEPERFRAME = 3 / 60f;
    static final float RADIUS = 54;

    public Video(int id) {
        this.id = id;
        frames = Res.video[id];
        pos_circle = new Vector2(Main.width / 2, Main.height / 2);
        pos_text = new Vector2(pos_circle.x, pos_circle.y - RADIUS - 10);
        float[] durations = new float[frames.length];
        for (int i = 0; i < durations.length; i++)
            durations[i] = 1;
        animation = new Animation(frames.length * TIMEPERFRAME, frames, durations, true);
    }

    public void update() {
        animation.update(Main.dt);

        if (animation.loops > 1) {
            progress_continue = Math.min(progress_continue + Main.dt * 3, 1);
            if (Gdx.input.justTouched())
                show = false;
        }
        count_rad = MathFuncs.loopRadians(count_rad, Main.dt * 3);


        currentFrame = animation.getTexture();

        if (show)
            progress += Main.dt * 3;
        else
            progress -= Main.dt * 3;

        progress = MathUtils.clamp(progress, 0, 1);

        Main.slowdown = progress;
        Main.isButtonPressed = true;

        if (progress <= 0)
            Main.video = null;

        radius = progress * (RADIUS + 2.5f * ((float) Math.sin(count_rad) - 1));
    }

    public void render(SpriteBatch batch) {
        batch.setColor(0, 0, 0, .75f * progress);
        batch.draw(Res.tex_pixel, 0, 0, Main.width, Main.height);
        batch.setColor(Color.WHITE);

        batch.setShader(Res.shader_cutCircle);
        Res.shader_cutCircle.setUniformf("r", radius / currentFrame.getTexture().getWidth());
        Res.shader_cutCircle.setUniformf("mid", (currentFrame.getU() + currentFrame.getU2()) / 2, (currentFrame.getV() + currentFrame.getV2()) / 2);
        batch.draw(currentFrame, pos_circle.x - RADIUS, pos_circle.y - RADIUS);
        batch.setShader(null);

        batch.setColor(1, 1, 1, progress * progress_continue);
        batch.draw(Res.tex_text_continue, pos_text.x - Res.tex_text_continue.getRegionWidth() / 2, pos_text.y);
        batch.setColor(1, 1, 1, 1);
    }
}
