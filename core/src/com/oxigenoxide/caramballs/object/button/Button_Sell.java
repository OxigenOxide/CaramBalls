package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Sell extends Button {
    boolean doSell;

    public Button_Sell(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_sell;
        tex_pressed = Res.tex_buttonPressed_sell;
    }

    @Override
    public void action() {
        doSell = !doSell;
        if (doSell)
            Main.farm.startSelling();
        else
            Main.farm.endSelling();
    }

    public void set(boolean doSell) {
        this.doSell = doSell;
    }

    public void render(SpriteBatch batch) {
        if (doSell || isTouched)
            batch.draw(tex_pressed, pos.x, pos.y);
        else
            batch.draw(tex, pos.x, pos.y);
    }
}
