package com.oxigenoxide.caramballs.object.entity.hole;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.balls;
import static com.oxigenoxide.caramballs.Main.mainBalls;

public class Hole_Farm extends Hole {
    final float FALLINEASIER = 2;
    int beginLevel;

    public Hole_Farm(float x, float y) {
        super(x, y);
        construct();
    }

    void construct() {
        radiusMax = 5;
        radius = radiusMax;
        radius_spawn = radiusMax;
    }

    @Override
    public void update() {
        super.update();

        for (Ball_Main ball : mainBalls) {
            if (!ball.fall && !(ball.height > 0) && MathFuncs.distanceBetweenPoints(ball.pos, pos) + ball.radius < radius + FALLINEASIER) {
                ball.fallInHole(this);

                beginLevel = ball.level;

                Main.startFade(new ActionListener() {
                    @Override
                    public void action() {
                        //if(Main.game.timesShown>0)
                        Game.reset();
                        Game.setLevelImminent(beginLevel);
                        Main.setScene(Main.game); // game will setup because gameOver == false
                    }
                });
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_hole_farm, (int)(pos.x - radius), pos.y - radius);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
