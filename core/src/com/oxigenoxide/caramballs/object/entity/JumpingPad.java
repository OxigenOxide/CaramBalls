package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.scene.Game;

public class JumpingPad extends Entity {
    Body body;
    TextureRegion tex;
    Sprite sprite;
    float wiggle;
    boolean doDispose;

    public JumpingPad() {
        radius_spawn = 10;
        pos = Game.getFreePosOnTable(radius_spawn);
        if(pos==null) {
            pos = new Vector2(-100, 100);
            doDispose=true;
        }
        pos.add(.5f,0);
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_jumpingPad);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        body.setUserData(this);
        tex = Res.tex_jumpingPad;
        sprite = new Sprite(tex);
    }

    public void update() {
        wiggle = Math.max(0, wiggle - .05f * Main.dt_one_slowed);
        sprite.setSize(tex.getRegionWidth() +  4 * (float) Math.sin(wiggle * Math.PI * 2), tex.getRegionHeight() +  4 * (float) Math.sin(wiggle * Math.PI * 2));
        sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y - sprite.getHeight() / 2);

        if(doDispose)
            dispose();
    }

    void wiggle() {
        wiggle = 1;
    }

    public void collide(Ball ball) {
        ball.velY = 5;
        Vector2 vec_dir = new Vector2(ball.pos);
        vec_dir.sub(pos);
        float mul = .25f;
        ball.addVelocity(vec_dir.x * mul, vec_dir.y * mul);
        wiggle();
        Main.addSoundRequest(ID.Sound.BOUNCE);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose(){
        body = Main.destroyBody(body);
        Main.jumpingPadsToRemove.add(this);
    }
}
