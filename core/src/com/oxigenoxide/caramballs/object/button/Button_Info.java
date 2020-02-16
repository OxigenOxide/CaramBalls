package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Info extends Button {
    public Button_Info(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_info;
        tex_pressed = Res.tex_buttonPressed_info;
    }

    @Override
    public void action() {
        Main.gm.dialog("WHATSUP!!!!!!!!!!!!!!!!!!!");
    }
}
