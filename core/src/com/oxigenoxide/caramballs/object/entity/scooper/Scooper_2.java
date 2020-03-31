package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Scooper_2 extends Scooper {
    private float count_move;

    public Scooper_2() {
        super();
        enableMotor = true;
        tex = Res.tex_scooper_2;
        tex_shine = Res.tex_scooperShine_2;
        pos = new Vector2(Main.width / 2, Main.height / 2);
        anchor = new Vector2(0, 0);
        construct();
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(80 * Main.MPP / 2, 20 * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);
        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(20 * Main.MPP / 2, 80 * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);
        body.setUserData(this);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        body.setSleepingAllowed(false);
        createJoint();
    }

    public void update() {
        super.update();
        count_move = MathFuncs.loopRadians(count_move, Main.dt_slowed);
        pos.y = Main.height / 2 + Main.height / 4 *(float) Math.sin(count_move);
        body_pivot.setTransform(pos.x * Main.MPP + anchor.x, pos.y * Main.MPP + anchor.y, 0); // at the position of the pivot
    }
}
