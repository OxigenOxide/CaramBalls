package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Button_Buy extends Button {

    int price;
    Vector2 pos_price;

    public Button_Buy(Vector2 pos, int price) {
        super(pos);
        this.price = price;
        tex = Res.tex_button_buy;
        tex_pressed = Res.tex_buttonPressed_buy;
        pos_price = new Vector2(pos.x + tex.getRegionWidth() / 2, pos.y + 4 - touchOffset);
    }

    @Override
    public void action() {
        Main.shop.buySkinBox();
    }


    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        pos_price.set(pos.x + tex.getRegionWidth() / 2, pos.y + 4 - touchOffset);
        Funcs.drawNumberSignAfterColor(batch, price, pos_price, ID.Font.SMALL, Res.tex_orb, -1, Res.COLOR_ORBNUMBER);
    }
}
