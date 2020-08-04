package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oxigenoxide.caramballs.Main;

public class Animation {
	public float count;
	public float countMax;
	int indices;
	public boolean isContinuous;
	boolean doLoop;
	TextureRegion[] textures;
	float[] durations;
	public boolean ended;
	public int loops;

	public Animation(float countMax, TextureRegion[] textures, float[] durations, boolean isContinuous) {
		this.countMax = countMax;
		this.textures = textures;
		this.isContinuous = isContinuous;
		indices = textures.length;
		float totDur = 0;
		for (float f : durations)
			totDur += f;
		this.durations = new float[durations.length];
		for (int i = 0; i < durations.length; i++)
			this.durations[i] = durations[i] / totDur;
	}

	public Animation(float countMax, TextureRegion[] textures, boolean isContinuous) {
		this.countMax = countMax;
		this.textures = textures;
		this.isContinuous = isContinuous;
		indices = textures.length;
	}

	public void update(float dt) {
		if (isContinuous || doLoop) {
			count += dt;
			ended = false;
			if (count >= countMax) {
				count = 0;
				loops++;
				if (doLoop) {
					doLoop = false;
					ended = true;
				}
			}
		} else
			ended = true;
	}

	public void update() {
		update(Main.dt_one);
	}

	public void loop() {
		doLoop = true;
	}

	float duration;
	float countPart;

	public TextureRegion getTexture() {
		if (!ended) {
			if (durations != null) {
				duration = 0;
				countPart = count / countMax;
				for (int i = 0; i < durations.length; i++) {
					if (countPart >= duration && countPart <= duration + durations[i])
						return textures[i];
					duration += durations[i];
				}
			} else {
				System.out.println("count factor: " + textures.length);
				return textures[(int) (textures.length * count / countMax)];
			}
		}
		return textures[0];
	}
}
