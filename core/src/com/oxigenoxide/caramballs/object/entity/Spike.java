package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Spike extends Entity {
	TextureRegion tex;
	int index_tex;
	float state = 0;
	boolean open;
	boolean close;
	Body body;
	float count;
	float countMax = 5;
	boolean isPermanent;
	Ball ballHit;
	float delay;
	int index_hole;
	int index_spike;

	public static final float RADIUS = 3;
	public static final float RADIUS_SPAWN = 5;

	public Spike(boolean isPermanent) {
		this.isPermanent = isPermanent;
		radius_spawn = RADIUS_SPAWN;
		pos = Game.getFreePos(radius_spawn);
		if (pos == null)
			pos = new Vector2(-100, -100);
		construct();
	}

	public Spike(float x, float y, boolean isPermanent) {
		radius_spawn = RADIUS_SPAWN;
		this.isPermanent = isPermanent;
		pos = new Vector2(x, y);
		if (!Game.isPosFree(pos, radius_spawn)) {
			pos = new Vector2(-100, -100);
			System.out.println(this);
			dispose();
		}
		construct();
	}

	public Spike(float x, float y, float delay, boolean isPermanent) {
		radius_spawn = RADIUS_SPAWN;
		this.delay = delay;
		this.isPermanent = isPermanent;
		//this.isPermanent = false;
		pos = new Vector2(x, y);
		if (!Game.isPosFree(pos, radius_spawn)) {
			pos = new Vector2(-100, -100);
			dispose();
		}
		construct();
	}

	private void construct() {
		open();
		tex = Res.tex_spike[index_tex];
		radius_spawn = RADIUS;
	}

	void createBody() {
		body = Main.world.createBody(Res.bodyDef_static);
		body.createFixture(Res.fixtureDef_spike);
		body.setUserData(this);
		body.setTransform(Main.MPP * (pos.x), Main.MPP * (pos.y), 0);
	}

	public void update() {
		delay = Math.max(delay - Main.dt_one, 0);
		if (delay > 0)
			return;
		if (open)
			state += .025f * Main.dt_one;
		if (close)
			state -= .025f * Main.dt_one;
		state = MathUtils.clamp(state, 0, 1);
		index_tex = (int) (state * (Res.tex_spike.length - 1));
		tex = Res.tex_spike[index_tex];

		if (state == 1 && body == null)
			createBody();

		if (!isPermanent) {
			count += Main.dt;
			if (count >= countMax)
				close();
		}

		if (state == 0)
			dispose();

		if (ballHit != null) {
			ballHit.destroy(0, .4f * ballHit.body.getLinearVelocity().len(), pos);
			ballHit = null;
		}
	}

	public void hitBall(Ball ball) {
		ballHit = ball;
	}

	public void open() {
		open = true;
		close = false;
	}

	public void close() {
		open = false;
		close = true;
	}

	public void render(SpriteBatch batch) {
		Funcs.setShaderNull(batch);
		if (delay > 0)
			return;
		batch.draw(tex, (int) (pos.x - tex.getRegionWidth() / 2), (int) pos.y - 5);
	}

	public void disappear() {
		close();
	}

	public Spike setLifeTime(float time) {
		countMax = time;
		return this;
	}

	public void dispose() {
		body = Main.destroyBody(body);
		Main.spikesToRemove.add(this);
		state = 0;
	}
}
