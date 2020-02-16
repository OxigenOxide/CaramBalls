package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;

public class Spike extends Entity {
    Texture tex;
    int tex_index;
    float state = 0;
    boolean open;
    boolean close;
    Body body;
    float count;
    float countMax=480;
    boolean isPermanent;
    Ball ballHit;
    float delay;
    public float radius;
    public Spike() {
        pos = Game.getFreePosOnTable(radius_spawn);
        if(pos==null)
            pos=new Vector2(-100,-100);
        construct();
    }

    public Spike(float x,float y, boolean isPermanent){
        this.isPermanent=isPermanent;
        pos=new Vector2(x,y);
        construct();
    }

    public Spike(float x,float y, float delay){
        this.delay = delay;
        this.isPermanent=isPermanent;
        pos=new Vector2(x,y);
        construct();
    }

    private void construct(){
        open();
        tex = Res.tex_spike[tex_index];
        radius=tex.getWidth()/2f;
        radius_spawn=radius;
    }

    void createBody(){
        body=Main.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.fixtureDef_spike);
        body.setUserData(this);
        body.setTransform(Main.METERSPERPIXEL*(pos.x+.5f),Main.METERSPERPIXEL*(pos.y+3),0);
    }
    public void update() {
        delay=Math.max(delay-Main.dt_one,0);
        if(delay>0)
            return;

        if(open)
            state+=.05f;
        if(close)
            state-=.05f;
        state = MathUtils.clamp(state, 0, 1);
        tex_index = (int) (4 - state * 4);
        tex = Res.tex_spike[tex_index];

        if(state==1 && body==null){
            createBody();
        }

        if(!isPermanent) {
            count += Main.dt_one;
            if (count >= countMax)
                close();
        }
        if(state==0)
            dispose();

        if(ballHit!=null) {
            ballHit.destroy(0, 1,pos);
            ballHit=null;
        }
    }

    public void hitBall(Ball ball){
        ballHit=ball;
    }

    public void open(){
        open=true;
        close=false;
    }
    public void close(){
        open=false;
        close=true;
    }

    public void render(SpriteBatch batch) {
        if(delay>0)
            return;
        batch.draw(tex, (int)(pos.x-tex.getWidth()/2), (int)pos.y);
    }

    public void dispose(){
        body = Main.destroyBody(body);
        Main.spikesToRemove.add(this);
    }
}
