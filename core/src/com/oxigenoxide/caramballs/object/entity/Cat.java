package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Cat extends Entity {

    Body body;

    public Cat(float x, float y) {
        pos = new Vector2(x, y);
        createBody();
        body.setTransform(pos.x * Main.MPP, (pos.y) * Main.MPP, 0);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(5 * Main.MPP);
        body.createFixture(Res.fixtureDef_circle);
        body.setUserData(this);
        Filter filter = new Filter();
        filter.maskBits = (Res.MASK_PASSTHROUGH);
        filter.categoryBits = (Res.MASK_CAT);
        body.getFixtureList().first().setFilterData(filter);
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_cat, pos.x - Res.tex_cat.getRegionWidth() / 2, pos.y-5);
    }
    public void dispose(){
        Main.catsToRemove.add(this);
        body = Main.destroyBody(body);
    }
}
