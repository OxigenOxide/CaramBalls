package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.math.Vector2;

public class Vector2Mod {

    private Vector2 v;
    private Vector2 v_res;
    private Vector2 trans;
    private Vector2 mul;

    public float x; // not mod compatible
    public float y; // not mod compatible

    public Vector2Mod(Vector2 v) {
        this.v = v; // will get updated
        v_res = new Vector2(v);
        x = v.x;
        y = v.y;
        trans = new Vector2();
        mul = new Vector2(1, 1);
    }

    public Vector2Mod(float x, float y) {
        this.v = new Vector2(x, y); // wont get updated
        v_res = new Vector2(v);
        this.x = x;
        this.y = y;
        trans = new Vector2();
        mul = new Vector2(1, 1);
    }

    public Vector2Mod trans(float dx, float dy) {
        trans.set(dx, dy); // won't get updated
        return this;
    }

    public Vector2Mod trans(Vector2 trans) {
        this.trans = trans; // will get updated
        return this;
    }

    public Vector2Mod mul(float x, float y) {
        this.mul.set(x, y); // won't get updated
        return this;
    }

    public Vector2Mod mul(Vector2 mul) {
        this.mul = mul; // will get updated
        return this;
    }

    public float getX() {
        return v.x * mul.x + trans.x;
    }

    public float getY() {
        return v.y * mul.y + trans.y;
    }

    public Vector2 get() {
        v_res.set(v.x * mul.x + trans.x, v.y * mul.y + trans.y);
        return v_res;
    }
}
