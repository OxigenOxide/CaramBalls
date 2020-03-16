package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Options extends Button {

    public Button_Options(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_options;
        tex_pressed = Res.tex_buttonPressed_options;
    }

    @Override
    public void action() {
        Main.menu.onOptionsPressed();
    }
}
