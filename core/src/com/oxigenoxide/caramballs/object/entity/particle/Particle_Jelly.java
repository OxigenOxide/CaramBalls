package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_Jelly extends Particle {

	public Particle_Jelly(float x, float y, float angle, float speed, int index, int type) {
		super(x, y);
		Main.particles_batch.add(this);
		vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
		if (type == 0)
			sprite = new Sprite(Res.tex_particle_jelly_green[index]);
		else
			sprite = new Sprite(Res.tex_particle_jelly_red[index]);
		resistance = .3f;
		lifeTime = 100;
		velY = 3.6f * speed * .5f * ((float) Math.random() + 1);
		doBounce = true;
		setSpritePosition();
		hasShadow = true;
		shadowWidth = sprite.getWidth();
		shadowHeight = sprite.getHeight();
	}

	public void update() {
		super.update();
	}

	public void render(SpriteBatch batch) {
		Funcs.setShaderNull(batch);
		super.render(batch);
	}

	public static void throwParticles(float x, float y, float angle, float speed, int type) {
		int amount = 3;
		for (int i = 0; i < amount; i++)
			//Main.particles.add(new Particle_Jelly(x, y, angle + (float) (Math.random() * Math.PI) / 2, .36f * speed * .5f * ((float) Math.random() + 1), i % 3, type));
			Main.particles.add(new Particle_Jelly(x, y, angle + (float) (Math.random() * Math.PI) / 2,  speed * .5f * ((float) Math.random() + 1), i % 3, type));
	}
}