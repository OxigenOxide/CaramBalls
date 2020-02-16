package com.oxigenoxide.caramballs.object.entity.collectable;

import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;

public class Collectable_Shield extends Collectable {
    public Collectable_Shield() {
        super(Res.tex_collectable_shield);
    }

    @Override
    public void pickUp(Ball ball) {
        super.pickUp(ball);
        ball.doActivateShield();
    }
}
