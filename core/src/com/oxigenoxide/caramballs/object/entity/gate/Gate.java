package com.oxigenoxide.caramballs.object.entity.gate;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.pole.Pole;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Gate extends Entity {

	float ang;
	Body body_trigger;
	Pole pole_0;
	Pole pole_1;
	Sprite sprite_line;
	boolean isDisposed;

	static final float RADIUS = 20;

	boolean doDispose;

	public Gate() {
		radius_spawn = RADIUS;
		pos = Game.getFreePos(RADIUS);
		ang = (float) (Math.random() * Math.PI); // half a circle
		sprite_line = new Sprite(Res.tex_pixel);

		if (pos != null) {
			createPoles();
			Main.poles.add(pole_0);
			Main.poles.add(pole_1);
			createBody();
			sprite_line.setSize(RADIUS * 2, 3);
			sprite_line.setRotation((float) Math.toDegrees(ang));
			sprite_line.setOrigin(0, 1.5f);
			sprite_line.setPosition(pole_1.pos.x, pole_1.pos.y - 1.5f);
		} else {
			pos = new Vector2(-1111, 0);
			doDispose = true;
		}
	}

	public void createBody() {
		body_trigger = Main.world.createBody(Res.bodyDef_static);
		((EdgeShape) Res.fixtureDef_gate.shape).set(
				RADIUS * (float) Math.cos(ang) * Main.MPP,
				RADIUS * (float) Math.sin(ang) * Main.MPP,
				RADIUS * (float) Math.cos(ang + Math.PI) * Main.MPP,
				RADIUS * (float) Math.sin(ang + Math.PI) * Main.MPP
		);
		body_trigger.createFixture(Res.fixtureDef_gate);
		body_trigger.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
		body_trigger.setUserData(this);
	}

	void createPoles() {

	}

	public void update() {
		if (doDispose)
			dispose();
	}

	public void onCollision(Ball ball) {
	}

	@Override
	public void dispose() {
		super.dispose();

		if (isDisposed)
			return;

		Main.destroyBody(body_trigger);
		Main.gatesToRemove.add(this);
		if(pole_0!=null) { // in that case pole_1 is null too
			pole_0.disappear();
			pole_1.disappear();
		}
		isDisposed = true;
	}

	public void renderFloor(SpriteBatch batch) {
		Funcs.setShaderNull(batch);
		sprite_line.draw(batch);
	}

}
