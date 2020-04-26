package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.oxigenoxide.caramballs.object.PowerOrb;
import com.oxigenoxide.caramballs.object.Projection;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.effect.Effect_BallExplosion;
import com.oxigenoxide.caramballs.object.effect.Effect_Split;
import com.oxigenoxide.caramballs.object.entity.PowerOrbEntity;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Sell;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OC_Fruit;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.ballsToAdd;
import static com.oxigenoxide.caramballs.Main.orbContainers;
import static com.oxigenoxide.caramballs.scene.Game.ball_king;

public class Ball_Main extends Ball {

    public int level;
    public int loop;
    float count_noPull;
    public Color[] palette;
    public Color[] paletteCombined;

    Projection projection;

    Vector2 pos_speechBubble;

    boolean readyToMilk;

    public float timeElapsed_milk;

    float speechBubbleDisposition;
    static final float SPEECHBUBBLEAMP = 2;
    float count_speechBubble;

    float count_alive;
    final int COUNTMAX_ALIVE = 10;

    boolean hasBeenCombined;

    long timeUntilMilk = 20000;

    PowerOrb[] powerOrbs = new PowerOrb[3];
    int amount_powerOrbs;
    float ang_powerOrbs;

    static final float SPLITVEL = 5;

    int blinks;
    static final int MAXBLINKS = 20;
    static final float BLINKTIME = .1f;
    Counter counter_blink;
    boolean visible = true;
    boolean invincible;

    public static float glowLoop;

    public static boolean commonColor = false;

    public Ball_Main(float x, float y, float height, int size, int level) {
        super(x, y, height, size);
        this.level = level;
        construct();
    }

    public Ball_Main(int level) {
        super(0,20);
        this.level = level;
        construct();
    }

    void construct() {
        loop = (int) (level / 9f);

        palette = getBallPalette(level, loop);
        paletteCombined = new Color[]{new Color(), new Color(), new Color(), new Color()};
        setSpriteTexture(Res.tex_ball[Game.ballType][size]);

        Main.mainBalls.add(this);

        counter_blink = new Counter(new ActionListener() {
            @Override
            public void action() {
                visible = !visible;
                if (visible)
                    Main.addSoundRequest(ID.Sound.TAP);
                blinks++;
                if (blinks < MAXBLINKS)
                    counter_blink.start();
                else {
                    blinks = 0;
                    invincible = false;
                }
            }
        }, BLINKTIME);

        if (Main.inGame()) {
            if (Game.level < level + 1) { // in new level
                Main.addSoundRequest(ID.Sound.SUCCESS);
                Game.nextLevel();
                makeInvincible();
                for (Ball_Main ball_main : Main.mainBalls)
                    if (ball_main != this && !ball_main.doDispose)
                        ball_main.explode(0, 1);
            }
        }
        isBallKing();
        setSpriteUnderGround();
    }


    @Override
    void construct_farm() {
        super.construct_farm();
        count_speechBubble = (float) Math.random();
        pos_speechBubble = new Vector2();
    }

    @Override
    public void update() {
        super.update();

        counter_blink.update();

        if (!isDisposed) {
            if (count_alive < COUNTMAX_ALIVE)
                count_alive += Main.dt;

            if (ballmain_hit != null && !ballmain_hit.isDisposed) {
                merge();
            }

            // gravitate to close balls
            if (count_noPull <= 0) {
                if (isKinetic() && !isPassthrough)
                    for (Ball_Main ball : Main.mainBalls) {
                        if (ball != this && !ball.isStuck && ball.isKinetic() && !ball.isPassthrough) {
                            if (ball.level == level && ball.size == size && MathFuncs.distanceBetweenPoints(ball.pos, pos) < 16) {
                                float ang = MathFuncs.angleBetweenPoints(pos, ball.pos);
                                body.setLinearVelocity(body.getLinearVelocity().x + (float) Math.cos(ang) * 2, body.getLinearVelocity().y + (float) Math.sin(ang) * 2);
                            }
                        }
                    }
            } else
                count_noPull -= Main.dt_one;

            if (doBeginGameOverCue) {
                Game.beginGameOverCue(this);
                doBeginGameOverCue = false;
            }
            if (doSplit) {
                split(pos_hitBy);
                doSplit = false;
            }

            if (projection != null) {
                projection.setPosition(pos.x, pos.y - 3 * radius / 9);
                projection.update();
            }

            for (PowerOrb po : powerOrbs)
                if (po != null)
                    po.update(ang_powerOrbs += Main.dt);

            if (ascend) ascend();

            Funcs.combinePalettes(paletteCombined, Res.PALETTE_WHITE, palette, progress_ascend);

            if (loop >= 1)
                Funcs.combineColors(palette[0], Color.BLACK, Res.COLOR_OUTLINE[loop], (float) (Math.sin(glowLoop) + 1) / 2);
        }
    }

