package com.oxigenoxide.caramballs.object.entity.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Cross;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Hit;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Pulse;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class Ball extends Entity {
    public Sprite sprite;
    Vector2 pos_last;
    public Body body;
    float wiggle;
    public float height;
    public float velY;
    public float radius;
    float count_hitCooldown;
    float maxSpeedMinimum = 6;
    float maxSpeed = maxSpeedMinimum;
    public int size = 0;
    public boolean isUnderGround;
    public boolean doDispose;
    Ball_Main ballmain_hit;
    Ball ball_hit;
    float angleToMid;
    float distToMid;
    float time_off;
    public boolean fall;
    Hole hole_fall;
    public Hole hole_spawn;
    public float count_recentlyHit;
    float countMax_recentlyHit = 30;
    public boolean isDisposed;
    boolean isPassthrough;
    boolean dontSplit;
    public boolean isLocked;
    boolean doDrainSpeed;
    public float count_cantGetStuck;
    boolean doActivateShield;
    boolean isShrinking;

    boolean frozen;
    boolean doFreeze;
    float freezeCooldown;
    float count_frozen;

    float selectedIntensity;
    float count_circle;
    float ringSizeFactor;
    public boolean canBeHit;
    public boolean doPermaPassthrough;
    float sizeFactor = 1;

    float ringRadius;

    float progress_ascend;
    boolean ascend;

    final static float COUNTMAX_HITCOOLDOWN = 30;
    final static int FREEZECOOLDOWNMAX = 30;
    final static float WIGGLEFACTOR = .25f;
    final static float HEIGHT_PASSTHROUGH = 15;
    final static int MAXRINGRADIUS = 20;
    final static float RINGWIDTH = 3f;
    boolean doWiggle = true;

    ParticleEffect particleEffect;

    static Texture tex_ring = new Texture(new Pixmap(MAXRINGRADIUS * 2, MAXRINGRADIUS * 2, Pixmap.Format.RGBA8888));

    public Ball(float x, float y, float height, int size) {
        this.height = height;
        this.size = size;
        pos = new Vector2(x, y);
        createBody();
        construct();
    }

    public Ball(float height, int size) {
        this.height = height;
        this.size = size;
        pos = Game.getFreePosOnTable(radius + 1);
        if (pos == null) {
            doDispose = true;
            pos = new Vector2(1000, -100);
        }
        createBody();
        construct();
    }

    public Ball(float height, float radius) {
        this.height = height;
        pos = Game.getFreePosOnTable(radius + 1);
        if (pos == null) {
            doDispose = true;
            pos = new Vector2(1000, -100);
        }
        createBody(radius);
        construct();
    }

    private void construct() {
        pos_last = new Vector2(pos);
        sprite = new Sprite(Res.tex_ball[0][0]);
        body.setTransform(pos.x * Main.MPP, pos.y * Main.MPP, 0);
        radius = body.getFixtureList().first().getShape().getRadius() * Main.PPM;
        radius_spawn = radius + 1;

        isPassthrough = true;
        if (height < 0) {
            body.setActive(false);
            isUnderGround = true;
        }
        if (height > HEIGHT_PASSTHROUGH) {
            setPassthrough(true);
        }

        if (Main.inFarm())
            construct_farm();
    }

    void construct_farm() {

    }

    public void update() {
        if (!isDisposed) {
            if (!frozen) {

                if (isShrinking) {
                    sprite.setSize(sprite.getWidth() - 1, sprite.getHeight() - 1);
                    sprite.setPosition((int) ((int) pos.x - sprite.getWidth() / 2), (int) ((int) pos.y + height - sprite.getHeight() / 2));
                    if (sprite.getWidth() <= 0)
                        dispose();
                    return;
                }

                if (isUnderGround && height >= 0) {
                    isUnderGround = false;
                    body.setActive(true);
                }

                if (!doPermaPassthrough)
                    setPassthrough(height < 0 || height > HEIGHT_PASSTHROUGH || ascend);

                if (fall) fall();

                if (isUnderGround) height += Main.dt_one_slowed * .2f;

                if (setSpriteUnderGround()) {
                    if (doDispose)
                        dispose();
                    return;
                }

                pos_last.set(pos);
                pos.set(body.getPosition());
                pos.scl(Main.PPM);
                if (size > 0 && doWiggle) // wiggle
                    sprite.setSize(sizeFactor * (float) (sprite.getRegionWidth() * (1 + wiggle * WIGGLEFACTOR * -Math.sin(wiggle * 15))), sizeFactor * (float) (sprite.getRegionHeight() * (1 + wiggle * WIGGLEFACTOR * -Math.cos(wiggle * 15))));
                sprite.setPosition((int) (pos.x - sprite.getWidth() / 2), (int) (pos.y + height - sprite.getHeight() / 2));

                if (MathFuncs.getHypothenuse(body.getLinearVelocity().x, body.getLinearVelocity().y) > maxSpeed)
                    body.setLinearVelocity(body.getLinearVelocity().x * .75f, body.getLinearVelocity().y * .75f);
                else if (doDrainSpeed)
                    body.setLinearVelocity(body.getLinearVelocity().x * .95f, body.getLinearVelocity().y * .95f);

                if (height == 0)
                    body.setLinearVelocity(body.getLinearVelocity().x * (1 - Main.worldProperties.friction), body.getLinearVelocity().y * (1 - Main.worldProperties.friction));

                wiggle = Math.max(0, wiggle - .05f * Main.dt_one_slowed);

                height += velY * Main.dt_one_slowed;
                height = Math.max(0, height);
                if (height == 0) {
                    if (velY < 0) {
                        velY = -velY * .5f;
                        onBounce(velY);
                        if (velY < 1f)
                            velY = 0;
                        wiggle(velY * .2f);
                    }
                } else
                    velY += Game.GRAVITY_PIXELS * .2f * Main.dt_one_slowed;

                count_hitCooldown = Math.max(0, count_hitCooldown - Main.dt_one);

                maxSpeed = Math.max(maxSpeedMinimum, maxSpeed * (float) Math.pow(.99f, Main.dt_one_slowed));

                /*

                // Push to mid so it balls don't get stuck in the corners

                distToMid = (float) Math.sqrt(Math.pow(pos.x - Main.width / 2, 2) + Math.pow((pos.y - Main.height / 2) * Main.width / Main.height, 2));
                if (distToMid > Main.width / 2 - 10) {
                    time_off += Main.dt_one_slowed;
                    if (time_off > 60) {
                        angleToMid = MathFuncs.angleBetweenPoints(pos, Main.pos_middle);
                        body.setLinearVelocity((float) Math.cos(angleToMid) * .025f + body.getLinearVelocity().x, (float) Math.sin(angleToMid) * .025f + body.getLinearVelocity().y);
                    }
                } else {
                    time_off = 0;
                }
                */

                if (body.getLinearVelocity().len() < .1f)
                    body.setLinearVelocity(0, 0);

                count_recentlyHit = Math.max(0, count_recentlyHit - Main.dt_one);
                count_cantGetStuck = Math.max(0, count_cantGetStuck - Main.dt_one_slowed);

                freezeCooldown = Math.max(0, freezeCooldown - Main.dt_one);

            } else { // if frozen
                count_frozen = Math.max(0, count_frozen - Main.dt_one);
                if (count_frozen == 0)
                    unfreeze();
            }
            if (doFreeze) {
                doFreeze = false;
                freeze();
            }
            if (doActivateShield) {
                doActivateShield = false;
                activateShield();
            }
            if (Main.ballSelector.isBallSelected(this))
                selectedIntensity += Main.dt_one * .1f;
            else
                selectedIntensity -= Main.dt_one * .1f;
            selectedIntensity = MathUtils.clamp(selectedIntensity, 0, 1);

            canBeHit = body.getLinearVelocity().len() < 1 && !Main.ballSelector.isBallSelected(this);

            if (canBeHit) {
                count_circle = (count_circle + Main.dt_one_slowed * .05f) % ((float) Math.PI * 2);
            } else {
                count_circle = -(float) Math.PI * .5f;
            }

            update_ring();

            if (Main.inFarm())
                update_farm();

            if (sizeFactor < 1)
                sizeFactor = Math.min(sizeFactor + Main.dt * 5, 1);

            ball_hit = null;

            if (particleEffect != null) {
                particleEffect.update(Main.dt);
                particleEffect.setPosition(pos.x, pos.y);
            }

            //THIS ALWAYS LAST
            if (doDispose) {
                dispose();
            }
        }
    }

    void ascend() {
        height = progress_ascend * 35;
        progress_ascend = Math.min(1, progress_ascend + Main.dt * 2);
        sprite.setSize(sprite.getRegionWidth() * (.7f + .3f * progress_ascend), sprite.getRegionHeight() * (.7f + .3f * progress_ascend));
        if (progress_ascend == 1) {
            ascend = false;
            velY = 0;
        }
    }

    public void setParticleTwinkle() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/particle.p"), Res.atlas);
        particleEffect.setEmittersCleanUpBlendFunction(false);
        particleEffect.setPosition(pos.x, pos.y);
        particleEffect.start();
    }

    public void endParticleTwinkle() {
        particleEffect.dispose();
        particleEffect = null;
    }

    public void shrink() {
        body = Main.destroyBody(body);
        isShrinking = true;
    }

    static final int DISPOSEBUFFER = 4;

    void update_farm() {
        if (pos.y - radius < Main.farm.pos_field.y - DISPOSEBUFFER || pos.y + radius > Main.farm.pos_field.y + Main.farm.FIELDWIDTH + 4 + DISPOSEBUFFER) {
            dispose();
        }
    }

    boolean doRing;

    void update_ring() {
        doRing = canBeHit && Main.inGame();
        if (doRing) {
            ringSizeFactor = Math.min(1, ringSizeFactor + Main.dt_one_slowed * .1f);
            ringRadius = ringSizeFactor * (radius + 3 + 2 * (float) Math.sin(count_circle));
            return;
        }
        ringSizeFactor = 0;
    }

    public Ball setPermaPassthrough(boolean b) {
        if (b)
            setPassthrough(true);
        doPermaPassthrough = b;
        return this;
    }

    public void onFailedSelect() {
        Main.particles.add(new Particle_Cross(pos.x, pos.y));
    }

    boolean isShielded;

    public void activateShield() {
        if (!isShielded) {
            Res.fixtureDef_shield.shape.setRadius((radius + 2) * Main.MPP);
            body.createFixture(Res.fixtureDef_shield);
            isShielded = true;
        }
    }

    boolean setSpriteUnderGround() {
        if (height < 0 || fall && height == 0) {
            sprite.setSize((float) (sprite.getRegionWidth() / (1 + Math.abs(height))), (float) (sprite.getRegionHeight() / (1 + Math.abs(height))));
            sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
            return true;
        }

        sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
        return false;
    }

    void onBounce(float bounceImpact) {
        Main.addSoundRequest(ID.Sound.BOUNCE, 1, bounceImpact * .2f);
    }

    public boolean isStuck;

    public void getStuck() {
        if (count_cantGetStuck <= 0) {
            isStuck = true;
            //body.setActive(false);
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }

    public void lock() {
        isLocked = true;
    }

    public void ignoreWalls() {
        Filter filter = new Filter();
        filter.maskBits = (Res.MASK_ZERO);
        filter.categoryBits = (Res.MASK_ZERO);
        body.getFixtureList().first().setFilterData(filter);
    }

    public Ball dontSplit() {
        dontSplit = true;
        return this;
    }

    public void setPassthrough(boolean b) {
        if (b) {
            isPassthrough = true;
            Filter filter = new Filter();
            filter.maskBits = (Res.MASK_ZERO | Res.MASK_WALL);
            filter.categoryBits = (Res.MASK_PASSTHROUGH);
            body.getFixtureList().first().setFilterData(filter);
        } else {
            isPassthrough = false;
            Filter filter = new Filter();
            filter.maskBits = (short) (Res.MASK_ZERO | Res.MASK_WALL);
            filter.categoryBits = (Res.MASK_ZERO);
            body.getFixtureList().first().setFilterData(filter);
        }
    }

    public boolean isKinetic() {
        return !fall && height >= 0;
    }

    public void fall() {
        if (MathFuncs.distanceBetweenPoints(pos, hole_fall.pos) > hole_fall.radiusMax - radius) {
            pos.set(pos.lerp(hole_fall.pos, .1f));
        } else
            height -= Main.dt_one * .1f;

        if (sprite.getWidth() < 2 || hole_fall.isDisposed) {
            disappearInHole();
        }
    }

    public void disappearInHole() {
        doDispose = true;
    }

    public void fallInHole(Hole hole) {
        if (hole == hole_spawn)
            return;

        if (!fall) {
            body.setActive(false);
            hole_fall = hole;
            fall = true;
        }
    }

    public Ball setDrainSpeed() {
        doDrainSpeed = true;
        return this;
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_a);
        Res.shader_a.setUniformf("a", .75f);
        if (isShielded)
            batch.draw(Res.tex_shield, (int) (pos.x - 2 - sprite.getRegionWidth() / 2), (int) (pos.y - 2 - sprite.getHeight() / 2 + height), sprite.getRegionWidth() + 4, sprite.getHeight() + 4);
        batch.setShader(null);
    }

    public void drawParticleEffect(SpriteBatch batch) {
        if (particleEffect != null)
            particleEffect.draw(batch);
    }

    void render_farm(SpriteBatch batch) {
    }

    public void drawSelectionRing(SpriteBatch batch) {
        if (doRing) {
            batch.setShader(Res.shader_circle);
            Res.shader_circle.setUniformf("r", ringRadius / 2f / MAXRINGRADIUS);
            Res.shader_circle.setUniformf("width", RINGWIDTH / 2f / MAXRINGRADIUS);
            float correction = 0;
            if (size == 2)
                correction = -.5f;
            batch.draw(tex_ring, (int) pos.x - MAXRINGRADIUS + correction, (int) pos.y - MAXRINGRADIUS - 3 * radius / 9);
            batch.setShader(null);
        }
    }

    public void render(ShapeRenderer sr) {

    }

    public void drawShapes(ShapeRenderer sr) {

    }

    public void renderShadow(ShapeRenderer sr) {
        if (!isUnderGround && !fall) {
            sr.setColor(selectedIntensity, selectedIntensity, selectedIntensity, .8f + .2f * selectedIntensity);
            float smallFactor = 1 / (1 + height * .02f);
            float shadowWidth = sizeFactor * sprite.getWidth() + selectedIntensity * 4;
            float shadowHeight = (shadowWidth * Game.WIDTHTOHEIGHTRATIO);

            float shadowWidth_small = shadowWidth * smallFactor;
            float shadowHeight_small = shadowHeight * smallFactor;

            //sr.ellipse((int) ((int) pos.x - shadowWidth_small / 2), (int) ((int) pos.y - shadowHeight_small / 2 - radius - 1 + shadowHeight / 2), shadowWidth_small, shadowHeight_small);
            //if (radius % 1 == 0)
            //sr.ellipse((sprite.getX() + radius - shadowWidth_small / 2), (int) ((int) pos.y - Main.test_float * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small);


            //sr.ellipse((int) ((int) pos.x - sprite.getRegionWidth() / 2f) + sprite.getRegionWidth() / 2f - shadowWidth_small / 2, (int) ((int) ((int) pos.y - radius) + .6f * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small, 20);
            sr.ellipse((int) (pos.x - sprite.getRegionWidth() / 2f) + sprite.getRegionWidth() / 2f - shadowWidth_small / 2, (float) (Math.floor(pos.y) + Math.floor(-radius + .6f * radius - shadowHeight_small / 2)), shadowWidth_small, shadowHeight_small, 20);


            //else
            //    sr.ellipse((int)(pos.x - (shadowWidth_small / 2)), (int) ((int) pos.y - Main.test_float * radius - shadowHeight_small / 2), shadowWidth_small, shadowHeight_small);
            //sr.ellipse((int) (pos.x - sprite.getRegionWidth() / 2 * smallFactor), (int) (pos.y - sprite.getRegionWidth() / 2 * Game.WIDTHTOHEIGHTRATIO * smallFactor) - 2, sprite.getRegionWidth() / 2 * smallFactor * 2, radius * smallFactor * 2 * Game.WIDTHTOHEIGHTRATIO);
        }
    }

    public void render_shield_shine(SpriteBatch batch) {
        if (isShielded)
            batch.draw(Res.tex_shield_shine, (int) (pos.x - 2 - sprite.getRegionWidth() / 2), (int) (pos.y - 2 - sprite.getHeight() / 2 + height), sprite.getRegionWidth() + 4, sprite.getHeight() + 4);
    }

    public void onSelect() {

    }

    public boolean testHit() {
        if (isKinetic())
            if (Main.tap_speed > Game.HITSPEEDTHRESHOLD && Gdx.input.isTouched() && MathFuncs.distanceBetweenPoints(Main.tap[0], pos) <= Math.max(20, radius) && count_hitCooldown == 0) {
                hit(Main.tap_angle, Main.tap_speed * 3);
                return true;
            }
        return false;
    }

    public Ball setVelocity(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
        return this;
    }

    public void addVelocity(float vx, float vy) {
        body.setLinearVelocity(body.getLinearVelocity().x + vx, body.getLinearVelocity().y + vy);
    }


    public void hit(float angle, float speed) {
        if (Main.game.inFlow())
            speed *= 1.5f;

        if (isStuck) {
            body.setType(BodyDef.BodyType.DynamicBody);
            isStuck = false;
            count_cantGetStuck = 10;
        }

        body.setLinearVelocity((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
        maxSpeed = speed;
        count_hitCooldown = COUNTMAX_HITCOOLDOWN;
        Main.particles.add(new Particle_Hit(pos.x, pos.y, angle + (float) Math.PI, radius));
        Main.addSoundRequest(ID.Sound.HIT, 0, 2f * speed, 0.82f + (float) Math.random() * 0.2f - 0.1f);
        time_off = 0;
    }

    public void contact(Object ud, Vector2 p, float impact) {
        wiggle();

        if (body.getLinearVelocity().len() > 5 && freezeCooldown == 0) {
            doFreeze = true;
            freezeCooldown = FREEZECOOLDOWNMAX;
        }
    }

    public void onCollision(Vector2 p, float impact) {

    }

    public void onCollision(Vector2 p, float impact, Object object_hit) {
        onCollision(p, impact);
    }

    public void doCollisionEffect(Vector2 p, float impact, Object object_hit) { // when its two balls will only execute on one of both
        dropPulseParticle(p.x, p.y + height, 1.5f * impact);
        Main.addSoundRequest(ID.Sound.HIT, 5, impact * .1f);
    }

    public void contactBall(Ball ball) {
        ball_hit = ball;
        //Main.addSoundRequest(ID.Sound.HIT);
    }

    public void contactBall_pre(Ball ball) {
    }

    static void dropPulseParticle(float x, float y, float size) {
        Main.particles.add(new Particle_Pulse(x, y, size));
    }

    public void wiggle() {
        if (!Main.noFX && Game.doWiggle && wiggle < 1) {
            wiggle = 1;
        }
    }

    public void wiggle(float intensity) {
        if (!Main.noFX && Game.doWiggle && wiggle < intensity) {
            wiggle = intensity;
        }
    }

    public void createBody() {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_ball_passThrough[size]);
        body.setUserData(this);
    }

    public void createBody(float radius) {
        body = Main.world.createBody(Res.bodyDef_dynamic);
        Res.fixtureDef_circle.shape.setRadius(radius * Main.MPP);
        body.createFixture(Res.fixtureDef_circle);
        body.setUserData(this);
    }

    public void destroy(float angle, float impact, Vector2 pos_danger) {

    }

    public void destroyShield() {
        if (isShielded && !isDisposed) {
            isShielded = false;
            body.destroyFixture(body.getFixtureList().get(1));
        }
    }

    public void explode(float angle, float impact) {
        Main.addSoundRequest(ID.Sound.GLASS);
        doDispose = true;
    }

    int getParticleAmount() {
        if (size == 0)
            return 3;
        else if (size == 1)
            return 5;
        else
            return 10;
    }

    public void applyForce(float angle, float speed) {
        body.applyForceToCenter((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed, true);
        maxSpeed = speed;
        dropPulseParticle(pos.x, pos.y + height, 7.5f);
        Main.addSoundRequest(ID.Sound.HIT, 5, 1, (float) Math.random() * .2f + .9f);
        count_recentlyHit = countMax_recentlyHit;
    }


    public void dispose() {
        System.out.println("destroy body: " + this); // body gets destroyed but also stays???
        Main.ballsToRemove.add(this);
        body = Main.destroyBody(body);
        isDisposed = true;
    }

    public void drawTrail(ShapeRenderer sr) {
        //if (height == 0) {
        float distance = MathFuncs.distanceBetweenPoints(pos, pos_last);
        float angle = MathFuncs.angleBetweenPoints(pos, pos_last);


        for (int i = 0; i < distance; i++) {
            //sr.circle(pos.x + (float) Math.cos(angle) * i * 1 + radius / 2 * (float) Math.cos(angle_random), pos.y + (float) Math.sin(angle) * i * 1 + radius / 2 * (float) Math.sin(angle_random), radius - (float) Math.random() * 3);
            //sr.circle(pos.x + (float) Math.cos(angle) * i * 1, pos.y + (float) Math.sin(angle) * i * 1 - 2, radius - (float) Math.random() * 3);
            sr.circle(pos.x + (float) Math.cos(angle) * i * 1, pos.y + (float) Math.sin(angle) * i * 1 - 2 + height, radius);
        }
        //}
        //float angle=(float)(Math.random()*Math.PI*2);
        //sr.circle(pos.x + radius/2*(float)Math.cos(angle), pos.y + radius/2*(float)Math.sin(angle), 5);
    }

    public void setSpriteTexture(TextureRegion texture) {
        sprite.setRegion(texture);
        sprite.setSize(texture.getRegionWidth(), texture.getRegionHeight());
        sprite.setPosition(pos.x - sprite.getRegionWidth() / 2, pos.y + height - sprite.getHeight() / 2);
    }

    public void freeze() {
        frozen = true;
        count_frozen = 1;
        body.setActive(false);
    }

    public void unfreeze() {
        frozen = false;
        body.setActive(true);
    }

    public void doActivateShield() {
        doActivateShield = true;
    }

    static final float PARTICLESPREAD = (float) Math.PI * .75f;

    public static void throwParticles(float angle, float impact, Vector2 pos, Color[] palette, int amount) {
        for (int i = 0; i < amount; i++) {
            Main.particlesToAdd.add(new Particle_Ball(pos.x, pos.y, angle + (float) Math.random() * PARTICLESPREAD - PARTICLESPREAD / 2, impact * (.5f + (float) Math.random()), palette));
        }
    }

    public static void throwParticles(float angle, float impact, Vector2 pos, Color[] palette) {
        throwParticles(angle, impact, pos, palette, 10);
    }

    public static void throwParticles(float angle, float impact, Vector2 pos, Color color, int amount) {
        for (int i = 0; i < amount; i++) {
            Main.particlesToAdd.add(new Particle_Ball(pos.x, pos.y, angle + (float) Math.random() * PARTICLESPREAD - PARTICLESPREAD / 2, impact * (.5f + (float) Math.random()), color));
        }
    }
}
