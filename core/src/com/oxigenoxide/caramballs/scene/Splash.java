package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import java.util.ArrayList;

public class Splash extends Scene {
    TextureRegion tex_splash;
    TextureRegion tex_noInternet;
    TextureRegion tex_wifiSymbol;
    Texture tex_buffer;
    Sprite sprite_wifiSymbol;
    Sprite sprite_noInternet;
    Sprite sprite;
    float alpha;
    float alpha_noInternet;
    int timeNoInternet;
    boolean goDown;
    int count;
    int dotRowLength = (int) Main.width / 2;
    LoadingBall loadingBall;
    Counter counter_dropBall;
    FrameBuffer buffer;
    float loadingProgress;
    ArrayList<TapCircle> tapCircles = new ArrayList<TapCircle>();
    ArrayList<TapCircle> tapCirclesToRemove = new ArrayList<TapCircle>();

    public Splash() {
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        counter_dropBall = new Counter(new ActionListener() {
            @Override
            public void action() {
                loadingBall.show();
            }
        }, 3).start();
        Res.queueAssets();
    }

    @Override
    public void show() {
        Main.setAdVisibility(false);
        tex_splash = Res.tex_oxigenoxide;
        tex_wifiSymbol = Res.tex_wifiSymbol;
        sprite_wifiSymbol = new Sprite(tex_wifiSymbol);
        sprite_wifiSymbol.setOrigin(8, 2);
        sprite_wifiSymbol.setPosition(Main.width / 2 - sprite_wifiSymbol.getWidth() / 2, Main.height / 2 + 20);
        sprite_wifiSymbol.setAlpha(alpha_noInternet);
        tex_noInternet = Res.tex_text_noInternet;
        sprite_noInternet = new Sprite(tex_noInternet);
        sprite_noInternet.setPosition(Main.width / 2 - tex_noInternet.getRegionWidth() / 2, Main.height / 2 - 10);
        sprite_noInternet.setAlpha(alpha_noInternet);
        loadingBall = new LoadingBall();
        sprite = new Sprite(tex_splash);
        sprite.setPosition(Main.width / 2 - tex_splash.getRegionWidth() / 2, Main.height / 2 - tex_splash.getRegionWidth() / 2);

    }

    @Override
    public void update() {
        Main.assets.update();
        loadingProgress = Main.assets.getProgress();
        counter_dropBall.update();
        if (Main.assets.isFinished() && Main.fbm.isSignedIn() && alpha == 0 && !Main.isLoaded && Main.fbm.isUpToDate()) {
            if (Main.signedIn) {
                Main.initializeResources();
                Main.onLoaded();
                Main.setSceneMenu();
            }
        }
        if (!Main.signedIn && !Main.fbm.isSignedIn()) {
            if (timeNoInternet > 200) {
                alpha_noInternet = Math.min(alpha_noInternet + .05f, 1);
                sprite_noInternet.setAlpha(alpha_noInternet);
                sprite_wifiSymbol.setAlpha(alpha_noInternet);
                sprite_wifiSymbol.setRotation(sprite_wifiSymbol.getRotation() + 1);
            }
            timeNoInternet++;
        }
        if (!goDown)
            alpha = Math.min(1, alpha + .02f);
        else {
            alpha = Math.max(0, alpha - .02f);
        }
        if (alpha == 1) {
            count++;
            if (count > 60)
                goDown = true;
        }
        sprite.setAlpha(alpha);
        loadingBall.update();

        if (Gdx.input.justTouched())
            tapCircles.add(new TapCircle(Main.tap[0].x, Main.tap[0].y));
        for (TapCircle tc : tapCircles)
            tc.update();
        tapCircles.removeAll(tapCirclesToRemove);
        tapCirclesToRemove.clear();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buffer.begin();
        Gdx.gl.glClearColor(Res.COLOR_SPLASH_BLUE.r, Res.COLOR_SPLASH_BLUE.g, Res.COLOR_SPLASH_BLUE.b, Res.COLOR_SPLASH_BLUE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        sprite.draw(batch);
        if (!Main.signedIn && timeNoInternet > 200) {
            sprite_wifiSymbol.draw(batch);
            sprite_noInternet.draw(batch);
        }
        loadingBall.drawShadow(batch);
        batch.end();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        //loadingBall.drawShadow(sr);
        loadingBall.render(sr);
        for (TapCircle tc : tapCircles)
            tc.render(sr);
        sr.end();
        batch.begin();
        loadingBall.render(batch);

        batch.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    setColor(sr, Main.assets.isFinished());
                    break;
                case 1:
                    setColor(sr, Main.fbm.isSignedIn());
                    break;
                case 2:
                    setColor(sr, alpha == 0);
                    break;
                case 3:
                    setColor(sr, Main.isLoaded);
                    break;
                case 4:
                    setColor(sr, Main.signedIn);
                    break;
            }
            sr.circle(Main.width / 2 - dotRowLength / 2 + dotRowLength / 4 * i, 5, 2, 15);
        }
        sr.end();
        buffer.end();

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        batch.disableBlending();

