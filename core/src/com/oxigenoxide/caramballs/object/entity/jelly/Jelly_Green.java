package com.oxigenoxide.caramballs.object.entity.jelly;

import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Jelly;
import com.oxigenoxide.caramballs.utils.Animation;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Jelly_Green extends Jelly {
	public Jelly_Green() {
		super();
		animation = new Animation(.5f, Res.tex_jelly_green, new float[]{1, 1, 1, 1}, true);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void onCollisionMainBall(Ball_Main ball_main) {
		super.onCollisionMainBall(ball_main);
		if (ball_main.body.getLinearVelocity().len() < .1f)
			ball_main.destroy();
		else {
			doExplode = true;
			particleSpeed = Math.max(1, .4f * ball_main.body.getLinearVelocity().len());
			particleAngle = MathFuncs.angleBetweenPoints(ball_main.pos, pos);
		}
	}

	@Override
	public void explode() {
		System.out.println("lin vel: " + body.getLinearVelocity().len());
		//Particle_Jelly.throwParticles(pos.x, pos.y, body.getLinearVelocity().angleRad(),body.getLinearVelocity().len() * Main.PPM, 0);
		Particle_Jelly.throwParticles(pos.x, pos.y, particleAngle, particleSpeed, 0);
		super.explode();
	}
}
