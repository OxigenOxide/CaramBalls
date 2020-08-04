package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;

public class SpikeWall {
    Body body_lower;
    Body body_upper;
    Body body_reference;
    Joint prismaticJoint_lower;
    Joint prismaticJoint_upper;

    boolean launched;

    static final float MOTORSPEED = 300;
    static final float MAXMOTORFORCE = 100;
    static final float OPPOSEDFORCE = 100;

    public SpikeWall() {

    }

    public void update() {
        if (launched) {
            body_lower.applyForceToCenter(0, Main.dt_one * (OPPOSEDFORCE * Math.max(0, -body_lower.getLinearVelocity().y)), true); // note: math.max
            body_upper.applyForceToCenter(0, Main.dt_one * (OPPOSEDFORCE * Math.min(0, -body_upper.getLinearVelocity().y)), true); // note: math.min
        }
    }

    public void render(SpriteBatch batch) {
        if (launched) {
            batch.draw(Res.tex_spikeWall_lower, -10, body_lower.getPosition().y * Main.PPM - Res.tex_spikeWall_lower.getRegionHeight());
            batch.draw(Res.tex_spikeWall_upper, -10, body_upper.getPosition().y * Main.PPM);
        }
    }

    public void reset() {
        launched = false;
        Main.destroyBody(body_lower);
        Main.destroyBody(body_upper);
    }

    public void launch() {
        reset();
        createBodies();
        //Game.makeObstaclesDisappear();
        launched = true;
    }

    void createBodies() {
        body_lower = Main.world.createBody(Res.bodyDef_dynamic);
        body_lower.createFixture(Res.fixtureDef_spikeWall_lower);
        body_lower.setTransform(0, 0, 0);
        body_lower.setFixedRotation(true);
        body_lower.setUserData(this);

        body_upper = Main.world.createBody(Res.bodyDef_dynamic);
        body_upper.createFixture(Res.fixtureDef_spikeWall_upper);
        body_upper.setTransform(0, Main.MPP * 192, 0);
        body_upper.setFixedRotation(true);
        body_upper.setUserData(this);

        body_reference = Main.world.createBody(Res.bodyDef_static);

        PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
        prismaticJointDef.bodyA = body_lower;
        prismaticJointDef.bodyB = body_reference;
        prismaticJointDef.localAxisA.set(0, 1);
        prismaticJointDef.enableMotor = true;
        prismaticJointDef.motorSpeed = -MOTORSPEED;
        prismaticJointDef.maxMotorForce = MAXMOTORFORCE;

        prismaticJoint_lower = Main.world.createJoint(prismaticJointDef);

        prismaticJointDef = new PrismaticJointDef();
        prismaticJointDef.bodyA = body_upper;
        prismaticJointDef.bodyB = body_reference;
        prismaticJointDef.enableMotor = true;
        prismaticJointDef.motorSpeed = MOTORSPEED;
        prismaticJointDef.maxMotorForce = MAXMOTORFORCE;
        prismaticJointDef.localAxisA.set(0, 1);

        prismaticJoint_upper = Main.world.createJoint(prismaticJointDef);
    }
}
