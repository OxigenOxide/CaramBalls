package com.oxigenoxide.caramballs.object.floatingReward;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class FR_Ball extends FloatingReward {
    int level;
    public FR_Ball(float x, float y, int level){
        super(x,y);
        this.level = level;
        tex= Res.tex_ball[0][0];
        sprite=new Sprite(tex);
    }

    @Override
    public void update() {
       super.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.ballPalette[level]);
        super.render(batch);
        batch.setShader(null);
    }
}
