package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.balls;

public class FarmSpike extends Entity {

    TextureRegion tex;
    int tex_index;
    float state = 0;
    boolean open;
    boolean close;
    Body body;
    Ball ballHit;
    public float radius;

    public FarmSpike(float x, float y) {
        radius_spawn = 4;
        pos = new Vector2(x, y);
        if (!Game.isPosFree(pos, radius_spawn)) {
            pos = new Vector2(-100, -100);
            dispose();
        }
        pos.add(.5f, .5f); // so hitbox is in mid
        construct();
    }

    private void construct() {
        tex = Res.tex_spike[tex_index];
        radius = 5.5f;
        radius_spawn = radius;
    }

    void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(5.5f * Main.MPP);
        body.createFixture(Res.fixtureDef_circle);
        body.setUserData(this);
        //body.setTransform(Main.MPP * (pos.x + .5f), Main.MPP * (pos.y + 3), 0);
        body.setTransform(Main.MPP * (pos.x), Main.MPP * (pos.y), 0);
    }

    public void update() {
        if (open)
            state += .05f * Main.dt_one;
        if (close)
            state -= .05f * Main.dt_one;
        state = MathUtils.clamp(state, 0, 1);
        tex_index = (int) (state * (Res.tex_farmSpike.length - 1));
        tex = Res.tex_farmSpike[tex_index];

        if (state == 1 && body == null)
            createBody();

        if (state == 0)
            dispose();

        if (ballHit != null) {
            ballHit.destroy(0, 1, pos);
            ballHit = null;
        }
    }

    public void hitBall(Ball ball) {
        ballHit = ball;
    }

    public void open() {
        open = true;
        close = false;

        for (Ball ball : balls) {
            float dst = MathFuncs.distanceBetweenPoints(pos, ball.pos);
            if (dst < ball.radius + radius)
                ball.addVelocity(-2f, 2f);
        }
    }

    public void close() {
        open = false;
        close = true;
    }

    public void render(SpriteBatch batch) {
        //Funcs.setShaderNull(batch);
        //batch.draw(tex, (int) (pos.x - tex.getRegionWidth() / 2), (int) pos.y);
        Funcs.setShaderNull(batch);
        batch.draw(tex, (int) (pos.x - tex.getRegionWidth() / 2), (int) (pos.y - 7.5f));

    }

    public void renderOnFloor(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        batch.draw(Res.tex_farmSpike_hole[tex_index], (int) (pos.x - tex.getRegionWidth() / 2), (int) (pos.y - 7.5f));
    }

    public void dispose() {
        body = Main.destroyBody(body);
    }
}
