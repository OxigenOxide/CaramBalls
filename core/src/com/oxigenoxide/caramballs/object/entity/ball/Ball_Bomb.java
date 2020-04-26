package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Explosion;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Bomb extends Ball {

    float count;
    float countMax = 200;
    final int BLINKS = 3;
    float whiteness;

    public Ball_Bomb(float x, float y, float height) {
        super(x, y, height, 1);
        setSpriteTexture(Res.tex_bomb);
    }

    public Ball_Bomb() {
        super(1);
        setSpriteTexture(Res.tex_bomb);
    }


    @Override
    public void update() {
        super.update();
        count += Main.dt_one_slowed;
        if (count > countMax) {
            explode();
        }
        whiteness = Math.max((float) Math.sin(count / countMax * (BLINKS - .75f) * 2 * (float) Math.PI), 0);
    }

    public void explode() {
        dispose();
        for (Ball ball : Main.balls) {
            if (ball.body != null && ball.isKinetic())
                ball.applyForce(MathFuncs.angleBetweenPoints(pos, ball.pos), getForce(MathFuncs.distanceBetweenPoints(pos, ball.pos)));
        }
        Main.particles.add(new Particle_Explosion(pos.x,pos.y));
    }

    static float getForce(float distance) {
        if (distance < Main.width)
            return 20;
        else
            return 20 / (distance - Main.width);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_overlay);
        Res.shader_overlay.setUniformf("newColor", 1, 1, 1, whiteness);
        sprite.draw(batch);
        batch.setShader(Res.shader_palette);
    }
    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(Res.COLOR_BOMB_SHADOW);
        super.drawTrail(sr);
    }
}
