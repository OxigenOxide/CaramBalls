package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;
import com.oxigenoxide.caramballs.utils.Funcs;

import java.awt.Color;

public class Wheel {

	Sprite sprite_wheel;
	Sprite sprite_base;
	Sprite sprite_arrow;
	Vector2 pos;
	Vector2 pos_number;

	int level_begin;
	int level_lowest;
	int level_current;

	float rot;
	float rotLimit;
	EventManager eventManager;

	WheelBall[] balls;

	int rotations;

	int index_begin = 5;
	int index_end = 3;
	int index_current;

	static final float RADIUSTOBALL = 23;

	boolean isRotating;

	float arrowSwing;

	public boolean finished;

	public Wheel() {
		pos = new Vector2(Main.width / 2, Main.height / 2);
		pos_number = new Vector2(pos.x, pos.y - 21);
		sprite_wheel = new Sprite(Res.tex_wheel);
		sprite_wheel.setPosition(pos.x - sprite_wheel.getWidth() / 2, pos.y - sprite_wheel.getHeight() / 2);
		sprite_base = new Sprite(Res.tex_wheel_base);
		sprite_base.setPosition(pos.x - sprite_base.getWidth() / 2, pos.y - sprite_base.getHeight() + 3);
		sprite_arrow = new Sprite(Res.tex_wheel_arrow);
		sprite_arrow.setPosition(pos.x - sprite_arrow.getWidth() / 2, pos.y + 28);
		sprite_arrow.setOrigin(5, 7.5f);
		balls = new WheelBall[8];
		eventManager = new EventManager(new EventListener() {
			@Override
			public int onEvent(int event) {
				if (event == 0)
					return 2;
				if (rotations < 2 && rotate())
					return 1;
				finish();
				return -1;
			}
		});
	}

	public void update() {
		eventManager.update(Main.dt);
		for (int i = 0; i < balls.length; i++)
			balls[i].update();
		//rot += Main.dt;
		if (isRotating) {
			rot = Math.min(rotLimit, rot + Main.dt);
			isRotating = rot != rotLimit;
			arrowSwing = Math.min(1, arrowSwing + Main.dt * 5);
		} else {
			arrowSwing = Math.max(0, arrowSwing - Main.dt * 5);
		}
		sprite_arrow.setRotation(-arrowSwing * 30);
		sprite_wheel.setRotation((float) Math.toDegrees(rot));
	}

	boolean rotate() {
		if (index_current > index_end) {
			rotLimit += (float) Math.PI / 4;
			rotations++;
			isRotating = true;
			Main.addSoundRequest(ID.Sound.TAP, 1, 2);
			level_current--;
			index_current--;
			return true;
		}
		return false;
	}

	void finish() {
		Main.rewardBall = new RewardBall(balls[index_end].pos_ball.x, balls[index_end].pos_ball.y, Math.max(0, level_begin - 2));
		Main.setScenePrevious();
		Main.addSoundRequest(ID.Sound.CORRECT);
		finished = true;
	}

	public void set(int level) {
		level_begin = level;
		level_current = level;
		eventManager.start();
		rotations = 0;
		rotLimit = 0;
		rot = 0;
		finished = false;
		index_current = index_begin;
		index_end = index_begin - Math.min(2, level_begin);

		System.out.println("set() " + level_begin + " " + index_begin);
		for (int i = 0; i < balls.length; i++)
			balls[i] = new WheelBall(i, level_begin - index_begin + i);

		Main.rewardBall.setTarget(balls[index_begin].pos_ball.x, balls[index_begin].pos_ball.y);
	}

	public void render(SpriteBatch batch) {
		sprite_wheel.draw(batch);
		for (WheelBall ball : balls)
			ball.render(batch);
		Funcs.setShaderNull(batch);
		sprite_base.draw(batch);
		sprite_arrow.draw(batch);
		batch.setShader(Res.shader_palette);
		Main.setPalette(null, null, Res.palette_mainBall[level_current % Main.BALLCOLORS][1], Res.palette_mainBall[level_current % Main.BALLCOLORS][2]);
		Funcs.drawNumber(batch, level_current + 1, pos_number, ID.Font.POP);
		Funcs.setShaderNull(batch);
	}

	class WheelBall {
		int level;
		int index;
		Sprite sprite;
		Vector2 pos_ball;

		public WheelBall(int index, int level) {
			this.level = level;
			this.index = index;
			if (level >= 0)
				sprite = new Sprite(Res.tex_ball[Game.selectedSkin][0]);
			else
				sprite = new Sprite(Res.tex_wheel_empty);
			pos_ball = new Vector2();
			setPosition();
		}

		public void update() {
			setPosition();
			sprite.setPosition(pos_ball.x - sprite.getWidth() / 2, pos_ball.y - sprite.getHeight() / 2);
		}

		void setPosition() {
			pos_ball.set(pos.x + RADIUSTOBALL * (float) Math.cos(rot + Math.PI / 4 * (index - index_begin + 2)), pos.y + RADIUSTOBALL * (float) Math.sin(rot + Math.PI / 4 * (index - index_begin + 2)));
		}

		public void render(SpriteBatch batch) {
			if (level >= 0) {
				batch.setShader(Res.shader_palette);
				Main.setPalette(Res.palette_mainBall[level % Main.BALLCOLORS]);
			}
			sprite.draw(batch);
		}
	}
}
