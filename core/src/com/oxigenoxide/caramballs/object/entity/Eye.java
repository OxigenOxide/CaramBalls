package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.Projection;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Eye;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Eye extends Entity {
    TextureRegion tex;
    Sprite sprite;
    Projection projection;
    Vector2 pos_eye;
    float count_float;
    Body body;
    boolean doDispose;
    int tier;
    int type;

    public Eye(float x, float y) { // not tested
        radius_spawn = 7;
        tex = Res.tex_eye[0];
        sprite = new Sprite(tex);
        sprite.setSize(0,0);
        pos = new Vector2(x, y);
        pos_eye = new Vector2();
        projection = new Projection(pos.x, pos.y, 20, type, 0);
        createBody();
    }

    public Eye() {
        tier = (int) (Math.random() * 3);
        type = (int) (Math.random() * 4);
        radius_spawn = 15;
        pos = Game.getFreePosOnTable(radius_spawn);
        if (pos == null) {
            pos = new Vector2(-100, 0);
            Main.eyesToRemove.add(this);
        }
        pos_eye = new Vector2();
        tex = Res.tex_eye[type];
        sprite = new Sprite(tex);
        sprite.setSize(0,0);
        projection = new Projection(pos.x, pos.y, 20, type, tier);
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        Res.fixtureDef_circle_noWall.shape.setRadius(6 * Main.MPP);
        body.createFixture(Res.fixtureDef_circle_noWall);
        body.setUserData(this);
    }

    public void update() {
        pos.set(body.getPosition());
        pos.scl(Main.PPM);

        count_float = (float) ((count_float + Main.dt_one * .05) % (2 * Math.PI));
        pos_eye.set(pos.x, pos.y + (int) (3 * Math.sin(count_float)));
        sprite.setPosition((int) (pos_eye.x - (float) Math.floor(sprite.getRegionWidth() / 2)), (int) (pos_eye.y - (float) Math.floor(sprite.getHeight() / 2)));
        sprite.setSize(sprite.getRegionWidth()+(sprite.getRegionWidth() - sprite.getRegionWidth())*.1f,
                sprite.getHeight()+(sprite.getRegionHeight() - sprite.getHeight())*.1f);
        projection.setPosition(pos_eye.x, pos_eye.y);
        projection.update();

        body.setLinearVelocity(body.getLinearVelocity().x * .8f, body.getLinearVelocity().y * .8f);
        for (Ball_Main ball_main : Main.mainBalls) {
            float dst = MathFuncs.distanceBetweenPoints(ball_main.pos, pos);
            if (dst < 50) {
                float ang = MathFuncs.angleBetweenPoints(ball_main.pos, pos);
                body.applyForceToCenter(1 / dst * (float) Math.cos(ang) * 60, 1 / dst * (float) Math.sin(ang) * 60, true);
            }
        }

        if (!MathFuncs.pointInRectangle(pos.x, pos.y, -5, -5, Main.width + 10, Main.height + 10))
            dispose();

            if (doDispose)
                dispose();
    }

    public void dispose() {
        Main.eyesToRemove.add(this);
        body = Main.destroyBody(body);
        releaseProjection();
    }

    public void collision(Ball_Main ball) { // Up untill now has been called only once in a lifetime
        doDispose = true;
        float factor = .3f;
        Main.particles.add(new Particle_Eye(pos.x, pos.y, factor * (pos.x - ball.pos.x), factor * (pos.y - ball.pos.y), type));
        ball.createProjection(type, tier);
    }

    public void releaseProjection() {
        if(projection!=null) {
            Main.projections.add(projection);
            projection.onRelease();
            projection = null;
        }
    }

    public void drawProjection(ShapeRenderer sr) {
        projection.render(sr);
    }

    //public void drawShadow(ShapeRenderer sr) {
    //    Main.drawShadow(sr, pos, 8);
    //}

    public void render(SpriteBatch batch, ShapeRenderer sr) {
        render(batch);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
