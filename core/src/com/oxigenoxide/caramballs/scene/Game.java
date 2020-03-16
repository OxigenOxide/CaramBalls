package com.oxigenoxide.caramballs.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.oxigenoxide.caramballs.FirebaseInterface;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.B2DContactListener;
import com.oxigenoxide.caramballs.object.ComboBar;
import com.oxigenoxide.caramballs.object.Meter;
import com.oxigenoxide.caramballs.object.ProgressBar;
import com.oxigenoxide.caramballs.object.Projection;
import com.oxigenoxide.caramballs.object.RewardBall;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.button.Button_Exit;
import com.oxigenoxide.caramballs.object.button.Button_Music;
import com.oxigenoxide.caramballs.object.button.Button_Sound;
import com.oxigenoxide.caramballs.object.entity.BallCapsule;
import com.oxigenoxide.caramballs.object.Bumper;
import com.oxigenoxide.caramballs.object.entity.Cannon;
import com.oxigenoxide.caramballs.object.entity.CircularBumper;
import com.oxigenoxide.caramballs.object.Crown;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.Eye;
import com.oxigenoxide.caramballs.object.entity.JumpingPad;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Obstacle;
import com.oxigenoxide.caramballs.object.entity.scooper.Scooper;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Break;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Orb;
import com.oxigenoxide.caramballs.object.entity.draggable.Draggable;
import com.oxigenoxide.caramballs.object.entity.draggable.Plank;
import com.oxigenoxide.caramballs.object.entity.draggable.Tire;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OC_Egg;
import com.oxigenoxide.caramballs.object.entity.FloorButton;
import com.oxigenoxide.caramballs.object.Orb;
import com.oxigenoxide.caramballs.object.OrbCounter;
import com.oxigenoxide.caramballs.object.Pin;
import com.oxigenoxide.caramballs.object.entity.Spike;
import com.oxigenoxide.caramballs.object.TutorialSwipeExample;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Pause;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.Point;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Bad;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Ball;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Fall;
import com.oxigenoxide.caramballs.object.entity.orbContainer.OC_Fruit;
import com.oxigenoxide.caramballs.object.entity.particle.Particle;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Ball;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Confetti;
import com.oxigenoxide.caramballs.object.entity.scooper.Scooper_0;
import com.oxigenoxide.caramballs.object.entity.scooper.Scooper_1;
import com.oxigenoxide.caramballs.object.floatingReward.FR_Eye;
import com.oxigenoxide.caramballs.object.floatingReward.FloatingReward;
import com.oxigenoxide.caramballs.object.place.Place;
import com.oxigenoxide.caramballs.object.place.Place_Default;
import com.oxigenoxide.caramballs.object.place.Place_Space;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import java.util.ArrayList;

import static com.oxigenoxide.caramballs.Main.ballCapsules;
import static com.oxigenoxide.caramballs.Main.balls;
import static com.oxigenoxide.caramballs.Main.ballsToAdd;
import static com.oxigenoxide.caramballs.Main.ballsToRemove;
import static com.oxigenoxide.caramballs.Main.bumpers;
import static com.oxigenoxide.caramballs.Main.cannons;
import static com.oxigenoxide.caramballs.Main.circularBumpers;
import static com.oxigenoxide.caramballs.Main.draggables;
import static com.oxigenoxide.caramballs.Main.entities;
import static com.oxigenoxide.caramballs.Main.entities_sorted;
import static com.oxigenoxide.caramballs.Main.eyes;
import static com.oxigenoxide.caramballs.Main.floatingRewards;
import static com.oxigenoxide.caramballs.Main.floorButtons;
import static com.oxigenoxide.caramballs.Main.gameData;
import static com.oxigenoxide.caramballs.Main.holes;
import static com.oxigenoxide.caramballs.Main.jumpingPads;
import static com.oxigenoxide.caramballs.Main.mainBalls;
import static com.oxigenoxide.caramballs.Main.orbContainers;
import static com.oxigenoxide.caramballs.Main.orbs;
import static com.oxigenoxide.caramballs.Main.particles;
import static com.oxigenoxide.caramballs.Main.particles_sr;
import static com.oxigenoxide.caramballs.Main.pins;
import static com.oxigenoxide.caramballs.Main.projections;
import static com.oxigenoxide.caramballs.Main.rewardOrbs;
import static com.oxigenoxide.caramballs.Main.scoopers;
import static com.oxigenoxide.caramballs.Main.setCamEffects;
import static com.oxigenoxide.caramballs.Main.setCamNormal;
import static com.oxigenoxide.caramballs.Main.setCamShake;
import static com.oxigenoxide.caramballs.Main.setMusic;
import static com.oxigenoxide.caramballs.Main.setNoCamEffects;
import static com.oxigenoxide.caramballs.Main.spikes;
import static com.oxigenoxide.caramballs.Main.tap;
import static com.oxigenoxide.caramballs.Main.world;

public class Game extends Scene {

    public static final float GRAVITY = -9.81f;
    public static final float GRAVITY_PIXELS = -9.81f * 1 / 4f;

    public static OrbCounter orbCounter;
    public static Color[] palette_table = new Color[]{new Color(), new Color(), new Color(), new Color()};
    public static Color[] palette_target_table = new Color[]{new Color(), new Color(), new Color(), new Color()};
    public static FrameBuffer buffer;
    public static FrameBuffer buffer_slow;
    public static FrameBuffer buffer_trail;
    public static FrameBuffer buffer_table;
    public static Texture tex_buffer;
    public static Texture tex_buffer_trail;
    public static Texture tex_buffer_slow;
    public static Texture tex_buffer_table;
    public static Sprite sprite_buffer_trail;

    public static Point point;
    public static Button_Pause button_pause;
    public static Button button_sound;
    public static Button button_music;
    public static Button button_exit;

    public static ArrayList<Ball> ballsToDrop = new ArrayList<Ball>();

    public static boolean doPixelate = true;
    public static final float HITSPEEDTHRESHOLD = 5;
    public static final float WIDTHTOHEIGHTRATIO = .8f;
    public static float worldSpeed = 1f;

    public static float slowdown_effect;
    public static float alpha_darkOverlay;
    public static boolean doDarkOverlay;
    public static Ball_Bad ball_bad;
    public static Crown crown;
    public static Ball_Main ball_king;
    public static int score;
    public static boolean doGameOver;
    public static FirebaseInterface fbm;
    public static int level;
    static boolean doClearTrail;
    static public boolean doWiggle = true;
    static Vector2 pos_clearTrail;
    public static Vector2 pos_zoom;
    float musicVolume;
    boolean playMusic = false;
    float gravityAngle;
    float gravityIntensity = .5f;
    static float countMax_nextEgg = 2000;
    float count_nextEgg = countMax_nextEgg;

    Vector2 gravity;
    Vector2 worldGravity;

    static TutorialSwipeExample tutorialSwipeExample;

