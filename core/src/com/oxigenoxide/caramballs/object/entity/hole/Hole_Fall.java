package com.oxigenoxide.caramballs.object.entity.hole;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Hole_Fall extends Hole {


    float breathe;
    float count_breathe;
    float radius_base;
    final float FALLINEASIER = 2;
    int lifeTime = 315;
    float breatheProgress;

    public Hole_Fall() {
        super();
        radiusMax = 10;
        radius_spawn = radiusMax;
        setPosition();
    }

    public Hole_Fall(float x, float y) {
        super(x, y);
        radiusMax = 10;
        radius_spawn = radiusMax;
    }

    @Override
    public void update() {
        super.update();

        if (count < lifeTime * 2 / 5f) {
            radius_base = count / (lifeTime * 2 / 5f) * radiusMax;
        }

        if (count > lifeTime * 4 / 5f && count <= lifeTime) {
            radius_base = radiusMax - (count - lifeTime * 4 / 5f) / (lifeTime-lifeTime * 4 / 5f) * radiusMax;
        }

        count_breathe = (float) ((count_breathe + .1f) % (2 * Math.PI));
        breathe = radius_base * .1f * (float) Math.sin(count_breathe);
        breatheProgress=((float) Math.sin(count_breathe)+1)/2;
        if(Main.noFX) {
            breathe = 0;
            breatheProgress=1;
        }
        radius = radius_base + breathe;

        if (count > lifeTime)
            dispose();

        for (Ball ball : Main.balls) {
            if (!ball.fall && !(ball.height>0)&& MathFuncs.distanceBetweenPoints(ball.pos, pos) + ball.radius < radius + FALLINEASIER)
                ball.fallInHole(this);
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        sr.setColor(1,.5f+.5f*breatheProgress,.5f+.5f*breatheProgress, 1);
        sr.circle(pos.x, pos.y, radius * 1.2f);
        super.render(sr);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
