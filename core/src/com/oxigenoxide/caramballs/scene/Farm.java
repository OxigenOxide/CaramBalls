package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.EggShop;
import com.oxigenoxide.caramballs.object.LifeShop;
import com.oxigenoxide.caramballs.object.PlaceEgg;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.button.Button_Sell;
import com.oxigenoxide.caramballs.object.effect.Effect;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.FarmSpike;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Balls;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Egg;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Farm;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Sell;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.RepeatCounter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.oxigenoxide.caramballs.Main.balls;
import static com.oxigenoxide.caramballs.Main.effects_back;
import static com.oxigenoxide.caramballs.Main.effects_front;
import static com.oxigenoxide.caramballs.Main.entities;
import static com.oxigenoxide.caramballs.Main.holes;
import static com.oxigenoxide.caramballs.Main.mainBalls;
import static com.oxigenoxide.caramballs.Main.rewardOrbs;
import static com.oxigenoxide.caramballs.Main.scrHD;
import static com.oxigenoxide.caramballs.Main.tap;

public class Farm extends Scene {
	public Vector2 pos_field;
	public Vector2 pos_orbs;
	public Vector2 pos_orb;
	//Button_Play button_play;
	Button_Balls button_shop;
	Button button_return;
	Button_Sell button_sell;
	FrameBuffer buffer;
	Texture tex_buffer;
	Body body_cage;
	RepeatCounter counter_save;
	static boolean hasBeenInFarm;
	//Hole_Sell hole_sell;
	Hole_Farm hole_farm;
	FarmSpike farmSpike;
	public EggShop eggShop;
	public LifeShop lifeShop;
	public PlaceEgg placeEgg;
	int highestLevel;
	Vector2 pos_peerForce;
	Vector2 dim_peerForce;

	public static final int FIELDWIDTH = 100;

	public Farm() {
		pos_field = new Vector2(4, 62 + scrHD);
		button_return = new Button_Return(new Vector2(5, Main.height - 17));
		//button_play = new Button_Play(new Vector2(22, 29));
		button_shop = new Button_Balls(new Vector2(2, 6));
		pos_orbs = new Vector2(88, Main.height - 10);
		pos_orb = new Vector2(0, pos_orbs.y - 1 + 3.5f);
		buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getHeight(), Gdx.graphics.getHeight(), true);
		counter_save = new RepeatCounter(new ActionListener() {
			@Override
			public void action() {
				saveBalls();
				saveEggs();
			}
		}, 5);
		farmSpike = new FarmSpike(85, 80 + scrHD);
		eggShop = new EggShop();
		lifeShop = new LifeShop();
		placeEgg = new PlaceEgg();

