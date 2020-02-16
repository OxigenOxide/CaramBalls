package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Funcs {
    public static void drawTriangle(ShapeRenderer sr, Vector2 v0, Vector2 v1, Vector2 v2){
        sr.triangle(v0.x,v0.y,v1.x,v1.y,v2.x,v2.y);
    }

    public static boolean justTouched(){
        return Gdx.input.justTouched() && !Gdx.input.isTouched(1);
    }
}
