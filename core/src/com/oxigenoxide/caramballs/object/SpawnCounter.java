package com.oxigenoxide.caramballs.object;

import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.SpawnListener;

import java.util.ArrayList;

public class SpawnCounter {
	SpawnListener spawnListener;
	float countMax;
	float count;
	float factor_randomCount;
	boolean enabled;
	ArrayList list;
	int max = 100; // standard max;
	float lifeTime = 4;

	public SpawnCounter(SpawnListener spawnListener) {
		this.spawnListener = spawnListener;
	}

	public void update(float dt) {
		if (!enabled || (list != null && list.size() >= max))
			return;

		count += dt;
		if (count >= countMax) {
			resetCount();
			spawnListener.action(lifeTime);
		}
	}

	void resetCount() {
		count = factor_randomCount * countMax * (float) Math.random();
	}

	public SpawnCounter enable() {
		enabled = true;
		resetCount();
		return this;
	}

	public void disable() {
		enabled = false;
		count = 0;
		max = 100;
		factor_randomCount = 0;
	}

	public SpawnCounter setCountMax(float countMax) {
		this.countMax = countMax;
		return this;
	}

	public SpawnCounter setRandomCount(float factor) {
		factor_randomCount = factor;
		return this;
	}

	public SpawnCounter setLifeTime(float lifeTime) {
		this.lifeTime = lifeTime;
		return this;
	}

	public SpawnCounter setMax(int max) {
		this.max = max;
		return this;
	}

	public SpawnCounter addList(ArrayList list) {
		this.list = list;
		return this;
	}
}
