package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Tracer {
    float radius;
    Tracer previous;
    Vector2 pos;

    public Tracer(float x, float y, float radius) {
        pos=new Vector2(x,y);
        this.radius = radius;
        if (Main.tracers.size() - 1 >= 0) {
            previous = Main.tracers.get(Main.tracers.size() - 1);
        }
    }

    public void update() {
        radius = Math.max(0, radius - Main.dt_one * .2f);
        if (radius == 0)
            dispose();
    }

    public void render(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, radius);
        if (previous != null) {
            float angle = MathFuncs.angleBetweenPoints(pos, previous.pos);
            sr.triangle(pos.x + (float) Math.cos(angle - Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle - Math.PI * .5f) * radius, pos.x + (float) Math.cos(angle + Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle + Math.PI * .5f) * radius, previous.pos.x + (float) Math.cos(angle + Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle + Math.PI * .5f) * previous.radius);
            sr.triangle(pos.x + (float) Math.cos(angle - Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle - Math.PI * .5f) * radius, previous.pos.x + (float) Math.cos(angle - Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle - Math.PI * .5f) * previous.radius, previous.pos.x + (float) Math.cos(angle + Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle + Math.PI * .5f) * previous.radius);
        }
    }
    public void dispose(){
        Main.tracersToRemove.add(this);
    }
}