package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Tutorial extends Button {
    public Button_Tutorial(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_tutorial;
        tex_pressed = Res.tex_buttonPressed_tutorial;
    }

    @Override
    public void action() {
        Main.amm.hide();
        Main.setSceneGame();
        Game.doSetTutorialMode = true;
    }
}
