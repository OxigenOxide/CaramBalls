package com.oxigenoxide.caramballs.object.entity.hole;

import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;

public class Hole_Ball extends Hole {

    EventManager eventManager;

    public Hole_Ball() {
        super();
        //radiusMax = Res.tex_ball[0][Ball_Main.getSize(getBallNum())].getWidth() / 2 + 1;
        radiusMax = (int) Res.ballRadius[0] + .75f;
        radius_spawn = radiusMax;
        setPosition();
        eventManager = new EventManager(new EventListener() {
            public int onEvent(int event) {
                switch (event) {
                    case 0:
                        return 120;
                    case 1:
                        spew();
                        return 80;
                    case 2:
                        return 100;
                    case 3:
                        dispose();
                }
                return -1;
            }
        }).start();
    }

    public void update() {
        super.update();

        eventManager.update(Main.dt_one_slowed);

        if (eventManager.getEvent() == 1)
            radius = eventManager.getProgress() * radiusMax;

        if (eventManager.getEvent() == 3)
            radius = (1 - eventManager.getProgress()) * radiusMax;
    }

    void spew() {

        if (Game.getTotalBallSize() < 8) {
            Ball ball = new Ball_Main(pos.x, pos.y, -10, 0, Game.level);
            float angle = (float) (Math.random() * Math.PI * 2);
            Main.balls.add(ball);
            ball.body.setLinearVelocity((float) Math.cos(angle) * 10, (float) Math.sin(angle) * 10);
            ball.velY = 8;
            ball.hole_spawn = this;
        }
    }

    int getBallNum() {
        if (Game.ball_king != null)
            return Math.max(0, Game.ball_king.getNum());
        return 0;
    }
}
