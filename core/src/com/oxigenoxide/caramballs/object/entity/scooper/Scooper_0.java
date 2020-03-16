package com.oxigenoxide.caramballs.object.entity.scooper;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Scooper_0 extends Scooper {

    public Scooper_0(){
        super();
        enableMotor=false;
        tex= Res.tex_scooper_0;
        tex_shine= Res.tex_scooperShine_0;
        System.out.println("tex: "+tex);
        pos = new Vector2(Main.width / 2, Main.height / 2);
        anchor = new Vector2(0,0);
        construct();
    }
}
