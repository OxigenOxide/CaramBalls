package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Orb extends Ball {

    int type;

    private static final float[] RADIUS = new float[]{3.5f, 6};

    public Ball_Orb() {
        super(Main.height, 1);
        type = (int) (Math.random() * 2);
        construct();
    }

    public Ball_Orb(float x, float y,int type) {
        super(x,y,0, 1);
        this.type = type;
        this.type = (int) (Math.random() * 2);
        velY=(float)Math.random()*4;
        construct();
    }

    private void construct() {
        radius=RADIUS[type];
        radius_spawn = radius + 1;
        body.getFixtureList().first().getShape().setRadius(radius*Main.METERSPERPIXEL);
        setSpriteTexture(Res.getOrbTex(type));
        lock();
    }

    @Override
    public void update() {
        super.update();
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void drawSelectionRing(SpriteBatch batch) {

    }

    @Override
    public void onCollision(Vector2 p, float impact, Object object_hit) {
        super.onCollision(p, impact);
    }

    @Override
    public void contactBall_pre(Ball ball) {
        super.contactBall_pre(ball);
        if (Funcs.getClass(ball) == Ball_Main.class) {
            doDispose = true;
            Main.rewardOrbs.add(new RewardOrb(pos.x, pos.y,type));
            body.getFixtureList().first().setSensor(true);
        }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        explode(angle, impact);
    }

    boolean hasExeploded;

    public void explode(float angle, float impact) {
        /*
        if (!hasExeploded) {
            hasExeploded = true;
            throwParticles(angle, impact, pos, Res.COLOR_SPLASH_BLUE, 10);
            super.explode(angle, impact);
        }
        */
    }

    @Override
    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) {
    }

    public int getSize() {
        return size;
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(Res.COLOR_SPLASH_BLUE.r, Res.COLOR_SPLASH_BLUE.g, Res.COLOR_SPLASH_BLUE.b, 1);
        super.drawTrail(sr);
    }

}