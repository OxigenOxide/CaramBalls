package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;

public class Scooper extends Entity {
    Sprite sprite;
    Sprite sprite_shine;
    float ang;
    Body body;
    Body body_pivot;
    RevoluteJoint revJoint;
    Vector2 anchor;

    TextureRegion tex_shine;
    TextureRegion tex;

    boolean enableMotor;
    float motorSpeed = 1;
    float maxMotorTorque = 1;
    float progress_grow;
    boolean shrink;

    boolean isDisposed;
    /*
    Idea for avoiding clipping:
        While the texture is glowing make the body a sensor and make in push the objects away that are inside it.
     */

    public Scooper() {
        super();
        radius_spawn = 10;
    }

    void construct() {
        sprite = new Sprite(tex);
        sprite_shine = new Sprite(tex_shine);
        createBody();
        sprite.setPosition(body.getPosition().x * Main.PPM - sprite.getWidth() / 2, body.getPosition().y * Main.PPM - sprite.getHeight() / 2);
        sprite_shine.setPosition(body.getPosition().x * Main.PPM - sprite_shine.getWidth() / 2, body.getPosition().y * Main.PPM - sprite_shine.getHeight() / 2 + 3);
    }

    void createBoxBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(sprite.getWidth() * Main.MPP / 2, sprite.getHeight() * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);
        body.setUserData(this);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        createJoint();
    }

    void createBody() {
        createBoxBody();
    }

    void createJoint(){
        body_pivot = Main.world.createBody(Res.bodyDef_static);
        body_pivot.setTransform(pos.x * Main.MPP + anchor.x, pos.y * Main.MPP + anchor.y, 0); // at the position of the pivot

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = body;
        revoluteJointDef.bodyB = body_pivot;
        revoluteJointDef.localAnchorA.set(anchor);
        revoluteJointDef.localAnchorB.set(0, 0);
        revoluteJointDef.enableMotor = enableMotor;
        revoluteJointDef.motorSpeed = motorSpeed;
        revoluteJointDef.maxMotorTorque = maxMotorTorque;

        revJoint = (RevoluteJoint) Main.world.createJoint(revoluteJointDef);
    }

    @Override
    public void update() {

        if(isDisposed)
            return;

        super.update();

        ang = body.getAngle();
        sprite.setRotation((float) Math.toDegrees(ang));
        sprite.setOriginCenter();
        sprite_shine.setOriginCenter();
        sprite_shine.setRotation((float) Math.toDegrees(ang));
        sprite.setAlpha(progress_grow);
        sprite_shine.setAlpha(progress_grow);
        //revJoint.setMotorSpeed(2 * (float) Math.PI * Main.test_float);
        sprite.setSize(progress_grow * sprite.getRegionWidth(), progress_grow * sprite.getRegionHeight());
        sprite_shine.setSize(progress_grow * sprite_shine.getRegionWidth(), progress_grow * sprite_shine.getRegionHeight());
        sprite.setPosition(body.getPosition().x * Main.PPM - sprite.getWidth() / 2, body.getPosition().y * Main.PPM - sprite.getHeight() / 2);
        sprite_shine.setPosition(body.getPosition().x * Main.PPM - sprite_shine.getWidth() / 2, body.getPosition().y * Main.PPM - sprite_shine.getHeight() / 2 + 3 * progress_grow);

        if (shrink)
            progress_grow = Math.max(0, progress_grow - Main.dt * 2);
        else
            progress_grow = Math.min(1, progress_grow + Main.dt * 2);

        if(progress_grow==0)
            dispose();

    }

    public void disappear(){
        shrink=true;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        sprite.draw(batch);
        sprite_shine.draw(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        isDisposed=true;
        body = Main.destroyBody(body);
        Main.scoopersToRemove.add(this);
    }
}
