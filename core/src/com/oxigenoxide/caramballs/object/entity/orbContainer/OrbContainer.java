package com.oxigenoxide.caramballs.object.entity.orbContainer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class OrbContainer extends Entity {
    float height;
    TextureRegion tex;
    Body body;
    float velY;
    boolean doDispose;
    boolean isPassthrough;
    ActionListener destroy_delayed;
    Ball ball_hitBy;

    int orbAmount = 10;

    public OrbContainer(TextureRegion tex) {
        this.tex = tex;
        pos = Game.getRandomPosOnTable(tex.getRegionWidth(), tex.getRegionHeight());
        height = Main.height;
        createBody();
        body.setTransform(pos.x * Main.MPP, (pos.y + 3) * Main.MPP, 0);
        setPassthrough(true);
        radius_spawn = 7;
    }

    public OrbContainer(float x, float y, float height) {
        pos = new Vector2(x, y);
        this.height = height;
        createBody();
        body.setTransform(pos.x * Main.MPP, (pos.y + 3) * Main.MPP, 0);
        setPassthrough(true);
        radius_spawn = 7;
    }

    public void createBody() {

    }

    public void update() {
        height += velY;
        height = Math.max(0, height);
        if (height == 0) {
            if (velY < 0) {
                velY = -velY * .5f;
                if (velY < 3) {
                    velY = 0;
                    setPassthrough(false);
                }
            }
        } else
            velY += Game.GRAVITY_PIXELS * .2f;

        if (destroy_delayed != null) {
            destroy_delayed.action();
            destroy_delayed = null;
        }

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
                        Ball_Orb ball_new = new Ball_Orb(pos.x, pos.y, 0);
                        ball_new.setVelocity(hitImpact * (float) Math.cos(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2), hitImpact * (float) Math.sin(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2));
                        Main.balls.add(ball_new);
                    }
                }
            };
            Main.shake();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, (int) (pos.x - tex.getRegionWidth() / 2), (int) (pos.y - 3 + height));
    }

    public void dispose() {
        body = Main.destroyBody(body);
        body = null;
        Main.orbContainersToRemove.add(this);
    }

    public void setPassthrough(boolean b) {
        if (b) {
            isPassthrough = true;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (Res.MASK_PASSTHROUGH);
            body.getFixtureList().first().setFilterData(filter);
        } else {
            isPassthrough = false;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (short) (Res.MASK_PASSTHROUGH | Res.MASK_ZERO);
            body.getFixtureList().first().setFilterData(filter);
        }
    }
}