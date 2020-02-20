package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_BallShard;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Pin extends Entity {
    Body body;
    boolean doDispose;

    public Pin(float x, float y) {
        pos = new Vector2((int)x, (int)y);
        body = Main.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.shape_pin, 1);
        body.setUserData(this);
        body.setTransform(Main.METERSPERPIXEL * (pos.x), Main.METERSPERPIXEL * (pos.y), 0);
    }

    public void update() {
        if (doDispose)
            dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_pin, pos.x - Res.tex_pin.getRegionWidth() / 2, pos.y-5);
    }

    public void destroy(Ball ball) {
        doDispose = true;
        float angle = MathFuncs.angleBetweenPoints(ball.pos, pos);
        float impact = 2;
        if (!Main.noFX)
            for (int i = 0; i < 10; i++)
                Main.particles.add(new Particle_BallShard(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), Res.eggPalette));
        Main.addSoundRequest(ID.Sound.GLASS);
        Main.shake();
        Game.throwConfetti(pos.x, pos.y);
        Game.onPinDestroyed();
    }

    public void dispose() {
        body = Main.destroyBody(body);
        Main.pinsToRemove.add(this);
    }
}
