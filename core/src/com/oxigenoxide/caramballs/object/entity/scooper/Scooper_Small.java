package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Scooper_Small extends Scooper {
    public Scooper_Small(float x, float y) {
        super();
        //enableMotor = true;
        tex = Res.tex_scooper_small;
        tex_shine = Res.tex_scooperShine_small;
        pos = new Vector2(x,y);
        anchor = new Vector2(0, 0);
        construct();
        shineHeight=1;
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        ((PolygonShape) Res.fixtureDef_box.shape).setAsBox(68 * Main.MPP / 2, 11 * Main.MPP / 2);
        body.createFixture(Res.fixtureDef_box);
        body.setUserData(this);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        body.setSleepingAllowed(false);
        createJoint();
    }
}
