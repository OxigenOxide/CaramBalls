package com.oxigenoxide.caramballs.object.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;

public class Effect {
    Vector2 pos;

    public Effect(float x, float y){
        pos=new Vector2(x,y);
    }
    public void update() {

    }

    public void render(SpriteBatch batch) {

    }

    public void dispose() {
            Main.effects_front.remove(this);
            Main.effects_back.remove(this);
            Main.effectsToRemove.add(this);

    }
}
