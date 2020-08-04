package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.tap;

public class BallSelector {

	Vector2 v0;
	Vector2 v1;
	Vector2 v2;
	Vector2 v_pointer;
	Vector2 v_lineEnd;
	Vector2 v_lineBegin;
	float radiusMax = 10;
	float radius;
	Ball ball_selected;
	boolean active;
	float cang;
	float distanceFromCenter;
	float stretched;
	float count_enoughStretched;
	float minDistanceFromCenter;
	int hitPower;
	float minStretchFactor;

	Sprite sprite_line;
	TextureRegion textureRegion_line;

	Sprite sprite_directionLine;


	boolean doTriangle;
	boolean enabled;

	final float DISTANCEMAX = 25;
	final float RELEASEBUFFER = 20;
	final float MAXSPEED = 8;
	final float MINSPEED = 2;


	public BallSelector() {
		v0 = new Vector2();
		v1 = new Vector2();
		v2 = new Vector2();
		v_pointer = new Vector2();
		v_lineBegin = new Vector2();
		v_lineEnd = new Vector2();
	}

	public void onResourcesLoaded() {
		textureRegion_line = new TextureRegion(Res.tex_ballSelector_line);
		sprite_line = new Sprite(textureRegion_line);
	}

	public void update() {
		active = ball_selected != null && Gdx.input.isTouched(0) && !ball_selected.isDisposed && !Game.doGameOverCue;
		if (!active) {
			Game.slowdown_effect = Math.max(Game.slowdown_effect - Main.dt_one * .05f, 0);
		}
		if (active) {
			if (Main.isInScene(Game.class)) {
				Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .9f);
				Main.slowdown = Game.slowdown_effect;
			}

			distanceFromCenter = ball_selected.pos.dst(tap[0]);
			doTriangle = distanceFromCenter >= minDistanceFromCenter;
			stretched = (MathUtils.clamp(distanceFromCenter, minDistanceFromCenter, DISTANCEMAX) - minDistanceFromCenter) / (DISTANCEMAX - minDistanceFromCenter);

			int newHitPower = (int) (stretched * 3);
			if (newHitPower != hitPower) {
				hitPower = newHitPower;
				Main.addSoundRequest(ID.Sound.TAP, 1, 1, .8f + 1.1f * hitPower);
			}
			textureRegion_line.setRegionWidth(7 * hitPower);

			sprite_line.setRegion(textureRegion_line);
			sprite_line.setSize(textureRegion_line.getRegionWidth(), textureRegion_line.getRegionHeight());
			sprite_line.setPosition(ball_selected.pos.x + minDistanceFromCenter, ball_selected.pos.y - 1.5f);

			if (enoughStretched())
				count_enoughStretched = Math.min(count_enoughStretched + Main.dt * 5, 1);
			else
				count_enoughStretched = Math.max(count_enoughStretched - Main.dt * 5, 0);

			//radius = count_enoughStretched * radiusMax / (1 + 1.5f * Math.min(DISTANCEMAX, MathFuncs.distanceBetweenPoints(ball_selected.pos, tap[0])) / DISTANCEMAX);
			radius = count_enoughStretched * (2 + stretched * 2);

			cang = MathFuncs.angleBetweenPoints(ball_selected.pos, tap[0]);

			v0.set(minDistanceFromCenter + 1, 0);
			v0.rotateRad(cang);
			v0.add(ball_selected.pos);

			if (distanceFromCenter < DISTANCEMAX) {
				v_pointer.set(tap[0]);
			} else {
				v_pointer.set(DISTANCEMAX, 0);
				v_pointer.rotateRad(cang);
				v_pointer.add(ball_selected.pos);
			}

			float a = MathFuncs.distanceBetweenPoints(v0, v_pointer);
			float ang = MathFuncs.angleBetweenPoints(v0, v_pointer);
			float c = (float) Math.sqrt(-Math.pow(radius, 2) + Math.pow(a, 2));
			float bang = (float) Math.acos(c / a);
			float ang_dir = cang + (float) Math.PI;

			v1.set(c, 0);
			v1.rotateRad(ang + bang);
			v1.add(v0);
			v2.set(c, 0);
			v2.rotateRad(ang - bang);
			v2.add(v0);

			v_lineEnd.set(minDistanceFromCenter + 20 * stretched, 0);
			v_lineEnd.rotateRad(ang_dir);
			v_lineEnd.add(ball_selected.pos);

			v_lineBegin.set(minDistanceFromCenter - 1, 0);
			v_lineBegin.rotateRad(ang_dir);
			v_lineBegin.add(ball_selected.pos);

			sprite_line.setRotation((float) Math.toDegrees(ang_dir));

			// auto-release
			//if (distanceFromCenter > DISTANCEMAX + RELEASEBUFFER)
			//	onRelease();
		}
	}

	public void onRelease() { // apparently onRelease sometimes is called after Gdx.input.isTouched is changed on android. This is always the contrary on desktop.
		if (enoughStretched() && ball_selected != null && !ball_selected.isDisposed)
			//ball_selected.hit(cang + (float) Math.PI, MINSPEED + (MAXSPEED - MINSPEED) * stretched); // stretch mapped to [MINSPEED, MAXSPEED]
			ball_selected.hit(cang + (float) Math.PI, getHitSpeed());
		ball_selected = null;
		Main.game.onBallRelease();
	}

	float getHitSpeed() {
		switch (hitPower) {
			case 0:
				return 0;
			case 1:
				return MINSPEED;
			case 2:
				return (MINSPEED + MAXSPEED) / 2;
			case 3:
				return MAXSPEED;
		}
		return 0;
	}

	boolean enoughStretched() {
		return distanceFromCenter > minDistanceFromCenter;
	}

	public void setSelected(Ball ball) {
		if(enabled) {
			ball_selected = ball;
			ball_selected.onSelect();
			count_enoughStretched = 0;
			minDistanceFromCenter = ball_selected.radius + 2;
			sprite_line.setOrigin(-minDistanceFromCenter - 1, 1.5f);
			hitPower = 0;
		}
	}

	public void render(ShapeRenderer sr) {
		//if (enoughStretched())
		if (active && radius > 0) {
			sr.setColor(1, 1, 1, 1);
			sr.circle(v_pointer.x, v_pointer.y, radius);
			if (doTriangle)
				sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
			//sr.rectLine(v_lineBegin.x, v_lineBegin.y, v_lineEnd.x, v_lineEnd.y, 3);

			//sr.circle(v_lineEnd.x, v_lineEnd.y, 1.5f);
		}
		//sr.triangle(0,0,0,20,20,0);
	}

	public void render(SpriteBatch batch) {
		//if (enoughStretched())
		if (active && radius > 0) {
			sprite_line.draw(batch);
		}
		//sr.triangle(0,0,0,20,20,0);
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public Ball getSelectedBall() {
		return ball_selected;
	}

	public boolean isBallSelected(Ball ball) {
		return ball_selected == ball;
	}
}