    static int countMax_nextButton = 300;
    float count_nextButton = countMax_nextButton;
    static float countMax_nextSpike = 2000;
    static float count_nextSpike = countMax_nextSpike;
    static int countMax_nextBallSpawnPeriod = 300;
    static float count_nextBallSpawnPeriod = countMax_nextBallSpawnPeriod;
    static int ballsToSpawn;
    static int ballsToSpawnMax = 4;
    static int countMax_nextBall = 150;
    static float count_nextBall = countMax_nextBall;
    static int countMax_nextFallHole = 150;
    static float count_nextFallHole = countMax_nextFallHole;
    static int countMax_nextCircularBumper = 150;
    static float count_nextCircularBumper = countMax_nextCircularBumper;
    static int countMax_nextCannon = 150;
    static float count_nextCannon = countMax_nextCannon;
    static int countMax_nextEye = 300;
    static float count_nextEye = countMax_nextCannon;
    static int countMax_nextOrb = 300;
    static float count_nextOrb = countMax_nextOrb;

    static Counter counter_dropObstacles;

    static float count_changeTableTop;
    static float countMax_changeTableTop = 60;
    static float count_slowShaderFluxuation;
    static boolean isChangingTableColor;

    static boolean doFloorButtons = false;
    static boolean doSpikes = false;
    static boolean doSpikePatches = false;
    static boolean doSpikeLines = false;
    static boolean doFallHoles = false;
    static boolean doCircularBumpers = false;
    static boolean doCannons = false;
    static boolean doSpawnOrbs = false;

    public static int ballType;
    public static int orbsCollected;

    static float worldStepFactor = .75f;

    static float count_hole;
    static float count_collectSound;
    float count;
    float count_trailClear;

    int random0;
    int random1;
    public static int worldType = 0;
    public static int worldType_previous = 0;
    public static int worldTier = 0;

    boolean doTilt;

    static boolean changeTableColor;
    static boolean doNextTutorial;
    static boolean doOnBallCollide;

    public static boolean doOnMainBallDestroyed;

    public static float collectSoundsToPlay;

    public static boolean isGameOver;
    public static boolean inTutorialMode;
    public static boolean doSetTutorialMode;
    public static boolean ultraSlow;
    public static boolean doGameOverCue;
    public static boolean doReplay;
    public static float count_gameOverCue;
    public static float countMax_gameOverCue = 120;
    public static boolean isPaused;
    public static Vector2 pos_floorFadeStain;
    public static Meter meter;
    public static ProgressBar progressBar;
    public static ComboBar comboBar;
    BitmapFont font;

    ContactListener contactListener;
    InputProcessor inputProcessor;

    static final boolean DODEBUGTOOLS = true;
    static boolean doSpawnObjects = true;

    public static Place[] places;
    public static Place place;


    public Game() {
        gravity = new Vector2(9.81f, 0);
        worldGravity = new Vector2();
        pos_floorFadeStain = new Vector2();
        balls = new ArrayList<Ball>();
        ballsToAdd = new ArrayList<Ball>();
        ballsToRemove = new ArrayList<Ball>();
        crown = new Crown();
        contactListener = new B2DContactListener();
        world.setContactListener(contactListener);
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_slow = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_trail = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_table = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        point = new Point();
        font = new BitmapFont();
        pos_clearTrail = new Vector2();
        sprite_buffer_trail = new Sprite(Res.tex_fullscreen);
        meter = new Meter();
        progressBar = new ProgressBar();
        comboBar = new ComboBar();
        places = new Place[]{new Place_Default(), new Place_Space()};

        orbCounter = new OrbCounter();
        palette_dir_table = new Vector3[]{new Vector3(), new Vector3(), new Vector3(), new Vector3()};
        palette_target_table = Res.tableTopPalette[0];
        pos_zoom = new Vector2(Main.width / 2, Main.height / 2);

        button_pause = new Button_Pause(new Vector2(Main.width - 2 - Res.tex_button_pause.getRegionWidth(), Main.height - 2 - Res.tex_button_pause.getRegionHeight()));
        button_sound = new Button_Sound(new Vector2(Main.width - 2 - Res.tex_button_pause.getRegionWidth(), Main.height - 2 - 16 - Res.tex_button_pause.getRegionHeight()));
        button_music = new Button_Music(new Vector2(Main.width - 2 - Res.tex_button_pause.getRegionWidth(), Main.height - 2 - 32 - Res.tex_button_pause.getRegionHeight()));
        button_exit = new Button_Exit(new Vector2(Main.width - 2 - Res.tex_button_pause.getRegionWidth(), Main.height - 2 - 48 - Res.tex_button_pause.getRegionHeight()));
        button_sound.setVisibility(false);
        button_music.setVisibility(false);
        button_exit.setVisibility(false);

        ballType = Main.gameData.selectedBall;

        counter_dropObstacles = new Counter(new ActionListener() {
            @Override
            public void action() {
                if (ballsToDrop.size() != 0) {
                    balls.add(ballsToDrop.get(0));
                    ballsToDrop.remove(0);
                    if (ballsToDrop.size() > 0)
                        counter_dropObstacles.start();
                }
            }
        }, .2f);

        for (int i = 0; i < 4; i++) {
            palette_table[i].set(palette_target_table[i]);
        }
    }

    @Override
    public void show() {
        super.show();
        if (doSetTutorialMode) {
            doSetTutorialMode = false;
            setTutorialMode();
        }
        if (!isGameOver)
            start();
    }

    @Override
    public void hide() {
        super.hide();
        Main.clearEntities();
        Main.worldProperties.setStandard();
        ultraSlow = false;
        Main.slowdown = 0;
    }

    @Override
    public void update() {

        counter_dropObstacles.update();

        Main.slowdown = 0;

        if (ultraSlow)
            Main.slowdown = .95f;

        if (isPaused || isGameOver) {
            Main.slowdown = 1;
        }
        count += Main.dt_one;
        count_trailClear += Main.dt_one_slowed;

        if (isChangingTableColor) {
            count_changeTableTop = count_changeTableTop - Main.dt_one;
            if (count_changeTableTop < 0) {
                isChangingTableColor = false;
                for (int i = 0; i < 4; i++) {
                    palette_table[i].set(palette_target_table[i]);
                }
            }
        }

        if (isGameOver || Main.gameOver.alpha > 0)
            Main.gameOver.update();

        if (!isGameOver && !inTutorialMode && !doGameOverCue && doSpawnObjects)
            update_game();

        if (inTutorialMode)
            update_tutorial();

        if (DODEBUGTOOLS) {
            if (Funcs.justTouched()) {
                if (MathFuncs.distanceBetweenPoints(tap[0], new Vector2(Main.width, Main.height)) < 10) {
                    for (Ball ball : balls)
                        ball.velY += 20;
                }
                if (MathFuncs.distanceBetweenPoints(tap[0], new Vector2(0, Main.height)) < 10) {
                    doPixelate = !doPixelate;
                    Main.gm.signOut();
                }
                if (MathFuncs.distanceBetweenPoints(tap[0], new Vector2(Main.width, 0)) < 10) {
                    balls.add(new Ball_Bad((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0));
                    Main.gm.showLeaderBoards();
                }

                if (MathFuncs.distanceBetweenPoints(tap[0], new Vector2(0, 0)) < 10)
                    balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, Ball_Main.getSize(Math.max(0, 0)), Ball_Main.getLevel(Math.max(0, 0))));

                if (MathFuncs.distanceBetweenPoints(tap[0], new Vector2(0, 0)) < 10) {
                    //nextLevel();
                    throwRandomBalls();
                }
            }
        }

