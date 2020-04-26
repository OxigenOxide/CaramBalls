package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;

public class Trail {
    Vector2 pos;
    float radius;
    float maxRadius;
    float maxCount = .5f;
    float count = maxCount;

    public Trail(float x, float y, float radius) {
        this.radius = radius;
        maxRadius = radius;
        pos = new Vector2(x, y);
    }

    public void update() {
        count -= Main.dt;
        radius = maxRadius * count / maxCount;
        if (radius < .7f)
            dispose();
    }

    public void render(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, radius, Math.max(3, (int) (radius * 3.5f)));
    }

    void dispose() {
        Main.trailsToRemove.add(this);
    }
}
