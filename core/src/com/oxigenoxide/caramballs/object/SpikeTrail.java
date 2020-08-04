package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.Spike;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;

import static com.oxigenoxide.caramballs.Main.spikeTrails;
import static com.oxigenoxide.caramballs.Main.spikeTrailsToRemove;

public class SpikeTrail {
	Vector2 pos;
	Counter counter;

	public SpikeTrail(float x, float y) {
		pos = new Vector2(x, y);
		counter = new Counter(new ActionListener() {
			@Override
			public void action() {
				dispose();
			}
		}, 3);
	}

	public void update() {
		counter.update();
		if (Game.isPosFree(pos, Spike.RADIUS_SPAWN)){
			dispose();
			Main.spikes.add(new Spike(pos.x,pos.y,false).setLifeTime(15));
		}
	}
	void dispose(){
		spikeTrailsToRemove.add(this);
	}
}