        Main.setNoCamEffects();
        batch.begin();
        batch.setShader(Res.shader_pixelate);
        Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);

        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        batch.setShader(null);
        batch.end();

        batch.enableBlending();
    }

    void setColor(ShapeRenderer sr, boolean b) {
        if (b)
            sr.setColor(1, 1, 1, 1);
        else
            sr.setColor(88 / 255f, 96 / 255f, 194 / 255f, 1);
    }

    @Override
    public void dispose() {
    }

    class TapCircle {
        Vector2 pos;
        float progress;

        public TapCircle(float x, float y) {
            pos = new Vector2(x, y);
        }

        public void update() {
            progress = Math.min(1, progress + Main.dt * 3);
            if (progress == 1)
                tapCirclesToRemove.add(this);
        }

        public void render(ShapeRenderer sr) {
            sr.setColor(1, 1, 1, 1 - progress);
            sr.circle(pos.x, pos.y, progress * 10);
        }
    }

    class LoadingBall {
        Vector2 pos;
        Vector2 vel;
        TextureRegion tex;
        TextureRegion tex_shadow;
        Sprite sprite_shadow;
        TextureRegion tex_filling;
        boolean visible;
        int y_floor = 30;
        final int RADIUS = 15;
        float radius_filling;
        Vector2 pos_number;
        float a_shadow;
        float progress_afterFull;

        public LoadingBall() {
            pos = new Vector2(Main.width / 2, Main.height / 2);
            pos_number = new Vector2();
            vel = new Vector2();
            tex = Res.tex_loadingBall_filled;
            tex_filling = Res.tex_loadingBall;
            tex_shadow = Res.tex_loadingBall_shadow;
            sprite_shadow = new Sprite(tex_shadow);
        }

        public void update() {
            //tex_filling.setRegionHeight((int) (tex_filling.getTexture().getHeight() * (1 - Math.pow(Main.assets.getProgress(),2))));
            if (visible) {
                vel.y -= .3f;
                pos.add(vel);
                if (Gdx.input.justTouched()) {
                    float angle = MathFuncs.angleBetweenPoints(Main.tap[0], pos);
                    float impact = 4;
                    vel.add(impact * (float) Math.cos(angle), impact * (float) Math.sin(angle));
                    Main.addSoundRequest(ID.Sound.HIT, 1, .5f);
                }
                if (pos.y < y_floor + RADIUS) {
                    pos.y = y_floor + RADIUS;
                    vel.y *= -.9f;
                    Main.addSoundRequest(ID.Sound.BOUNCE, 1, vel.y * .1f);
                }
                if (pos.x < RADIUS) {
                    pos.x = RADIUS;
                    vel.x *= -.9f;
                    Main.addSoundRequest(ID.Sound.BOUNCE, 1, vel.x * .1f);
                }
                if (pos.x > Main.width - RADIUS) {
                    pos.x = Main.width - RADIUS;
                    vel.x *= -.9f;
                    Main.addSoundRequest(ID.Sound.BOUNCE, 1, -vel.x * .1f);
                }
                pos_number.set((int) pos.x, (int) (pos.y - 2));


                if (a_shadow < 1) {
                    a_shadow = Math.min(1, a_shadow + Main.dt);
                }
                radius_filling = (float) (RADIUS * Math.min(1, Math.max(0, Math.pow(Main.assets.getProgress(), 2)) + .05f));
                float smallFactor = 1 / (1 + (pos.y - y_floor - RADIUS) * .02f);
                sprite_shadow.setPosition(pos.x - RADIUS * smallFactor, y_floor + RADIUS - 8 - tex_shadow.getRegionHeight() / 2 * smallFactor);
                sprite_shadow.setSize(tex_shadow.getRegionWidth() * smallFactor, tex_shadow.getRegionHeight() * smallFactor);
                sprite_shadow.setAlpha(a_shadow);

                if (loadingProgress > .95f)
                    progress_afterFull = Math.min(1, progress_afterFull + 2 * Main.dt);
            }
        }

        public void show() {
            visible = true;
            pos.set(Main.width / 2, Main.height + 30);
            vel.set(0, 0);
        }


        public void render(ShapeRenderer sr) {

            if (loadingProgress > .95f && visible) {
                sr.setColor(1, 1, 1, (1 - progress_afterFull));
                sr.circle((int) pos.x, (int) pos.y, RADIUS + progress_afterFull * 15);
            }

        }

        public void render(SpriteBatch batch) {

            if (visible) {
                batch.draw(tex, (int) pos.x - RADIUS, (int) pos.y - RADIUS);
                batch.draw(tex_filling, (int) pos.x - radius_filling, (int) pos.y - radius_filling, radius_filling * 2, radius_filling * 2);
                batch.setShader(Res.shader_c);
                Res.shader_c.setUniformf("c", Res.COLOR_SPLASH_BLUE);
                Main.drawNumberSignAfter(batch, (int) (loadingProgress * 100), pos_number, ID.Font.SMALL, Res.tex_number_small_percent, 0);
                batch.setShader(null);
            }

        }

        public void drawShadow(SpriteBatch batch) {
            if (visible) {
                sprite_shadow.draw(batch);
            }
        }
    }
}
