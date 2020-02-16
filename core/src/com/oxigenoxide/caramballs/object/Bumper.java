package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Bumper {
    boolean isHorizontal;
    Vector2 pos;
    float height;
    int wall;
    Sprite sprite;
    float[] line;
    float angle;
    float wiggle;

    public Bumper(int wall) {
        this.wall = wall;
        sprite = new Sprite(Res.tex_bumper);
        pos = new Vector2();
        if (wall == 0) {
            isHorizontal = false;
            pos.set(0, Main.height/2);
            sprite.setPosition(pos.x, pos.y - sprite.getHeight() / 2);
            sprite.setFlip(true, false);
            line = new float[]{pos.x + sprite.getWidth(), pos.y - sprite.getHeight() / 2, pos.x + sprite.getWidth(), pos.y + sprite.getHeight() / 2};
            angle = 0;
        }
        if (wall == 1) {
            isHorizontal = true;
            sprite.setFlip(true, false);
            pos.set(Main.width/2, Main.height);
            sprite.setOrigin(0, sprite.getHeight() / 2);
            sprite.setPosition(pos.x, pos.y - sprite.getHeight() / 2);
            sprite.setRotation(-90);
            line = new float[]{pos.x - sprite.getHeight() / 2, pos.y - sprite.getWidth(), pos.x + sprite.getHeight() / 2, pos.y - sprite.getWidth()};
            angle = -(float) Math.PI * .5f;
        }
        if (wall == 2) {
            isHorizontal = false;
            sprite.setFlip(true, false);
            pos.set(Main.width, Main.height/2);
            sprite.setOrigin(0, sprite.getHeight() / 2);
            sprite.setPosition(pos.x, pos.y - sprite.getHeight() / 2);
            sprite.setRotation(-180);
            line = new float[]{pos.x - sprite.getWidth(), pos.y - sprite.getHeight() / 2, pos.x - sprite.getWidth(), pos.y + sprite.getHeight() / 2};
            angle = -(float) Math.PI;
        }
        if (wall == 3) {
            isHorizontal = true;
            sprite.setFlip(true, false);
            pos.set(Main.width/2, 0);
            sprite.setOrigin(0, sprite.getHeight() / 2);
            sprite.setPosition(pos.x, pos.y - sprite.getHeight() / 2);
            sprite.setRotation(-270);
            line = new float[]{pos.x - sprite.getHeight() / 2, pos.y + sprite.getWidth(), pos.x + sprite.getHeight() / 2, pos.y + sprite.getWidth()};
            angle = -(float) Math.PI * 1.5f;
        }
    }


    public void update() {
        for (Ball ball : Main.balls) {
            if (MathFuncs.intersectCircleLine(line[0], line[1], line[2], line[3], ball.pos.x, ball.pos.y, ball.radius) && ball.count_recentlyHit == 0) {
                ball.body.setLinearVelocity(ball.body.getLinearVelocity().x * (float) Math.abs(Math.sin(angle)), ball.body.getLinearVelocity().y * (float) Math.abs(Math.cos(angle)));
                float radius=ball.body.getFixtureList().get(0).getShape().getRadius();
                ball.applyForce(angle, 2500*radius*radius);
                if (!Main.noFX)
                    wiggle = 1;
            }
        }
        wiggle = Math.max(0, wiggle -= .05);
        sprite.setSize(sprite.getTexture().getWidth() - (float) Math.sin(wiggle * 10) * 2, sprite.getTexture().getHeight());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
