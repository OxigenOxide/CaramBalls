package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;

public class Button_Music extends Button {
    public Button_Music(Vector2 pos) {
        super(pos);
        setTextures();
    }

    void setTextures(){
        if(Main.isMusicMuted){
            tex = Res.tex_button_musicMuted;
            tex_pressed = Res.tex_buttonPressed_musicMuted;
        }
        else{
            tex = Res.tex_button_music;
            tex_pressed = Res.tex_buttonPressed_music;
        }
    }

    @Override
    public void action() {
        if(Main.isMusicMuted)
            Main.unmuteMusic();
        else
            Main.muteMusic();

        setTextures();
    }
}
