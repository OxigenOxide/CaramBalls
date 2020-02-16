package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_ToGame extends Button {
    public Button_ToGame(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_toGame;
        tex_pressed = Res.tex_buttonPressed_toGame;
    }

    @Override
    public void action() {
        super.action();
        Main.setSceneMenu();
    }
}
