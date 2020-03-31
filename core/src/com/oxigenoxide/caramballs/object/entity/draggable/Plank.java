package com.oxigenoxide.caramballs.object.entity.draggable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import java.util.ArrayList;
import java.util.Arrays;

public class Plank extends Draggable {
    Vector2[] v;
    Vector2[] v_render;
    Vector2[] v_outline;

    public Plank() {
        super(25);
        sprite = new Sprite(Res.tex_woodPlank);
        createBody(0);
        v = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2()};
        v_render = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2()};
        v_outline = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2()};
        body.setTransform(body.getPosition().x,body.getPosition().y,.1f);
    }

    @Override
    void createBody(float radius) {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_plank);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        body.setUserData(this);
    }

    @Override
    public void update() {
        super.update();
        if(isDisposed)
            return;

        pos.set(pos.x + .5f, pos.y + .5f);

        sprite.setSize(sprite.getRegionWidth() * size, sprite.getRegionHeight() * size);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2 + 7);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        v[0].set(32, 9.5f).scl(size);
        v[1].set(32, -9.5f).scl(size);
        v[2].set(-32, -9.5f).scl(size);
        v[3].set(-32, 9.5f).scl(size);
        v[0].rotateRad(body.getAngle());
        v[1].rotateRad(body.getAngle());
        v[2].rotateRad(body.getAngle());
        v[3].rotateRad(body.getAngle());
        v[0].add(body.getPosition().scl(Main.PPM));
        v[1].add(body.getPosition().scl(Main.PPM));
        v[2].add(body.getPosition().scl(Main.PPM));
        v[3].add(body.getPosition().scl(Main.PPM));
        v[0].add(0, 8 * size);
        v[1].add(0, 8 * size);
        v[2].add(0, 8 * size);
        v[3].add(0, 8 * size);

        Vector2 v_lowest;
        Vector2 v_highest;
        Vector2 v_side_0;
        Vector2 v_side_1;
        v_lowest = v[0];
        v_highest = v[0];
        for (int i = 1; i < 4; i++) {
            if (v[i].y < v_lowest.y) {
                v_lowest = v[i];
            } else if (v[i].y > v_highest.y) {
                v_highest = v[i];
            }
        }
        ArrayList<Vector2> vertices = new ArrayList<Vector2>(Arrays.asList(v[0], v[1], v[2], v[3]));
        vertices.remove(v_lowest);
        vertices.remove(v_highest);

        v_side_0 = vertices.get(0);
        v_side_1 = vertices.get(1);

        v_render[0].set(v_side_0);
        v_render[1].set(v_highest);
        v_render[2].set(v_side_1);
        v_render[3].set(v_side_1.x, v_side_1.y - 8);
        v_render[4].set(v_lowest.x, v_lowest.y - 8);
        v_render[5].set(v_side_0.x, v_side_0.y - 8);

        v_outline[0].set(v_render[0]);
        v_outline[0].add(getOutlineDir(v_render[0], v_render[1], v_render[5]));
        v_outline[1].set(v_render[1]);
        v_outline[1].add(getOutlineDir(v_render[1], v_render[0], v_render[2]));
        v_outline[2].set(v_render[2]);
        v_outline[2].add(getOutlineDir(v_render[2], v_render[1], v_render[3]));
        v_outline[3].set(v_render[3]);
        v_outline[3].add(getOutlineDir(v_render[3], v_render[2], v_render[4]));
        v_outline[4].set(v_render[4]);
        v_outline[4].add(getOutlineDir(v_render[4], v_render[5], v_render[3]));
        v_outline[5].set(v_render[5]);
        v_outline[5].add(getOutlineDir(v_render[5], v_render[0], v_render[4]));
    }

    Vector2 getOutlineDir(Vector2 v0, Vector2 v1, Vector2 v2) {

        float ang_v1 = MathFuncs.angleBetweenPoints(v0, v1);
        float ang_v2 = MathFuncs.angleBetweenPoints(v0, v2);
        float ang = .5f * (ang_v1 + ang_v2);
        if (Math.abs(ang - ang_v2) < Math.PI * .5f)
            ang += Math.PI;

        return new Vector2((float) Math.cos(ang), (float) Math.sin(ang));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void onCollision() {
        super.onCollision();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        render(batch);
    }

    @Override
    public void setPivotPosition() {
        pos_pivot.set(18, 0);
        pos_pivot.rotateRad(body.getAngle());
        pos_pivot.add(pos.x, pos.y);
        pos_pivot.set((int) pos_pivot.x, (int) pos_pivot.y);
        pos_pivot_visual.set(pos_pivot.x, pos_pivot.y + 8);
    }


    @Override
    public void render(ShapeRenderer sr) {
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);
        Funcs.drawTriangle(sr, v_outline[0], v_outline[1], v_outline[2]);
        Funcs.drawTriangle(sr, v_outline[0], v_outline[2], v_outline[5]);
        Funcs.drawTriangle(sr, v_outline[2], v_outline[3], v_outline[5]);
        Funcs.drawTriangle(sr, v_outline[3], v_outline[4], v_outline[5]);

        sr.setColor(Res.COLOR_PLANK);
        Funcs.drawTriangle(sr, v_render[0], v_render[1], v_render[2]);
        Funcs.drawTriangle(sr, v_render[0], v_render[2], v_render[5]);
        Funcs.drawTriangle(sr, v_render[2], v_render[3], v_render[5]);
        Funcs.drawTriangle(sr, v_render[3], v_render[4], v_render[5]);
    }
}
