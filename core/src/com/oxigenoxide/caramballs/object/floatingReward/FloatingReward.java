package com.oxigenoxide.caramballs.object.floatingReward;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.Vector2Mod;

public class FloatingReward {

    TextureRegion tex;
    Vector2 pos;
    Vector2Mod pos_target;
    Vector2Mod pos_endTarget;
    Sprite sprite;
    float scaleUp_end = 1;
    float scaleUp = 1;
    final Vector2Mod pos_middle = new Vector2Mod(Main.width / 2, Main.height / 2);
    float radius_shine;
    float radiusDest_shine = 25;
    float count;
    float startCount;
    float count_shine;
    boolean doCount;
    int activity;
    float lerpAlpha;

    FloatingReward(float x, float y) {
        pos = new Vector2(x, y);
        doNextActivity();
        pos_endTarget = new Vector2Mod(Main.width / 2, Main.height + 150);
    }

    public void update() {
        lerpAlpha = Math.min(.001f * (startCount - count), .1f);
        pos.lerp(pos_target.get(), lerpAlpha);
        sprite.setSize(sprite.getWidth() + (sprite.getRegionWidth() * scaleUp - sprite.getWidth()) * lerpAlpha, sprite.getHeight() + (sprite.getRegionHeight() * scaleUp - sprite.getHeight()) * lerpAlpha);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
        count_shine = MathFuncs.loopOne(count_shine, Main.dt*.25f);
        radius_shine += (radiusDest_shine - radius_shine) * lerpAlpha;

        if (doCount)
            count -= Main.dt_one;

        if (count < 0) {
            count = 0;
            doCount = false; // before doNextActivity();
            doNextActivity();
        }
    }

    void doNextActivity() {
        switch (activity) {
            case 0:
                pos_target = pos_middle;
                setCounter(120);
                break;
            case 1:
                pos_target = pos_endTarget;
                scaleUp = scaleUp_end;
                radiusDest_shine = 0;
                setCounter(120);
                break;
            case 2:
                dispose();
        }
        activity++;
    }

    public void setCounter(int frames) {
        count = frames;
        doCount = true;
        startCount = count;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        Main.floatingRewardsToRemove.add(this);
    }

    public void drawShine(ShapeRenderer sr) {
        Funcs.drawShine(sr, pos, radius_shine, count_shine);
        /*
        sr.setColor(1, 1, 200 / 255f, 180 / 255f);
        */
    }
}
