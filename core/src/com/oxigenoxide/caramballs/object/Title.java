package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Title {
    int amount_letters = 5;
    int[] letterHeight;
    float count;
    float y;
    float amp = 5;

    public Title() {
        letterHeight = new int[amount_letters];
        y = Main.height - 26 - Main.adHeight - amp;
    }

    public void update() {
        count += Main.dt_one;
        for (int i = 0; i < amount_letters; i++) {
            letterHeight[i] = (int) (Math.sin(count * .05f + i) * amp);
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = amount_letters - 1; i >= 0; i--) {
            batch.draw(Res.tex_title[i], 2 + i * 19, y + letterHeight[i]);
        }
        batch.draw(Res.tex_underTitle, Main.width / 2 - Res.tex_underTitle.getRegionWidth() / 2, y - 23);
    }
}
