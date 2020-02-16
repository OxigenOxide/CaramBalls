package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Sound extends Button {
    public Button_Sound(Vector2 pos) {
        super(pos);
        setTextures();
    }

    void setTextures() {
        if (Main.isSoundMuted) {
            tex = Res.tex_button_soundMuted;
            tex_pressed = Res.tex_buttonPressed_soundMuted;
        } else {
            tex = Res.tex_button_sound;
            tex_pressed = Res.tex_buttonPressed_sound;
        }
    }

    @Override
    public void action() {
        if (Main.isSoundMuted)
            Main.unmuteSound();
        else
            Main.muteSound();
        setTextures();
    }
}
