package com.oxigenoxide.caramballs.object.entity.pole;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Pole extends Entity {
	Sprite sprite;
	Body body;
	float progress;
	boolean disappear;

	static final float RADIUS = 3;

	public Pole(float x, float y) {
		pos = new Vector2(x, y);
		createBody();
	}

	public void update() {
		if (disappear)
			progress = Math.max(progress - Main.dt_slowed * 3, 0);
		else
			progress = Math.min(progress + Main.dt_slowed * 3, 1);

		sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - RADIUS);
		sprite.setSize(sprite.getRegionWidth() * progress, sprite.getRegionHeight() * progress);

		if (progress <= 0)
			dispose();
	}

	@Override
	public void render(SpriteBatch batch) {
		Funcs.setShaderNull(batch);
		sprite.draw(batch);
	}

	public void disappear() {
		disappear = true;
	}

	void createBody() {
		body = Main.world.createBody(Res.bodyDef_static);
		Res.fixtureDef_circle.shape.setRadius(Main.MPP * RADIUS);
		body.createFixture(Res.fixtureDef_circle);
		body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
	}

	@Override
	public void dispose() {
		super.dispose();
		Main.destroyBody(body);
		Main.polesToRemove.add(this);
	}
}
