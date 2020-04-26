package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Ball_Inflate extends Ball {

    Color[] palette;
    boolean doInflate;
    Counter counter_inflateCooldown;

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
        counter_inflateCooldown = new Counter(1);
        counter_inflateCooldown.start();
    }

    @Override
    public void update() {
        super.update();
        counter_inflateCooldown.update();
        if (doInflate) {
            doInflate = false;
            inflate();

        }
    }

    public void render(SpriteBatch batch) {
        batch.setShader(null);
        super.render(batch);
        batch.setShader(Res.shader_palette);
        Main.setPalette(palette);
        sprite.draw(batch);
        batch.setShader(null);
        render_shield_shine(batch);
    }

    @Override
    public void drawSelectionRing(SpriteBatch batch) {

    }

    @Override
    public void onCollision(Vector2 p, float impact, Object object_hit) {
        super.onCollision(p,impact,object_hit);
        if (Funcs.getClass(object_hit) == Ball_Main.class)
            if (counter_inflateCooldown.getProgress() == 1) {
                doInflate = true;
                counter_inflateCooldown.start();
            }
    }

    void inflate() {
        if (size < 3) {
            size++;
            body.destroyFixture(body.getFixtureList().first());
            body.createFixture(Res.fixtureDef_ball[size]);
            radius = body.getFixtureList().first().getShape().getRadius() * Main.PPM;
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
