package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.PlusMessage;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_PowerOrb extends Ball {

    private static final float RADIUS = 3f;

    static final float SPAWNHEIGHT = 30;

    Counter counter_blink;
    float count_startBlinking = 5;
    boolean isBlinking;
    boolean visible = true;
    int blinks;
    static final int MAXBLINKS = 20;
    static final float BLINKTIME = .1f;
    float count_life;
    float count_colorWeight;

    Color[] palette = new Color[]{new Color(), new Color(), new Color(), new Color()};

    public Ball_PowerOrb() {
        super(SPAWNHEIGHT, 1);
        construct();
    }

    private void construct() {
        radius = RADIUS;
        radius_spawn = radius + 1;
        body.getFixtureList().first().getShape().setRadius(radius * Main.MPP);
        setSpriteTexture(Res.tex_powerOrb[0]);
        sizeFactor = 0;
        doWiggle = false;
        lock();
        counter_blink = new Counter(new ActionListener() {
            @Override
            public void action() {
                counter_blink.start();
                visible = !visible;
                blinks++;
                if (blinks >= MAXBLINKS)
                    dispose();
            }
        }, BLINKTIME);
    }

    @Override
    public void update() {
        super.update();

        count_colorWeight = MathFuncs.loopRadians(count_colorWeight, Main.dt * 3);

        Funcs.combinePalettes(palette, Res.PALETTE_POWERORB_0, Res.PALETTE_POWERORB_1, (1 + (float) Math.sin(count_colorWeight)) / 2);

        counter_blink.update();

        if (!isBlinking) {
            if (count_startBlinking > 0)
                count_startBlinking -= Main.dt;
            else {
                isBlinking = true;
                counter_blink.start();
            }
        }
        count_life += Main.dt;

        if (body != null && count_life > 1)
            for (Ball_Main ball : Main.mainBalls) {
                if (MathFuncs.distanceBetweenPoints(ball.pos, pos) < 15 && !ball.arePowerOrbsSaturated()) {
                    float ang = MathFuncs.angleBetweenPoints(pos, ball.pos);
                    body.setLinearVelocity(body.getLinearVelocity().x + (float) Math.cos(ang) * 2, body.getLinearVelocity().y + (float) Math.sin(ang) * 2);
                }
            }

        if (ascend) ascend();
    }

    @Override
    void update_trail() {
        // no trail
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            batch.setShader(Res.shader_palette);
            Main.setPalette(palette);
            sprite.draw(batch);
            batch.setShader(null);
        }
    }

    @Override
    public void drawSelectionRing(SpriteBatch batch) {

    }

    @Override
    public void onCollision(Vector2 p, float impact, Object object_hit) {
        super.onCollision(p,impact,object_hit);
    }
/*
    @Override
    public void contactBall_pre(Ball ball) {
        super.contactBall_pre(ball);
        if (Funcs.getClass(ball) == Ball_Main.class) {
            doDispose = true;
            body.getFixtureList().first().setSensor(true);
        }
    }
*/
    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        explode(angle, impact);
    }

    public void explode(float angle, float impact) {

    }

    @Override
    public void renderShadow(ShapeRenderer sr) {
        if (visible) super.renderShadow(sr);
    }

    @Override
    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) {
    }

    public int getSize() {
        return size;
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        // no trail
    }

    int getType() {
        switch (Game.difficulty) {
            case ID.Difficulty.INTRO:
                return (int) (Math.random() * 1);
            case ID.Difficulty.EASY:
                return (int) (Math.random() * 2);
            case ID.Difficulty.NORMAL:
                return (int) (Math.random() * 3);
            case ID.Difficulty.HARD:
                return 1 + (int) (Math.random() * 2);
            case ID.Difficulty.INSANE:
                return 1 + (int) (Math.random() * 3);
        }
        return 3;
    }

    public void collect() {
        doDispose = true;
        body.getFixtureList().first().setSensor(true);
    }
}
