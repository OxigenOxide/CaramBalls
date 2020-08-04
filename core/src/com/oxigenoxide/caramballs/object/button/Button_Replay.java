package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Replay extends Button {
    public Button_Replay(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_replay;
        tex_pressed = Res.tex_buttonPressed_replay;
    }

    @Override
    public void action() {
        super.action();
        //Main.setScenePrevious(); // farm
        Main.popScene();
        Main.setSceneDownGrading();
    }
}
