package com.oxigenoxide.caramballs.object.entity.hole;

import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;

public class Hole_Reward extends Hole { // made strictly for scene Farm
    EventManager eventManager;
    int TIMEPERBALL = 20;
    int[] levels_balls;
    int totalBalls;
    float count_spew = 0;
    int thrownBalls;

    public Hole_Reward(float x, float y, int[] levels_balls) {
        super(x, y);
        radiusMax = (int) Res.ballRadius[2] + .75f;
        this.levels_balls = levels_balls;
        totalBalls = levels_balls.length;
        eventManager = new EventManager(new EventListener() {
            public int onEvent(int event) {
                switch (event) {
                    case 0:
                        return 60;
                    case 1:
                        return TIMEPERBALL * (int) (totalBalls-.5);
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

        eventManager.update();

        if (eventManager.getEvent() == 1)
            radius = eventManager.getProgress() * radiusMax;
        if (eventManager.getEvent() == 2) {
            count_spew -= Main.dt_one;
            if (count_spew <= 0) {
                count_spew = TIMEPERBALL + count_spew;
                spew();
            }
        }
        if (eventManager.getEvent() == 3)
            radius = (1 - eventManager.getProgress()) * radiusMax;

    }

    void spew() {
        Ball ball = new Ball_Main(pos.x, pos.y, -10, 0, levels_balls[thrownBalls]).setPermaPassthrough(true);
        Main.balls.add(0,ball);
        float ang = (.5f + (float) Math.random()) * (float) Math.PI * .5f;
        ball.body.setLinearVelocity((float) Math.cos(ang) * 3, (float) Math.sin(ang) * 3);
        ball.velY = 7;
        ball.hole_spawn = this;
        hasSpewed = true;
        thrownBalls++;
    }
}
