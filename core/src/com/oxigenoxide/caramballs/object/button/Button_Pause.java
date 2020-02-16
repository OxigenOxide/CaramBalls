package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Res;

public class Button_Pause extends Button {
    public Button_Pause(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_pause;
        tex_pressed = Res.tex_buttonPressed_pause;
    }

    @Override
    public void action() {
        if(Game.isPaused){
            Game.unpause();
            tex = Res.tex_button_pause;
            tex_pressed = Res.tex_buttonPressed_pause;
        }
        else{
            Game.pause();
            tex = Res.tex_button_resume;
            tex_pressed = Res.tex_buttonPressed_resume;
        }
    }
}