        int value = 0;
        for (Ball_Main ball : mainBalls) {
            if (!ball.isUnderGround && !ball.doDispose) {
                value += ball.getValue();
            }
        }
        if (value > score) {
            score = value;
        }
        meter.update();
        progressBar.update();
        comboBar.update();

        if (tutorialSwipeExample != null)
            tutorialSwipeExample.update();
        orbCounter.update();

        if (!isGameOver) {
            if (button_pause.isTouching()) {
                button_pause.update();
            }
            if (button_sound.isTouching()) {
                button_sound.update();
            }
            if (button_music.isTouching()) {
                button_music.update();
            }
            if (button_exit.isTouching()) {
                button_exit.update();
            }
        }

        int ballsCounted = 0;
        for (Ball_Main ball : mainBalls) {
            if (!ball.isUnderGround)
                ballsCounted++;
        }
        //System.out.println("BallsCounted: " +ballsCounted+ "  TotalBallSize: "+Game.getTotalBallSize());


        if (playMusic) {
            if (!point.isActive)
                musicVolume = Math.min(musicVolume + .01f, .75f);
            else {
                musicVolume = Math.max(musicVolume - .08f, .35f);
            }
        }

        if (doGameOver) {
            doGameOver = false;
            gameOver();
        }

        if (!isGameOver)
            world.step(Main.dt_slowed * worldStepFactor, 3, 8);

        /*
        if (doTilt) {
            gravityAngle += .01f;
            worldGravity.set(gravity);
            worldGravity.setAngle((float) Math.toDegrees(gravityAngle)).scl(gravityIntensity);
            world.setGravity(worldGravity);
            gravityIntensity = (1 + (float) Math.sin(count / 100)) / 2;
        } else {
            world.setGravity(Vector2.Zero);
            gravityIntensity = 0;
        }
        */

        //gravityIntensity=0;
        if (doOnBallCollide) {
            onBallCollide();
            doOnBallCollide = false;
        }

        int num = (int) (count_trailClear / 3) % 4;
        switch (num) {
            case 0:
                random0 = 0;
                random1 = 0;
                break;
            case 1:
                random0 = 1;
                random1 = 0;
                break;
            case 2:
                random0 = 0;
                random1 = 1;
                break;
            case 3:
                random0 = 1;
                random1 = 1;
                break;
        }
/*
        if (changeTableColor) {

            for (int i = 0; i < 4; i++) {
                palette_table[i].add(palette_dir_table[i].x, palette_dir_table[i].y, palette_dir_table[i].z, 0);
            }
            if (Math.abs(palette_table[0].r - palette_target_table[0].r) < .01f && Math.abs(palette_table[0].g - palette_target_table[0].g) < .01f && Math.abs(palette_table[0].b - palette_target_table[0].b) < .01f)
                changeTableColor = false;

        }
*/
        place.update();

        if (doNextTutorial) {
            setNextTutorial();
            doNextTutorial = false;
        }
        if (doOnMainBallDestroyed) {
            onMainBallDestroyed();
            doOnMainBallDestroyed = false;
        }

        if (doGameOverCue) {
            update_gameOverCue();
            zoom += (ZOOM_MAX - zoom) * .02f;
            zoom = Math.min(ZOOM_MAX, zoom);
        } else {
            if (zoom > 1) {
                zoom -= Main.dt_one * ZOOMOUT_SPEED;
                zoom = Math.max(1, zoom);
            }
        }

        if (doDarkOverlay) {
            alpha_darkOverlay = Math.min(.5f, alpha_darkOverlay + .05f);
        } else {
            alpha_darkOverlay = Math.max(0, alpha_darkOverlay - .05f);
        }

