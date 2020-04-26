package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class PowerOrb {
    Sprite sprite;
    Vector2 pos;
    Vector2 pos_target;
    int index;
    int amount_powerOrbs;
    Ball_Main ball_parent;
    boolean isUpgraded;
    Color[] palette = new Color[]{new Color(), new Color(), new Color(), new Color()};
    float count_colorWeight;

    public PowerOrb(float x, float y, Ball_Main ball_parent, int index) {
        this.ball_parent = ball_parent;
        this.index = index;
        sprite = new Sprite(Res.tex_powerOrb[0]);
        pos = new Vector2(x, y);
        pos_target = new Vector2(x, y);
    }

    public void update(float ang) {
        count_colorWeight = MathFuncs.loopRadians(count_colorWeight, Main.dt * 3);

        Funcs.combinePalettes(palette, Res.PALETTE_POWERORB_0, Res.PALETTE_POWERORB_1, (1 + (float) Math.sin(count_colorWeight)) / 2);

        pos_target.set(ball_parent.radius + 6, 0);

        switch (amount_powerOrbs) {
            case 2:
                if (index == 1)
                    ang += Math.PI;
                break;
            case 3:
                if (index == 1)
                    ang += Math.PI * 2 / 3f;
                if (index == 2)
                    ang += Math.PI * 4 / 3f;
                break;
        }

        pos_target.rotateRad(ang);
        pos_target.add(ball_parent.pos);
        pos.lerp(pos_target, .5f);
        sprite.setPosition((int) (pos.x - sprite.getRegionWidth() / 2f), (int) (pos.y - sprite.getRegionHeight() / 2f));
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        Main.setPalette(palette);
        sprite.draw(batch);
        batch.setShader(null);
    }

    public boolean isUpgraded() {
        return isUpgraded;
    }

    public void use() {

    }

    public void setAmount(int amount) {
        amount_powerOrbs = amount;
    }

    public void setParent(Ball_Main parent) {
        ball_parent = parent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void upgrade() {
        sprite.setRegion(Res.tex_powerOrb[1]);
        sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
        isUpgraded = true;
    }
}
