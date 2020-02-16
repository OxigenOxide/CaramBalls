package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Cross extends Button {
    public Button_Cross(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_cross;
        tex_pressed = Res.tex_buttonPressed_cross;
    }

    @Override
    public void action() {
        Main.setSceneGame();
    }
}
