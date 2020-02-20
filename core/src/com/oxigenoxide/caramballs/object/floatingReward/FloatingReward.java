package com.oxigenoxide.caramballs.object.floatingReward;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;

public class FloatingReward {

    TextureRegion tex;
    Vector2 pos;
    Vector2 pos_target;
    Sprite sprite;
    float scaleUp=2f;
    final Vector2 pos_middle=new Vector2(Main.width/2,Main.height/2);
    float ang_shine;
    float count_shineRadius;
    float radiusOffset_shine;
    float radius_shine;
    float count;
    float startCount;
    boolean doCount;
    int activity;
    float lerpAlpha;

    FloatingReward(float x, float y){
        pos=new Vector2(x,y);
        doNextActivity();
    }

    public void update(){
        lerpAlpha=Math.min(.001f*(startCount-count),.1f);
        pos.lerp(pos_target,lerpAlpha);
        sprite.setPosition(pos.x-sprite.getRegionWidth()/2,pos.y-sprite.getHeight()/2);
        sprite.setSize(sprite.getRegionWidth()+(sprite.getRegionWidth() * scaleUp - sprite.getRegionWidth()) *lerpAlpha, sprite.getHeight() +(sprite.getRegionHeight() * scaleUp - sprite.getHeight()) *lerpAlpha);
        ang_shine+=Main.dt_one;
        count_shineRadius= (float)((count_shineRadius+.1f*Main.dt_one)%(2*Math.PI));
        radiusOffset_shine=(float)Math.sin(count_shineRadius)*2;
        radius_shine+=(25-radius_shine)*lerpAlpha;

        if(doCount)
            count-=Main.dt_one;
        if(count < 0) {
            count = 0;
            doCount=false; // before doNextActivity();
            doNextActivity();
        }
    }
    void doNextActivity(){
        switch(activity){
            case 0:
                pos_target=pos_middle;
                setCounter(120);
                break;
            case 1:
                pos_target = new Vector2(Main.width/2,Main.height+150);
                setCounter(120);
                break;
            case 2:
                dispose();
        }
        activity++;
    }
    public void setCounter(int frames){
        count=frames;
        doCount=true;
        startCount=count;
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void dispose(){
        Main.floatingRewardsToRemove.add(this);
    }
    public void drawShine(ShapeRenderer sr){
        sr.setColor(1,1,200/255f,180/255f);
        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine,30,5);
        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine+120,30,5);
        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine+240,30,5);

        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine + 60,30,5);
        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine+120 + 60,30,5);
        sr.arc(pos.x,pos.y,radius_shine+radiusOffset_shine,ang_shine+240 + 60,30,5);
        //sr.cone(pos.x,pos.y,0,10,10);
    }
}
