package com.oxigenoxide.caramballs.object.entity.jelly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Jelly;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Jelly_Red extends Jelly {
	Sprite sprite_shield;
	Animation animation_shield;
	public final static int DIST_SHIELD = 10;
	final static int SHIELDWIDTH = 17;
	final static int SHIELDHEIGHT = 4;

	float count_shieldBump;

	public Jelly_Red() {
		super();
		animation_shield = new Animation(.7f, Res.tex_jellyShield, new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, false);
		animation = new Animation(.5f, Res.tex_jelly_red, new float[]{1, 1, 1, 1}, true);
		sprite_shield = new Sprite(Res.tex_jellyShield[0]);
		sprite_shield.setOrigin(SHIELDWIDTH / 2f, SHIELDHEIGHT + DIST_SHIELD);
	}

	@Override
	public void update() {
		animation_shield.update(Main.dt_slowed);
		body.setAngularVelocity(MathFuncs.getShortestAngle(body.getAngle(), angle) * 4);
		sprite_shield.setOrigin(SHIELDWIDTH / 2f, SHIELDHEIGHT + DIST_SHIELD - (float) Math.sin(count_shieldBump * Math.PI) * 3);
		sprite_shield.setPosition(pos.x - SHIELDWIDTH / 2f, pos.y - DIST_SHIELD - SHIELDHEIGHT + (float) Math.sin(count_shieldBump * Math.PI) * 3);
		sprite_shield.setRotation((float) Math.toDegrees(body.getAngle()));
		sprite_shield.setRegion(animation_shield.getTexture());
		count_shieldBump = Math.max(0, count_shieldBump - Main.dt_slowed * 2);
		super.update();
		//body.apply
	}

	@Override
	void createBody() {
		super.createBody();
		body.createFixture(Res.fixtureDef_jellyShield);
		body.getFixtureList().get(1).setUserData(true);
	}

	@Override
	public void onCollisionMainBall(Ball_Main ball_main) {
		super.onCollisionMainBall(ball_main);
		if (ball_main.body.getLinearVelocity().len() < .1f)
			ball_main.destroy();
		else {
			particleSpeed = Math.max(1, .4f * ball_main.body.getLinearVelocity().len());
			particleAngle = MathFuncs.angleBetweenPoints(ball_main.pos, pos);
			doExplode = true;
		}
	}

	public void onCollisionMainBall_shield(Ball_Main ball_main) {
		super.onCollisionMainBall(ball_main);
		if (ball_main.body.getLinearVelocity().len() < .1f)
			ball_main.destroy();
		else {
			animation_shield.loop();
			count_shieldBump = 1;
			Main.addSoundRequest(ID.Sound.SHIELD, 1, 1, .9f + (float) Math.random() * .2f);
		}
	}

	@Override
	public void explode() {
		Particle_Jelly.throwParticles(pos.x, pos.y, particleAngle, particleSpeed, 1);
		super.explode();
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		sprite_shield.draw(batch);
	}
}
