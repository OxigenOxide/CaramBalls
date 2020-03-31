package com.oxigenoxide.caramballs.object.floatingReward;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Vector2Mod;

public class FR_Skin extends FloatingReward {
    int skin;

    public FR_Skin(float x, float y, int skin, Vector2Mod pos_endTarget) {
        super(x, y);
        this.skin = skin;
        tex = Res.tex_ball[skin][1];
        sprite = new Sprite(tex);
        scaleUp = 2;
        radiusDest_shine=35;
        this.pos_endTarget = pos_endTarget; // this position will get updated constantly
    }

    @Override
    public void update() {
        super.update();
    }


    @Override
    void doNextActivity() {
        if (activity == 1)
            Main.shop.endDarkOverlay();
        if (activity == 2)
            Main.shop.unlockSkin(skin);
        super.doNextActivity();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.PALETTE_WHITEBALL);
        super.render(batch);
        batch.setShader(null);
    }
}
