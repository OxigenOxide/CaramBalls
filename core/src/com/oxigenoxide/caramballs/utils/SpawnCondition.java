package com.oxigenoxide.caramballs.utils;

import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.Entity;

import java.util.ArrayList;

public class SpawnCondition {

	public ArrayList<Entity> entities;
	public float distance;

	public SpawnCondition(ArrayList<Entity> entities, float distance) {
		this.entities = entities;
		this.distance = distance;
	}

	public SpawnCondition(int entityID, float distance) {
		this.entities = Main.getEntityArrayList(entityID);
		this.distance = distance;
	}
}
