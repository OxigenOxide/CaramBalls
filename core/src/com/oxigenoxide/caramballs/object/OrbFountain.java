package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Obstacle;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Orb;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;

public class OrbFountain {
    Vector2 pos;
    Counter counter_nextOrb;
    int orbsToThrow = 10;
    Sprite sprite;
    float size;
    float height;

    public OrbFountain(float x, float y) {
        pos = new Vector2(x, y);
        counter_nextOrb = new Counter(new ActionListener() {
            @Override
            public void action() {
                if (orbsToThrow > 0) {
                    orbsToThrow--;
                    counter_nextOrb.start();
                    Ball_Orb ball_orb = new Ball_Orb(pos.x, pos.y, height);
                    float ang = (float) (Math.random() * Math.PI * 2);
                    ball_orb.setVelocity((float) Math.cos(ang) * 2, (float) Math.sin(ang) * 2);
                    ball_orb.velY = 4;
                    Main.balls.add(ball_orb);
                }
            }
        }, .2f);
        counter_nextOrb.start();
        sprite = new Sprite(Res.tex_fountain);
    }

    public void update() {
        if (orbsToThrow > 0) {
            size = Math.min(size + Main.dt, 1);
            height = Math.min(height + Main.dt * 10, 15);
            if (size == 1)
                counter_nextOrb.update();
        } else
            size = Math.max(size - Main.dt, 0);
        sprite.setSize(sprite.getRegionWidth() * size, sprite.getRegionHeight() * size);
        sprite.setPosition((int)((int)pos.x - sprite.getWidth() / 2), (int)((int)pos.y + height - sprite.getHeight() / 2));
        if (size == 0)
            dispose();
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void drawShadow(ShapeRenderer sr) {
        Main.drawShadow(sr, pos, sprite.getWidth());
    }

    void dispose() {
        Main.game.orbFountain = null;
    }
}
