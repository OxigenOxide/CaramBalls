package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class TTPText {
    float count;
    boolean visible;
    float y;

    public TTPText(float y) {
        this.y=(int)y;
    }

    public void update() {
        count = (count + Main.dt_one) % 60;
        visible = count > 30;
    }

    public void render(SpriteBatch batch) {
        if (visible)
            batch.draw(Res.tex_ttptext, Main.width / 2 - Res.tex_ttptext.getWidth() / 2, y- Res.tex_ttptext.getHeight()/2);
    }
}
