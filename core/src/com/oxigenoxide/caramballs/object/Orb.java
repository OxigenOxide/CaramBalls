package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Orb extends Entity {
    float ang;
    float vel;
    boolean isFollowing;
    Ball ball_following;
    float lerpAlpha;

    public Orb(float x, float y) {
        pos = new Vector2(x, y);
        ang = (float) (Math.random() * Math.PI * 2);
        vel = 2;
        Game.orbCounter.show();
    }

    public void update() {
        vel = Math.max(0, vel - .1f);
        pos.add((float) Math.cos(ang) * vel, (float) Math.sin(ang) * vel);
        if (!isFollowing && vel == 0) {

            float closestDistance = -1;
            Ball closestBall=null;
            for (Ball ball : Main.balls) {
                if(ball.isKinetic() && ball.getClass()== Ball_Main.class) {
                    float dst = MathFuncs.distanceBetweenPoints(ball.pos, pos);
                    if (closestDistance == -1) {
                        closestDistance = dst;
                        closestBall = ball;
                    }
                    if (dst < closestDistance) {
                        closestBall = ball;
                        closestDistance = dst;
                    }
                }
            }
            if(closestBall!=null) {
                isFollowing=true;
                ball_following = closestBall;
            }
        }
        if (isFollowing && ball_following!=null) {
            lerpAlpha=Math.min(1,lerpAlpha+.02f);
            pos.lerp(ball_following.pos, lerpAlpha);
            if(MathFuncs.distanceBetweenPoints(pos,ball_following.pos)<ball_following.radius)
                collect();
        }
    }
    public void collect(){
        Main.orbsToRemove.add(this);
        Main.gameData.orbs++;
        Game.orbCounter.onOrbCollected();
        Game.collectSoundsToPlay++;
        Game.orbsCollected++;
        Main.userData.orbsCollected++;
        System.out.println("collect orb");
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_orb, pos.x, pos.y);
    }
}
