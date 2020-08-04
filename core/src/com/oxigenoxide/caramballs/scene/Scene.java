package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.caramballs.object.entity.Entity;

import java.util.ArrayList;

public class Scene {
    public int timesShown;
    boolean isShown;

    public void update() {

    }

    public void render(SpriteBatch batch, ShapeRenderer sr) {

    }

    public void renderOnTop(SpriteBatch batch, ShapeRenderer sr) {

    }

    public void dispose() {

    }

    public void show() {
        timesShown++;
        isShown = true;
    }

    public void onKeyDown(int keycode) {

    }

    public void saveData() {

    }


    public void addEntities(ArrayList<Entity> entities) { // function for adding scene specific entities
    }

    public void hide() {
        isShown = false;
    }

    public boolean isShown() {
        return isShown;
    }
}
