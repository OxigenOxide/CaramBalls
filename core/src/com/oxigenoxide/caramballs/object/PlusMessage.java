package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class PlusMessage {
    Sprite sprite;
    Vector2 pos;
    float alpha = 1;

    public PlusMessage(float x, float y, int type) {
        pos = new Vector2(x, y);
        sprite = new Sprite(Res.tex_plusMessage[type]);
    }

    public void update() {
        pos.y += Main.dt * 6;
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y);
        alpha -= Main.dt;
        sprite.setAlpha(alpha);
        if (alpha <= 0)
            Main.plusMessagesToRemove.add(this);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
