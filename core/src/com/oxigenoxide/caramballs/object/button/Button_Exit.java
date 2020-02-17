package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Exit extends Button {
    public Button_Exit(Vector2 pos) {
        super(pos);
        tex= Res.tex_button_exit;
        tex_pressed= Res.tex_buttonPressed_exit;
    }

    @Override
    public void action() {
        super.action();
        Main.setScenePrevious();
    }
}
