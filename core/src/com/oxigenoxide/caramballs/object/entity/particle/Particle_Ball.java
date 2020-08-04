package com.oxigenoxide.caramballs.object.entity.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Particle_Ball extends Particle {
	Color[] palette;

	public Particle_Ball(float x, float y, float angle, float speed, int level) {
		super(x, y);
		palette = Res.palette_mainBall[level];
		construct(angle, speed);
	}

	public Particle_Ball(float x, float y, float angle, float speed, Color[] palette) {
		super(x, y);
		this.palette = palette;
		construct(angle, speed);
	}

	public Particle_Ball(float x, float y, float angle, float speed, Color color) {
		super(x, y);
		this.palette = new Color[]{null, null, color, null};
		construct(angle, speed);
	}

	public void construct(float angle, float speed) {
		Main.particles_batch.add(this);
		vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
		sprite = new Sprite(Res.tex_particle_ball[(int) (Math.random() * 2)]);
		resistance = .3f;
		lifeTime = 35;
		velY = 4.5f * speed;
		doShrink = true;
		//velY = 3.6f * speed * .5f * ((float) Math.random() + 1);
		doBounce = true;
		hasShadow = true;
		shadowWidth = sprite.getWidth();
		shadowHeight = sprite.getHeight();
		setSpritePosition();
	}

	public void update() {
		super.update();
	}

	public void render(SpriteBatch batch) {
		Funcs.setShader(batch, Res.shader_palette);
		Main.setPalette(palette);
		super.render(batch);
		batch.setShader(null);
	}
}
