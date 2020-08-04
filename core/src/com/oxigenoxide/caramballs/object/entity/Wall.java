package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Wall extends Entity {
    static final float WIDTH = 50;
    static final float HEIGHT = 8;
    Body body;

    public Wall(float x, float y) {
        pos = new Vector2(x, y);
        createBody();
    }

    void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(WIDTH * Main.MPP / 2, HEIGHT * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);
        body.setTransform(Main.MPP * (pos.x), Main.MPP * (pos.y), 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_wall, pos.x - WIDTH / 2, pos.y - HEIGHT / 2);
    }

    @Override
    public void dispose() {
        Main.wallsToRemove.add(this);
        Main.destroyBody(body);
    }
}
