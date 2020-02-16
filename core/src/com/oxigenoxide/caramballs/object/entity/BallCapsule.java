package com.oxigenoxide.caramballs.object.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_BallShard;
import com.oxigenoxide.caramballs.object.floatingReward.FR_Ball;
import com.oxigenoxide.caramballs.scene.Game;

public class BallCapsule extends Entity {
    Vector2 pos_capsule;
    Vector2 pos_ball;
    float count_capsule;
    float count_ball;
    Texture tex;
    Texture tex_ball;
    Texture tex_shine;
    Body body;
    boolean doDispose;
    int level;

    public BallCapsule() {
        pos = Game.getFreePosOnTable(6.5f);
        if (pos == null)
            pos = new Vector2(-100, -500);
        tex = Res.tex_ballCapsule;
        pos_capsule = new Vector2();
        tex_ball = Res.tex_ball[0][0];
        tex_shine = Res.tex_ballCapsule_shine;
        createBody();
        radius_spawn= 7;
        pos_ball=new Vector2();
        level=(int)(Math.random()*5);
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(6 * Main.METERSPERPIXEL);
        body.createFixture(Res.fixtureDef_circle);
        body.setTransform(pos.x * Main.METERSPERPIXEL, pos.y * Main.METERSPERPIXEL, 0);
        body.setUserData(this);
    }

    public void update() {
        count_capsule = (count_capsule + Main.dt_one * .05f) % ((float) Math.PI * 2);
        count_ball = (count_ball + Main.dt_one * .01f) % ((float) Math.PI * 2);
        pos_capsule.set(pos.x, pos.y - 4 + 2 * (float) Math.sin(count_capsule));
        pos_ball.set(pos_capsule.x, pos_capsule.y + 9 + 1 * (float) Math.sin(count_capsule));
        if(doDispose)
            dispose();
    }

    public void shatter(){
        Main.rewardBalls.add(level);
        Main.floatingRewards.add(new FR_Ball(pos_ball.x,pos_ball.y,level));
        for (int i = 0; i < 5; i++) {
            Main.particles.add(new Particle_BallShard(pos.x, pos.y, (float) (Math.random() * Math.PI * 2), (.5f + (float) Math.random()), Res.palette_ballCapsule));
        }
        Main.addSoundRequest(ID.Sound.GLASS);
        doDispose=true;
    }

    public void renderShadow(ShapeRenderer sr){
        Main.drawShadow(sr,pos,8);
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, pos_capsule.x - tex.getWidth() / 2, pos_capsule.y);
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.ballPalette[level]);
        batch.draw(tex_ball, pos_ball.x - tex_ball.getWidth() / 2, pos_ball.y - tex_ball.getHeight()/2);
        batch.setShader(null);
        batch.draw(tex_shine, pos_capsule.x - tex_shine.getWidth() / 2, pos_capsule.y + 10);
    }
    public void dispose(){
        body = Main.destroyBody(body);
        Main.ballCapsulesToRemove.add(this);
    }
}
