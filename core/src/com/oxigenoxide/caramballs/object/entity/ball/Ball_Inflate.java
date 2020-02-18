package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Inflate extends Ball {

    Color[] palette;
    boolean doInflate;

    public Ball_Inflate(float x, float y) {
        super(x, y, Main.height, 0);
        construct();
    }

    public Ball_Inflate() {
        super(Main.height, 0);
        construct();
    }

    private void construct() {
        setSpriteTexture(Res.tex_ball[20][size]);
        palette = Res.PALETTE_BASICBALL;
        lock();
    }

    @Override
    public void update() {
        super.update();
        if (doInflate) {
            doInflate = false;
            inflate();
        }
    }

    public void render(SpriteBatch batch) {
        batch.setShader(null);
        super.render(batch);
        batch.setShader(Res.shader_palette);
        Res.shader_palette.setUniformf("color0", palette[0].r, palette[0].g, palette[0].b, 1);
        Res.shader_palette.setUniformf("color1", palette[1].r, palette[1].g, palette[1].b, 1);
        Res.shader_palette.setUniformf("color2", palette[2].r, palette[2].g, palette[2].b, 1);
        Res.shader_palette.setUniformf("color3", palette[3].r, palette[3].g, palette[3].b, 1);
        sprite.draw(batch);
        batch.setShader(null);
        render_shield_shine(batch);
    }

    @Override
    public void drawSelectionRing(SpriteBatch batch) {

    }

    @Override
    public void onCollision(Vector2 p, float impact) {
        super.onCollision(p, impact);
        doInflate = true;
    }

    void inflate() {
        if (size < 2) {
            size++;
            body.destroyFixture(body.getFixtureList().first());
            body.createFixture(Res.fixtureDef_ball[size]); // TODO: FIX CRASH
            radius = body.getFixtureList().first().getShape().getRadius() * Main.PIXELSPERMETER;
            setSpriteTexture(Res.tex_ball[20][size]);
        }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        if (!Main.inScreenShotMode) {
            if (size == 0 || dontSplit) {
                explode(angle, impact);
            }
        }
    }

    boolean hasExeploded;

    public void explode(float angle, float impact) {
        if (!hasExeploded) {
            hasExeploded = true;
            impact = Math.min(impact, 4);
            if (!Main.noFX) {
                for (int i = 0; i < getParticleAmount(); i++) {
                    Main.particlesToAdd.add(new Particle_Ball(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), palette));
                }
            }
            super.explode(angle, impact);
        }
    }


    public int getSize() {
        return size;
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(palette[1].r, palette[1].g, palette[1].b, 1);
        super.drawTrail(sr);
    }
}
