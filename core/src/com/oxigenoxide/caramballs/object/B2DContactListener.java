package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.BallCapsule;
import com.oxigenoxide.caramballs.object.entity.Bullet;
import com.oxigenoxide.caramballs.object.entity.Cat;
import com.oxigenoxide.caramballs.object.entity.CircularBumper;
import com.oxigenoxide.caramballs.object.entity.Eye;
import com.oxigenoxide.caramballs.object.entity.JumpingPad;
import com.oxigenoxide.caramballs.object.entity.Spike;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Bad;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.collectable.Collectable;
import com.oxigenoxide.caramballs.object.entity.draggable.Draggable;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OrbContainer;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        java.lang.Object udA = contact.getFixtureA().getBody().getUserData();
        java.lang.Object udB = contact.getFixtureB().getBody().getUserData();

        Class classA = getClass(udA);
        Class classB = getClass(udB);

        Vector2 contactPoint = new Vector2(contact.getWorldManifold().getPoints()[0]);
        contactPoint.scl(Main.PIXELSPERMETER);


        if (classA == Ball_Main.class && classB == Ball_Main.class) {
            Ball ballA = (Ball) udA;
            Ball ballB = (Ball) udB;
            ballA.contactBall(ballB);
            Game.onBallCollide_delayed();
        }
        if (classA == Ball_Bad.class && classB == Ball_Bad.class) {
            Ball_Bad ball_badA = (Ball_Bad) udB;
            Ball_Bad ball_badB = (Ball_Bad) udA;
            ball_badB.contactBallBad(ball_badA);
            ball_badA.contactBallBad(ball_badB);
        }

        if (udB instanceof Ball && udA instanceof Ball) { // collision only called on one ball
            Ball ball0 = (Ball) udB;
            Ball ball1 = (Ball) udA;
            float impact = MathFuncs.getHypothenuse(ball0.body.getLinearVelocity().x, ball0.body.getLinearVelocity().y)
                    + MathFuncs.getHypothenuse(ball1.body.getLinearVelocity().x, ball1.body.getLinearVelocity().y);
            ball0.onCollision(contactPoint, impact);
        }

        for (int i = 0; i < 2; i++) {
            if (udA instanceof Ball) {
                Ball ball = (Ball) udA;
                float impact = MathFuncs.getHypothenuse(ball.body.getLinearVelocity().x, ball.body.getLinearVelocity().y);

                if (udB instanceof Draggable) {
                    ((Draggable) udB).onCollision();
                } else if (classB == JumpingPad.class) {
                    JumpingPad jp = (JumpingPad) udB;
                    jp.collide(ball);
                }
                if (classA == Ball_Main.class) {
                    if (classB == Ball_Bad.class) {
                        Ball_Bad ball_bad = (Ball_Bad) udB;
                        ball_bad.contactBall(ball);
                        return;
                    } else if (udB instanceof OrbContainer) {
                        OrbContainer oc = (OrbContainer) udB;
                        oc.destroy(ball);
                    } else if (classB == Pin.class) {
                        Pin pin = (Pin) udB;
                        pin.destroy(ball);
                    } else if (classB == Bullet.class) {
                        Bullet bullet = (Bullet) udB;
                        bullet.hit(ball);
                    } else if (classB == CircularBumper.class) {
                        CircularBumper c = (CircularBumper) udB;
                        c.collide(impact);
                    } else if (classB == BallCapsule.class) {
                        BallCapsule bc = (BallCapsule) udB;
                        bc.shatter();
                    } else if (classB == Eye.class) {
                        Eye eye = (Eye) udB;
                        eye.collision((Ball_Main) ball);
                    } else if (udB instanceof Collectable) {
                        Collectable c = (Collectable) udB;
                        c.pickUp((Ball) udA);
                        return; // So Ball.contact isn't called and no hit sound is played
                    }
                }

                ball.contact(udB, contactPoint, impact);

                if (!(udB instanceof Ball) && classB != Spike.class)
                    ball.onCollision(contactPoint, impact);
            }

            if (classA == Spike.class && (classB == Ball_Main.class || classB == Ball_Bad.class)) {
                Spike spike = (Spike) udA;
                spike.hitBall((Ball) udB);
            }

            if (classA == Bullet.class) {
                Bullet bullet = (Bullet) udA;
                bullet.doDispose = true;
            }

            udA = contact.getFixtureB().getBody().getUserData();
            udB = contact.getFixtureA().getBody().getUserData();
            classA = getClass(udA);
            classB = getClass(udB);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        java.lang.Object udA = contact.getFixtureA().getBody().getUserData();
        java.lang.Object udB = contact.getFixtureB().getBody().getUserData();

        for (int i = 0; i < 2; i++) {
            if (udA instanceof Ball) {
                Ball ball = (Ball) udA;
                Main.shake(MathUtils.clamp(.2f * MathFuncs.getHypothenuse(ball.body.getLinearVelocity().y, ball.body.getLinearVelocity().x), 0, 4));
                Main.setShakeAng(ball.body.getLinearVelocity().angleRad());
            }
            udA = contact.getFixtureB().getBody().getUserData();
            udB = contact.getFixtureA().getBody().getUserData();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    Class getClass(java.lang.Object ud) {
        if (ud != null)
            return ud.getClass();
        else
            return null;
    }

    Class getSuperClass(Class c) {
        if (c != null)
            return c.getSuperclass();
        else
            return null;
    }
}