		pos_peerForce = new Vector2(46, 29 + scrHD);
		dim_peerForce = new Vector2(16, 38);
	}

	HashMap<Boolean, Integer> ints;
	@Override
	public void update() {

		counter_save.update();
		//if (button_play.isTouching())
		//    button_play.update();
		//if (hole_reward == null)
		if (button_shop.isTouching())
			button_shop.update();
		if (button_return.isTouching())
			button_return.update();
		if (button_sell.isTouching())
			button_sell.update();
		//button_play.slide();

		hole_farm.update();
		farmSpike.update();
		eggShop.update();
		lifeShop.update();
		placeEgg.update();

		for (Ball_Main bm : Main.mainBalls) {
			if (bm.doPermaPassthrough)
				if (bm.pos.y > pos_field.y + bm.radius + 1)
					bm.setPermaPassthrough(false);
			if (bm.level > 0 && MathFuncs.pointInRectangle(bm.pos.x, bm.pos.y, pos_peerForce.x, pos_peerForce.y, dim_peerForce.x, dim_peerForce.y))
				bm.addVelocity(0, -10 * Main.dt);
		}

		Main.world.step(Main.dt, 3, 8);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer sr) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		buffer.begin();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();

		batch.draw(Res.tex_farmField, 2, pos_field.y - 34);
		//button_play.render(batch);
		button_shop.render(batch);
		button_return.render(batch);
		button_sell.render(batch);
		batch.draw(Res.tex_text_yourBalls, 17, pos_field.y + 104);
		eggShop.render(batch);
		lifeShop.render(batch);

		hole_farm.render(batch);
		farmSpike.renderOnFloor(batch);
		batch.end();

		sr.begin(ShapeRenderer.ShapeType.Filled);
		for (Hole h : holes)
			h.render(sr);
		sr.setColor(0, 0, 0, .8f);
		placeEgg.renderShadow(sr);
		Main.ballSelector.render(sr);
		sr.end();

		batch.begin();

		for (Ball b : balls)
			b.renderShadow(batch);

		Main.ballSelector.render(batch);

		for (Effect effect : effects_back)
			effect.render(batch);

		for (Entity e : Main.entities_sorted)
			e.render(batch);

		Funcs.setShaderNull(batch);

		for (Effect effect : effects_front)
			effect.render(batch);

		// speech bubbles
		for (Ball_Main ball_main : mainBalls)
			ball_main.render_farm(batch);

		placeEgg.render(batch);
		// orb display
		batch.draw(Res.tex_orbCountBar, pos_orbs.x - Res.tex_orbCountBar.getRegionWidth() / 2, pos_orbs.y - 2);
		int width = Funcs.drawNumberSignColor(batch, (int) Main.orbs_visual, pos_orbs, ID.Font.SMALL, Res.tex_orb, -1, Res.COLOR_ORBNUMBER);
		pos_orb.x = pos_orbs.x - width / 2 + 3.5f;

		for (RewardOrb ro : rewardOrbs)
			ro.render(batch);

		//if (!Main.inHTML)
		//	drawClocks(batch);

		batch.end();

		buffer.end(); // BUFFER END

		batch.disableBlending();

		tex_buffer = buffer.getColorBufferTexture();
		tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		Main.setNoCamEffects();
		batch.begin();

		if (Main.test_boolean)
			batch.setShader(Res.shader_pixelate);
		Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);

		batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
		batch.setShader(null);

		batch.end();
		Main.setCamEffects();
		batch.enableBlending();
	}

	@Override
	public void dispose() {
		eggShop.dispose();
	}

	@Override
	public void show() {

		super.show();
		//body_cage
		body_cage = Main.world.createBody(Res.bodyDef_static);
		body_cage.createFixture(Res.fixtureDef_cage);
		body_cage.setTransform(pos_field.x * Main.MPP, pos_field.y * Main.MPP, 0);
		body_cage.createFixture(Res.fixtureDef_cage_corner_0);
		body_cage.createFixture(Res.fixtureDef_cage_corner_1);

		//add elapsed time to farm balls
		if (Main.gameData.time_leftFarm != 0) {
			for (Ball_Main.Ball_Main_Data ball_main_data : Main.gameData.farmBalls)
				ball_main_data.timeElapsed_milk += System.currentTimeMillis() - Main.gameData.time_leftFarm;
			for (Ball_Egg.Ball_Egg_Data ball_egg_data : Main.gameData.eggBalls)
				ball_egg_data.timeElapsed += System.currentTimeMillis() - Main.gameData.time_leftFarm;
		}

		//spawn farm balls & eggs
		for (Ball_Main.Ball_Main_Data ball_data : Main.gameData.farmBalls)
			balls.add(new Ball_Main(ball_data.x, ball_data.y, 0, ball_data.size, ball_data.level).setTimeElapsed_milk(ball_data.timeElapsed_milk));
		for (Ball_Egg.Ball_Egg_Data ball_egg_data : Main.gameData.eggBalls)
			balls.add(new Ball_Egg(ball_egg_data.x, ball_egg_data.y, ball_egg_data.level).setTimeElapsed(ball_egg_data.timeElapsed));
		if (balls.size() == 0)
			balls.add(new Ball_Egg(Main.pos_middle.x, Main.pos_middle.y + 20, 0));

		button_sell = new Button_Sell(new Vector2(56, 6));

		hasBeenInFarm = true;

		//hole_sell = new Hole_Sell(85, 80 + scrHD);
		//holes.add(hole_sell);
		hole_farm = new Hole_Farm(Main.width / 2, scrHD + 40);

		Ball_Main.commonColor = false;
	}

	public void onBallAdd(int level) {
		if (level > highestLevel) {
			highestLevel = level;
			eggShop.onNewHighestBall(highestLevel);
		}
	}

	@Override
	public void addEntities(ArrayList<Entity> entities) {
		entities.add(farmSpike);
	}

	public void saveBalls() {
		Main.gameData.farmBalls.clear();
		for (Ball_Main bm : Main.mainBalls) {
			//System.out.println(" save: " + bm.timeElapsed_milk);
			Main.gameData.farmBalls.add(new Ball_Main.Ball_Main_Data(bm.pos.x, bm.pos.y, bm.size, bm.level, (long) bm.timeElapsed_milk));
		}
		// Lesson learned, again, most variables you shouldn't just set to an object
		// Second lesson learned, clear a list before adding objects again if you want to copy
	}

	public void saveEggs() {
		Main.gameData.eggBalls.clear();
		for (Ball_Egg ball_egg : Main.eggBalls)
			Main.gameData.eggBalls.add(new Ball_Egg.Ball_Egg_Data(ball_egg.pos.x, ball_egg.pos.y, ball_egg.level, (long) ball_egg.timeElapsed));
	}


	public void onPause() {
		Main.gameData.time_leftFarm = System.currentTimeMillis();
	}

	public void startSelling() {
		//hole_sell.open();
		farmSpike.open();
	}

	public void endSelling() {
		//hole_sell.close();
		farmSpike.close();
	}

	public void saveData() {
		eggShop.saveData();
	}

	@Override
	public void hide() {
		super.hide();

		saveBalls();
		saveEggs();
		body_cage = Main.destroyBody(body_cage);
		farmSpike.dispose();
		Main.clearEntities();
		Main.gameData.time_leftFarm = System.currentTimeMillis();
	}

	void drawClocks(SpriteBatch batch) { // replaced by bars
		float progress;
		TextureRegion textureRegion;
		batch.setShader(Res.shader_clock);
		for (Ball_Main ball : mainBalls) {
			progress = ball.getProgressMilk();
			if (progress == 1 || ball.height < 0) continue;
			textureRegion = Res.tex_clock[ball.size];

			Res.shader_clock.setUniformf("progress", progress);
			Res.shader_clock.setUniformf("uv", (textureRegion.getU() + textureRegion.getU2()) / 2, (textureRegion.getV() + textureRegion.getV2()) / 2);

			batch.draw(textureRegion, (int) (ball.pos.x - textureRegion.getRegionWidth() / 2f), (int) (ball.pos.y - textureRegion.getRegionHeight() / 2f));

			batch.flush();
		}
		batch.setShader(null);
	}

	@Override
	public void onKeyDown(int keycode) {
		if (keycode == Input.Keys.ENTER) {
			Game.reset();
			Main.setScene(Main.game); // game will setup because gameOver == false
			Game.setLevel(5);
		}

		if (keycode == Input.Keys.B)
			balls.add(new Ball_Main(Main.tap[0].x, Main.tap[0].y, 5, 0, 0));

		if (keycode == Input.Keys.E)
			balls.add(new Ball_Egg(Main.tap[0].x, Main.tap[0].y, 0).setTimeElapsed(10000000));

		if (keycode == Input.Keys.M)
			Main.gameData.orbs += 200;
	}
}
