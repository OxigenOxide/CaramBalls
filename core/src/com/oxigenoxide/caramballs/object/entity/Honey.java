package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Honey extends Entity {
    Sprite sprite;
    static final float RADIUS = 8f;
    float wiggle;
    float size;

    public Honey() {
        sprite = new Sprite(Res.tex_honey);
        pos = Game.getRandomPosOnTable(sprite.getWidth(), sprite.getHeight());
        pos.add(0, .5f);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
    }

    public void update() {
        for (Ball ball : Main.balls) {
            if (MathFuncs.distanceBetweenPoints(ball.pos, pos) < RADIUS + ball.radius) {
                if (!ball.isStuck) {
                    ball.getStuck();
                    wiggle();
                }
            }
        }
        size = Math.min(1, size + Main.dt_one * .1f);
        sprite.setSize(size * (float) (sprite.getTexture().getWidth() * (1 + wiggle * .5f * -Math.sin(wiggle * 15))), size * (float) (sprite.getTexture().getHeight() * (1 + wiggle * .5f * -Math.cos(wiggle * 15))));
        wiggle = Math.max(0, wiggle -= .05 * Main.dt_one_slowed);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
    }

    public void wiggle() {
        wiggle = .5f;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        Main.honeyToRemove.add(this);
    }
}
