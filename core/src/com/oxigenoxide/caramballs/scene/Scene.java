package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Scene {
    public int timesShown;
    boolean isShown;

    public void update() {

    }

    public void render(SpriteBatch batch, ShapeRenderer sr) {

    }

    public void dispose() {

    }

    public void show() {
        timesShown++;
        isShown = true;
    }

    public void hide() {
        isShown = false;
    }

    public boolean isShown() {
        return isShown;
    }
}