        count_slowShaderFluxuation = MathFuncs.loopRadians(count_slowShaderFluxuation, Main.dt_slowed * 3);
    }

    public void throwRandomBalls() {
        int amount_balls = 10;
        for (int i = 0; i < amount_balls; i++) {
            balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0, (int) (10 * Math.random())).setVelocity((float) Math.random() * 35, (float) Math.random() * 35));
        }
    }

    public void putRandomObstacles() {
        int amount_obstacles = 3;
        for (int i = 0; i < amount_obstacles; i++) {
            createSpikePatch();
            //balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0, level).setVelocity((float) Math.random() * 5, (float) Math.random() * 5));
        }
    }

    public static void update_gameOverCue() {
        count_gameOverCue -= Main.dt_one;

        if (mainBalls.size() != 0) {
            pos_zoom.set(mainBalls.get(0).pos);
            pos_zoom.x = MathUtils.clamp(pos_zoom.x, Main.width / 2 / zoom, Main.width - Main.width / 2 / zoom);
            pos_zoom.y = MathUtils.clamp(pos_zoom.y, Main.height / 2 / zoom, Main.height - Main.height / 2 / zoom);
        }
        if (count_gameOverCue < 0) {
            endGameOverCue();
        }
        if (mainBalls.size() != 0 && count_gameOverCue < countMax_gameOverCue * .5f) {
            Ball_Main ballToDestroy = mainBalls.get(0);
            if (ballToDestroy.height >= 0)
                ballToDestroy.explode(0, 1);
            else
                ballToDestroy.dispose();
        }
    }

    static final float ZOOMIN_SPEED = .01f;
    static final float ZOOMOUT_SPEED = .05f;

    public static void beginGameOverCue(Ball_Main ball) {
        if (Main.inGame()) {
            if (!doGameOverCue) {
                for (Hole hole : holes) {
                    if (hole.getClass() == Hole_Ball.class)
                        hole.dispose();
                }
                for (Ball ball_ : balls) {
                    if (ball_.getClass() == Ball_Main.class)
                        ball_.dispose();
                }

                Main.rewardBall = new RewardBall(ball.pos.x, ball.pos.y, ball.level);
                Main.setSceneGameOver();
                //Main.setScene(Main.gameOver);

                //doGameOverCue = true;
                count_gameOverCue = countMax_gameOverCue;
                ultraSlow = true;

                Main.addSoundRequest(ID.Sound.SLOWDOWN);
            }
        }
    }

    static void endGameOverCue() {
        gameOver();
        Main.addSoundRequest(ID.Sound.SPEEDUP);
        doGameOverCue = false;
        ultraSlow = false;
        pos_zoom = new Vector2(Main.width / 2, Main.height / 2);
    }


    void update_game() {

        float spawnSpeedFactor = 1;

        if (Main.noFlow)
            spawnSpeedFactor = 2f;

        if (!Main.inScreenShotMode) {
            if (doFloorButtons) {
                if (floorButtons.size() == 0) {
                    count_nextButton -= Main.dt_one_slowed * spawnSpeedFactor;
                    if (count_nextButton <= 0) {
                        count_nextButton = countMax_nextButton;
                        floorButtons.add(new FloorButton());
                    }
                }
            }
        }

        if (getTotalBallSize() < 8) {
            if (Main.noFlow)
                count_nextBallSpawnPeriod -= Main.dt_one_slowed * .5f;
            else
                count_nextBallSpawnPeriod -= Main.dt_one_slowed;

            if (count_nextBallSpawnPeriod <= 0) {
                count_nextBall -= Main.dt_one_slowed;

                if (count_nextBall <= 0) {
                    count_nextBall = countMax_nextBall;
                    holes.add(new Hole_Ball());
                    ballsToSpawn--;

                    if (ballsToSpawn <= 0) {
                        ballsToSpawn = ballsToSpawnMax;
                        count_nextBallSpawnPeriod = countMax_nextBallSpawnPeriod;
                    }
                }
            }
        }

        if (doFallHoles) {
            count_nextFallHole -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextFallHole < 0) {
                holes.add(new Hole_Fall());
                count_nextFallHole = countMax_nextFallHole;
            }
        }

        if (doSpikes) {
            count_nextSpike -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextSpike <= 0) {
                count_nextSpike = countMax_nextSpike;
                if (doSpikePatches)
                    createSpikePatch();
                else if (doSpikeLines)
                    createSpikeLine();
                else
                    spikes.add(new Spike());
            }
        }

        if (doCannons) {
            count_nextCannon -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextCannon <= 0) {
                count_nextCannon = countMax_nextCannon;
                cannons.add(new Cannon());
            }
        }

        if (doCircularBumpers) {
            count_nextCircularBumper -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextCircularBumper <= 0) {
                count_nextCircularBumper = countMax_nextCircularBumper;
                circularBumpers.add(new CircularBumper());
            }
        }

        if (doSpawnOrbs) {
            count_nextOrb -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextOrb <= 0) {
                count_nextOrb = countMax_nextOrb * (.5f + .5f * (float) Math.random());
                balls.add(new Ball_Orb());
            }
        }

        /*
        if (true) {
            count_nextEye -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_nextEye <= 0) {
                count_nextEye = countMax_nextEye;
                eyes.add(new Eye());
                ballCapsules.add(new BallCapsule());
            }
        }

*/
        collectSoundsToPlay = Math.min(collectSoundsToPlay, 4);
        if (collectSoundsToPlay > 0) {
            count_collectSound -= Main.dt_one_slowed * spawnSpeedFactor;
            if (count_collectSound <= 0) {
                count_collectSound = 2;
                collectSoundsToPlay--;
                System.out.println("addsoundrequest collect");
                Main.addSoundRequest(ID.Sound.COLLECT, 1, 1, (float) Math.random() * .4f + .8f);
            }
        }
    }

    public static int getTotalBallSize() {
        int totalSize = 0;
        for (Ball_Main ball : mainBalls) {
            totalSize += Math.pow(2, ball.size);
        }
        return totalSize;
    }

    public void createSpikePatch() {
        Vector2 pos_rnd = getFreePosOnTable(12);
        if (pos_rnd != null)
            createSpikePatch(pos_rnd.x, pos_rnd.y);
    }

    public void createSpikeLine() {
        Vector2 pos = getRandomPosOnTable(4);
        int spikes = 5;
        float ang = (float) (Math.random() * Math.PI * 2);
        for (int i = 0; i < spikes; i++) {
            if (isPosFree(pos, 4))
                Main.spikes.add(new Spike(pos.x, pos.y, i * 10));
            pos.add((float) Math.cos(ang) * 10, (float) Math.sin(ang) * 10);
        }
    }

    public void createSpikePatch(float x, float y) {
        int amount = 5;
        float increment = (float) Math.PI * 2f / amount;
        float ang = (float) (Math.random() * Math.PI * 2);
        spikes.add(new Spike(x, y, false));
        float r = 9;
        for (int i = 0; i < amount; i++) {
            spikes.add(new Spike(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), false));
            ang += increment;
        }
    }

    boolean first = true;
    public static float zoom = 1;
    public static final float ZOOM_MAX = 2;

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {

        buffer_trail.begin();
        //batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl20.glDisable(GL20.GL_BLEND);

        if (comboBar.inFlow()) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            if (doClearTrail) {
                doClearTrail = false;
                sr.setColor(0, 0, 0, 0);
                sr.circle(pos_clearTrail.x, pos_clearTrail.y, 30);
                float angle;
                for (int i = 0; i < 5; i++) {
                    angle = (float) (Math.random() * 2 * Math.PI);
                    sr.circle(pos_clearTrail.x + (float) Math.cos(angle) * 30, pos_clearTrail.y + (float) Math.sin(angle) * 30, 30 * (float) Math.random() + 5);
                }
            }

            for (Ball ball : balls)
                ball.drawTrail(sr);
            sr.end();
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFuncSeparate(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.enableBlending();
        batch.setBlendFunctionSeparate(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //if((int)count%3==0) {
        batch.begin();
        batch.setShader(Res.shader_random);
        Res.shader_random.setUniformf("random0", random0);
        Res.shader_random.setUniformf("random1", random1);
        Res.shader_random.setUniformf("height", Main.height);
        batch.draw(Res.tex_random, 0, 0);
        batch.setShader(null);
        batch.end();

        //}
        //batch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE_MINUS_SRC_ALPHA);

        //sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.setColor(1, 1, 1, .01f);
        //sr.rect(0, 0, Main.width, Main.height);
        //sr.end();

        buffer_trail.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        tex_buffer_trail = buffer_trail.getColorBufferTexture();
        tex_buffer_trail.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sprite_buffer_trail.setTexture(tex_buffer_trail);
        sprite_buffer_trail.setSize(Main.width, Main.height);
        sprite_buffer_trail.setFlip(false, true);
        //sprite_buffer_trail.setAlpha(.8f);

        buffer.begin();
        Gdx.gl.glClearColor(.5f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glDisable(GL20.GL_BLEND);


        batch.begin(); // table

        if (count_changeTableTop > 0) {
            batch.setShader(Res.shader_palette);
            Main.setPalette(palette_table);
            drawTabletop(batch, worldType_previous);

            batch.setShader(Res.shader_floorFade);
            Main.setFloorFade(palette_target_table, pos_floorFadeStain, (countMax_changeTableTop - count_changeTableTop) / countMax_changeTableTop * (Main.width + Main.height));
            drawTabletop(batch, worldType);
            batch.setShader(null);
        } else {
            batch.setShader(Res.shader_palette);
            Main.setPalette(palette_table);
            drawTabletop(batch, worldType);
            batch.setShader(null);
        }

        place.drawOnBackground(batch);

        if (Main.worldProperties.hasFloor) {
            setNoCamEffects();
            sprite_buffer_trail.draw(batch);
            setCamEffects();
        }

        /*
        if (!isGameOver && !inTutorialMode && !Main.noScore) {
            batch.setShader(Res.shader_palette);
            Res.shader_palette.setUniformf("color1", palette_table[3].r * .85f, palette_table[3].g * .9f, palette_table[3].b * .9f, 1);
            Res.shader_palette.setUniformf("color3", 1, 1, 1, 1);
            Main.drawNumber(batch, score, new Vector2(Main.width / 2, (int) (Main.height * 3 / 4f)), ID.Font.FIELD);
            batch.setShader(null);
        }
        */
        if (inTutorialMode)
            render_tutorial(batch);

        for (FloorButton fb : floorButtons)
            fb.render(batch);

        for (Ball ball : balls)
            ball.drawSelectionRing(batch);

        batch.end();
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (Main.worldProperties.hasFloor) {
            sr.setColor(0, 0, 0, .8f);
            for (Ball ball : balls)
                ball.renderShadow(sr);
            sr.setColor(0, 0, 0, .8f); // reset, because ball shadow can become white
            for (BallCapsule bc : ballCapsules)
                bc.renderShadow(sr);
        }
        sr.setColor(0, 0, 0, 1);
        for (Hole hole : holes)
            hole.render(sr);
        for (Particle particle : particles_sr)
            particle.render(sr);
        for (Ball ball : balls)
            ball.drawShapes(sr);
        for (Projection p : projections)
            p.render(sr);
        for (Eye eye : eyes)
            eye.drawProjection(sr);
        for (Draggable d : draggables)
            d.render(sr);
        sr.end();

        batch.begin();
        for (Entity entity : entities_sorted) {
            entity.render(batch, sr);
        }

        for (Orb orb : orbs)
            orb.render(batch);

        for (RewardOrb ro : rewardOrbs)
            ro.render(batch);

        setCamNormal();
        for (Bumper bumper : bumpers)
            bumper.render(batch);
        setCamShake();

        if (inTutorialMode)
            batch.draw(Res.tex_tutorialMode, 0, Main.height - 21);

        batch.end();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (FloatingReward fr : floatingRewards)
            fr.drawShine(sr);
        if (tutorialSwipeExample != null)
            tutorialSwipeExample.render(sr);
        Main.ballSelector.render(sr);
        Main.dragSelector.render(sr);

        sr.end();

        batch.begin();

        for (FloatingReward fr : floatingRewards)
            fr.render(batch);
        batch.end();
        if (tutorialSwipeExample != null) {
            batch.begin();
            tutorialSwipeExample.render(batch);
            batch.end();
        }

        buffer.end();

        //END SPRITE DRAWING

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        buffer_slow.begin();
        Gdx.gl.glClearColor(.2f, .2f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.setShader(Res.shader_slow);
        Res.shader_slow.setUniformf("intensity", comboBar.getFlowFactor() * (.4f + .2f * (float) Math.sin(count_slowShaderFluxuation)));

        setNoCamEffects();
        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        batch.setShader(null);
        meter.render(batch);
        progressBar.render(batch);
        comboBar.render(batch);
        if (!Main.inScreenShotMode)
            orbCounter.render(batch);

        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 0, 0, 0, alpha_darkOverlay);
        batch.draw(Res.tex_fullscreen, 0, 0);
        batch.setShader(null);
        if (!isGameOver) {
            button_pause.render(batch);
            button_sound.render(batch);
            button_music.render(batch);
            button_exit.render(batch);
        }
        if (isPaused) {
            batch.draw(Res.tex_text_paused, Main.width / 2 - Res.tex_text_paused.getRegionWidth() / 2, Main.height / 2);
        }
        setCamEffects();
        font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 0, 0);
        batch.end();

        buffer_slow.end();

        tex_buffer_slow = buffer_slow.getColorBufferTexture();
        tex_buffer_slow.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Gdx.gl20.glDisable(GL20.GL_BLEND);
        batch.disableBlending();
        batch.begin();
        if (doPixelate) {
            batch.setShader(Res.shader_pixelate);
            Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);
        }
        setNoCamEffects();


        if (zoom != 1)
            batch.draw(tex_buffer_slow, -(zoom - 1) * Main.width / 2 + (-pos_zoom.x + Main.width / 2) * zoom, Main.height + (zoom - 1) * Main.height / 2 + (-pos_zoom.y + Main.height / 2) * zoom, Main.width * zoom, -Main.height * zoom);
        else
            batch.draw(tex_buffer_slow, 0, Main.height, Main.width, -Main.height);
        setCamEffects();
        batch.setShader(null);

        batch.end();
        batch.enableBlending();
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        Gdx.gl20.glLineWidth(1);
        Main.b2dr.render(world, Main.cam.combined);
    }

    public void render_tutorial(SpriteBatch batch) {

        if (tutorialStage == ID.TutorialStage.SLOWDOWN) {
            Main.drawNumber(batch, (int) count_catTutorial, new Vector2(Main.width / 2, Main.height / 2 + 37), ID.Font.FIELD);
            batch.draw(Res.tex_gap, 26, 68 + Main.height / 2 - 192 / 2);
            batch.draw(Res.tex_text_slowdown, Main.width / 2 - Res.tex_text_slowdown.getRegionWidth() / 2, 50);
            if (Main.slowdown > 0) {
                batch.draw(Res.tex_symbol_checkmark, Main.width / 2 - Res.tex_symbol_checkmark.getRegionWidth() / 2, 35);
            } else {
                batch.draw(Res.tex_symbol_cross, Main.width / 2 - Res.tex_symbol_cross.getRegionWidth() / 2, 35);
            }
        }
        if (tutorialStage == ID.TutorialStage.PIN)
            batch.draw(Res.tex_tutorialHole, 0, 0);
    }

    void drawTabletop(SpriteBatch batch, int type) {
        batch.draw(Res.tex_tabletop[type], Main.width / 2 - Res.tex_tabletop[type].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[type].getHeight() / 2);
    }

    public static void pause() {
        isPaused = true;
        button_sound.setVisibility(true);
        button_music.setVisibility(true);
        button_exit.setVisibility(true);
        setDarkOverlay();
    }


    public static void unpause() {
        isPaused = false;
        button_sound.setVisibility(false);
        button_music.setVisibility(false);
        button_exit.setVisibility(false);
        setNoDarkOverlay();
    }

    static int tutorialStage;

    public static void setTutorialMode() {
        inTutorialMode = true;
        tutorialStage = 0;
        setTutorial(tutorialStage);
    }

    static float count_catTutorial;
    static boolean doRestartTutorial;
    static boolean catTutorialFinished;

    void update_tutorial() {

        switch (tutorialStage) {
            case ID.TutorialStage.SLOWDOWN:
                if (mainBalls.size() > 0)
                    count_catTutorial = Math.max(0, count_catTutorial - Main.dt);
                if ((int) count_catTutorial == 0 && !catTutorialFinished) {
                    catTutorialFinished = true;
                    body_gap = Main.destroyBody(body_gap);
                    throwConfetti(Main.width / 2, Main.height / 2 - 10);
                    transitionToNextTutorial();
                }
                break;
        }
        if (doRestartTutorial) {
            doRestartTutorial = false;
            setTutorial(tutorialStage);
        }


    }

    static Vector2 pos_tutorialBall = new Vector2();
    static boolean hasDroppedBallTutorial;
    static Body body_gap;

    static void setTutorial(int stage) {
        Main.clearEntities();
        float x, y;
        float ang;
        Ball ball;
        switch (stage) {
            case ID.TutorialStage.PIN:
                Vector2 pos_spike = new Vector2();
                pos_spike.set(18, 0);
                for (int i = 0; i < 20; i++) {
                    spikes.add(new Spike(pos_spike.x, pos_spike.y, true));
                    pos_spike.y += Main.height / 20;
                }
                pos_spike.set(56, 0);
                for (int i = 0; i < 20; i++) {
                    spikes.add(new Spike(pos_spike.x, pos_spike.y, true));
                    pos_spike.y += Main.height / 20;
                }
                pins.add(new Pin(38, Main.height / 2 + 30));
                ball = new Ball_Main(37.5f, 30, Main.height, 1, 0).dontSplit();
                balls.add(ball);
                pos_tutorialBall.set(37.5f, 30);
                tutorialSwipeExample = new TutorialSwipeExample();
                break;
/*
            case ID.TutorialStage.MOREPINS:
                x = Main.width / 2;
                y = Main.height / 2-20;
                float dx=30*(float) Math.cos(Math.PI * 1 / 3f);
                float dy=30*(float) Math.sin(Math.PI * 1 / 3f);
                pins.add(new Pin(x, y));
                pins.add(new Pin(x+dx, y+dy));
                pins.add(new Pin(x-dx, y+dy));
                pins.add(new Pin(x+dx*2, y+dy*2));
                pins.add(new Pin(x-dx*2, y+dy*2));
                pins.add(new Pin(x, y+dy*2));
                balls.add(new Ball_Main(Main.width / 2, 30, 0, 0, 0));
                break;
                */
            case ID.TutorialStage.MOREPINS:
                x = Main.width / 2;
                y = Main.height / 2 - 20;
                for (int i = 0; i < 8; i++) {
                    ang = i / 8f * (float) Math.PI * 2;
                    pins.add(new Pin(x + 40 * (float) Math.cos(ang), y + 40 * (float) Math.sin(ang)));
                }
                pos_tutorialBall.set(x, y);
                ball = new Ball_Main(x, y, Main.height, 0, 0);
                balls.add(ball);
                tutorialSwipeExample = null;
                break;
            case ID.TutorialStage.MERGE:
                x = Main.width / 2;
                y = Main.height / 2;
                balls.add(new Ball_Main(x - 30, y, Main.height, 1, 0));
                balls.add(new Ball_Main(x + 30, y, Main.height, 2, 0));
                //balls.add(new Ball_Main(x + 30 * (float) Math.cos(Math.PI * 1 / 3f), y + 30 * (float) Math.sin(Math.PI * 1 / 3f), Main.height, 0, 0));
                balls.add(new Ball_Main(x, y - 40, Main.height, 0, 0));
                hasDroppedBallTutorial = false;
                break;
/*
            case ID.TutorialStage.SLOWDOWN:
                for (int i = 0; i < 12; i++) {
                    x = Main.width / 11 * i;
                    spikes.add(new Spike(x, Main.height / 2 - 40, true));
                }

                Vector2 pos_badBalls = new Vector2();
                Vector2 pos_cat = new Vector2(Main.width / 2, Main.height / 2 + 10);
                float dst = 80;
                float speed = 1;
                ang = 0;
                int amount_badBalls = 4;
                Ball badBall;
                for (int i = 0; i < amount_badBalls; i++) {
                    pos_badBalls.set(pos_cat.x + (float) Math.cos(ang) * dst, pos_cat.y + (float) Math.sin(ang) * dst);
                    badBall = new Ball_Bad(pos_badBalls.x, pos_badBalls.y, 0, 0);
                    badBall.body.setLinearVelocity(-(float) Math.cos(ang) * speed, -(float) Math.sin(ang) * speed);
                    badBall.ignoreWalls();
                    badBall.lock();
                    balls.add(badBall);
                    ang += Math.PI / (amount_badBalls - 1);
                }
                x = Main.width / 2;
                y = 30;
                pos_tutorialBall.set(x, y);
                balls.add(new Ball_Main(x, y, 0, 1, 0).setDrainSpeed().dontSplit());
                cats.add(new Cat(pos_cat.x, pos_cat.y));
                count_catTutorial = 5;
                catTutorialFinished = false;
                break;
                */
            case ID.TutorialStage.SLOWDOWN:

                body_gap = Main.destroyBody(body_gap);

                body_gap = world.createBody(Res.bodyDef_static);
                body_gap.createFixture(Res.fixtureDef_gap);
                body_gap.setTransform(0, (Main.height / 2 - 192 / 2) * Main.MPP, 0);

                Vector2 pos_badBalls = new Vector2();
                Vector2 pos_cat = new Vector2(Main.width / 2, Main.height / 2);
                float dst = 80;
                float speed = 1;
                ang = 0;
                int amount_badBalls = 6;
                Ball badBall;
                for (int i = 0; i < amount_badBalls; i++) {
                    pos_badBalls.set(pos_cat.x + (float) Math.cos(ang) * dst, pos_cat.y + (float) Math.sin(ang) * dst);
                    badBall = new Ball_Bad(pos_badBalls.x, pos_badBalls.y, 0, 0);
                    badBall.body.setLinearVelocity(-(float) Math.cos(ang) * speed, -(float) Math.sin(ang) * speed);
                    badBall.ignoreWalls();
                    badBall.lock();
                    balls.add(badBall);
                    ang += Math.PI * 2 / (amount_badBalls);
                }
                pos_tutorialBall.set(pos_cat.x, pos_cat.y);
                balls.add(new Ball_Main(pos_cat.x, pos_cat.y, 0, 1, 0).setDrainSpeed().dontSplit());
                count_catTutorial = 5;
                catTutorialFinished = false;
                break;
        }
    }


    public static void setMusicCurrentLevel() {
        Main.setMusic(Res.music[level % 5]);
    }

    static boolean doNextTutorialLevel;

    public static void onFadePeak() {
        if (inTutorialMode) {
            if (doNextTutorialLevel) {
                doNextTutorialLevel = false;
                doNextTutorial = true;
                resetLevel();
            } else {
                setTutorial(tutorialStage);
            }
        }
        if (doReplay) {
            doReplay = false;
            replay();
            showInterstitialWithChance();
        }
    }

    public static void showInterstitialWithChance() {
        if (!Main.noAds)
            if ((int) (Math.random() * 1) == 0)
                Main.amm.showInterstitial();
    }

    public static void onBallCollide() {
        balls.add(new Ball_Main(Main.width / 2, Main.height / 2 + 30, Main.height, 0, 0).setDrainSpeed());
    }

    public static void onBallCollide_delayed() {
        if (inTutorialMode) {
            if (!hasDroppedBallTutorial && tutorialStage == ID.TutorialStage.MERGE) {
                hasDroppedBallTutorial = true;
                doOnBallCollide = true;
            }
        }
    }


    public static void onBallMerge() {
        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    break;
                case ID.TutorialStage.MOREPINS:
                    break;
                case ID.TutorialStage.MERGE:

                    if (ballsToAdd.get(ballsToAdd.size() - 1).size == 0) {
                        transitionToNextTutorial();
                        for (Ball ball : ballsToAdd)
                            ball.dispose();
                        ballsToAdd.clear();
                        break;
                    }
                    /*
                    ballsToAdd.add(new Ball_Main((float) Math.random() * 108, (float) Math.random() * 192, Main.height, ballsToAdd.get(ballsToAdd.size() - 1).size, 0).setDrainSpeed());
                   */
                    break;
                default:

            }
        }
    }

    public static void onBallCombined() {
        comboBar.onBallCombined();
    }


    public static void onOrbCollected() {

    }

    static void setNextTutorial() {
        tutorialStage++;
        if (tutorialStage > 3) {
            endTutorial();
            return;
        }
        setTutorial(tutorialStage);
    }

    public static void endTutorial() {
        Main.clearEntities();
        tutorialStage = 0;
        inTutorialMode = false;
        body_gap = Main.destroyBody(body_gap);
        Main.setSceneMenuNow();
    }

    public static void onMainBallDestroyed() {

        comboBar.onMainBallDestroyed();

        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    balls.add(new Ball_Main(pos_tutorialBall.x, pos_tutorialBall.y, Main.height, 1, 0).setDrainSpeed().dontSplit());
                    break;
                case ID.TutorialStage.MOREPINS:
                    break;
                case ID.TutorialStage.MERGE:

                    break;
                case ID.TutorialStage.SLOWDOWN:
                    if (!catTutorialFinished) {
                        if (inTutorialMode) {
                            Main.startFade();
                        }
                        Main.shake();
                    }
                    break;
            }
        }
    }

    public static void transitionToNextTutorial() {
        doNextTutorialLevel = true;
        Main.startFade();
    }

    public static void setHardMode() {
        //worldStepFactor=5f;
    }

    public void setDoWiggle(boolean b) {
        doWiggle = b;
    }

    public static void onPinDestroyed() {
        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    transitionToNextTutorial();
                    break;
                case ID.TutorialStage.MOREPINS:
                    if (pins.size() == 1)
                        transitionToNextTutorial();
                    break;
                case ID.TutorialStage.MERGE:
                    break;
            }
        }
    }

    public static void onCatHit() {
        doRestartTutorial = true;
        Main.shake();
    }

    public static void replay() {
        reset();
    }

    public static void reset() {
        setPlace(0);
        //entities are already cleared after leaving the scene
        resetLevel();
        ball_king = null;
        isGameOver = false;
        count_hole = 0;
        unpause();
        button_pause.setTexture();
        comboBar.reset();
    }

    public static void start() {
        setup();
    }

    public static void setPlace(int place) {
        Game.place = places[place];
        Game.place.onEnter();
    }

    public static void nextLevel() {
        level++;
        if (!Main.noLevels)
            setMusicCurrentLevel();

        changeTableColor();

        onNextLevel();

        /*
        if (!Main.inScreenShotMode && !Main.noLevels) {
            int eggsToDrop = level;
            float x = Main.width / 2;
            float y = Main.height / 2;
            float increment = (float) Math.PI * 2f / Math.min(9, eggsToDrop);
            float ang = (float) (Math.random() * Math.PI * 2);
            float r = 20;
            int eggsDropped = 0;
            int i = 0;
            while (eggsDropped < 9 && eggsDropped < eggsToDrop) {
                Main.orbContainers.add(new OC_Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
                ang += increment;
                eggsDropped++;
                i++;
            }
            increment = (float) Math.PI * 2f / (eggsToDrop - eggsDropped);
            ang = (float) (Math.random() * Math.PI * 2);
            r = 32;

            while (eggsDropped < eggsToDrop) {
                Main.orbContainers.add(new OC_Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
                ang += increment;
                eggsDropped++;
                i++;
            }
        }
        */
    }

    public static void changeWorld(int type, int tier) {
        worldType_previous = worldType;
        worldType = type;
        worldTier = tier;
    }

    public static void createEggRing() {
        int eggsToDrop = 7;
        float x = Main.width / 2;
        float y = Main.height / 2;
        float increment = (float) Math.PI * 2f / Math.min(9, eggsToDrop);
        float ang = (float) (Math.random() * Math.PI * 2);
        float r = 20;
        int eggsDropped = 0;
        int i = 0;
        while (eggsDropped < 9 && eggsDropped < eggsToDrop) {
            Main.orbContainers.add(new OC_Fruit(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
            ang += increment;
            eggsDropped++;
            i++;
        }
        increment = (float) Math.PI * 2f / (eggsToDrop - eggsDropped);
        ang = (float) (Math.random() * Math.PI * 2);
        r = 32;

        while (eggsDropped < eggsToDrop) {
            Main.orbContainers.add(new OC_Fruit(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
            ang += increment;
            eggsDropped++;
            i++;
        }
    }

    public static void onNextLevel() {

        progressBar.show();

        bumpers.clear();
        doFloorButtons = false;
        doSpikes = false;
        doSpikePatches = false;
        doFallHoles = false;
        doCircularBumpers = false;
        doSpikeLines = false;
        doSpawnOrbs = true;
        countMax_nextOrb = 800;

        for(Scooper scooper:scoopers)
            scooper.disappear();
        for(Spike spike:spikes)
            spike.disappear();

        float harderFactor = (1 + .5f * level);

        /*
            switch (level) {
                case 0:
                    initiateLevel_easy();
                    break;
                case 900:
                    doSpikes = true;
                    doSpikeLines = true;
                    doCircularBumpers = true;
                    countMax_nextSpike = 400;
                    countMax_nextBallSpawnPeriod = 0;
                    break;
            }
            */

        if (level < 100) {
            initiateLevel_easy();
            return;
        }
        if (level < 10) {
            initiateLevel_medium();
            return;
        }
        initiateLevel_hard();
    }

    public static void initiateLevel_easy() { // collection of easy levels
        int random = (int) (Math.random() * 6);
        switch (random) {
            case 0:
                doSpikes = true;
                countMax_nextSpike = 300;
                countMax_nextBallSpawnPeriod = 0;
                break;
            case 1:
                doCircularBumpers = true;
                countMax_nextCircularBumper = 50;
                countMax_nextBallSpawnPeriod = 0;
                break;
            case 2:
                scoopers.add(new Scooper_0());
                break;
            case 3:
                counter_dropObstacles.start();
                for (int i = 0; i < 10; i++)
                    ballsToDrop.add(new Ball_Obstacle());
                break;
            case 4:
                int amount_spikes = (int) (Main.width / 10);
                for (int i = 0; i < amount_spikes; i++)
                    spikes.add(new Spike(i*10,1,true));
                for (int i = 0; i < amount_spikes; i++)
                    spikes.add(new Spike(i*10,Main.height-14,true));
                break;
            case 5:
                scoopers.add(new Scooper_1());
                break;
            //counter_dropObstacles.start();
                /*
                for (int i = 0; i < 10; i++)
                    ballsToDrop.add(new Ball_Obstacle());
                for (int i = 0; i < 10; i++)
                    ballsToDrop.add(new Ball_Inflate());
                for (int i = 0; i < 3; i++)
                    ballsToDrop.add(new Ball_Bad());
                    */
        }
    }

    public static void initiateLevel_medium() { // collection of medium-difficulty levels
        int random = (int) (Math.random() * 2);
        switch (random) {

        }
    }

    public static void initiateLevel_hard() { // collection of hard levels
        int random = (int) (Math.random() * 2);
        switch (random) {

        }
    }

    public static void spawnBumperOnRandomWall() {
        bumpers.add(new Bumper((int) (Math.random() * 4)));
    }

    public static void resetLevel() {
        level = 0;
        changeTableColor();
    }

    public static void setDarkOverlay() {
        doDarkOverlay = true;
    }

    public static void setNoDarkOverlay() {
        doDarkOverlay = false;
    }

    public static void throwConfetti(float x, float y) {
        if (!Main.noFX) {
            float ang;
            float impact;
            for (int i = 0; i < 10; i++) {
                ang = (float) (Math.random() * 2 * Math.PI);
                impact = (float) (Math.random() * 5);
                particles.add(new Particle_Confetti(x, y, impact * (float) Math.cos(ang), impact * (float) Math.sin(ang)));
            }
        }
    }

    static Vector3[] palette_dir_table;

    public static void changeTableColor() {

        count_changeTableTop = countMax_changeTableTop;
        isChangingTableColor = true;

        if (!Main.noLevels) {
            changeTableColor = true;
            //palette_target_table = Res.tableTopPalette[level];
            palette_target_table = place.getTableColor();
            //palette_target_table = getRandomTableColor();
            for (int i = 0; i < 4; i++) {
                palette_dir_table[i].set(palette_target_table[i].r, palette_target_table[i].g, palette_target_table[i].b);
                palette_dir_table[i].add(-palette_table[i].r, -palette_table[i].g, -palette_table[i].b);
                palette_dir_table[i].scl(.01f);
            }
        }
    }

    static float colorPoint = 1;

    public static Color[] getRandomTableColor() {
        Color[] palette = new Color[4];
        colorPoint = getNewColorPoint();
        float r = getColorProx(0, colorPoint);
        float g = getColorProx(1, colorPoint);
        float b = getColorProx(2, colorPoint);
        float baseWhiteness = .5f;
        float colorMul = .4f;
        Color color_primary = new Color(baseWhiteness + r * colorMul, baseWhiteness + g * colorMul, baseWhiteness + b * colorMul, 1);
        float translate = .1f;
        r = getColorProx(0, colorPoint + translate);
        g = getColorProx(1, colorPoint + translate);
        b = getColorProx(2, colorPoint + translate);
        float darkMul = .8f;
        Color color_secondary = new Color(baseWhiteness + r * colorMul, baseWhiteness + g * colorMul, baseWhiteness + b * colorMul, 1);
        color_secondary.mul(darkMul);

        color_secondary.a = 1;
        color_primary.a = 1;

        palette[0] = color_secondary;
        palette[1] = color_primary;
        palette[2] = color_secondary;
        palette[3] = color_secondary;
        return palette;
    }

    static float getColorProx(float from, float to) {
        float dst = Math.abs(to - from);
        float prox = Math.max(0, 1 - dst);
        if (from < .5f && prox <= 0) {
            from = 3 + from;
            dst = Math.abs(to - from);
            prox = Math.max(0, 1 - dst);
        } else if (from > 2.5f && prox <= 0) {
            from = 3 - from;
            dst = Math.abs(to - from);
            prox = Math.max(0, 1 - dst);
        }
        return prox;
    }

    static float getNewColorPoint() {
        float prev = colorPoint;
        while (getColorProx(prev, colorPoint) > .5f) {
            colorPoint = (float) Math.random() * 3;
        }
        return colorPoint;
    }

    public static Vector2 getRandomPosOnTable(float width, float height) {
        return new Vector2((int) (width / 2 + Math.random() * (Main.width - width)), (int) (height / 2 + Math.random() * (Main.height - height)));
    }

    public static Vector2 getRandomPosOnTable(float radius) {
        return new Vector2((int) (radius + Math.random() * (Main.width - radius * 2)), (int) (radius + Math.random() * (Main.height - radius * 2)));
    }

    public static boolean isPosFree(Vector2 pos, float radius) {
        for (Entity entity : entities) {
            if (entity.radius_spawn != 0 && MathFuncs.distanceBetweenPoints(pos, entity.pos) < radius + entity.radius_spawn)
                return false;
        }
        return true;
    }

    public static Vector2 getFreePosOnTable(float radius) {
        return getFreePosOnTable(radius, 5);
    }

    public static Vector2 getFreePosOnTable(float radius, int tries_max) {
        int tries = 0;
        Vector2 pos = null;
        while (tries < tries_max) {
            tries++;
            pos = getRandomPosOnTable(radius);
            if (!isPosFree(pos, radius))
                continue;
            break;
        }
        if (tries >= 5)
            return null;
        return pos;
    }

    public boolean inFlow() {
        return comboBar.inFlow();
    }

    static void setup() {
        Main.userData.gamesPlayed++;

        Main.setAdVisibility(false);

        setPlace(0);

        onNextLevel();

        orbsCollected = 0;
        score = 0;
        level = 0;
        count_nextBallSpawnPeriod = 0;
        count_nextBall = 0;
        ballsToSpawn = ballsToSpawnMax;
        count_nextSpike = 400;

        body_gap = Main.destroyBody(body_gap);

        setMusic(Res.music[0]);
    }


    public static void gameOver() {
        if (!inTutorialMode) {
            if (orbsCollected > gameData.highscore) {
                gameData.highscore = orbsCollected;
                if (!Main.noScore)
                    Main.gm.submitScore(orbsCollected);
            }
            Main.setAdVisibility(true);
            isGameOver = true;
            Main.setNoMusic();
        }
    }


    @Override
    public void dispose() {
        buffer.dispose();
        buffer_table.dispose();
        buffer_trail.dispose();
        buffer_slow.dispose();
    }

    public static void clearTrail(float x, float y) {
        pos_clearTrail.set(x, y);
        doClearTrail = true;
    }

    public void onKeyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
                floatingRewards.add(new FR_Eye(tap[0].x, tap[0].y, (int) (Math.random() * 4)));
                break;
            case Input.Keys.B:
                balls.add(new Ball_Main(tap[0].x, tap[0].y, 0, 1, 0));
                break;
            case Input.Keys.C:
                ballCapsules.add(new BallCapsule());
                break;
            case Input.Keys.D:
                break;
            case Input.Keys.E:
                orbContainers.add(new OC_Fruit());
                break;
            case Input.Keys.F:
                break;
            case Input.Keys.G:
                break;
            case Input.Keys.H:
                break;
            case Input.Keys.I:
                createEggRing();
                break;
            case Input.Keys.J:
                jumpingPads.add(new JumpingPad());
                break;
            case Input.Keys.K:
                break;
            case Input.Keys.L:
                break;
            case Input.Keys.M:
                break;
            case Input.Keys.N:
                nextLevel();
                break;
            case Input.Keys.O:
                balls.add(new Ball_Break(tap[0].x, tap[0].y));
                break;
            case Input.Keys.P:
                break;
            case Input.Keys.Q:
                eyes.add(new Eye());
                break;
            case Input.Keys.R:
                break;
            case Input.Keys.S:
                spikes.add(new Spike(tap[0].x, tap[0].y, false));
                break;
            case Input.Keys.T:
                throwRandomBalls();
                break;
            case Input.Keys.U:
                break;
            case Input.Keys.V:
                for (int i = 0; i < 10; i++)
                    Main.particlesToAdd.add(new Particle_Ball(tap[0].x, tap[0].y, (float) (0 + Math.random() * Math.PI * 1.2f - Math.PI * .6f), MathFuncs.toPPF(3) * (.5f + (float) Math.random()), 1));
                break;
            case Input.Keys.W:
                Main.draggables.add(new Plank());
                break;
            case Input.Keys.X:
                balls.add(new Ball_Orb());
                break;
            case Input.Keys.Y:
                Main.draggables.add(new Tire());
                break;
            case Input.Keys.Z:
                break;
        }
    }

}
