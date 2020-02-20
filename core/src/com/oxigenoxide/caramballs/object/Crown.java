package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.scene.Game.ball_king;

public class Crown {
    Vector2 pos;
    boolean inTransition;
    TextureRegion tex;

    public Crown() {
        pos = new Vector2();
        tex = Res.tex_crown;
    }

    public void update() {
        if (ball_king != null) {
            if (inTransition) {
                pos.interpolate(ball_king.pos, .5f, Interpolation.linear);
                if (MathFuncs.distanceBetweenPoints(pos, ball_king.pos) < 10) {
                    inTransition = false;
                }
            } else {
                pos.set(ball_king.pos.x - tex.getRegionWidth() / 2, ball_king.pos.y + ball_king.sprite.getHeight() / 2 + 1);
            }
        }
    }

    public void newOwner(Ball_Main ball_king) {
        if (Game.ball_king == null)
            pos.set(ball_king.pos.x, ball_king.pos.y);
        Game.ball_king = ball_king;
        inTransition = true;
    }

    public void render(SpriteBatch batch) {
        if (ball_king != null && !ball_king.isUnderGround)
            batch.draw(tex, pos.x, pos.y+ball_king.height);
    }
}
