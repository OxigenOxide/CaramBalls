package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Explosion;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Star extends Ball {


    public Ball_Star(float x, float y, float height) {
        super(x, y, height, 1);
        setSpriteTexture(Res.tex_ball_sun);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void drawShapes(ShapeRenderer sr) {
        sr.setColor(Res.COLOR_SUNSHINE);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.circle(pos.x,pos.y,50,28);
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(Res.COLOR_BOMB_SHADOW);
        super.drawTrail(sr);
    }

}
