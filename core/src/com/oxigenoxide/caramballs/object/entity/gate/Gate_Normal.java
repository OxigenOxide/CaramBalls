package com.oxigenoxide.caramballs.object.entity.gate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.pole.Pole_Danger;
import com.oxigenoxide.caramballs.object.entity.pole.Pole_Normal;
import com.oxigenoxide.caramballs.scene.Game;

public class Gate_Normal extends Gate {
	boolean doSpawnMainBall;

	void createPoles() {
		pole_0 = new Pole_Normal(pos.x + RADIUS * (float) Math.cos(ang),
				pos.y + RADIUS * (float) Math.sin(ang));
		pole_1 = new Pole_Normal(pos.x + RADIUS * (float) Math.cos(ang + Math.PI),
				pos.y + RADIUS * (float) Math.sin(ang + Math.PI));
	}

	@Override
	public void update() {
		super.update();
		sprite_line.setColor(0.75f,0.75f,0.75f, 1);
		if (doSpawnMainBall) {
			doSpawnMainBall = false;
			Main.balls.add(new Ball_Main(pos.x, pos.y, 10, 0, Game.level - 1).setAscend());
			dispose();
		}
	}

	public void render(SpriteBatch batch) {

	}

	public void onCollision(Ball ball) {
		doSpawnMainBall = true;
	}
}