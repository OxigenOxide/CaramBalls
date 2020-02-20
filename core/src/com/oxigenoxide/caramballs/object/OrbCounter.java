package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class OrbCounter {
    Vector2 pos, pos_num;
    float slide;
    TextureRegion tex;
    boolean open = true, close;
    float count_show;
    float countMax_show = 180;
    float shakeIntensity;

    public OrbCounter() {
        pos = new Vector2(Main.width, Main.height - 30);
        pos_num = new Vector2();
        tex = Res.tex_orbCounter;
    }

    public void update() {

        count_show-=Main.dt_one;
        count_show=Math.max(0,count_show);
        if(count_show==0) {
            close = true;
            open=false;
        }
        else{
            close = false;
            open=true;
        }

        if (open) {
            slide += .05f;
        }
        if (close) {
            slide -= .05f;
        }
        slide = MathUtils.clamp(slide, 0, 1);
        pos.set(Main.width - tex.getRegionWidth() * slide, Main.height - 30 + (int) (shakeIntensity * 3 * Math.sin(shakeIntensity*20)));
        pos_num.set(pos.x + 22, pos.y + 5);

        shakeIntensity = Math.max(0, shakeIntensity - .05f);
    }

    public void onOrbCollected() {
        shakeIntensity = 1;
    }

    public void show(){
        count_show=countMax_show;
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, pos.x, pos.y);
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 1, 1, 1, 1);
        Main.drawNumber(batch, Main.gameData.orbs, pos_num, ID.Font.SMALL);
        batch.setShader(null);
    }
}