    public void update_farm() {
        super.update_farm();

        if (timeElapsed_milk >= timeUntilMilk) readyToMilk = true;

        if (readyToMilk) {
            pos_speechBubble.set((int) pos.x, (int) pos.y + height);
            count_speechBubble = (count_speechBubble + Main.dt * 5) % (2 * (float) Math.PI);
            speechBubbleDisposition = SPEECHBUBBLEAMP * (float) Math.cos(count_speechBubble) + SPEECHBUBBLEAMP;
        } else {
            timeElapsed_milk += Main.dt * 1000;
        }
        timeElapsed_milk = Math.min(timeUntilMilk, timeElapsed_milk);
    }

    void merge() {
        doDispose = true;
        ballmain_hit.doDispose = true;

        onCombine();
        ballmain_hit.onCombine();

        // Projection
        Projection projectionToPass = null;
        if (ballmain_hit.projection != null)
            projectionToPass = ballmain_hit.projection;
        else if (projection != null)
            projectionToPass = projection;


        Ball_Main ball_new;
        if (size + 1 < 3) { // stay in same level
            ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, (size + 1), level);
            Game.meter.onCombine(ball_new);
            if (projectionToPass != null)
                ball_new.createProjection(projectionToPass.getType(), projectionToPass.getTier());
        } else { // to next level
            ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, 0, level + 1);
            Game.pos_floorFadeStain.set(ball_new.pos);

