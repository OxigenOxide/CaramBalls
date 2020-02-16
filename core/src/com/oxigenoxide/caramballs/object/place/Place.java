package com.oxigenoxide.caramballs.object.place;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;

public class Place {

    public float friction;
    public boolean hasFloor;

    public float colorRange_begin;
    public static float colorRange_end;

    public Place(){

    }
    public void update(){

    }
    public void onEnter(){
        Main.worldProperties.setFriction(friction);
        Main.worldProperties.setHasFloor(hasFloor);
    }
    public Color[] getTableColor(){
        return Game.getRandomTableColor();
    }

    public void drawOnBackground(SpriteBatch batch){

    }
    public void initiateLevel_easy(){

    }
    public void initiateLevel_medium(){

    }
    public void initiateLevel_hard(){

    }
}
