package com.oxigenoxide.caramballs.object.entity.gate;

import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.pole.Pole;
import com.oxigenoxide.caramballs.object.entity.pole.Pole_Danger;

public class Gate_Danger extends Gate {
    void createPoles() {
        pole_0 = new Pole_Danger(pos.x + RADIUS * (float) Math.cos(ang),
                pos.y + RADIUS * (float) Math.sin(ang));
        pole_1 = new Pole_Danger(pos.x + RADIUS * (float) Math.cos(ang + Math.PI),
                pos.y + RADIUS * (float) Math.sin(ang + Math.PI));
    }

    @Override
    public void update() {
        super.update();
        sprite_line.setColor(1,0,0,.25f);
    }

    public void onCollision(Ball ball) {
        ball.destroy_delayed();
    }
}