            if (projectionToPass != null) {
                Game.changeWorld(projectionToPass.getType(), projectionToPass.getTier());
                projectionToPass.onReleaseGrow();

                Game.setPlace(projectionToPass.getType());

                projection = null;
                ballmain_hit.projection = null;
                Main.projections.add(projectionToPass);
            }
        }
        ball_new.hasBeenCombined = true;
        ball_new.body.setLinearVelocity(body.getLinearVelocity().add(ballmain_hit.body.getLinearVelocity()).scl(.5f));

        ball_new.ang_powerOrbs = ang_powerOrbs;
        ball_new.amount_powerOrbs = Math.min(3, amount_powerOrbs + ballmain_hit.amount_powerOrbs);
        ball_new.setPowerOrbs(combinePowerOrbs(powerOrbs, ballmain_hit.powerOrbs));

        Main.ballsToAdd.add(ball_new);
        if (Main.inGame())
            Main.game.onBallCombined();

        if ((count_alive < 1 && hasBeenCombined) || (ballmain_hit.count_alive < 1 && ballmain_hit.hasBeenCombined))
            Main.game.giveTrickReward(ball_new.pos.x, ball_new.pos.y);

        //reward
        if (size == 2)
            orbContainers.add(new OC_Fruit(ball_new.pos.x, ball_new.pos.y));
        else if (size == 1)
            ballsToAdd.add(new Ball_Orb(ball_new.pos.x, ball_new.pos.y, true));
        else
            ballsToAdd.add(new Ball_Orb(ball_new.pos.x, ball_new.pos.y, true));

        // effects
        Main.shake();
        Main.fbm.writeMerges();
        Main.addSoundRequest(ID.Sound.PLOP, 6);

        Game.onBallMerge();

        ballmain_hit = null;
    }

    @Override
    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) {
        if (Funcs.getClass(object_hit) != Ball_Orb.class)
            super.doCollisionEffect(p, impact, object_hit);
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
    public void contact(Object ud, Vector2 p, float impact) {
        super.contact(ud, p, impact);
    }

    @Override
    public void contactBall(Ball ball) {


        if (ball.getClass() == Ball_Main.class) {
            Ball_Main ball_main = (Ball_Main) ball;
            if (!ball_main.isUnderGround && ball_main.size == size && ball_main.level == level) {
                ballmain_hit = ball_main;
                return;
            }
        }
        super.contactBall(ball);
    }

    @Override
    public void wiggle() {
        super.wiggle();
    }

    @Override
    public void createBody() {
        super.createBody();
    }

    public void isBallKing() {

        if (Game.inTutorialMode)
            return;

        if (ball_king == null) {
            Game.crown.newOwner(this);
            return;
        }
        if (size > ball_king.size || level > ball_king.level) {
            if (level >= ball_king.level) {
                Game.crown.newOwner(this);
            }
        }
    }

    public void drawShapes(ShapeRenderer sr) {
        if (projection != null)
            projection.render(sr);
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            if (commonColor)
                Funcs.setShader(batch, Res.shader_palette);
            else
                batch.setShader(Res.shader_palette);

            Main.setPalette(palette);
            sprite.draw(batch);
        }

        for (PowerOrb po : powerOrbs)
            if (po != null)
                po.render(batch);
    }

    @Override
    public void renderShadow(ShapeRenderer sr) {
        if (visible)
            super.renderShadow(sr);
    }

    public void render_farm(SpriteBatch batch) {
        if (readyToMilk)
            batch.draw(Res.tex_speechBubbleOrb, pos_speechBubble.x - Res.tex_speechBubbleOrb.getRegionWidth() / 2 - 1, pos_speechBubble.y + (int) speechBubbleDisposition);
    }

    void milk() {
        int orbAmount = 5;
        float angle;
        timeElapsed_milk = 0;
        for (int i = 0; i < orbAmount; i++) {
            angle = (float) (Math.random() * Math.PI * 2);
            Ball_Orb ball_new = new Ball_Orb(pos.x + (radius+4) * (float) Math.cos(angle), pos.y + (radius+4) * (float) Math.sin(angle), 0);
            ball_new.setVelocity(5 * (float) Math.cos(angle), 5 * (float) Math.sin(angle));
            Main.ballsToAdd.add(ball_new);
        }
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        if (invincible)
            return;

        if (amount_powerOrbs > 0) {
            amount_powerOrbs--;
            powerOrbs[amount_powerOrbs].use();
            powerOrbs[amount_powerOrbs] = null;
            makeInvincible();
            return;
        }

        if (Main.INVINCIBLE)
            return;

        if (!Game.inTutorialMode && size == 0) {
            int ballsCounted = 0;

            for (Ball_Main ball : Main.mainBalls) {
                if (!ball.isUnderGround)
                    ballsCounted++;
            }
            if (ballsCounted <= 1) {
                doBeginGameOverCue = true;
                return;
            }
        }

        if (size == 0 || dontSplit) {
            Game.meter.loseBall();
            if (Game.ballDepot.inTerminalPhase()) {
                Main.rewardOrbs.add(new RewardOrb(pos.x, pos.y, 0).goToOrbDisplay());
                dispose();
            } else explode(angle, impact);
        } else if (pos_danger != null) {
            pos_hitBy = pos_danger;
            doSplit = true;
        }

    }

    public Ball_Main makeInvincible() {
        counter_blink.start();
        invincible = true;
        return this;
    }

    boolean doBeginGameOverCue;
    boolean doSplit;
    Vector2 pos_hitBy;
    boolean hasExeploded;


    public void explode(float angle, float impact) {
        if (!hasExeploded) {
            hasExeploded = true;
            impact = Math.min(impact, 2);
            Main.effects.add(new Effect_BallExplosion(pos.x, pos.y));
            throwParticles(angle, impact, pos, palette, getParticleAmount());
            //pushBodies();
            super.explode(angle, impact);
            if (Main.inGame())
                Game.doOnMainBallDestroyed = true;
        }
    }

    public void pushBodies() {
        if (body == null)
            return;
        Array<Body> bodies = new Array<Body>();
        Main.world.getBodies(bodies);
        float force;
        float ang;
        for (Body body : bodies) {
            if (body != this.body && Main.canBodyBePushed(body.getUserData())) {
                force = Math.min(30, 60 / (float) Math.pow(MathFuncs.distanceBetweenPoints(body.getPosition(), this.body.getPosition()), 2)); // inverse square law
                ang = MathFuncs.angleBetweenPoints(this.body.getPosition(), body.getPosition());
                body.applyForce(force * (float) Math.cos(ang), force * (float) Math.sin(ang), body.getPosition().x, body.getPosition().y, true);
            }
        }
    }

    static final float SPLITANGLE = (float) Math.PI;
    //static final float SPLITANGLE=(float)Math.PI*1/2f;
    boolean isSplit;

    public void split(Vector2 pos_danger) {
        if (!isSplit) {
            isSplit = true;

            Game.meter.onSplit(this);

            float ang = MathFuncs.angleBetweenPoints(pos_danger, pos);
            Vector2 vel = new Vector2((float) Math.cos(ang) * SPLITVEL, (float) Math.sin(ang) * SPLITVEL);
            int size = this.size - 1;
            float r = Res.ballRadius[size] + 2;

            Ball_Main ball_1 = new Ball_Main(pos.x + (float) Math.cos(ang + SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang + SPLITANGLE / 2) * r, 0, size, this.level);
            Ball_Main ball_2 = new Ball_Main(pos.x + (float) Math.cos(ang - SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang - SPLITANGLE / 2) * r, 0, size, this.level);
            ball_1.body.setLinearVelocity(vel.rotateRad(SPLITANGLE / 2));
            ball_2.body.setLinearVelocity(vel.rotateRad(-SPLITANGLE));

            Main.ballsToAdd.add(ball_1);
            Main.ballsToAdd.add(ball_2);
            ball_1.setNoPull();
            ball_2.setNoPull();

            Main.effects.add(new Effect_Split(pos.x, pos.y, ang, this.size));

            setPassthrough(true);

            if (projection != null)
                ball_1.createProjection(projection.getType(), projection.getTier());

            doDispose = true;
        }
    }

    public void createProjection(int type, int tier) {
        projection = new Projection(pos.x, pos.y, radius + 6, type, tier);
    }

    public int getValue() {
        return (int) Math.pow(2, getNum());
    }

    public void setNoPull() {
        count_noPull = 30;
    }

    public int getNum() {
        return level * 3 + size;
    }

    public int getSize() {
        return size;
    }

    public static int getLevel(int num) {
        return num / 3;
    }

    public static int getSize(int num) {
        return num % 3;
    }

    @Override
    public void fallInHole(Hole hole) {
        super.fallInHole(hole);

        if (Main.inGame()) {
            int ballsCounted = 0;

            for (Ball_Main ball : Main.mainBalls)
                if (!ball.isUnderGround)
                    ballsCounted++;

            if (ballsCounted <= 1) {
                Game.beginGameOverCue(this);
            } else {
                Main.mainBalls.remove(this);
            }
        }
    }

    @Override
    public void disappearInHole() {
        if (Funcs.getClass(hole_fall) == Hole_Sell.class)
            sell();
        super.disappearInHole();
    }

    public void sell() {
        for (int i = 0; i < getNum() + 1; i++)
            Main.rewardOrbs.add(new RewardOrb(pos.x, pos.y, 0).spread());
    }

    public void onCombine() {
        if (readyToMilk) {
            milk();
            readyToMilk = false;
        }
    }

    public void collectPowerOrb(Ball_PowerOrb poe) {

        if(arePowerOrbsSaturated())
            return;
        int index;
        if (powerOrbs[0] == null)
            index = 0;
        else if (powerOrbs[1] == null)
            index = 1;
        else if (powerOrbs[2] == null)
            index = 2;
        else {
            if (!powerOrbs[0].isUpgraded())
                powerOrbs[0].upgrade();
            else if (!powerOrbs[1].isUpgraded())
                powerOrbs[1].upgrade();
            else if (!powerOrbs[2].isUpgraded())
                powerOrbs[2].upgrade();
            poe.collect();
            return;
        }
        poe.collect();

        powerOrbs[index] = new PowerOrb(poe.pos.x, poe.pos.y, this, index);

        for (PowerOrb po : powerOrbs)
            if (po != null)
                po.setAmount(index + 1);

        amount_powerOrbs = index + 1;
    }

    public void setPowerOrbs(PowerOrb[] powerOrbs) {
        this.powerOrbs = powerOrbs;
        for (PowerOrb po : powerOrbs)
            if (po != null) {
                po.setParent(this);
                po.setAmount(amount_powerOrbs);
            }

    }

    public boolean arePowerOrbsSaturated() {
        return amount_powerOrbs == 3 && powerOrbs[0].isUpgraded() && powerOrbs[1].isUpgraded() && powerOrbs[2].isUpgraded();
    }

    static PowerOrb[] combinePowerOrbs(PowerOrb[] powerOrbs0, PowerOrb[] powerOrbs1) {
        PowerOrb[] newPowerOrbs = powerOrbs0;

        outer:
        for (int i = 0; i < 3; i++) {
            if (powerOrbs1[i] != null) {
                for (int j = 0; j < 3; j++) {

                    if (newPowerOrbs[j] == null) {
                        newPowerOrbs[j] = powerOrbs1[i];
                        newPowerOrbs[j].setIndex(j);
                        continue outer;
                    }
                }
                for (int j = 0; j < 3; j++) {
                    if (!newPowerOrbs[j].isUpgraded()) {
                        newPowerOrbs[j].upgrade();
                        continue outer;
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if (newPowerOrbs[i] != null)
                newPowerOrbs[i].setIndex(i);
        }

        return newPowerOrbs;
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(palette[1].r, palette[1].g, palette[1].b, 1);
        super.drawTrail(sr);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this == ball_king)
            ball_king = null;
        Main.mainBalls.remove(this);
    }

    public static Color[] getBallPalette(int level, int loop) {

        Color[] palette_normal = Res.palette_mainBall[level % (Res.palette_mainBall.length)];

        if (loop == 0)
            return palette_normal;

        Color[] palette = new Color[4];
        //palette[0] = Funcs.combineColors(Res.COLOR_OUTLINE[Math.min(loop, Res.COLOR_OUTLINE.length - 1)], );
        palette[0] = Color.BLACK.cpy();
        palette[1] = palette_normal[1];
        palette[2] = palette_normal[2];
        palette[3] = palette_normal[3];

        return palette;
    }

    @Override
    public void onSelect() {
        if (readyToMilk) {
            milk();
            readyToMilk = false;
        }
    }

    public Ball_Main setTimeElapsed_milk(long timeElapsed_milk) {
        timeElapsed_milk = Math.min(timeUntilMilk, timeElapsed_milk);
        if (readyToMilk)
            timeElapsed_milk = 0;

        this.timeElapsed_milk = timeElapsed_milk;
        return this;
    }

    public float getProgressMilk() {
        return timeElapsed_milk / timeUntilMilk;
    }

    public static Color[] getBallPalette(int level) {
        return getBallPalette(level % (Res.palette_mainBall.length), (level / Res.palette_mainBall.length));
    }

    static public class Ball_Main_Data {
        public float x, y;
        public int size, level;
        public long timeElapsed_milk;

        public Ball_Main_Data() {
            // this constructor is actually being used for serialisation
        }

        public Ball_Main_Data(float x, float y, int size, int level, long timeElapsed_milk) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.level = level;
            this.timeElapsed_milk = timeElapsed_milk;
        }
    }

}
