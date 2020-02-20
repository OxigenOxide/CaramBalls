package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Bad;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class FloorButton extends Entity {
    boolean isPressed;
    static int width = 15, height = 15;
    TextureRegion texture;
    Sprite sprite;
    boolean doImpactFrames;
    int impactFrames;
    float alpha = 1;
    float timeAfterPress;

    public FloorButton(float x, float y) {
        radius_spawn = 10;
        pos = new Vector2((int) x, (int) y);
        texture = Res.tex_floorButton_danger;
        sprite = new Sprite(texture);
        sprite.setPosition(pos.x - width / 2, pos.y - height / 2);

    }

    public FloorButton() {
        radius_spawn = 12;
        pos = Game.getFreePosOnTable(radius_spawn);
        texture = Res.tex_floorButton_danger;
        sprite = new Sprite(texture);
        sprite.setPosition(pos.x - width / 2, pos.y - height / 2);

    }

    public void update() {
        for (Ball ball : Main.balls)
            if (MathFuncs.distanceBetweenPoints(ball.pos, pos) - ball.radius < width / 2)
                press();

        if (isPressed) {
            timeAfterPress += Main.dt_one;
            if (timeAfterPress > 120)
                alpha = Math.max(0, alpha - .05f);
        }

        if (alpha == 0)
            dispose();

        sprite.setAlpha(alpha);
    }

    public void press() {
        if (!isPressed) {
            isPressed = true;
            sprite.setRegion(Res.tex_floorButtonPressed_danger);
            sprite.setSize(sprite.getRegionWidth(),sprite.getRegionHeight());
            doImpactFrames = true;
            Main.ballsToAdd.add(new Ball_Bad(pos.x, pos.y + 3, Main.height, 0));
        }
    }

    public void render(SpriteBatch batch) {

        if (doImpactFrames) {
            batch.setShader(Res.shader_c);
            Res.shader_c.setUniformf("c", 1, 1, 1, 1);
        }
        sprite.draw(batch);
        if (doImpactFrames) {
            batch.setShader(null);
            impactFrames++;
            if (impactFrames > 4)
                doImpactFrames = false;
        }
    }

    public void dispose() {
        Main.floorButtonsToRemove.add(this);
    }
}
