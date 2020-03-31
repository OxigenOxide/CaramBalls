package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;

public class Ball_Orb extends Ball {

    int type;

    private static final float[] RADIUS = new float[]{3.5f, 6};

    static final int SPAWNHEIGHT = 30;

    Counter counter_blink;
    float count_startBlinking = 5;
    boolean isBlinking;
    boolean visible = true;
    int blinks;
    final int MAXBLINKS = 20;

    public Ball_Orb() {
        super(SPAWNHEIGHT, 1);
        type = (int) (Math.random() * 2);
        construct();
    }

    public Ball_Orb(float x, float y, int type) {
        super(x, y, 0, 1);
        this.type = type;
        velY = (float) Math.random() * 4;
        construct();
    }

    public Ball_Orb(float x, float y, int type, boolean ascend) {
        super(x, y, 0, 1);
        this.type = type;
        this.ascend = ascend;
        construct();
    }

    public Ball_Orb(float x, float y, int type, float height) {
        super(x, y, height, 1);
        this.type = type;
        velY = (float) Math.random() * 4;
        construct();
    }

    private void construct() {
        radius = RADIUS[type];
        radius_spawn = radius + 1;
        body.getFixtureList().first().getShape().setRadius(radius * Main.MPP);
        setSpriteTexture(Res.getOrbTex(type));
        sizeFactor = 0;
        doWiggle = false;
        lock();
        counter_blink = new Counter(new ActionListener() {
            @Override
            public void action() {
                counter_blink.start();
                visible = !visible;
                blinks++;
                if (blinks >= MAXBLINKS)
                    dispose();
            }
        }, .08f);
    }

    @Override
    public void update() {
        super.update();
        counter_blink.update();
        if (!isBlinking) {
            if (count_startBlinking > 0)
                count_startBlinking -= Main.dt;
            else {
                isBlinking = true;
                counter_blink.start();
            }
        }

        if (ascend) ascend();
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            if (ascend) {
                batch.setShader(Res.shader_c_over);
                Res.shader_c_over.setUniformf("c", 1, 1, 1, (1 - progress_ascend));
            }
            sprite.draw(batch);
            batch.setShader(null);
        }
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
            if (Main.inGame())
                Game.comboBar.pickupOrb(RewardOrb.getValue(type));
            Main.rewardOrbs.add(new RewardOrb(pos.x, pos.y, type));
            body.getFixtureList().first().setSensor(true);
        }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        explode(angle, impact);
    }

    public void explode(float angle, float impact) {

    }

    @Override
    public void renderShadow(ShapeRenderer sr) {
        if (visible) super.renderShadow(sr);
    }

    @Override
    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) {
    }

    public int getSize() {
        return size;
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        // no trail
    }

}
