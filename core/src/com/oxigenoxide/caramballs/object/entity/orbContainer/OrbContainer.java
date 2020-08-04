package com.oxigenoxide.caramballs.object.entity.orbContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Orb;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Funcs;

public class OrbContainer extends Entity {
    float height;
    TextureRegion tex;
    Sprite sprite;
    Body body;
    float velY;
    boolean doDispose;
    boolean isPassthrough;
    ActionListener destroy_delayed;
    Ball ball_hitBy;

    int orbAmount = 5;
    float size;

    final static float ASCENDHEIGHT = 40;

    boolean ascend;

    public OrbContainer(TextureRegion tex) {
        this.tex = tex;
        sprite = new Sprite(tex);
        pos = Game.getRandomPosOnTable(tex.getRegionWidth(), tex.getRegionHeight());
        height = Main.height;
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        setPassthrough(true);
        radius_spawn = 6;
    }

    public OrbContainer(float x, float y, float height) {
        pos = new Vector2(x, y);
        radius_spawn = 6;
//        if (!Game.isPosFree(pos, radius_spawn)) {
//            doDispose = true;
//            pos.set(1000, -400);
//        }
        this.height = height;
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        setPassthrough(true);
    }

    public OrbContainer(float x, float y) {
        pos = new Vector2(x, y);
        radius_spawn = 6;
//        if (!Game.isPosFree(pos, radius_spawn)) {
//            doDispose = true;
//            pos.set(1000, -400);
//        }
        height = 0;
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        setPassthrough(true);
        ascend = true;
    }

    public void createBody() {
    }

    public void update() {

        if (ascend) {
            height = size * ASCENDHEIGHT;
            if (size == 1) { // then drop
                ascend = false;
                velY = 0;
            }
        } else {
            height += velY * Main.dt_one;
            height = Math.max(0, height);
            if (height == 0) {
                if (velY < 0) {
                    velY = -velY * .5f;
                    if (velY < 3) {
                        velY = 0;
                    }
                    setPassthrough(false);
                }
            } else
                velY += Game.GRAVITY_PIXELS * .2f * Main.dt_one;
        }
        if (destroy_delayed != null) {
            destroy_delayed.action();
            destroy_delayed = null;
        }


        sprite.setSize(sprite.getRegionWidth() * (.7f + .3f * size), sprite.getRegionHeight() * (.7f + .3f * size));
        sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y - sprite.getHeight() / 2 + height));

        size = Math.min(size + Main.dt * 2, 1);

        if (doDispose)
            dispose();
    }

    float SPREADORBS = (float) Math.PI * .75f;


    float hitImpact;
    float hitAngle;

    public void destroy(Ball ball) {
        if (!doDispose) {
            doDispose = true;

            ball_hitBy = ball;

            hitAngle = ball_hitBy.body.getLinearVelocity().angleRad();
            hitImpact = ball_hitBy.body.getLinearVelocity().len();

            destroy_delayed = new ActionListener() {
                @Override
                public void action() {
                    for (int i = 0; i < orbAmount; i++) {
                        Ball_Orb ball_new = new Ball_Orb(pos.x, pos.y);
                        ball_new.setVelocity(hitImpact * (float) Math.cos(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2), hitImpact * (float) Math.sin(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2));
                        Main.balls.add(ball_new);
                    }
                }
            };
            Main.shake();
        }
    }

    public void render(SpriteBatch batch) {

        if (size != 1) {
            batch.setShader(Res.shader_c_over);
            Res.shader_c_over.setUniformf("c", 1, 1, 1, (1 - size));
        }
        else
            Funcs.setShaderNull(batch);

        sprite.draw(batch);
        batch.setShader(null);
    }

    public void drawShadow(ShapeRenderer sr) {
        Main.drawShadow(sr, pos.x, pos.y, sprite.getWidth());
    }


    public void dispose() {
        body = Main.destroyBody(body);
        body = null;
        Main.orbContainersToRemove.add(this);
    }

    public void setPassthrough(boolean b) {
        if (b && !isPassthrough) {
            isPassthrough = true;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (Res.MASK_PASSTHROUGH);
            body.getFixtureList().first().setFilterData(filter);
        } else if (isPassthrough) {
            isPassthrough = false;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (short) (Res.MASK_PASSTHROUGH | Res.MASK_ZERO);
            body.getFixtureList().first().setFilterData(filter);
        }
    }
}