package com.oxigenoxide.caramballs.object.floatingReward;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class FR_Eye extends FloatingReward{
    int type;
    public FR_Eye(float x, float y, int type){
        super(x,y);
        this.type = type;
        tex= Res.tex_eye[type];
        sprite=new Sprite(tex);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
