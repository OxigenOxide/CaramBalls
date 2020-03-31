package com.oxigenoxide.caramballs.object.entity.draggable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.scene.Game;

public class Draggable extends Entity {
    Body body;
    Sprite sprite;
    boolean doDispose;
    public Vector2 pos_pivot;
    public Vector2 pos_pivot_visual;
    float size;
    boolean disappear;
    boolean isDisposed;

    public Draggable(int radius_spawn) {
        this.radius_spawn = radius_spawn;
        pos = Game.getFreePosOnTable(radius_spawn);
        pos_pivot = new Vector2();
        pos_pivot_visual = new Vector2();
        if (pos == null) {
            pos = new Vector2(-500, 100);
            doDispose = true;
        }
    }

    void createBody(float radius) {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        Res.fixtureDef_circle.shape.setRadius(radius * Main.MPP);
        body.createFixture(Res.fixtureDef_circle);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        body.setUserData(this);
    }

    public void update() {

        if (disappear) {
            size = Math.max(0, size - .05f * Main.dt_one_slowed);
            if (size == 0) {
                dispose();
                return;
            }
        } else if (size < 1) {
            size = Math.min(1, size + .05f * Main.dt_one_slowed);
        }


        pos.set(body.getPosition());
        pos.scl(Main.PPM);
        setPivotPosition();

        body.setAngularVelocity(body.getAngularVelocity() * .9f);
        body.setLinearVelocity(body.getLinearVelocity().x * .9f, body.getLinearVelocity().y * .9f);
    }

    public void setPivotPosition() {
        pos_pivot.set(0, 0);
        pos_pivot_visual.set(0, 0);
    }

    public void dispose() {
        isDisposed = true;
        Main.draggablesToRemove.add(this);
        body = Main.destroyBody(body);
    }

    public void drag(float dx, float dy) {
        if (!isDisposed)
            body.applyForce(dx, dy, pos_pivot.x * Main.MPP, pos_pivot.y * Main.MPP, true);
        //body.applyForceToCenter(dx,dy,true);
    }

    public void onCollision() {

    }

    public void disappear() {
        disappear = true;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        drawPivot(batch);
    }

    void drawPivot(SpriteBatch batch) {
        batch.draw(Res.tex_draggable_pivot, pos_pivot_visual.x - 3, pos_pivot_visual.y - 3);
    }
}
