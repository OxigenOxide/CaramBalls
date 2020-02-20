package com.oxigenoxide.caramballs.object.entity.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.Entity;

public class Collectable extends Entity {
    Sprite sprite;
    Body body;
    boolean doDispose;
    float factor_height;
    float factor_width;
    float heightOffset;
    float heightVel;
    float count_nextBob;

    Collectable(TextureRegion texture) {
        sprite = new Sprite(texture);
        pos = Game.getFreePosOnTable(5);
        body = Main.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.fixtureDef_collectable);
        body.setUserData(this);

        if (pos == null) {
            pos = new Vector2(-100, -100);
            dispose();
        }
        body.setTransform(pos.x * Main.METERSPERPIXEL, pos.y * Main.METERSPERPIXEL, 0);
        sprite.setPosition((int) (pos.x - sprite.getRegionWidth() / 2), (int) (pos.y - sprite.getRegionHeight() / 2));
    }

    public void update() {
        if (doDispose) {
            doDispose = false;
            dispose();
        }
        heightOffset += heightVel;
        heightVel -= Game.GRAVITY;
    }

    void jump() {
        heightVel = 5;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void pickUp(Ball ball) {
        doDispose = true;
    }

    public void dispose() {
        body = Main.destroyBody(body);
        Main.collectablesToRemove.add(this);
    }
}
