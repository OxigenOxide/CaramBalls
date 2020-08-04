package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Fruit extends Ball {

    static final float SPAWNHEIGHT = 30;
    public static final float RADIUS = 6;

    int orbAmount = 5;

    float SPREADORBS = (float) Math.PI * .75f;

    boolean doSplat;

    Ball ball_hitBy;

    public Ball_Fruit(float x, float y) {
        super(x, y, SPAWNHEIGHT, 1);
        setSpriteTexture(Res.tex_fruit[Game.nextFruit]);
        construct();
    }

    public Ball_Fruit(float x, float y, float height) { // fruit in rings
        super(x, y, height, 1);
        setSpriteTexture(Res.getRandomFruitTex());
        construct();
    }

    private void construct() {
        radius = RADIUS;
        radius_spawn = radius + 1;
        body.getFixtureList().first().getShape().setRadius(radius * Main.MPP);
        sizeFactor = 0;
        doWiggle = true;
        sprite.setOrigin(6, 6);
        lock();
        wiggleFactor = .5f;
    }

    @Override
    public void update() {
        super.update();

        if (ascend) ascend();

        if (doSplat) splat();
    }

    void splat() {
        Game.throwConfetti(pos.x, pos.y);
        Main.addSoundRequest(ID.Sound.SPLAT, 5, 1, .5f);

        hitAngle = body.getLinearVelocity().angleRad();

        for (int i = 0; i < orbAmount; i++) {
            Ball_Orb ball_new = new Ball_Orb(pos.x, pos.y);
            ball_new.setVelocity(hitImpact * (float) Math.cos(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2), hitImpact * (float) Math.sin(hitAngle + Math.random() * SPREADORBS - SPREADORBS / 2));
            Main.ballsToAdd.add(ball_new);
        }

        dispose();
    }

    @Override
    void update_farm() {
        super.update_farm();
    }

    public void render(SpriteBatch batch) {
        Funcs.setShaderNull(batch);
        sprite.draw(batch);
    }

    @Override
    public void drawSelectionRing(SpriteBatch batch) {
        // no selection ring
    }

    float hitAngle;
    float hitImpact;

    @Override
    public void onCollision(Vector2 p, float impact, Object object_hit) {
        super.onCollision(p, impact, object_hit);

        if (impact > 6 && object_hit instanceof Ball) {
            doSplat = true;
            ball_hitBy = (Ball) object_hit;
            hitImpact = impact;
        }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        // no destroy
    }

    @Override
    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) {
        // no
    }

    @Override
    void update_trail() {
        // no trail
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        // no trail
    }
}
