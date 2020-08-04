package com.oxigenoxide.caramballs.object.entity.jelly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.effect.Effect_BallExplosion;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.SpawnCondition;

public class Jelly extends Entity {
	public static final float RADIUS_SPAWN = 7.5f;
	public Sprite sprite;
	Body body;
	Ball ball_target;
	float angle;
	float velLen;
	float velFactor; // how fast you are going in range [0, 1] with 1 being velMax;
	float velMax = 10 * Main.MPP;
	Animation animation;
	boolean doDispose;
	boolean doExplode;
	boolean disappear;
	float size;
	float particleAngle;
	float particleSpeed;

	static final SpawnCondition[] spawnConditions = new SpawnCondition[]{new SpawnCondition(ID.Entity.BALL_MAIN, 30), new SpawnCondition(ID.Entity.ALL, 1)};

	public Jelly() {
		radius_spawn = RADIUS_SPAWN;
		//pos = Game.getFreePos(radius_spawn);
		pos = Game.getFreePos(radius_spawn, spawnConditions);
		if (pos == null) {
			pos = new Vector2(-100, -100);
			doDispose = true;
		}
		createBody();
		sprite = new Sprite(Res.tex_jelly_green[0]);
	}

	void createBody() {
		body = Main.world.createBody(Res.bodyDef_dynamic);
		Res.fixtureDef_circle.shape.setRadius(Main.MPP * 7.5f);
		body.createFixture(Res.fixtureDef_circle);
		body.setUserData(this);
		body.setTransform(Main.MPP * (pos.x), Main.MPP * (pos.y), 0);
	}

	public void update() {
		velLen = body.getLinearVelocity().len();
		velFactor = velLen / velMax;
		//animation.update(Main.dt_slowed * velFactor * .68f * Main.test_float);
		animation.update(Main.dt_slowed * velFactor);
		sprite.setRegion(animation.getTexture());

		float dst;
		float dst_shortest = 500;
		for (Ball_Main ball_main : Main.mainBalls) {
			dst = pos.dst(ball_main.pos);
			if (dst < dst_shortest) {
				dst_shortest = dst;
				ball_target = ball_main;
			}
		}

		if (ball_target != null) {
			angle = MathFuncs.angleBetweenPoints(pos, ball_target.pos);
			//body.setLinearVelocity(10 * Main.test_float * Main.MPP * (float) Math.cos(angle), 10 * Main.test_float * Main.MPP * (float) Math.sin(angle));
			body.applyForceToCenter(2 * Main.MPP * (float) Math.cos(angle), 2 * Main.MPP * (float) Math.sin(angle), true);
			if (velLen > velMax)
				body.setLinearVelocity(body.getLinearVelocity().x / velFactor, body.getLinearVelocity().y / velFactor);
		}

		pos.set(body.getPosition().x * Main.PPM, body.getPosition().y * Main.PPM);

		sprite.setSize(sprite.getRegionWidth() * size, sprite.getRegionHeight() * size);
		sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - 9 * size);

		if (disappear)
			size -= Main.dt * 5;
		else
			size = Math.min(1, size + Main.dt * 5);

		if (size <= 0)
			dispose();

		if (doDispose)
			dispose();
		if (doExplode)
			explode();
	}

	public void disappear() {
		disappear = true;
	}

	public void onCollisionMainBall(Ball_Main ball_main) {

	}

	public void render(SpriteBatch batch) {
		Funcs.setShaderNull(batch);
		sprite.draw(batch);
	}

	public void explode() {
		Main.addSoundRequest(ID.Sound.SLIME_DEATH, 1, .35f, .8f + (float) Math.random() * .3f);
		//Main.effects.add(new Effect_BallExplosion(pos.x,pos.y));
		Main.shake(1.15f * particleSpeed);
		dispose();
	}

	@Override
	public void dispose() {
		Main.jelliesToRemove.add(this);
		Main.destroyBody(body);
	}
}
