package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

public class Projection {
    Vector2[] v;
    float count;
    Vector2 pos;
    float radius;
    float radiusMax;
    int tier;
    Color color;
    float count_shrink;
    float countMax_shrink = 120;
    float growProgress;
    boolean isReleased;
    boolean doGrow;
    int type;
    float alpha_factor=1;

    public Projection(float x, float y, float radius) {
        radiusMax = radius;
        pos = new Vector2(x, y);
        v = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2()};
        color = new Color(0, 0, 0, 1);
    }

    public Projection(float x, float y, float radius, int type, int tier) {
        this.tier = tier;
        this.type = type;
        this.color = Res.COLOR_PROJECTION[type];
        radiusMax = radius;
        pos = new Vector2(x, y);
        v = new Vector2[]{new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2()};
    }


    public void setPosition(float x, float y) {
        pos.set(x, y);
    }

    public void onRelease() {
        isReleased = true;
    }

    public void onReleaseGrow() {
        isReleased = true;
        doGrow = true;
    }

    public void update() {
        count = (count + Main.dt_one * .01f) % ((float) Math.PI * 2);

        arrangeVertices();

        if (isReleased) {
            if(doGrow){
                growProgress=Math.min(1,growProgress+.02f*Main.dt_one);
                radius=radiusMax*(1+growProgress*2);
                alpha_factor=1-growProgress;
                if(growProgress>=1)
                    dispose();
            }
            else {
                count_shrink += Main.dt_one_slowed;
                radius = radius * (float) Math.pow(.95, Main.dt_one);
                if (count_shrink > countMax_shrink)
                    dispose();
            }
        }
        else{
            radius += (radiusMax - radius) * (float)Math.pow(.05, Main.dt_one);
        }
    }

    void arrangeVertices(){
        float ang = count;
        switch(tier){
            case 0:
                for (int i = 0; i < 3; i++) {
                    v[i].set(pos.x + (radius) * (float) Math.cos(ang), pos.y + (radius) * (float) Math.sin(ang));
                    ang += Math.PI * 2 / 3f;
                }
                break;
            case 1:
                for (int i = 0; i < 3; i++) {
                    v[i].set(pos.x + (radius) * (float) Math.cos(ang), pos.y + (radius) * (float) Math.sin(ang));
                    ang += Math.PI * 2 / 3f;
                }
                ang += Math.PI * 1 / 3f;
                for (int i = 3; i < 6; i++) {
                    v[i].set(pos.x + (radius) * (float) Math.cos(ang), pos.y + (radius) * (float) Math.sin(ang));
                    ang += Math.PI * 2 / 3f;
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    v[i].set(pos.x + (radius) * (float) Math.cos(ang), pos.y + (radius) * (float) Math.sin(ang));
                    ang += Math.PI * 1 / 2f;
                }
                ang += Math.PI * 1 / 4f;
                for (int i = 4; i < 8; i++) {
                    v[i].set(pos.x + (radius) * (float) Math.cos(ang), pos.y + (radius) * (float) Math.sin(ang));
                    ang += Math.PI * 1 / 2f;
                }
                break;
        }
    }
    void drawVertices(ShapeRenderer sr){
        switch(tier){
            case 0:
                drawTriangle(sr,v[0],v[1],v[2]);
                break;
            case 1:
                drawTriangle(sr,v[0],v[1],v[2]);
                drawTriangle(sr,v[3],v[4],v[5]);
                break;
            case 2:
                drawTriangle(sr,v[0],v[1],v[2]);
                drawTriangle(sr,v[2],v[3],v[0]);
                drawTriangle(sr,v[4],v[5],v[6]);
                drawTriangle(sr,v[6],v[7],v[4]);
                break;
        }
    }

    public static void drawTriangle(ShapeRenderer sr,Vector2 v0, Vector2 v1,Vector2 v2){
        sr.triangle(v0.x,v0.y,v1.x,v1.y,v2.x,v2.y);
    }
    public void dispose() {
        Main.projectionsToRemove.add(this);
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(color.r,color.g,color.b,color.a*alpha_factor);
        sr.set(ShapeRenderer.ShapeType.Filled);
        drawVertices(sr);
    }

    public int getTier(){
        return tier;
    }
    public int getType(){
        return type;
    }
}
