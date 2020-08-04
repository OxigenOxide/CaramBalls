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
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Break extends Ball {

    Color[] palette;
    Counter counter_crackCooldown;
    int phase;

    public Ball_Break(float x, float y) {
        super(x, y, Main.height, 2);
        construct();
    }

    public Ball_Break() {
        super(Main.height, 2);
        construct();
    }

    private void construct() {
        setSpriteTexture(Res.tex_ball[21][size]);
        palette = Res.PALETTE_BASICBALL;
        lock();
        counter_crackCooldown = new Counter(1);
        counter_crackCooldown.start();
    }

    @Override
    public void update() {
        super.update();
        counter_crackCooldown.update();
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
            if (counter_crackCooldown.getProgress() == 1) {
                if (phase < 1) {
                    phase++;
                    setSpriteTexture(Res.tex_ball[21 + phase][size]);
                    counter_crackCooldown.start();
                }
            }
    }

    @Override
    public void contactBall(Ball ball) {
        super.contactBall(ball);
        if (Funcs.getClass(ball) == Ball_Main.class)
            if (counter_crackCooldown.getProgress() == 1) {
                float angle = MathFuncs.angleBetweenPoints(ball.pos, pos);
                float impact = Math.max(.3f, .3f * MathFuncs.toPPF(ball.body.getLinearVelocity().len()));
                if (phase < 1) {
                    phase++;
                    setSpriteTexture(Res.tex_ball[21 + phase][size]);
                    counter_crackCooldown.start();
                    throwParticles(impact, pos, palette, 3);
                } else explode(angle, impact);
            }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        explode(angle, impact);
    }

    boolean hasExeploded;

    public void explode(float angle, float impact) {
        if (!hasExeploded) {
            hasExeploded = true;
            throwParticles(impact, pos, palette, 10);
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
