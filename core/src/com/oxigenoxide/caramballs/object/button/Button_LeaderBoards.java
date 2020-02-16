package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_LeaderBoards extends Button {
    public Button_LeaderBoards(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_leaderBoards;
        tex_pressed = Res.tex_buttonPressed_leaderBoards;
    }

    @Override
    public void action() {
        super.action();
        Main.gm.showLeaderBoards();
    }
}
