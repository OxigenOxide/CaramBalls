package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Funcs {
    public static void drawTriangle(ShapeRenderer sr, Vector2 v0, Vector2 v1, Vector2 v2) {
        sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
    }

    public static boolean justTouched() {
        return Gdx.input.justTouched() && !Gdx.input.isTouched(1);
    }

    public static Class getClass(Object object) {
        if (object == null)
            return null;
        return object.getClass();
    }

    //static final Color COLOR_SHINE = new Color(1, 1, 200 / 255f, 210 / 255f);
    static final Color COLOR_SHINE = new Color(1, 1, .8f,.75f);

    public static void drawShine(ShapeRenderer sr, Vector2 pos, float radius, float progress) {
        float angle = progress * 360;
        float offset = 2.5f * (float) Math.cos(progress * 2 * Math.PI);
        sr.setColor(COLOR_SHINE);
        for (int i = 0; i < 6; i++)
            sr.arc(pos.x, pos.y, radius + offset, angle + 60 * i, 30, 5);
    }
}
