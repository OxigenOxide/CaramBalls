package com.oxigenoxide.caramballs.object.entity.pole;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.oxigenoxide.caramballs.Res;

public class Pole_Normal extends Pole {
    public Pole_Normal(float x, float y) {
        super(x, y);
        sprite = new Sprite(Res.tex_pole_normal);
    }
}