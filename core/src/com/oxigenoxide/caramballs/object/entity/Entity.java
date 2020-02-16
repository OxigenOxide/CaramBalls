package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Entity {

    public float radius_spawn = 0; // the radius no other spawn radius should touch
    public Vector2 pos;

    // When creating a new entity:
    //   - add its ID to the class ID
    //   - add its list to Main.clearEntities()
    //   - add its list to updates
    //   - add its list to renders


    // Notes:
    //   - never re-declare the pos variable

    public Entity(){

    }
    public void update(){

    }
    public void render(SpriteBatch batch){

    }
    public void render(ShapeRenderer sr){

    }
    public void render(SpriteBatch batch, ShapeRenderer sr){
        render(sr);
        render(batch);
    }
    public void dispose(){

    }
}
