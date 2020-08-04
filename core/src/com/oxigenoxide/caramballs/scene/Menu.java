package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.TTPText;
import com.oxigenoxide.caramballs.object.Title;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Options;
import com.oxigenoxide.caramballs.object.button.Button_Tutorial;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;
import com.oxigenoxide.caramballs.utils.Funcs;

import static com.oxigenoxide.caramballs.Main.balls;
import static com.oxigenoxide.caramballs.Main.entities_sorted;

public class Menu extends Scene {
	Title title;
	TTPText ttpText;
	Button button_options;
	Button button_tutorial;
	boolean buttonTouched;
	boolean optionsMenuOpen;
	EventManager eventManager_options;
	boolean musicButtonOut, soundButtonOut;

	Vector2 pos_musicButton_hidden, pos_soundButton_hidden;
	Vector2 pos_musicButton_out, pos_soundButton_out;

	// spawning balls
	Counter counter_spawnBalls;
	Vector2 pos_spawnCircle;
	float radius_spawnCircle;
	float timeFirstSpawn = 4;

	Counter counter_jump;

	float jumpDelayPerBall = 10;

	FrameBuffer buffer;
	Texture tex_buffer;

	public Menu() {
		buffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Main.width, (int) Main.height, true);
		title = new Title();
		ttpText = new TTPText(Main.height / 2);
		eventManager_options = new EventManager(new EventListener() {
			@Override
			public int onEvent(int event) {
				switch (event) {
					case 0:
						musicButtonOut = optionsMenuOpen;
						return 20;
					case 1:
						soundButtonOut = optionsMenuOpen;
				}
				return -1;
			}
		});

		pos_musicButton_out = new Vector2(76, 3);
		pos_soundButton_out = new Vector2(61, 3);

		pos_musicButton_hidden = new Vector2();
		//pos_soundButton_hidden

		counter_spawnBalls = new Counter(new ActionListener() {
			@Override
			public void action() {
				float ang = -(float) (Math.random() * Math.PI);
				float speed = 2 + 2 * (float) Math.random();
				Ball newBall = new Ball_Main(
						pos_spawnCircle.x + radius_spawnCircle * (float) Math.cos(ang),
						pos_spawnCircle.y - 15 + radius_spawnCircle * (float) Math.sin(ang),
						0, (int) (Math.random() * 3), (int) (Math.random() * Main.BALLCOLORS))
						.setVelocity(speed * (float) -Math.cos(ang), speed * (float) -Math.sin(ang));
				newBall.velY = 3 + (float) Math.random() * 5;
				Main.balls.add(newBall);
				counter_spawnBalls.setTime((.5f + (float) Math.random() * 5) * Main.mainBalls.size());
				counter_spawnBalls.start();
			}
		}, timeFirstSpawn);
		counter_spawnBalls.start();

		counter_jump = new Counter(new ActionListener() {
			@Override
			public void action() {
				if (Main.mainBalls.size() != 0) {
					Ball ball_target = Main.mainBalls.get((int) (Math.random() * Main.mainBalls.size()));
					if (ball_target.height == 0)
						ball_target.velY = 3 + (float) Math.random() * 5;
				}
				counter_jump.setTime(jumpDelayPerBall / (Main.mainBalls.size() + 1));
				counter_jump.start();
			}
		}, jumpDelayPerBall);
		counter_jump.start();

		radius_spawnCircle = Main.height / 2;

		pos_spawnCircle = new Vector2(Main.pos_middle);
	}

	@Override
	public void show() {
		optionsMenuOpen = false;
		Main.setAdVisibility(true);
		button_options = new Button_Options(new Vector2(Main.width - 2 - Res.tex_button_options.getRegionWidth(), 2));
		button_tutorial = new Button_Tutorial(new Vector2(12, 10));
		button_tutorial.setVisibility(false);
		Main.border.setActive(false);
		Main.ballSelector.disable();
		jumpDelayPerBall = 10;

		counter_spawnBalls.setTime(timeFirstSpawn);
		counter_spawnBalls.setProgress(0);
		counter_jump.setProgress(1);
	}

	@Override
	public void hide() {
		super.hide();
		Main.border.setActive(true);
		Main.ballSelector.enable();
		Main.clearEntities();
	}

	@Override
	public void update() {
		counter_jump.update();
		counter_spawnBalls.update();
		title.update();
		ttpText.update();
		buttonTouched = false;
		if (button_options.isTouching()) {
			button_options.update();
			buttonTouched = true;
		}

		if (button_tutorial.isTouching()) {
			button_tutorial.update();
			buttonTouched = true;
		}

		if (!button_options.isTouched && !buttonTouched && Gdx.input.justTouched()) {
			Main.amm.hide();
			Main.setSceneFarm();
			jumpDelayPerBall = .05f;
			counter_jump.action();
			Main.addSoundRequest(ID.Sound.CORRECT);
		}

		Main.world.step(Main.dt, 3, 8);
		//if (soundButtonOut)
		//    button_tutorial.pos.

	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer sr) {
		buffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.setShader(Res.shader_palette);
		Main.setPalette(Res.palette_table[0]);
		batch.draw(Res.tex_tabletop[0], Main.width / 2 - Res.tex_tabletop[0].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[0].getHeight() / 2);
		batch.setShader(null);

		for (Ball ball : balls)
			ball.renderShadow(batch);

		for (Entity entity : entities_sorted)
			entity.render(batch, sr);

		Funcs.setShaderNull(batch);

		button_options.render(batch);
		ttpText.render(batch);
		title.render(batch);
		button_tutorial.render(batch);
		batch.end();
		buffer.end();

		batch.disableBlending();

		tex_buffer = buffer.getColorBufferTexture();
		tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		Main.setNoCamEffects();
		batch.begin();

		batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);

		batch.end();
		Main.setCamEffects();
		batch.enableBlending();
	}

	public void onOptionsPressed() {
		optionsMenuOpen = !optionsMenuOpen;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void onKeyDown(int keycode) {
		if (keycode == Input.Keys.B)
			Main.balls.add(new Ball_Main(Main.tap[0].x, Main.tap[0].y, 1, 1, 1));
	}
}
