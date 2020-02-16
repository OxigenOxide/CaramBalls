package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Button_Balls extends Button {

    public Button_Balls(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_balls;
        tex_pressed = Res.tex_buttonPressed_balls;
    }

    public void setNew(){
        tex = Res.tex_button_balls_new;
        tex_pressed = Res.tex_buttonPressed_balls_new;
    }
    public void setNormal(){
        tex = Res.tex_button_balls;
        tex_pressed = Res.tex_buttonPressed_balls;
    }

    @Override
    public void action() {
        Main.setSceneShop();
    }
}
