package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class ComboBar {
    TextureRegion tex_bar;
    int width = 72;
    float filled;
    Vector2 pos_num;
    float count;

    public ComboBar() {
        tex_bar = new TextureRegion(Res.tex_comboBar);
        pos_num = new Vector2(Main.width / 2, Main.height - 12);
    }

    public void update() {
        filled = Main.test_float;
        tex_bar.setRegion(Res.tex_comboBar);
        tex_bar.setRegionWidth((int) (filled * width));
        count = (count + .1f) % (2 * 2 * (float) Math.PI);
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_bend);
        Res.shader_bend.setUniformf("time", count);
        Res.shader_bend.setUniformf("texSize", tex_bar.getRegionWidth(), tex_bar.getRegionHeight());
        batch.draw(tex_bar, Main.width / 2 - tex_bar.getRegionWidth() / 2, Main.height - 12 - 8);
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c",1,1,1,1);
        Main.drawNumber(batch, 100, pos_num, ID.Font.SMALL);
        batch.setShader(null);
    }
}
