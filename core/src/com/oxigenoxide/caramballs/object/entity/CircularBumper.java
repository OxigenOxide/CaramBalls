package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.object.SoundRequest;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class CircularBumper extends Entity {
    Sprite sprite;
    Body body;
    float radius = 8.5f;
    float wiggle;
    float size;
    boolean disappear;
    float count;

    public CircularBumper() {
        sprite = new Sprite(Res.tex_circularBumper);
        radius_spawn = sprite.getRegionWidth() / 2 + 1; // not perfect
        pos = Game.getFreePosOnTable(radius_spawn);
        if(pos==null) {
            pos = new Vector2(-100, -200);
            disappear=true;
        }
        pos.add(.5f, .5f);
        sprite.setPosition(pos.x - (int) sprite.getRegionWidth() / 2, pos.y - (int) sprite.getRegionWidth() / 2); // Correct, twice sprite.getRegionWidth()
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(radius * Main.MPP);
        Res.fixtureDef_circle.restitution = 2;
        body.createFixture(Res.fixtureDef_circle);
        Res.fixtureDef_circle.restitution = 1; //reset
        body.setUserData(this);
    }

    public void collide(float impact) {
        wiggle = Math.min(5, impact * .25f);
        Main.soundRequests.add(new SoundRequest(ID.Sound.BOUNCE,1));
    }

    public void update() {
        count+=Main.dt_one_slowed;
        if(count>200)
            disappear=true;
        wiggle = Math.max(0, wiggle - Main.dt_one_slowed * .02f);
        sprite.setSize((sprite.getRegionWidth() + wiggle * (float) Math.sin(wiggle * 20)) * size, (sprite.getRegionHeight() + wiggle * (float) Math.sin(wiggle * 20)) * size);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
        if (disappear)
            size = Math.max(0, size - Main.dt_one_slowed * .1f);
        else
            size = Math.min(1, size + Main.dt_one_slowed * .1f);
        if(size==0)
            dispose();
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        body = Main.destroyBody(body);
        Main.circularBumpersToRemove.add(this);
    }
}
