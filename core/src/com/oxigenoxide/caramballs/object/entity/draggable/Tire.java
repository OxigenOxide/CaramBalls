package com.oxigenoxide.caramballs.object.entity.draggable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.SoundRequest;
import com.oxigenoxide.caramballs.utils.Counter;

public class Tire extends Draggable {
    Sprite sprite_base;
    Counter counter_wiggle;

    public Tire() {
        super(23);
        sprite = new Sprite(Res.tex_tire_top);
        sprite_base = new Sprite(Res.tex_tire_base);
        createBody(22);
        counter_wiggle = new Counter(1);
    }

    @Override
    public void update() {
        super.update();
        pos.set((int)pos.x,(int)pos.y);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2 + 5);

        float wiggleFactor = (1 - counter_wiggle.getProgress()) * (.04f - .04f * (float) Math.cos(7.5 * Math.PI * (1 - counter_wiggle.getProgress())));
        sprite_base.setSize(sprite_base.getTexture().getWidth() * (1 + wiggleFactor), sprite_base.getTexture().getHeight() * (1 + wiggleFactor));
        sprite_base.setPosition(pos.x - sprite_base.getWidth() / 2, pos.y - 25 * (1 + wiggleFactor));
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        counter_wiggle.update();
    }

    public void createBody(float radius){
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_tire);
        body.setTransform(pos.x * Main.METERSPERPIXEL, pos.y * Main.METERSPERPIXEL, 0);
        body.setUserData(this);
    }

    public void onCollision() {
        counter_wiggle.start();
        Main.soundRequests.add(new SoundRequest(ID.Sound.BOUNCE,1));
    }

    @Override
    public void setPivotPosition() {
        pos_pivot.set(16, 0);
        pos_pivot.rotateRad(body.getAngle());
        pos_pivot.add(pos.x, pos.y);
        pos_pivot.set((int)pos_pivot.x,(int)pos_pivot.y);
        pos_pivot_visual.set(pos_pivot.x, pos_pivot.y + 5);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite_base.draw(batch);
        super.render(batch);
    }
}
