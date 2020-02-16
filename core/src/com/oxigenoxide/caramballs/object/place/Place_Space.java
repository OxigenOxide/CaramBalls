package com.oxigenoxide.caramballs.object.place;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Place_Space extends Place {

    Vector2 pos_stars = new Vector2();
    Vector2 pos_stars_back = new Vector2();
    Color[] palette_background = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK};

    public Place_Space() {
        friction = 0;
        hasFloor = false;
    }

    @Override
    public void update() {
        pos_stars.set((pos_stars.x - Main.dt * 11) % -122, (pos_stars.y - Main.dt * 0) % 249);
        pos_stars_back.set((pos_stars_back.x - Main.dt * 3) % -122, (pos_stars_back.y - Main.dt * 0) % 249);
    }

    @Override
    public void drawOnBackground(SpriteBatch batch) {
        batch.draw(Res.tex_stars, (int) pos_stars.x, (int) pos_stars.y);
        batch.draw(Res.tex_stars_back, (int) pos_stars_back.x, (int) pos_stars_back.y);
    }

    @Override
    public Color[] getTableColor() {
        return palette_background;
    }

    @Override
    public void initiateLevel_easy() {
    }

    @Override
    public void initiateLevel_medium() {
    }

    @Override
    public void initiateLevel_hard() {
    }
}
