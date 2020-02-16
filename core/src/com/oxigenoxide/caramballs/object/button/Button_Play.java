package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;

public class Button_Play extends Button {
    Vector2 pos_original;
    boolean doSlideIntoScreen;
    public Button_Play(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_play;
        tex_pressed = Res.tex_buttonPressed_play;
        pos_original=new Vector2(pos);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void action() {
        Main.setSceneGame();
    }

    public void slide(){
        if(doSlideIntoScreen){
            pos.x+=(pos_original.x-pos.x)*.3f;
        }
    }

    public void slideIntoScreen(){
        doSlideIntoScreen=true;
    }
    public void placeOutOfScreen(){
        pos.set(-tex.getWidth(),pos_original.y);
    }
}
