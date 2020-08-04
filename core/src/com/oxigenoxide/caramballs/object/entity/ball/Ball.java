package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.oxigenoxide.caramballs.object.PulseEffect;
import com.oxigenoxide.caramballs.object.SpikeTrail;
import com.oxigenoxide.caramballs.object.Trail;
import com.oxigenoxide.caramballs.object.entity.CircularBumper;
import com.oxigenoxide.caramballs.object.entity.Spike;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Cross;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Hit;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_MegaHit;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball extends Entity {
	public Sprite sprite;
	Vector2 pos_last;
	Vector2 pos_mid = new Vector2();
	public Body body;
	float wiggle;
	public float height;
	public float velY;
	public float radius;
	float count_hitCooldown;
	float maxSpeedMinimum = 6;
	float maxSpeed = maxSpeedMinimum;
	public int size = 0;
	public boolean isUnderGround;
	public boolean doDispose;
	Ball_Main ballmain_hit;
	Ball ball_hit;
	float angleToMid;
	float distToMid;
	float time_off;
	public boolean fall;
	Hole hole_fall;
	public Hole hole_spawn;
	public float count_recentlyHit;
	float countMax_recentlyHit = 30;
	public boolean isDisposed;
	boolean isPassthrough;
	boolean dontSplit;
	public boolean isLocked;
	boolean doDrainSpeed;
	public float count_cantGetStuck;
	boolean doActivateShield;
	boolean isShrinking;

	boolean frozen;
	boolean doFreeze;
	float freezeCooldown;
	float count_frozen;

	float selectedFactor;
	float count_circle;
	float ringSizeFactor;
	public boolean canBeHit;
	public boolean doPermaPassthrough;
	boolean doDestroy;
	float sizeFactor = 1;
	float count_red;

	float ringRadius;

	float progress_ascend;
	boolean ascend;

	float distanceTraveled;
	float friction;
	float wiggleFactor = .25f;

	final static float COUNTMAX_HITCOOLDOWN = 30;
	final static int FREEZECOOLDOWNMAX = 30;
	final static float HEIGHT_PASSTHROUGH = 15;
	final static int MAXRINGRADIUS = 20;
	final static float RINGWIDTH = 3f;
	final static float DST_TRAIL = 15;
	final static float MAXVEL = 7;

	boolean doWiggle = true;

	ParticleEffect particleEffect;

	static Texture tex_ring = new Texture(new Pixmap(MAXRINGRADIUS * 2, MAXRINGRADIUS * 2, Pixmap.Format.RGBA8888));

	public Ball(float x, float y, float height, int size) {
		this.height = height;
		this.size = size;
		pos = new Vector2(x, y);
		createBody();
		construct();
	}

	public Ball(float height, int size) {
		this.height = height;
		this.size = size;
		pos = Game.getSafePosOnTable(radius + 1, 10);
		if (pos == null) {
			doDispose = true;
			pos = new Vector2(1000, -100);
		}
		createBody();
		construct();
	}

	public Ball(float height, float radius) {
		this.height = height;
		pos = Game.getSafePosOnTable(radius + 1, 10);
		if (pos == null) {
			doDispose = true;
			pos = new Vector2(1000, -100);
		}
		createBody(radius);
		construct();
	}

	public Ball(int size) {
		this.size = size;
		this.height = 0;
		pos = Game.getSafePosOnTable(Res.tex_ball[0][size].getRegionWidth() / 2f + 1, 10);
		if (pos == null) {
			doDispose = true;
			pos = new Vector2(1000, -100);
		}
		createBody();
		construct();
		ascend = true;
	}

	public Ball(int size, int triesMax) {
		this.size = size;
		this.height = 0;
		pos = Game.getSafePosOnTable(Res.tex_ball[0][size].getRegionWidth() / 2f + 1, triesMax);
		if (pos == null) {
			doDispose = true;
			pos = new Vector2(1000, -100);
		}
		createBody();
		construct();
		ascend = true;
	}

	public Ball(float height, int size, int triesMax) {
		this.size = size;
		this.height = height;
		pos = Game.getSafePosOnTable(Res.tex_ball[0][size].getRegionWidth() / 2f + 1, triesMax);
		if (pos == null) {
			doDispose = true;
			pos = new Vector2(1000, -100);
		}
		createBody();
		construct();
	}

	private void construct() {
		pos_last = new Vector2(pos);
		sprite = new Sprite(Res.tex_ball[0][0]);
		body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
		radius = body.getFixtureList().first().getShape().getRadius() * Main.PPM;
		radius_spawn = radius + 2;

		isPassthrough = true;
		if (height < 0) {
			body.setActive(false);
			isUnderGround = true;
		}
		if (height > HEIGHT_PASSTHROUGH) {
			setPassthrough(true);
		}

		if (Main.inFarm())
			construct_farm();

		friction = Main.worldProperties.friction;
	}

	public Ball setAscend() {
		ascend = true;
		return this;
	}

	void construct_farm() {

	}

	Vector2 pos_trail = new Vector2();

	public void update() {
		if (!isDisposed) {
			float speed = 0;
			if (body != null)
				speed = body.getLinearVelocity().len();
			if (!frozen) {
				// circle trail
				update_trail();

				//shrink ball to disappear
				if (isShrinking) {
					sprite.setSize(sprite.getWidth() - Main.dt_one, sprite.getHeight() - Main.dt_one);
					sprite.setPosition((int) ((int) pos.x - sprite.getWidth() / 2), (int) ((int) pos.y + height - sprite.getHeight() / 2));
					if (sprite.getWidth() <= 0)
						dispose();
					return;
				}

				if (isUnderGround && height >= 0) {
					isUnderGround = false;
					body.setActive(true);
				}

				if (!doPermaPassthrough)
					setPassthrough(height < 0 || height > HEIGHT_PASSTHROUGH || ascend);

				if (fall) fall();

				if (isUnderGround) height += Main.dt_one_slowed * .2f;

				if (setSpriteUnderGround()) {
					if (doDispose)
						dispose();
					return;
				}

				// set pos
				pos_last.set(pos);
				pos.set(body.getPosition());
				pos.scl(Main.PPM);

				if (size > 0 && doWiggle) // wiggle
					sprite.setSize(sizeFactor * (float) (sprite.getRegionWidth() * (1 + wiggle * wiggleFactor * -Math.sin(wiggle * 15))), sizeFactor * (float) (sprite.getRegionHeight() * (1 + wiggle * wiggleFactor * -Math.cos(wiggle * 15))));

				sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y + height - sprite.getHeight() / 2));
				//pos_mid.set(sprite.getX() + radius, sprite.getY() + radius);
				pos_mid.set(sprite.getX() + radius, (int) (pos.y - sprite.getHeight() / 2) + radius);

				if (height == 0)
					body.setLinearVelocity(body.getLinearVelocity().x * (float) Math.pow((1 - friction), Main.dt_one_slowed), body.getLinearVelocity().y * (float) Math.pow((1 - friction), Main.dt_one_slowed));

				wiggle = Math.max(0, wiggle - .05f * Main.dt_one_slowed);

				height += velY * Main.dt_one_slowed;
				height = Math.max(0, height);
				if (height == 0) {
					if (velY < 0) {
						velY = -velY * .5f;
						onBounce(velY);
						if (velY < 1f)
							velY = 0;
						wiggle(velY * .2f);
					}
				} else
					velY += Game.GRAVITY_PIXELS * .2f * Main.dt_one_slowed;

				count_hitCooldown = Math.max(0, count_hitCooldown - Main.dt_one);

				maxSpeed = Math.max(maxSpeedMinimum, maxSpeed * (float) Math.pow(.99f, Main.dt_one_slowed));

                /*

                // Push to mid so it balls don't get stuck in the corners

                distToMid = (float) Math.sqrt(Math.pow(pos.x - Main.width / 2, 2) + Math.pow((pos.y - Main.height / 2) * Main.width / Main.height, 2));
                if (distToMid > Main.width / 2 - 10) {
                    time_off += Main.dt_one_slowed;
                    if (time_off > 60) {
                        angleToMid = MathFuncs.angleBetweenPoints(pos, Main.pos_middle);
                        body.setLinearVelocity((float) Math.cos(angleToMid) * .025f + body.getLinearVelocity().x, (float) Math.sin(angleToMid) * .025f + body.getLinearVelocity().y);
                    }
                } else {
                    time_off = 0;
                }
                */

				if (speed < .1f)
					body.setLinearVelocity(0, 0);

				count_recentlyHit = Math.max(0, count_recentlyHit - Main.dt_one);
				count_cantGetStuck = Math.max(0, count_cantGetStuck - Main.dt_one_slowed);
				freezeCooldown = Math.max(0, freezeCooldown - Main.dt_one);
				count_red = Math.max(0, count_red - Main.dt * 5);

			} else { // if frozen
				count_frozen = Math.max(0, count_frozen - Main.dt_one);
				if (count_frozen == 0)
					unfreeze();
			}
			if (doFreeze) {
				doFreeze = false;
				freeze();
			}
			if (doActivateShield) {
				doActivateShield = false;
				activateShield();
			}

			if (!isLocked) {
				if (Main.ballSelector.isBallSelected(this))
					selectedFactor += Main.dt_one * .1f;
				else
					selectedFactor -= Main.dt_one * .1f;
				selectedFactor = MathUtils.clamp(selectedFactor, 0, 1);
			}

			canBeHit = Main.inFarm() || !isLocked && speed < 1 && !Main.ballSelector.isBallSelected(this)/* && !isPassthrough*/;

			if (canBeHit)
				count_circle = (count_circle + Main.dt_one_slowed * .05f) % ((float) Math.PI * 2);
			else
				count_circle = -(float) Math.PI * .5f;

			update_ring();

			if (Main.inFarm())
				update_farm();

			if (sizeFactor < 1)
				sizeFactor = Math.min(sizeFactor + Main.dt * 5, 1);

			ball_hit = null;

			if (particleEffect != null) {
				particleEffect.update(Main.dt);
				particleEffect.setPosition(pos.x, pos.y);
			}

			if (doDestroy) {
				destroy(body.getLinearVelocity().angleRad(), body.getLinearVelocity().len() * .4f);
				doDestroy = false;
			}

			//THIS ALWAYS LAST
			if (doDispose) {
				dispose();
			}
		}
	}

	void update_trail() {
		float dst = pos_last.dst(pos);
		float ang = MathFuncs.angleBetweenPoints(pos_last, pos);
		while (distanceTraveled + dst > DST_TRAIL) {
			pos_trail.set(DST_TRAIL - distanceTraveled, 0);
			pos_trail.rotateRad(ang);
			pos_trail.add(pos_last);
			createTrail(pos_trail.x, pos_trail.y);
			dst -= DST_TRAIL - distanceTraveled;
			distanceTraveled = 0;
		}
		distanceTraveled = distanceTraveled + dst;
	}

	void createTrail(float x, float y) {
		Main.trails.add(new Trail(x, y, getTrailRadius()));
	}

	void ascend() {
		height = progress_ascend * 35;
		progress_ascend = Math.min(1, progress_ascend + Main.dt * 2);
		sprite.setSize(sprite.getRegionWidth() * (.7f + .3f * progress_ascend), sprite.getRegionHeight() * (.7f + .3f * progress_ascend));
		if (progress_ascend == 1) {
			ascend = false;
			velY = 0;
		}
	}

	public void setParticleTwinkle() {
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("particles/particle.p"), Res.atlas);
		particleEffect.setEmittersCleanUpBlendFunction(false);
		particleEffect.setPosition(pos.x, pos.y);
		particleEffect.start();
	}

	public void endParticleTwinkle() {
		particleEffect.dispose();
		particleEffect = null;
	}

	public void shrink() {
		body = Main.destroyBody(body);
		isShrinking = true;
	}

	static final int DISPOSEBUFFER = 4;

	void update_farm() {

	}

	boolean doRing;

	void update_ring() {
		doRing = canBeHit && Main.inGame();
		if (doRing) {
			ringSizeFactor = Math.min(1, ringSizeFactor + Main.dt_one_slowed * .1f);
			ringRadius = ringSizeFactor * (radius + 3 + 2 * (float) Math.sin(count_circle));
			return;
		}
		ringSizeFactor = 0;
	}

	public Ball setPermaPassthrough(boolean b) {
		if (b)
			setPassthrough(true);
		doPermaPassthrough = b;
		return this;
	}

	public void onFailedSelect() {
		count_red = (float) Math.PI;
		Main.addSoundRequest(ID.Sound.ALARM);
		//Main.particles.add(new Particle_Cross(pos.x, pos.y));
	}

	boolean isShielded;

	public void activateShield() {
		if (!isShielded) {
			Res.fixtureDef_shield.shape.setRadius((radius + 2) * Main.MPP);
			body.createFixture(Res.fixtureDef_shield);
			isShielded = true;
		}
	}

	boolean setSpriteUnderGround() {
		if (height < 0 || fall && height == 0) {
			sprite.setSize((float) (sprite.getRegionWidth() / (1 + Math.abs(height))), (float) (sprite.getRegionHeight() / (1 + Math.abs(height))));
			sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
			return true;
		}

		sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
		return false;
	}

	void onBounce(float bounceImpact) {
		Main.addSoundRequest(ID.Sound.BOUNCE, 1, bounceImpact * .2f);
	}

	public boolean isStuck;

	public void getStuck() {
		if (count_cantGetStuck <= 0) {
			isStuck = true;
			//body.setActive(false);
			body.setType(BodyDef.BodyType.StaticBody);
		}
	}

	public void lock() {
		isLocked = true;
	}

	public void ignoreWalls() {
		Filter filter = new Filter();
		filter.maskBits = (Res.MASK_ZERO);
		filter.categoryBits = (Res.MASK_ZERO);
		body.getFixtureList().first().setFilterData(filter);
	}

	public Ball dontSplit() {
		dontSplit = true;
		return this;
	}

	public void setPassthrough(boolean b) {
		if (b) {
			isPassthrough = true;
			Filter filter = new Filter();
			filter.maskBits = (short) (Res.MASK_ZERO | Res.MASK_WALL | Res.MASK_SPIKEWALL);
			filter.categoryBits = (Res.MASK_PASSTHROUGH);
			body.getFixtureList().first().setFilterData(filter);
		} else {
			isPassthrough = false;
			Filter filter = new Filter();
			filter.maskBits = (short) (Res.MASK_ZERO | Res.MASK_WALL | Res.MASK_SPIKEWALL);
			filter.categoryBits = (Res.MASK_ZERO);
			body.getFixtureList().first().setFilterData(filter);
		}
	}

	public boolean isKinetic() {
		return !fall && height >= 0;
	}

	public void fall() {
		if (MathFuncs.distanceBetweenPoints(pos, hole_fall.pos) > hole_fall.radiusMax - radius) {
			pos.set(pos.lerp(hole_fall.pos, .1f));
		} else
			height -= Main.dt_one * .1f;

		if (sprite.getWidth() < 2 || hole_fall.isDisposed) {
			disappearInHole();
		}
	}

	public void disappearInHole() {
		doDispose = true;
	}

	public void fallInHole(Hole hole) {
		if (hole == hole_spawn)
			return;

		if (!fall) {
			body.setActive(false);
			hole_fall = hole;
			fall = true;
		}
	}

	public Ball setDrainSpeed() {
		doDrainSpeed = true;
		return this;
	}

	public void render(SpriteBatch batch) {
	}

	public void drawParticleEffect(SpriteBatch batch) {
		if (particleEffect != null)
			particleEffect.draw(batch);
	}

	void render_farm(SpriteBatch batch) {
	}

	public void drawSelectionRing(SpriteBatch batch) {
		if (doRing) {
			batch.setShader(Res.shader_circle);
			Res.shader_circle.setUniformf("r", ringRadius / 2f / MAXRINGRADIUS);
			Res.shader_circle.setUniformf("width", RINGWIDTH / 2f / MAXRINGRADIUS);
			float correction = 0;
			if (size == 2)
				correction = -.5f;
			batch.draw(tex_ring, (int) pos.x - MAXRINGRADIUS + correction, (int) pos.y - MAXRINGRADIUS - 3 * radius / 9);
			batch.setShader(null);
		}
	}

	public void render(ShapeRenderer sr) {

	}

	public void drawShapes(ShapeRenderer sr) {

	}

	/*
	public void renderShadow(ShapeRenderer sr) {
		if (!isUnderGround && !fall) {
			sr.setColor(selectedFactor, selectedFactor, selectedFactor, .8f + .2f * selectedFactor);
			float smallFactor = 1 / (1 + height * .02f);
			float shadowWidth = sizeFactor * sprite.getWidth() + selectedFactor * 4;
			float shadowHeight = (shadowWidth * Game.WIDTHTOHEIGHTRATIO)  + selectedFactor * 4;

			float shadowWidth_small = shadowWidth * smallFactor;
			float shadowHeight_small = shadowHeight * smallFactor;

			//sr.ellipse((int) ((int) pos.x - shadowWidth_small / 2), (int) ((int) pos.y - shadowHeight_small / 2 - radius - 1 + shadowHeight / 2), shadowWidth_small, shadowHeight_small);
			//if (radius % 1 == 0)
			//sr.ellipse((sprite.getX() + radius - shadowWidth_small / 2), (int) ((int) pos.y - Main.test_float * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small);


			//sr.ellipse((int) ((int) pos.x - sprite.getRegionWidth() / 2f) + sprite.getRegionWidth() / 2f - shadowWidth_small / 2, (int) ((int) ((int) pos.y - radius) + .6f * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small, 20);
			sr.ellipse((int) (pos.x - sprite.getRegionWidth() / 2f) + sprite.getRegionWidth() / 2f - shadowWidth_small / 2, (float) (Math.floor(pos.y) + Math.floor(-radius + .6f * radius - shadowHeight_small / 2)), shadowWidth_small, shadowHeight_small, 20);


			//else
			//    sr.ellipse((int)(pos.x - (shadowWidth_small / 2)), (int) ((int) pos.y - Main.test_float * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small);
			//sr.ellipse((int) (pos.x - sprite.getRegionWidth() / 2 * smallFactor), (int) (pos.y - sprite.getRegionWidth() / 2 * Game.WIDTHTOHEIGHTRATIO * smallFactor) - 2, sprite.getRegionWidth() / 2 * smallFactor * 2, radius * smallFactor * 2 * Game.WIDTHTOHEIGHTRATIO);
		}
	}
*/

	public void renderShadow(ShapeRenderer sr, float radius) {
		if (!isUnderGround && !fall) {
			sr.setColor(selectedFactor, selectedFactor, selectedFactor, .8f + .2f * selectedFactor);
			float smallFactor = 1 / (1 + height * .02f);
			float shadowRadius = smallFactor * (radius + selectedFactor * 2);
			//if ((radius) % 1 == 0)
			//	sr.circle((int) pos.x, (int) pos.y - 1 * (1 - selectedFactor), shadowRadius, 20);
			//else
			//	sr.circle(Math.round(pos.x) - .5f, Math.round(pos.y) - 1 - (1 - selectedFactor), shadowRadius, 50);
			sr.circle(pos_mid.x, pos_mid.y - (1 - selectedFactor), shadowRadius, 40);
			sr.circle(pos_mid.x, pos_mid.y - (1 - selectedFactor), shadowRadius, 40);
		}
	}

	public void renderShadow(SpriteBatch batch, float radius) {
		if (!isUnderGround && !fall) {
			batch.setColor(selectedFactor, selectedFactor, selectedFactor, .8f + .2f * selectedFactor);
			float smallFactor = 1 / (1 + height * .02f);
			float shadowRadius = smallFactor * (radius + selectedFactor * 2);
			batch.draw(Res.getShadow(shadowRadius * 2), pos_mid.x - shadowRadius, pos_mid.y - (1 - selectedFactor) - shadowRadius, shadowRadius * 2, shadowRadius * 2);
			batch.setColor(1,1,1,1);
		}
	}

	public void renderShadow(ShapeRenderer sr) {
		renderShadow(sr, sprite.getRegionWidth() / 2);
	}

	public void render_shield_shine(SpriteBatch batch) {
		if (isShielded)
			batch.draw(Res.tex_shield_shine, (int) (pos.x - 2 - sprite.getRegionWidth() / 2f), (int) (pos.y - 2 - sprite.getHeight() / 2 + height), sprite.getRegionWidth() + 4, sprite.getHeight() + 4);
	}

	public void renderShadow(SpriteBatch batch) {
		renderShadow(batch, sprite.getRegionWidth() / 2f);
	}


	public void onSelect() {
		Main.addSoundRequest(ID.Sound.BUTTONCLICK_1, 1, .25f * Main.test_float, (float) (.7 + Math.random() * .2));
	}

	public boolean testHit() {
		if (isKinetic())
			if (Main.tap_speed > Game.HITSPEEDTHRESHOLD && Gdx.input.isTouched() && MathFuncs.distanceBetweenPoints(Main.tap[0], pos) <= Math.max(20, radius) && count_hitCooldown == 0) {
				hit(Main.tap_angle, Main.tap_speed * 3);
				return true;
			}
		return false;
	}

	public Ball setVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
		return this;
	}

	public Ball setVelocity(Vector2 v) {
		body.setLinearVelocity(v.x, v.y);
		return this;
	}

	public void addVelocity(float vx, float vy) {
		body.setLinearVelocity(body.getLinearVelocity().x + vx, body.getLinearVelocity().y + vy);
	}


	public void hit(float angle, float speed) {
		if (Main.game.inFlow())
			speed *= 1.5f;

		if (isStuck) {
			body.setType(BodyDef.BodyType.DynamicBody);
			isStuck = false;
			count_cantGetStuck = 10;
		}

		body.setLinearVelocity((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
		maxSpeed = speed;
		count_hitCooldown = COUNTMAX_HITCOOLDOWN;

		if (Main.game.inFlow()) {
			Main.particles.add(new Particle_MegaHit(pos.x, pos.y, angle + (float) Math.PI, radius));
			Main.addSoundRequest(ID.Sound.PUNCH);
			//punchBack(angle);
		} else
			Main.particles.add(new Particle_Hit(pos.x, pos.y, angle + (float) Math.PI, radius));

		//Main.addSoundRequest(ID.Sound.HIT, 0, .05f * speed, 0.82f + (float) Math.random() * 0.2f - 0.1f);
		//Main.addSoundRequest(ID.Sound.SHOT, 0, 2f * speed, 0.82f + (float) Math.random() * 0.2f - 0.1f);
		Main.addSoundRequest(ID.Sound.SHOT, 0, .03f * speed, .9f + (float) Math.random() * .2f);

		time_off = 0;
	}

	void punchBack(float ang) {
		Array<Body> bodies = new Array<Body>();
		Main.world.getBodies(bodies);
		float force = 5;
		ang += Math.PI;
		float r = 20;
		Vector2 v_c = new Vector2(pos.x + r * (float) Math.cos(ang), pos.y + r * (float) Math.sin(ang));
		for (Body body : bodies) {
			if (body != this.body)
				if (body.getPosition().scl(Main.PPM).dst(v_c) < r) {
					//body.applyForce(force * (float) Math.cos(ang), force * (float) Math.sin(ang), body.getPosition().x, body.getPosition().y, true);
					body.setLinearVelocity(body.getLinearVelocity().x + force * (float) Math.cos(ang), body.getLinearVelocity().y + force * (float) Math.sin(ang));
				}
		}
	}

	public void contact(Object ud, Vector2 p, float impact) {
		wiggle(impact * .15f);

		if (body != null && body.getLinearVelocity().len() > 5 && freezeCooldown == 0) {
			doFreeze = true;
			freezeCooldown = FREEZECOOLDOWNMAX;
		}
	}

	public float getTrailRadius() {
		switch (size) {
			case 0:
				return 4;
			case 1:
				return 6;
			case 2:
				return 7;
			case 3:
				return 7;
		}
		return radius - 2;
	}

	public void onCollision(Vector2 p, float impact, Object object_hit) {
		if (object_hit instanceof CircularBumper) {
			float vel = body.getLinearVelocity().len();
			if (vel > MAXVEL)
				body.setLinearVelocity(body.getLinearVelocity().x / vel * MAXVEL, body.getLinearVelocity().y / vel * MAXVEL);
		}
	}

	public void doCollisionEffect(Vector2 p, float impact, Object object_hit) { // when its two balls will only execute on one of both
		dropPulseParticle(p.x, p.y + height, 1.5f * impact);
		if (object_hit instanceof Ball)
			Main.addSoundRequest(ID.Sound.HIT_SOFT, 5, impact * .1f, (float) Math.random() * .2f + .9f);
		else
			Main.addSoundRequest(ID.Sound.HIT, 5, impact * .15f, (float) Math.random() * .2f + .9f);
	}

	public void contactBall(Ball ball) {
		ball_hit = ball;
		//Main.addSoundRequest(ID.Sound.HIT);
	}

	public void contactBall_pre(Ball ball) {
	}

	static void dropPulseParticle(float x, float y, float size) {
		Main.pulseEffects.add(new PulseEffect(x, y, size * 2));
	}

	public void wiggle() {
		if (Game.doWiggle)
			wiggle = Math.max(1, wiggle);
	}

	public void wiggle(float intensity) {
		if (Game.doWiggle) {
			intensity = Math.min(intensity, 1.35f);
			wiggle = Math.max(intensity, wiggle);
		}
	}

	public void createBody() {
		body = Main.world.createBody(Res.bodyDef_dynamic);
		body.createFixture(Res.fixtureDef_ball_passThrough[size]);
		body.setUserData(this);
		body.setFixedRotation(true);
	}

	public void createBody(float radius) {
		body = Main.world.createBody(Res.bodyDef_dynamic);
		Res.fixtureDef_circle.shape.setRadius(radius * Main.MPP);
		body.createFixture(Res.fixtureDef_circle);
		body.setUserData(this);
		body.setFixedRotation(true);
	}

	public void destroy_delayed() {
		doDestroy = true;
	}

	public void destroy(float angle, float impact, Vector2 pos_danger) {

	}

	public void destroy(float angle, float impact) {
		destroy(angle, impact, pos);
	}

	public void destroy() {
		destroy(body.getLinearVelocity().angleRad(), Math.max(1, 0.4f * body.getLinearVelocity().len()));
	}


	public void destroyShield() {
		if (isShielded && !isDisposed) {
			isShielded = false;
			body.destroyFixture(body.getFixtureList().get(1));
		}
	}

	public void explode(float angle, float impact) {
		Main.addSoundRequest(ID.Sound.BALLBREAK);
		doDispose = true;
	}

	int getParticleAmount() {
		if (size == 0)
			return 3;
		else if (size == 1)
			return 6;
		else
			return 10;
	}

	public void applyForce(float angle, float speed) {
		body.applyForceToCenter((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed, true);
		maxSpeed = speed;
		dropPulseParticle(pos.x, pos.y + height, 7.5f);
		Main.addSoundRequest(ID.Sound.HIT, 5, 1, (float) Math.random() * .2f + .9f);
		count_recentlyHit = countMax_recentlyHit;
	}


	public void dispose() {
		Main.ballsToRemove.add(this);
		body = Main.destroyBody(body);
		isDisposed = true;
	}

	public void drawTrail(ShapeRenderer sr) {
		//if (height == 0) {
		float distance = MathFuncs.distanceBetweenPoints(pos, pos_last);
		float angle = MathFuncs.angleBetweenPoints(pos, pos_last);


		for (int i = 0; i < distance; i++) {
			//sr.circle(pos.x + (float) Math.cos(angle) * i * 1 + radius / 2 * (float) Math.cos(angle_random), pos.y + (float) Math.sin(angle) * i * 1 + radius / 2 * (float) Math.sin(angle_random), radius - (float) Math.random() * 3);
			//sr.circle(pos.x + (float) Math.cos(angle) * i * 1, pos.y + (float) Math.sin(angle) * i * 1 - 2, radius - (float) Math.random() * 3);
			sr.circle(pos.x + (float) Math.cos(angle) * i * 1, pos.y + (float) Math.sin(angle) * i * 1 - 2 + height, radius);
		}
		//}
		//float angle=(float)(Math.random()*Math.PI*2);
		//sr.circle(pos.x + radius/2*(float)Math.cos(angle), pos.y + radius/2*(float)Math.sin(angle), 5);
	}

	public void setSpriteTexture(TextureRegion texture) {
		sprite.setRegion(texture);
		sprite.setSize(texture.getRegionWidth(), texture.getRegionHeight());
		sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y + height - sprite.getHeight() / 2);
	}

	public void freeze() {
		frozen = true;
		count_frozen = 1;
		body.setActive(false);
	}

	public void unfreeze() {
		frozen = false;
		body.setActive(true);
	}

	public void doActivateShield() {
		doActivateShield = true;
	}

	static final float PARTICLESPREAD = (float) Math.PI * .75f;

	public static void throwParticles(float impact, Vector2 pos, Color[] palette, int amount) {
		for (int i = 0; i < amount; i++)
			Main.particlesToAdd.add(new Particle_Ball(pos.x, pos.y, (float) (Math.random() * Math.PI * 2), impact * .5f * (1 + (float) Math.random()), palette));
		//Main.particlesToAdd.add(new Particle_Ball(pos.x, pos.y, angle + (float) Math.random() * PARTICLESPREAD - PARTICLESPREAD / 2, Main.test_float * impact * (.5f + (float) Math.random()), palette));
	}

	public static void throwParticles(float impact, Vector2 pos, Color[] palette) {
		throwParticles(impact, pos, palette, 5);
	}
}
