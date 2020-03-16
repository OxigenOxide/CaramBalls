package com.oxigenoxide.caramballs.object.entity.hole;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.balls;

public class Hole_Sell extends Hole {

    final float FALLINEASIER = 2;
    Counter count_progress;
    boolean close;
    static final Color COLOR_OUTLINE = new Color(30 / 255f, 150 / 255f, 50 / 255f, 1);

    public Hole_Sell(float x, float y) {
        super(x, y);
        construct();
    }

    void construct() {
        radiusMax = 13;
        radius_spawn = radiusMax;
        count_progress = new Counter(new ActionListener() {
            @Override
            public void action() {
                onCountFinished();
            }
        }, 1);
        close = true;
    }

    @Override
    public void update() {
        super.update();
        count_progress.update();

        if (close) radius = (1 - count_progress.getProgress()) * radiusMax;
        else radius = count_progress.getProgress() * radiusMax;

        for (Ball ball : balls) {
            if (!ball.fall && !(ball.height > 0) && MathFuncs.distanceBetweenPoints(ball.pos, pos) + ball.radius < radius + FALLINEASIER) {
                ball.fallInHole(this);
            }
            //if(!close && count_progress.getProgress()<1)
        }
    }

    public void close() {
        close = true;
        count_progress.start();
    }

    public void open() {
        close = false;
        count_progress.start();

        // push balls away so they dont fall in
        for (Ball ball : balls) {
            float dst = MathFuncs.distanceBetweenPoints(pos, ball.pos);
            if (dst < radiusMax)
                ball.addVelocity(-2f, 2f);
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        sr.setColor(COLOR_OUTLINE);
        sr.circle(pos.x, pos.y, radius + 4);
        super.render(sr);
    }

    public void onCountFinished() {
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
