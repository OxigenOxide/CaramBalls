package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball_Bad extends Ball {

    float destroyAngle;
    float destroyImpact;

    boolean giveSpeedAfterFall;
    float glowRadiusVariation;
    float count_glow;
    float count_smile;
    TextureRegion tex_smile;

    public Ball_Bad(float x, float y, float height, int size) {
        super(x, y, height, size);
        setSpriteTexture(Res.tex_ball_bad);
        radius = Res.tex_ball_bad.getRegionWidth() / 2;
        maxSpeedMinimum = 4;
        if (height > 30)
            giveSpeedAfterFall = true;
    }

    public Ball_Bad() {
        super(Main.height, 1);
        setSpriteTexture(Res.tex_ball_bad);
        radius = Res.tex_ball_bad.getRegionWidth() / 2;
        maxSpeedMinimum = 4;
        if (height > 30)
            giveSpeedAfterFall = true;
    }

    @Override
    public void update() {
        super.update();
        if (ballmain_hit != null) {
            ballmain_hit.destroy(destroyAngle, destroyImpact, pos);
            ballmain_hit = null;
        }
        count_glow = (float) ((count_glow + .1f * Main.dt_one_slowed) % (Math.PI * 2));
        glowRadiusVariation = (float) Math.sin(count_glow);
        if (Game.doGameOverCue) {
            count_smile += .1f;
            count_smile = Math.min(4, count_smile);
            tex_smile = Res.tex_badSmile[(int) count_smile];
        }

    }

    @Override
    public void drawShapes(ShapeRenderer sr) {
        if (!Main.noFX) {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.setColor(1, 0, 0, .3f - glowRadiusVariation * .1f);
            sr.circle((int) pos.x, (int) pos.y + height - 3 * radius / 9, 8 + glowRadiusVariation * 6);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.setShader(null);
        sprite.draw(batch);
        if (tex_smile != null)
            batch.draw(tex_smile, sprite.getX(), sprite.getY());
        //batch.setShader(Res.shader_palette);
    }

    @Override
    public boolean testHit() {
        return super.testHit();
    }

    @Override
    public void hit(float angle, float speed) {
        super.hit(angle, speed);
    }

    @Override
    public void renderShadow(ShapeRenderer sr) {
        super.renderShadow(sr);
    }

    @Override
    public void contact(Object ud, Vector2 p, float impact) {
        super.contact(ud, p, impact);
    }

    @Override
    public void contactBall(Ball ball) {
        super.contactBall(ball);
        if (Funcs.getClass(ball) == Ball_Main.class) {
            Ball_Main ball_main = (Ball_Main) ball;
            if (!ball_main.isUnderGround) {
                ballmain_hit = ball_main;
                Vector2 force = new Vector2(ball.body.getLinearVelocity().x + body.getLinearVelocity().x, ball.body.getLinearVelocity().y + body.getLinearVelocity().y);
                destroyAngle = (float) Math.atan2(force.y, force.x);
                destroyImpact = MathFuncs.getHypothenuse(force.x, force.y);
                // destroyed in update()
            }
        }
    }

    public void contactBallBad(Ball_Bad ball_bad) {
        super.contactBall(ball_bad);
        Vector2 force = new Vector2(ball_bad.body.getLinearVelocity().x + body.getLinearVelocity().x, ball_bad.body.getLinearVelocity().y + body.getLinearVelocity().y);
        destroy((float) Math.atan2(force.y, force.x), MathFuncs.getHypothenuse(force.x, force.y), pos);
    }

    @Override
    void onBounce(float bounceImpact) {
        if (giveSpeedAfterFall) {
            giveSpeedAfterFall = false;
            double randang = (Math.PI * Math.random());
            body.setLinearVelocity((float) Math.cos(randang) * 1, (float) Math.sin(randang) * 1);
        }
        super.onBounce(bounceImpact);
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(Res.COLOR_RED);
        super.drawTrail(sr);
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        super.destroy(angle, impact, pos_danger);
        explode(angle, impact);
    }

    @Override
    public void explode(float angle, float impact) {
        super.explode(angle, impact);
        impact = Math.min(impact, 4);
        throwParticles(angle, impact, pos, Res.ballBadPalette);
    }

    @Override
    public void wiggle() {
        super.wiggle();
    }

    @Override
    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        //body.createFixture(Res.fixtureDef_badBall_opponents);
        body.createFixture(Res.fixtureDef_ball[0]);
        body.setUserData(this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
