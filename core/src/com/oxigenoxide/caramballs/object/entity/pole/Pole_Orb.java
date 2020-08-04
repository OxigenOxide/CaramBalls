package com.oxigenoxide.caramballs.object.entity.pole;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.oxigenoxide.caramballs.Res;

public class Pole_Orb extends Pole {
	public Pole_Orb(float x, float y) {
		super(x, y);
		sprite = new Sprite(Res.tex_pole_orb);
	}
}
