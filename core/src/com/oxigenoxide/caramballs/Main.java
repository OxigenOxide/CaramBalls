package com.oxigenoxide.caramballs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.oxigenoxide.caramballs.object.BallSelector;
import com.oxigenoxide.caramballs.object.DragSelector;
import com.oxigenoxide.caramballs.object.Projection;
import com.oxigenoxide.caramballs.object.entity.BallCapsule;
import com.oxigenoxide.caramballs.object.entity.Bullet;
import com.oxigenoxide.caramballs.object.Bumper;
import com.oxigenoxide.caramballs.object.entity.Cannon;
import com.oxigenoxide.caramballs.object.entity.Cat;
import com.oxigenoxide.caramballs.object.entity.CircularBumper;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.Eye;
import com.oxigenoxide.caramballs.object.entity.JumpingPad;
import com.oxigenoxide.caramballs.object.entity.draggable.Draggable;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OC_Egg;
import com.oxigenoxide.caramballs.object.entity.FloorButton;
import com.oxigenoxide.caramballs.object.entity.Honey;
import com.oxigenoxide.caramballs.object.Orb;
import com.oxigenoxide.caramballs.object.Pin;
import com.oxigenoxide.caramballs.object.SoundRequest;
import com.oxigenoxide.caramballs.object.entity.Spike;
import com.oxigenoxide.caramballs.object.Tracer;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.collectable.Collectable;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OrbContainer;
import com.oxigenoxide.caramballs.object.entity.particle.Particle;
import com.oxigenoxide.caramballs.object.floatingReward.FloatingReward;
import com.oxigenoxide.caramballs.scene.Farm;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.scene.GameOver;
import com.oxigenoxide.caramballs.scene.Menu;
import com.oxigenoxide.caramballs.scene.Scene;
import com.oxigenoxide.caramballs.scene.Shop;
import com.oxigenoxide.caramballs.scene.Splash;
import com.oxigenoxide.caramballs.scene.Welcome;
import com.oxigenoxide.caramballs.utils.DataManager;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.GameData;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main extends ApplicationAdapter {
    static SpriteBatch batch;
    static Game game;
    static Scene menu;
    static Farm farm;
    static Scene welcome;
    static Scene splash;
    static GameOver gameOver;
    static Scene currentScene;
    static Scene overlayScene;
    static Scene nextScene_delayed;
    static Scene previousScene;
    static ArrayList<Scene> sceneStack = new ArrayList<Scene>();
    public static Scene nextScene;
    public static Box2DDebugRenderer b2dr;
    public static OrthographicCamera cam;
    public static ShapeRenderer sr;
    public static float width, height;
    public static Vector2[] tap;
    public static Vector2[] tap_previous;
    public static Vector2[] tap_delta;
    public static Vector2[] tap_begin;
    public static Vector2 pos_middle;
    public static float[] tap_distance;
    public static float tap_speed;
    public static float tap_angle;
    public static float[] speedDistanceLastFrames;
    public static float[] speedTimeLastFrames;
    public static float[] directionLastFrames;
    public static final int TAPSPEEDPRECISION = 10;
    public static final int TAPANGLEPRECISION = 5;
    public static Vector2 pos_cam;
    public static ArrayList<SoundRequest> soundRequests;
    public static SoundRequest[] soundRequestsToPlay;
    public static float dt_one;
    public static float dt;
    public static final float PIXELSPERMETER = 40;
    //public static final float PIXELSPERMETER = 1;
    public static final float METERSPERPIXEL = 1 / PIXELSPERMETER;

    public static ArrayList<Ball> balls;
    public static ArrayList<Ball_Main> mainBalls;
    public static ArrayList<Ball> ballsToAdd;
    public static ArrayList<Ball> ballsToRemove;
    public static ArrayList<Integer> rewardBalls = new ArrayList<Integer>();
    public static ArrayList<Pin> pins;
    public static ArrayList<Pin> pinsToRemove;
    public static ArrayList<Particle> particles;
    public static ArrayList<Particle> particlesToRemove;
    public static ArrayList<Particle> particlesToAdd;
    public static ArrayList<Particle> particles_batch;
    public static ArrayList<Particle> particles_sr;
    public static ArrayList<Tracer> tracers;
    public static ArrayList<Tracer> tracersToRemove;
    public static ArrayList<Bumper> bumpers;
    public static ArrayList<Hole> holes;
    public static ArrayList<Hole> holesToRemove;
    public static ArrayList<FloorButton> floorButtons;
    public static ArrayList<FloorButton> floorButtonsToRemove;
    public static ArrayList<Spike> spikes;
    public static ArrayList<Spike> spikesToRemove;
    public static ArrayList<OC_Egg> eggs;
    public static ArrayList<OC_Egg> eggsToRemove;
    public static ArrayList<Draggable> draggables = new ArrayList<Draggable>();
    public static ArrayList<Draggable> draggablesToRemove = new ArrayList<Draggable>();
    public static ArrayList<Orb> orbs;
    public static ArrayList<Orb> orbsToRemove;
    public static ArrayList<Cat> cats;
    public static ArrayList<Cat> catsToRemove;
    public static ArrayList<Cannon> cannons;
    public static ArrayList<Cannon> cannonsToRemove;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Bullet> bulletsToRemove;
    public static ArrayList<Honey> honey;
    public static ArrayList<Honey> honeyToRemove;
    public static ArrayList<Collectable> collectables;
    public static ArrayList<Collectable> collectablesToRemove;
    public static ArrayList<OrbContainer> orbContainers;
    public static ArrayList<OrbContainer> orbContainersToRemove;
    public static ArrayList<CircularBumper> circularBumpers;
    public static ArrayList<CircularBumper> circularBumpersToRemove;
    public static ArrayList<Entity> entities = new ArrayList<Entity>();
    public static ArrayList<JumpingPad> jumpingPads = new ArrayList<JumpingPad>();
    public static ArrayList<JumpingPad> jumpingPadsToRemove = new ArrayList<JumpingPad>();
    public static ArrayList<BallCapsule> ballCapsules = new ArrayList<BallCapsule>();
    public static ArrayList<BallCapsule> ballCapsulesToRemove = new ArrayList<BallCapsule>();
    public static ArrayList<FloatingReward> floatingRewards = new ArrayList<FloatingReward>();
    public static ArrayList<FloatingReward> floatingRewardsToRemove = new ArrayList<FloatingReward>();
    public static ArrayList<Eye> eyes = new ArrayList<Eye>();
    public static ArrayList<Eye> eyesToRemove = new ArrayList<Eye>();
    public static ArrayList<Projection> projections = new ArrayList<Projection>();
    public static ArrayList<Projection> projectionsToRemove = new ArrayList<Projection>();


    public static BallSelector ballSelector;
    public static DragSelector dragSelector;
    public static float pointsPerPixel;
    public static float pixelsPerPoint;
    static Music currentMusic;
    static Music lastMusic;
    public static AssetManager assets;
    public static FirebaseInterface fbm;
    public static GameInterface gm;
    public static AdMobInterface amm;
    public static boolean resourcesLoaded;
    public static boolean signedIn;
    public static boolean isMusicMuted;
    public static boolean isSoundMuted;
    static boolean inGame;
    public static int testerID = 0;
    public static Shop shop;
    public static boolean isLoaded;
    public static Body border;
    public static final int adHeight = 17;

    public static boolean noAds = false; // DONE
    public static boolean noFX = false; // DONE
    public static boolean noMusic = true; // DONE
    public static boolean noCollection = false; // DONE
    public static boolean noLevels = false; // DONE
    public static boolean noFlow = false; // DONE
    public static boolean noScore = false; // DONE

    public static boolean doFade;
    public static boolean inScreenShotMode;

    public static float shakeAng;
    public static float fadeDir;
    static float fade;

    public static float dt_slowed;
    public static float dt_one_slowed;

    public static float slowdown = 0;

    public static Vector2 dim_screen;

    public static Res res;

    static float shakeIntensity;

    public static UserData userData;

    public static GameData gameData;

    public static World world;
    public static WorldProperties worldProperties;

    public static float test_float = 1;
    public static boolean isButtonPressed;

    long startTime;

 /*
    Every time you release check this:
        - DEBUGTOOLS in Main should be false
        - RESEARCHMODE should be false
        - DOSCREENSHOTMODE should be false
*/

    // GET RELEASE KEY: keytool -list -v -keystore ‪C:\Keys\googlekeys.jks -alias key_ball (Release key is pretty useless)

    // CREATE RUNNABLE JAR: gradlew desktop:dist

    // CONFIG //
    boolean doReportFPS = false;
    public static final int SOUNDCAP = 4;
    public static final boolean RESEARCHMODE = false;
    public static final boolean DOSCREENSHOTMODE = false;
    // CONFIG //

    public Main(FirebaseInterface fbm, AdMobInterface amm, GameInterface gm) {
        super();
        resetStatics();
        this.fbm = fbm;
        this.amm = amm;
        this.gm = gm;
    }

    void resetStatics() {
        isLoaded = false;
        resourcesLoaded = false;
        userData = null;
        signedIn = false;
    }

    long rank;

    @Override
    public void create() {
        fbm.signIn();
        assets = new AssetManager();
        Res.preload();
        width = 108;
        height = Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * 108;
        pos_middle = new Vector2(width / 2, height / 2);
        soundRequests = new ArrayList<SoundRequest>();
        soundRequestsToPlay = new SoundRequest[SOUNDCAP];

        pointsPerPixel = Gdx.graphics.getWidth() / width;
        pixelsPerPoint = 1 / pointsPerPixel;

        batch = new SpriteBatch();

        cam = new OrthographicCamera(width, height);
        cam.position.set(width / 2, height / 2, 0);
        b2dr = new Box2DDebugRenderer();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);

        //game = new Game();
        menu = new Menu();
        splash = new Splash();

        dim_screen = new Vector2(Main.width, Main.height);
        tap = new Vector2[]{new Vector2(), new Vector2()};
        tap_previous = new Vector2[]{new Vector2(), new Vector2()};
        tap_delta = new Vector2[]{new Vector2(), new Vector2()};
        tap_begin = new Vector2[]{new Vector2(), new Vector2()};
        tap_distance = new float[2];

        pos_cam = new Vector2();
        pos_cam.set(width / 2, height / 2);

        speedDistanceLastFrames = new float[TAPSPEEDPRECISION];
        speedTimeLastFrames = new float[TAPSPEEDPRECISION];
        directionLastFrames = new float[TAPANGLEPRECISION];

        ShaderProgram.pedantic = false;

        setScene(splash);

        DataManager.getInstance().initializeGameData();
        gameData = DataManager.getInstance().gameData;

        isMusicMuted=gameData.isMusicMuted;
        isSoundMuted=gameData.isSoundMuted;

        worldProperties = new WorldProperties();

        startTime = System.currentTimeMillis();

        testerID = gameData.testerID;

        rank = gm.getRank();

        ballSelector = new BallSelector();
        dragSelector = new DragSelector();



        balls = new ArrayList<Ball>();
        ballsToAdd = new ArrayList<Ball>();
        ballsToRemove = new ArrayList<Ball>();
        bumpers = new ArrayList<Bumper>();
        particles = new ArrayList<Particle>();
        particles_batch = new ArrayList<Particle>();
        particles_sr = new ArrayList<Particle>();
        particlesToRemove = new ArrayList<Particle>();
        particlesToAdd = new ArrayList<Particle>();
        mainBalls = new ArrayList<Ball_Main>();
        pins = new ArrayList<Pin>();
        pinsToRemove = new ArrayList<Pin>();
        tracers = new ArrayList<Tracer>();
        eggsToRemove = new ArrayList<OC_Egg>();
        eggs = new ArrayList<OC_Egg>();
        orbs = new ArrayList<Orb>();
        orbsToRemove = new ArrayList<Orb>();
        spikes = new ArrayList<Spike>();
        cats = new ArrayList<Cat>();
        catsToRemove = new ArrayList<Cat>();
        spikesToRemove = new ArrayList<Spike>();
        tracersToRemove = new ArrayList<Tracer>();
        holes = new ArrayList<Hole>();
        holesToRemove = new ArrayList<Hole>();
        floorButtons = new ArrayList<FloorButton>();
        floorButtonsToRemove = new ArrayList<FloorButton>();
        cannons = new ArrayList<Cannon>();
        cannonsToRemove = new ArrayList<Cannon>();
        honey = new ArrayList<Honey>();
        honeyToRemove = new ArrayList<Honey>();
        bullets = new ArrayList<Bullet>();
        bulletsToRemove = new ArrayList<Bullet>();
        collectablesToRemove = new ArrayList<Collectable>();
        collectables = new ArrayList<Collectable>();
        orbContainers = new ArrayList<OrbContainer>();
        orbContainersToRemove = new ArrayList<OrbContainer>();
        circularBumpers = new ArrayList<CircularBumper>();
        circularBumpersToRemove = new ArrayList<CircularBumper>();

        createInputProcessor();
    }

    public static void setScreenShotMode() {
        game.setDoWiggle(false);
        inScreenShotMode = true;
    }

    public void printTime() {
        System.out.println("STARTTIME: " + startTime + " CURRENTTIME: " + System.currentTimeMillis() + " TIMEBETWEEN: " + ((System.currentTimeMillis() - startTime) / 1000));
    }

    public void update() {
        dt = Gdx.graphics.getDeltaTime();
        dt_one = dt * 60;

        dt_slowed = Main.dt * (1 - slowdown);
        dt_one_slowed = dt_slowed * 60;

        if (doReportFPS)
            System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());

        for (int i = 0; i < tap.length; i++) {
            tap[i].set(((Gdx.input.getX(i) / 4f) * (width / (Gdx.graphics.getWidth() / 4f))),
                    ((-(Gdx.input.getY(i) / 4f - Gdx.graphics.getHeight() / 4f) * (height / (Gdx.graphics.getHeight() / 4f)))));

            tap_delta[i].set(tap[i].x - tap_previous[i].x, tap[i].y - tap_previous[i].y);
            tap_distance[i] = MathFuncs.getHypothenuse(tap_delta[i].x, tap_delta[i].y);
        }
        tap_angle = MathFuncs.angleBetweenPoints(tap_previous[0], tap[0]);

        if (Gdx.input.justTouched()) // questionable since it does not test for every pointer
            for (int i = 0; i < tap.length; i++)
                tap_begin[i].set(tap[i]);

        for (int i = 0; i < tap.length; i++) {
            tap_previous[i].set(tap[i]);
        }

        for (int i = 0; i < TAPSPEEDPRECISION - 1; i++) {
            speedDistanceLastFrames[i] = speedDistanceLastFrames[i + 1];
            speedTimeLastFrames[i] = speedTimeLastFrames[i + 1];
        }
        for (int i = 0; i < TAPANGLEPRECISION - 1; i++) {
            directionLastFrames[i] = directionLastFrames[i + 1];
        }

        speedDistanceLastFrames[TAPSPEEDPRECISION - 1] = tap_distance[0];
        speedTimeLastFrames[TAPSPEEDPRECISION - 1] = dt_one;
        directionLastFrames[TAPANGLEPRECISION - 1] = tap_delta[0].angleRad();

        tap_speed = MathFuncs.getSum(speedDistanceLastFrames) / MathFuncs.getSum(speedTimeLastFrames);

        isButtonPressed = false;

        currentScene.update();
        if (overlayScene != null)
            overlayScene.update();

        if (!signedIn && fbm.isSignedIn() && fbm.isSnapshotLoaded()) {
            signedIn = true;
            onSignIn();
        }

        if (signedIn && !fbm.isSignedIn()) {
            signedIn = false;
        }

        shakeIntensity = Math.max(0, shakeIntensity - .1f);

        if (doFade) {
            fade += fadeDir * .03f;
            //fade += fadeDir * .0005f;
            if (fade >= 2.2f) {
                fadeDir = -1;
                onFadePeak();
            }
            if (fade <= 0)
                doFade = false;
        }

        // objects
        balls.addAll(ballsToAdd);
        ballsToAdd.clear();
        for (Ball ball : balls)
            ball.update();
        balls.removeAll(ballsToRemove);
        ballsToRemove.clear();

        for (Orb orb : orbs) {
            orb.update();
        }
        orbs.removeAll(orbsToRemove);
        orbsToRemove.clear();

        for (Bumper bumper : bumpers)
            bumper.update();

        for (OC_Egg egg : eggs)
            egg.update();
        eggs.removeAll(eggsToRemove);
        eggsToRemove.clear();

        for (OrbContainer oc : orbContainers)
            oc.update();
        orbContainers.removeAll(orbContainersToRemove);
        orbContainersToRemove.clear();

        particles.addAll(particlesToAdd);
        particlesToAdd.clear();
        for (Particle particle : particles)
            particle.update();
        particles.removeAll(particlesToRemove);
        particlesToRemove.clear();

        for (Hole hole : holes)
            hole.update();
        holes.removeAll(holesToRemove);
        holesToRemove.clear();

        for (Tracer tracer : tracers)
            tracer.update();
        tracers.removeAll(tracersToRemove);
        tracersToRemove.clear();

        for (Cat cat : cats)
            cat.update();
        cats.removeAll(catsToRemove);
        catsToRemove.clear();

        for (Bullet b : bullets)
            b.update();
        bullets.removeAll(bulletsToRemove);
        bulletsToRemove.clear();

        for (FloorButton fb : floorButtons)
            fb.update();
        floorButtons.removeAll(floorButtonsToRemove);
        floorButtonsToRemove.clear();

        for (Honey h : honey)
            h.update();
        honey.removeAll(honeyToRemove);
        honeyToRemove.clear();

        for (Collectable c : collectables)
            c.update();
        collectables.removeAll(collectablesToRemove);
        collectablesToRemove.clear();

        for (Spike s : spikes)
            s.update();
        spikes.removeAll(spikesToRemove);
        spikesToRemove.clear();

        for (Pin p : pins)
            p.update();
        pins.removeAll(pinsToRemove);
        pinsToRemove.clear();

        for (Cannon c : cannons)
            c.update();
        cannons.removeAll(cannonsToRemove);
        cannonsToRemove.clear();

        for (CircularBumper c : circularBumpers)
            c.update();
        circularBumpers.removeAll(circularBumpersToRemove);
        circularBumpersToRemove.clear();

        for (JumpingPad jp : jumpingPads)
            jp.update();
        jumpingPads.removeAll(jumpingPadsToRemove);
        jumpingPadsToRemove.clear();

        for (BallCapsule bc : ballCapsules)
            bc.update();
        ballCapsules.removeAll(ballCapsulesToRemove);
        ballCapsulesToRemove.clear();

        for (FloatingReward fr : floatingRewards)
            fr.update();
        floatingRewards.removeAll(floatingRewardsToRemove);
        floatingRewardsToRemove.clear();

        for (Eye eye : eyes)
            eye.update();
        eyes.removeAll(eyesToRemove);
        eyesToRemove.clear();

        for (Projection p : projections)
            p.update();
        projections.removeAll(projectionsToRemove);
        projectionsToRemove.clear();

        for (Draggable d : draggables)
            d.update();
        draggables.removeAll(draggablesToRemove);
        draggablesToRemove.clear();

        // detect a selection of a ball or a draggable
        if (Funcs.justTouched() && (!Game.isGameOver && !Game.isPaused || Main.inFarm()) && !Main.isButtonPressed) {
            float distance;
            float closestDistance_ball = 40;
            float closestDistance_draggable = 40;
            Ball closestBall = null;
            for (Ball ball : Main.balls) {
                if (!ball.isLocked && ball.isKinetic() && ball.height <= 20) {
                    distance = MathFuncs.distanceBetweenPoints(ball.pos, tap[0]);
                    if (distance < closestDistance_ball) {
                        closestDistance_ball = distance;
                        closestBall = ball;
                    }
                }
            }
            Draggable closestDraggable = null;
            for (Draggable d : Main.draggables) {
                distance = MathFuncs.distanceBetweenPoints(d.pos_pivot_visual, tap[0]);
                if (distance < closestDistance_draggable) {
                    closestDistance_draggable = distance;
                    closestDraggable = d;
                }
            }

            if (closestDistance_draggable < closestDistance_ball) {
                if (closestDraggable != null)
                    dragSelector.setSelected(closestDraggable);
            } else {
                if (closestBall != null) {
                    if (closestBall.canBeHit)
                        ballSelector.setSelected(closestBall);
                    else if (closestDistance_ball < closestBall.radius + 10)
                        closestBall.onFailedSelect();
                }
            }
        }

        ballSelector.update();
        dragSelector.update();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET))
            test_float += .01f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET))
            test_float -= .01f;
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSLASH))
            System.out.println("TEST_FLOAT: " + test_float);


        if (!isSoundMuted) {
            int index = 0;
            for (SoundRequest sr : soundRequests) { // add as many soundrequests to soundRequestsToPlay as allowed
                soundRequestsToPlay[index] = sr;
                index++;
                if (index >= SOUNDCAP)
                    break;
            }

            if (soundRequests.size() > SOUNDCAP) { // sort soundrequest by priority
                for (SoundRequest sr : soundRequests) {
                    for (int i = 0; i < SOUNDCAP; i++) {
                        if (soundRequestsToPlay[i] != null && sr.priority > soundRequestsToPlay[i].priority) {
                            if (i + 1 < SOUNDCAP)
                                soundRequestsToPlay[i + 1] = soundRequestsToPlay[i];
                            soundRequestsToPlay[i] = sr;
                        }
                    }
                }
            }
            for (SoundRequest sr : soundRequestsToPlay) { // play soundRequestsToPlay
                if (sr != null)
                    playSound(sr.soundID, sr.volume, sr.pitch);
            }
            for (int i = 0; i < SOUNDCAP; i++) {
                soundRequestsToPlay[i] = null;
            }
        }
        soundRequests.clear(); // also clear when sound is muted

        if (nextScene_delayed != null && !doFade) {
            startFade(nextScene_delayed);
            nextScene_delayed = null;
        }

        // Music
        if (currentMusic != null)
            currentMusic.setVolume(Math.min(1, currentMusic.getVolume() + .01f));
        if (lastMusic != null) {
            lastMusic.setVolume(Math.max(0, lastMusic.getVolume() - .01f));
            if (lastMusic.getVolume() == 0) {
                lastMusic.stop();
                lastMusic = null;
            }
        }
    }

    public static void addSoundRequest(int soundID, int priority, float volume, float pitch) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID, priority, volume, pitch));
    }

    public static void addSoundRequest(int soundID, int priority, float volume) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID, priority, volume));
    }

    public static void addSoundRequest(int soundID, int priority) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID, priority));
    }

    public static void addSoundRequest(int soundID) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID));
    }

    public static void setMusic(Music music) {
        if (!isMusicMuted) {
            if (lastMusic != null)
                lastMusic.stop();
            lastMusic = currentMusic;
            currentMusic = music;
            currentMusic.setVolume(0);
            currentMusic.setLooping(true);
            currentMusic.play();
        }
    }

    public static void setNoMusic() {
        if (lastMusic != null)
            lastMusic.stop();
        lastMusic = currentMusic;
        currentMusic = null;
    }

    public static void shake() {
        if (!noFX && !inScreenShotMode)
            if (shakeIntensity < 3)
                shakeIntensity = 3;
    }

    public static void shake(float impact) {
        if (!noFX && !inScreenShotMode)
            if (impact > shakeIntensity && impact > 2)
                shakeIntensity = impact;
    }

    public static void shakeSmall() {
        if (!noFX)
            if (shakeIntensity < 2)
                shakeIntensity = 2;
    }

    public static void setShakeAng(float ang) {
        //shakeAng=(float)Math.PI*.5f;
        //shakeAng=(float)Math.PI*.5f;
        //shakeAng=(float)Math.toDegrees(ang);

        shakeAng = ang;
    }

    public static ArrayList<Entity> entities_sorted = new ArrayList<Entity>();

    public static void clearEntities() {
        for (int i = 0; i < ID.Entity.AMOUNT; i++) {
            ArrayList<Entity> entities = getEntityArrayList(i);
            for (Entity entity : entities) {
                entity.dispose();
            }
            entities.clear();
        }

    }

    public static ArrayList<Entity> getEntityArrayList(int id) {
        Object arrayList = null;
        switch (id) {
            case ID.Entity.BALL:
                arrayList = balls;
                break;
            case ID.Entity.COLLECTABLE:
                arrayList = collectables;
                break;
            case ID.Entity.HOLE:
                arrayList = holes;
                break;
            case ID.Entity.ORBCONTAINER:
                arrayList = orbContainers;
                break;
            case ID.Entity.PARTICLE:
                arrayList = particles;
                break;
            case ID.Entity.BALLCAPSULE:
                arrayList = ballCapsules;
                break;
            case ID.Entity.BULLET:
                arrayList = bullets;
                break;
            case ID.Entity.CANNON:
                arrayList = cannons;
                break;
            case ID.Entity.CAT:
                arrayList = cats;
                break;
            case ID.Entity.CIRCULARBUMPER:
                arrayList = circularBumpers;
                break;
            case ID.Entity.EYE:
                arrayList = eyes;
                break;
            case ID.Entity.FLOORBUTTON:
                arrayList = floorButtons;
                break;
            case ID.Entity.HONEY:
                arrayList = honey;
                break;
            case ID.Entity.JUMPINGPAD:
                arrayList = jumpingPads;
                break;
            case ID.Entity.SPIKE:
                arrayList = spikes;
                break;
            case ID.Entity.DRAGGABLE:
                arrayList = draggables;
                break;
        }
        return (ArrayList<Entity>) arrayList; // going against the laws of java
    }

    @Override
    public void render() {
        update();

        entities.clear();
        entities_sorted.clear();

        entities.addAll(balls);
        entities.addAll(collectables);
        entities.addAll(orbContainers);
        entities.addAll(bullets);
        entities.addAll(cannons);
        entities.addAll(cats);
        entities.addAll(circularBumpers);
        entities.addAll(floorButtons);
        entities.addAll(honey);
        entities.addAll(spikes);
        entities.addAll(jumpingPads);
        entities.addAll(ballCapsules);
        entities.addAll(eyes);
        entities.addAll(draggables);
        entities.addAll(particles_batch);

        if (entities.size() > 0) {
            entities_sorted.add(entities.get(0));
            for (Entity e : entities) { // lowest y co-ord gets lowest index
                if (entities.indexOf(e) == 0)
                    continue;
                int mid, l = 0, r = entities_sorted.size() - 1;
                while (l != r) {
                    mid = (l + r) / 2;
                    if (entities_sorted.get(mid).pos.y < e.pos.y)
                        r = mid;
                    else
                        l = mid + 1;
                }
                mid = l; // not 1, but l
                if (entities_sorted.get(mid).pos.y < e.pos.y)
                    entities_sorted.add(mid, e);
                else
                    entities_sorted.add(mid + 1, e);
            }
        }

        setCamEffects();
        currentScene.render(batch, sr);
        if (overlayScene != null)
            overlayScene.render(batch, sr);
        setNoCamEffects();
        if (resourcesLoaded) {
            batch.begin();
            if (fade > 0) {
                batch.setShader(Res.shader_fade);
                Res.shader_fade.setUniformf("fade", (float) Math.min(fade, 2f));
                Res.shader_fade.setUniformf("screenSize", width, height);
                Res.shader_fade.setUniformf("dir", fadeDir);
                batch.draw(Res.tex_fade, 0, 0);
                batch.setShader(null);
            }
            batch.end();
        }


    }

    public static void setCamEffects() {
        //setCamZoom();
        setCamShake();
    }

    public static void setNoCamEffects() {
        setCamNoZoom();
        setCamNormal();
    }

    public static void setCamZoom() {
        cam.zoom = .5f;
    }

    public static void setCamNoZoom() {
        cam.zoom = 1;
    }

    public static void setCamShake() {
        cam.position.set(pos_cam.x + (int) (Math.cos(shakeAng) * shakeIntensity * Math.sin(shakeIntensity * 10)), pos_cam.y + (int) (Math.sin(shakeAng) * shakeIntensity * Math.sin(shakeIntensity * 10)), 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
    }

    public static void setCamNormal() {
        cam.position.set(pos_cam.x, pos_cam.y, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
    }

    public static int drawNumberSign(SpriteBatch batch, int number, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width += tex_sign.getWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
        return textWidth;
    }

    public static int drawNumberSignAfter(SpriteBatch batch, int number, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width += tex_sign.getWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;

        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }

        iWidth += 2;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);

        return textWidth;
    }

    public static ArrayList<Integer> getDigits(int number) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        return digits;
    }

    public static int getTextWidth(ArrayList<Integer> digits, int font) {
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width--;
        return width;
    }

    public static void drawNumberSign(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int iWidth = 0;
        batch.draw(tex_sign, pos.x + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font) {
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, int number, Vector2 pos, int font) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width--;
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    public static void drawShadow(ShapeRenderer sr, Vector2 pos, float shadowWidth) {
        sr.ellipse((int) ((int) pos.x - shadowWidth / 2), (int) ((int) pos.y - shadowWidth * Game.WIDTHTOHEIGHTRATIO) - 1, shadowWidth, shadowWidth * Game.WIDTHTOHEIGHTRATIO);
    }

    @Override
    public void dispose() {

        batch.dispose();
        if (isLoaded) {
            game.dispose();
            menu.dispose();
            shop.dispose();
            splash.dispose();
            welcome.dispose();
            assets.dispose();
            DataManager.getInstance().saveData();
        }
    }

    public void onPause() { // refering to the android 'pause' when leaving the app
        if (signedIn) {
            System.out.println("ONPAUSE");
            userData.timePlayed += (System.currentTimeMillis() - startTime) / 1000;
            startTime = System.currentTimeMillis();
            userData.adsClicked += amm.getAdsClicked();
            userData.orbs = Main.gameData.orbs;

            Main.gameData.userData = userData;
            Shop.saveData();

            userData.ballsUnlocked.clear();
            userData.highscore = gameData.highscore;
            for (boolean b : Main.gameData.unlocks) {
                userData.ballsUnlocked.add(b);
            }
            fbm.setUserData(userData);
            fbm.leave();

            DataManager.getInstance().saveData();
        }
    }

    public void onResume() {
        System.out.println("ONRESUME");
        startTime = System.currentTimeMillis();
    }

    @Override
    public void pause() {
        super.pause();
        //Gdx.app.exit();
    }

    static void onSignIn() {
        fbm.getUID();
        fbm.onSignIn();
        testerID = fbm.getTesterID();
        if (RESEARCHMODE)
            setVersion(testerID);
        gameData.testerID = testerID;
        userData = fbm.getUserData();

        if (userData == null)
            userData = new UserData();

        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date);
        boolean isNewDate = true;
        for (String s : userData.daysPlayed) {
            if (s.equals(formattedDate)) {
                isNewDate = false;
                break;
            }
        }
        if (isNewDate)
            userData.daysPlayed.add(formattedDate);
    }

    public static void onLoaded() {
        createWorld();
        isLoaded = true;
        game = new Game();
        if (DOSCREENSHOTMODE)
            setScreenShotMode();
        menu = new Menu();
        farm = new Farm();
        welcome = new Welcome();
        shop = new Shop();
    }

    public static void muteMusic() {
        setNoMusic();
        isMusicMuted = true;
        gameData.isMusicMuted=isMusicMuted;
    }

    public static void unmuteMusic() {
        isMusicMuted = false;
        Game.setMusicCurrentLevel();
        gameData.isMusicMuted=isMusicMuted;
    }

    public static void muteSound() {
        isSoundMuted = true;
        gameData.isSoundMuted=isMusicMuted;
    }

    public static void unmuteSound() {
        isSoundMuted = false;
        gameData.isSoundMuted=isMusicMuted;
    }


    public static void setVersion(int id) {

        noAds = false;
        noFlow = false;
        noFX = false;
        noScore = false;
        noMusic = false;
        noLevels = false;
        noCollection = false;

        int version = id % 10;
        switch (version) {
            case 0:
                noAds = true;
                break;
            case 1:
                noFlow = true;
                break;
            case 2:
                noFX = true;
                break;
            case 3:
                noScore = true;
                break;
            case 4:
                noMusic = true;
                break;
            case 5:
                noLevels = true;
                break;
            case 6:
                noCollection = true;
                break;
            default:
        }
    }

    public static void startFade(Scene nextScene) {
        if (!doFade) {
            Main.nextScene = nextScene;
            startFade();
        } else if (Main.nextScene != nextScene) {
            nextScene_delayed = nextScene;
        }
    }

    public static void startFade() {
        if (!doFade) {
            doFade = true;
            fadeDir = 1;
        }
    }

    public static void onFadePeak() {
        if (nextScene != null) {
            setScene(nextScene);
            System.out.println("SETSCENE: " + nextScene.getClass());
            nextScene = null;
            return;
        }
        Game.onFadePeak();
    }

    public static void setPalette(Color[] colors) {
        Res.shader_palette.setUniformf("color0", colors[0]);
        Res.shader_palette.setUniformf("color1", colors[1]);
        Res.shader_palette.setUniformf("color2", colors[2]);
        Res.shader_palette.setUniformf("color3", colors[3]);
    }

    public static void setFloorFade(Color[] colors, Vector2 center, float radius) {
        Res.shader_floorFade.setUniformf("color0", colors[0]);
        Res.shader_floorFade.setUniformf("color1", colors[1]);
        Res.shader_floorFade.setUniformf("color2", colors[2]);
        Res.shader_floorFade.setUniformf("color3", colors[3]);
        Res.shader_floorFade.setUniformf("center", center.x, Main.height - center.y);
        Res.shader_floorFade.setUniformf("radius", radius);
        Res.shader_floorFade.setUniformf("screenSize", Main.width, Main.height);
    }

    public static void setOverlayGameOver() {
        overlayScene = gameOver;
    }

    public static void setOverlayNull() {
        overlayScene = null;
    }

    public static void setSceneGame() {

        //gameOver = new GameOver();
        startFade(game);
        inGame = true;
    }


    public static void setSceneMenu() {
        startFade(menu);
    }

    public static void setScenePrevious() {
        Scene previousScene = sceneStack.get(sceneStack.size() - 2);
        startFade(previousScene);
        sceneStack.remove(sceneStack.size() - 1);
        sceneStack.remove(sceneStack.size() - 1); // Yes, twice

    }

    public static void setSceneMenuNow() {
        setScene(menu);
    }

    public static void setSceneWelcome() {
        startFade(welcome);
    }

    public static void setSceneFarm() {
        startFade(farm);
    }

    public static void setSceneShop() {
        startFade(shop);
    }

    static void setScene(Scene scene) {
        sceneStack.add(scene);
        previousScene = currentScene;
        if (currentScene != null)
            currentScene.hide();
        currentScene = scene;
        scene.show();
    }

    public static void setAdVisibility(boolean visibility) {
        if (!noAds) {
            if (visibility) {
                amm.show();
            } else
                amm.hide();
        }
    }

    static void createWorld() {
        world = new World(new Vector2(0, 0), true);
        border = world.createBody(Res.bodyDef_static);
        border.createFixture(Res.fixtureDef_border);
    }

    public static Body destroyBody(Body body) {
        if (body != null && body.isActive()) {
            int max = body.getFixtureList().size;
            for (int i = 0; i < max; i++) {
                body.destroyFixture(body.getFixtureList().get(0));
            }
            world.destroyBody(body);
        }
        return null;
    }

    public static void initializeResources() {
        res = new Res();
        resourcesLoaded = true;
        res.sprite_watermark.setPosition(width - res.sprite_watermark.getWidth() - 2, 2);
    }

    public class WorldProperties {
        public float friction; //pixels per second per second
        public boolean hasFloor;

        public WorldProperties() {
            setStandard();
        }

        public void setStandard() {
            friction = .05f;
        }

        public void setFriction(float friction) {
            this.friction = friction;
        }

        public void setHasFloor(boolean hasFloor) {
            this.hasFloor = hasFloor;
        }
    }

    public static boolean inGame() {
        return currentScene == game;
    }

    public static boolean inFarm() {
        return currentScene == farm;
    }


    static void playSound(int soundID, float volume, float pitch) {
        switch (soundID) {
            case ID.Sound.PLOP:
                playPlopSound();
                break;
            case ID.Sound.GLASS:
                playGlassBreakSound();
                break;
            case ID.Sound.SLOWDOWN:
                playSlowDownSound();
                break;
            case ID.Sound.SPEEDUP:
                playSpeedUpSound();
                break;
            case ID.Sound.HIT:
                Res.sound_ballHit.play(volume * .5f, pitch, 0);
                break;
            case ID.Sound.SPLAT:
                Res.sound_splat.play(volume, (float) Math.random() * .3f + .8f, 0);
                break;
            case ID.Sound.BOUNCE:
                Res.sound_bounce.play(volume, (float) Math.random() * .3f + .8f, 0);
                break;
            case ID.Sound.BUTTONCLICK_0:
                Res.sound_buttonClick1.play(volume);
                break;
            case ID.Sound.BUTTONCLICK_1:
                Res.sound_buttonClick2.play(volume);
                break;
            case ID.Sound.COLLECT:
                Res.sound_collect.play(volume,pitch,1);
                break;
        }
    }

    public static void playPlopSound() {
        Res.sound_plop.play(1, (float) Math.random() * .4f + .8f, 0);
    }

    public static void playGlassBreakSound() {
        Res.sound_glassBreak.play(1, (float) Math.random() * .4f + .9f, 0);
    }

    public static void playSlowDownSound() {
        Res.sound_slowdown.play(1, 1, 0);
    }

    public static void playSpeedUpSound() {
        Res.sound_speedup.play(1, 1, 0);
    }



    /*
    public static Vector2 intersectCircleLine(float m, float c, float r, float x, float y) {
        float a_ = (float) (Math.pow(m, 2) + 1);
        float b_ = 2 * (m * c - m * y - x);
        float c_ = (float) (Math.pow(y, 2) - Math.pow(r, 2) + Math.pow(x, 2) - 2 * c * y + Math.pow(c, 2));
        float D = (float) (Math.pow(b_, 2) - 4 * a_ * c_);
        float x_ = (-b_ - (float) Math.sqrt(D)) / (2 * a_);

        return new Vector2(x_, m * x_ + c);
    }
    */


    public Main setDevMode() {
        noMusic = true;
        return this;
    }

    public void createInputProcessor() {
        InputProcessor inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                //point.onRelease();
                if(!Gdx.input.isTouched(0)) {
                    ballSelector.onRelease();
                    dragSelector.onRelease();
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(inputProcessor);



    }
}
