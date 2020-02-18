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

public class Ball_Obstacle extends Ball {

    Color[] palette;

    public Ball_Obstacle(float x, float y) {
        super(x, y, Main.height, 1 + (int) (Math.random() * 2));
        construct();
    }

    public Ball_Obstacle() {
        super(Main.height, 1 + (int) (Math.random() * 2));
        construct();
    }

    private void construct() {
        setSpriteTexture(Res.tex_ball[0][size]);
        palette = Res.PALETTE_BASICBALL;
        lock();
    }

    @Override
    public void update() {
        super.update();

        if (!isDisposed) {
            if (ballmain_hit != null && !ballmain_hit.isDisposed) {

                doDispose = true;
                ballmain_hit.doDispose = true;

                Ball_Main ball_new;

                if (size + 1 < 3) { // stay in same level
                    ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, (size + 1), 0);
                } else { // to next level
                    ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, 0, 1);
                    Game.pos_floorFadeStain.set(ball_new.pos);
                }

                ball_new.body.setLinearVelocity(body.getLinearVelocity().add(ballmain_hit.body.getLinearVelocity()).scl(.5f));
                Main.ballsToAdd.add(ball_new);

                ballmain_hit = null;
                Main.shake();
                Main.fbm.writeMerges();
                Main.addSoundRequest(ID.Sound.PLOP, 6);
                Game.onBallMerge();
            }

            if (doSplit) {
                split(pos_hitBy);
                doSplit = false;
            }
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
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        if (!Main.inScreenShotMode) {
            if (size == 0 || dontSplit) {
                explode(angle, impact);
            } else if (pos_danger != null) {
                pos_hitBy = pos_danger;
                doSplit = true;
            }
        }
    }

    boolean doSplit;
    Vector2 pos_hitBy;
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

    static final float SPLITANGLE = (float) Math.PI;
    boolean isSplit;

    public void split(Vector2 pos_danger) {
        if (!isSplit) {
            isSplit = true;
            //Vector2 vel = new Vector2(body.getLinearVelocity());
            //float ang= (float)Math.atan2(vel.y,vel.x);

            float ang = MathFuncs.angleBetweenPoints(pos_danger, pos);
            Vector2 vel = new Vector2((float) Math.cos(ang) * 5, (float) Math.sin(ang) * 5);
            int size = this.size - 1;
            float r = Res.ballRadius[size] + 2;
            Ball_Main ball_1 = new Ball_Main(pos.x + (float) Math.cos(ang + SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang + SPLITANGLE / 2) * r, 0, size, 0);
            Ball_Main ball_2 = new Ball_Main(pos.x + (float) Math.cos(ang - SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang - SPLITANGLE / 2) * r, 0, size, 0);
            ball_1.body.setLinearVelocity(vel.rotateRad(SPLITANGLE / 2));
            ball_2.body.setLinearVelocity(vel.rotateRad(-SPLITANGLE));

            Main.ballsToAdd.add(ball_1);
            Main.ballsToAdd.add(ball_2);
            ball_1.setNoPull();
            ball_2.setNoPull();

            setPassthrough(true);

            doDispose = true;
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
