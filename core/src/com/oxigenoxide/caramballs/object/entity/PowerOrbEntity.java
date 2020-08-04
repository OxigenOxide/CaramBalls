package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class PowerOrbEntity extends Entity {
    Sprite sprite;
    Body body;
    float size;
    boolean disappear;
    boolean doDispose;
    float count;
    Color[] palette = new Color[]{new Color(), new Color(), new Color(), new Color()};
    float count_colorWeight;

    public PowerOrbEntity() {
        sprite = new Sprite(Res.tex_powerOrb[0]);
        radius_spawn = sprite.getRegionWidth() / 2 + 1; // not perfect
        pos = Game.getFreePos(radius_spawn);
        if (pos == null) {
            pos = new Vector2(-100, -200);
            disappear = true;
        }
        pos.add(.5f, .5f);
        sprite.setPosition(pos.x - (int) sprite.getRegionWidth() / 2, pos.y - (int) sprite.getRegionWidth() / 2); // Correct, twice sprite.getRegionWidth()
        createBody();
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_powerOrb);
        body.setUserData(this);
    }

    public void update() {
        pos.set(body.getPosition());
        pos.scl(Main.PPM);

        count_colorWeight = MathFuncs.loopRadians(count_colorWeight, Main.dt * 3);

        Funcs.combinePalettes(palette, Res.PALETTE_POWERORB_0, Res.PALETTE_POWERORB_1, (1 + (float) Math.sin(count_colorWeight)) / 2);

        count += Main.dt_one_slowed;
        if (count > 200)
            disappear = true;
        sprite.setSize((sprite.getRegionWidth()) * size, (sprite.getRegionHeight()) * size);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
        if (disappear)
            size = Math.max(0, size - Main.dt_one_slowed * .1f);
        else
            size = Math.min(1, size + Main.dt_one_slowed * .1f);

        if (size == 0)
            dispose();

        if (doDispose)
            dispose();
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        Main.setPalette(palette);
        sprite.draw(batch);
        batch.setShader(null);
    }

    public void dispose() {
        body = Main.destroyBody(body);
        Main.powerOrbEntitiesToRemove.add(this);
    }

    public void collect() {
        doDispose = true;
        body.getFixtureList().first().setSensor(true);
    }
}
