package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Scooper_3 extends Scooper {
    private float count_move;

    public Scooper_3() {
        super();
        enableMotor = true;
        tex = Res.tex_scooper_3;
        tex_shine = Res.tex_scooperShine_0;
        pos = new Vector2(Main.width / 2, Main.height / 2);
        anchor = new Vector2(0, 0);
        construct();
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);

        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(89 * Main.MPP / 2, 20 * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);

        PolygonShape shape_spike = new PolygonShape();
        FixtureDef fixDef_spike = Res.fixtureDef_scooperSpike;
        fixDef_spike.shape = shape_spike;

        //set spike shapes and fixtures
        float[] verts = new float[]{20.5f * Main.MPP, 10 * Main.MPP, 27.5f * Main.MPP, 10 * Main.MPP, 24 * Main.MPP, 17 * Main.MPP};

        MathFuncs.translateVertices(verts, -8 * Main.MPP, 0);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        MathFuncs.translateVertices(verts, 8 * Main.MPP, 0);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        MathFuncs.translateVertices(verts, 8 * Main.MPP, 0);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        MathFuncs.negateVertices(verts);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        MathFuncs.translateVertices(verts, 8 * Main.MPP, 0);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        MathFuncs.translateVertices(verts, 8 * Main.MPP, 0);
        shape_spike.set(verts);
        body.createFixture(fixDef_spike);

        for (int i = 1; i < 7; i++) body.getFixtureList().get(i).setUserData(true);

        body.setUserData(this);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);

        createJoint();
    }

    public void update() {
        super.update();
        //count_move = MathFuncs.loopRadians(count_move, Main.dt_slowed);
        pos.y = Main.height / 2 + Main.height / 4 * (float) Math.sin(count_move);
        body_pivot.setTransform(pos.x * Main.MPP + anchor.x, pos.y * Main.MPP + anchor.y, 0); // at the position of the pivot
    }
}