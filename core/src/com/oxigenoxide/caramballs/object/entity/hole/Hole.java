package com.oxigenoxide.caramballs.object.entity.hole;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;


public class Hole extends Entity {
    public float radius;
    public float radiusMax;
    float count;
    boolean hasSpewed;
    public boolean isDisposed;

    Hole() {
        pos = new Vector2();
    }

    Hole(float x, float y) {
        pos = new Vector2((int) x + .5f, (int) y + .5f);
    }

    public void update() {
        count += Main.dt_one_slowed;
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(0, 0, 0, 1);
        sr.circle(pos.x, pos.y, radius, 15);
    }

    public void dispose() {
        Main.holesToRemove.add(this);
        isDisposed = true;
    }

    public void setPosition() {
        Vector2 pos_rnd = Game.getFreePosOnTable(radius_spawn, 10);
        if (pos_rnd == null) {
            dispose();
            pos.set(-100, -100);
        } else {
            pos.set(pos_rnd);
        }
    }
}
