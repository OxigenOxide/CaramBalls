package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Scooper_1 extends Scooper {

    public Scooper_1() {
        super();
        enableMotor = false;
        tex = Res.tex_scooper_0;
        tex_shine = Res.tex_scooperShine_0;
        System.out.println("tex: " + tex);
        pos = new Vector2(6 + tex.getRegionWidth() / 2, Main.height / 2);
        anchor = new Vector2((-tex.getRegionWidth() / 2  + 9)* Main.MPP,0);
        construct();
    }
}