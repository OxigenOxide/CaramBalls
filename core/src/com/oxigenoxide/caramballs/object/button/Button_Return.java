package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Return extends Button {
    public Button_Return(Vector2 pos) {
        super(pos);
        tex= Res.tex_button_return;
        tex_pressed= Res.tex_buttonPressed_return;
    }

    @Override
    public void action() {
        super.action();
        Main.setScenePrevious();
    }
}
