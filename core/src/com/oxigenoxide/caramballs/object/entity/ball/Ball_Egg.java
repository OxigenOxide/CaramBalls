package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.PlusMessage;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Egg extends Ball {

	static final float SPAWNHEIGHT = 30;
	public static final float RADIUS = 6;
	static final float BARWIDTH = 11;

	static final Vector2 dpos_bar = new Vector2(-6, 8);

	float progress;
	public float timeElapsed;
	public float timeToHatch;
	public int level;

	float count_shake;

	boolean doCrack;

	public Ball_Egg(float x, float y, int level) {
		super(x, y, SPAWNHEIGHT, 1);
		this.level = level;
		construct();
	}

	private void construct() {
		radius = RADIUS;
		radius_spawn = radius + 1;
		body.getFixtureList().first().getShape().setRadius(radius * Main.MPP);
		setSpriteTexture(Res.tex_egg);
		sizeFactor = 0;
		doWiggle = false;
		sprite.setOrigin(6, 6);
		Main.eggBalls.add(this);
		timeToHatch = (1 + level) * 120000;
		friction = .1f;
		//lock();
	}

	@Override
	public void update() {
		super.update();

		timeElapsed += Main.dt * 1000;

		progress = Math.min(1, timeElapsed / timeToHatch);

		if (progress == 1) {
			count_shake = MathFuncs.loopRadians(count_shake, Main.dt * 30);
			sprite.setRotation(10 * (float) Math.sin(count_shake));
			//sprite.setPosition(sprite.getX() + 1 * (float) Math.sin(count_shake), sprite.getY());
		}

		if (ascend) ascend();

		if (doCrack) crack();
	}

	void crack() {
		Ball_Main newBall = new Ball_Main(pos.x, pos.y, 0, 0, level);
		//newBall.setVelocity(body.getLinearVelocity());
		newBall.velY = 5;
		Main.ballsToAdd.add(newBall);
		Main.addSoundRequest(ID.Sound.EGGCRACK);
		Main.addSoundRequest(ID.Sound.PLOP);
		//Game.throwConfetti(pos.x, pos.y);
		throwParticles(1, pos, Res.palette_mainBall[level]);
		//throwParticles(body.getLinearVelocity().angleRad(), 1, pos, Res.palette_mainBall[level], getParticleAmount());
		dispose();
	}

	@Override
	public void onSelect() {
		if (progress == 1)
			doCrack = true;
		else
			super.onSelect();
	}

	@Override
	void update_trail() {
		// no trail
	}

	@Override
	void update_farm() {
		super.update_farm();
		//if (pos.y - radius < Main.farm.pos_field.y - DISPOSEBUFFER || pos.y + radius > Main.farm.pos_field.y + Main.farm.FIELDWIDTH + 4 + DISPOSEBUFFER)
		//    dispose();
	}

	public void render(SpriteBatch batch) {
		batch.setShader(Res.shader_palette);
		Main.setPalette(Res.palette_egg[level % Main.BALLCOLORS]);
		sprite.draw(batch);
		Funcs.setShaderNull(batch);
		if (progress < 1)
			drawBar(batch);
	}

	void drawBar(SpriteBatch batch) {
		batch.draw(Res.tex_eggBar, (int) pos.x + dpos_bar.x, pos.y + height + dpos_bar.y);
		batch.setColor(Res.palette_mainBall[level % Main.BALLCOLORS][2]);
		batch.draw(Res.tex_pixel, (int) pos.x + dpos_bar.x, pos.y + height + dpos_bar.y, (float) Math.floor(BARWIDTH * progress), 1);
		batch.draw(Res.tex_pixel, (int) pos.x + dpos_bar.x + 1, pos.y + height + dpos_bar.y + 1, (float) Math.floor(BARWIDTH * progress), 1);
		batch.setColor(Color.WHITE);
	}

	@Override
	public void dispose() {
		super.dispose();
		Main.eggBalls.remove(this);
	}

	@Override
	public void drawSelectionRing(SpriteBatch batch) {

	}

	@Override
	public void onCollision(Vector2 p, float impact, Object object_hit) {
		super.onCollision(p, impact, object_hit);
	}

	@Override
	public void destroy(float angle, float impact, Vector2 pos_danger) {
		explode(angle, impact);
	}

	public void explode(float angle, float impact) {

	}

	@Override
	public void renderShadow(ShapeRenderer sr) {
		super.renderShadow(sr);
	}

	public int getSize() {
		return size;
	}

	@Override
	public void drawTrail(ShapeRenderer sr) {
		// no trail
	}

	public Ball_Egg setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
		return this;
	}

	static public class Ball_Egg_Data {
		public float x, y;
		public int level;
		public long timeElapsed;

		public Ball_Egg_Data() {
			// this constructor is actually being used for serialisation
		}

		public Ball_Egg_Data(float x, float y, int level, long timeElapsed) {
			this.x = x;
			this.y = y;
			this.level = level;
			this.timeElapsed = timeElapsed;
		}
	}
}
