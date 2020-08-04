package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.RewardBall;
import com.oxigenoxide.caramballs.object.TTPText;
import com.oxigenoxide.caramballs.object.Title;
import com.oxigenoxide.caramballs.object.Wheel;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Options;
import com.oxigenoxide.caramballs.object.button.Button_Tutorial;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;
import com.oxigenoxide.caramballs.utils.Funcs;

public class DownGrading extends Scene {

    public Wheel wheel;
    FrameBuffer buffer;
    Texture tex_buffer;

    public DownGrading() {
        wheel = new Wheel();
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getHeight(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void show() {
        wheel.set(Main.rewardBall.getLevel());
    }

    @Override
    public void update() {
        wheel.update();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        wheel.render(batch);
        batch.end();

        buffer.end(); // BUFFER END

        batch.disableBlending();

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Main.setNoCamEffects();
        batch.begin();

        batch.setShader(Res.shader_pixelate);
        Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);

        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        batch.setShader(null);

        batch.end();
        Main.setCamEffects();
        batch.enableBlending();
    }

    @Override
    public void renderOnTop(SpriteBatch batch, ShapeRenderer sr) {

    }

    @Override
    public void dispose() {
        buffer.dispose();
    }
}
