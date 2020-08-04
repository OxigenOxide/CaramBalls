package com.oxigenoxide.caramballs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxigenoxide.caramballs.object.BallSelector;
import com.oxigenoxide.caramballs.object.DragSelector;
import com.oxigenoxide.caramballs.object.PlusMessage;
import com.oxigenoxide.caramballs.object.Projection;
import com.oxigenoxide.caramballs.object.PulseEffect;
import com.oxigenoxide.caramballs.object.RewardBall;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.SpikeTrail;
import com.oxigenoxide.caramballs.object.Trail;
import com.oxigenoxide.caramballs.object.effect.Effect;
import com.oxigenoxide.caramballs.object.entity.BallCapsule;
import com.oxigenoxide.caramballs.object.entity.Bullet;
import com.oxigenoxide.caramballs.object.Bumper;
import com.oxigenoxide.caramballs.object.entity.Cannon;
import com.oxigenoxide.caramballs.object.entity.Cat;
import com.oxigenoxide.caramballs.object.entity.CircularBumper;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.Eye;
import com.oxigenoxide.caramballs.object.entity.JumpingPad;
import com.oxigenoxide.caramballs.object.entity.PowerOrbEntity;
import com.oxigenoxide.caramballs.object.entity.Wall;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Bad;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Egg;
import com.oxigenoxide.caramballs.object.entity.gate.Gate;
import com.oxigenoxide.caramballs.object.entity.jelly.Jelly;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_Pulse;
import com.oxigenoxide.caramballs.object.entity.pole.Pole;
import com.oxigenoxide.caramballs.object.entity.scooper.Scooper;
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
import com.oxigenoxide.caramballs.object.video.Video;
import com.oxigenoxide.caramballs.scene.DownGrading;
import com.oxigenoxide.caramballs.scene.Farm;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.scene.GameOver;
import com.oxigenoxide.caramballs.scene.Menu;
import com.oxigenoxide.caramballs.scene.Scene;
import com.oxigenoxide.caramballs.scene.Shop;
import com.oxigenoxide.caramballs.scene.Splash;
import com.oxigenoxide.caramballs.scene.Welcome;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.DataManager;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.GameData;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.UserData;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class Main extends ApplicationAdapter {
	static SpriteBatch batch;
	public static Game game;
	public static Menu menu;
	public static Farm farm;
	public static Scene welcome;
	public static Scene splash;
	public static GameOver gameOver;
	public static DownGrading downGrading;
	static Scene currentScene;
	static Scene overlayScene;
	static Scene nextScene_delayed;
	static Scene previousScene;
	static ArrayList<Scene> sceneStack = new ArrayList<Scene>();
	static ArrayList<Scene> scenes = new ArrayList<Scene>();
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
	public static final float PPM = 40;
	public static final float MPP = 1 / PPM;

	public static ArrayList<Ball> balls;
	public static ArrayList<Ball_Main> mainBalls = new ArrayList<Ball_Main>();
	public static ArrayList<Ball_Bad> badBalls = new ArrayList<Ball_Bad>();
	public static ArrayList<Ball_Egg> eggBalls = new ArrayList<Ball_Egg>();
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
	public static ArrayList<RewardOrb> rewardOrbs = new ArrayList<RewardOrb>();
	public static ArrayList<RewardOrb> rewardOrbsToRemove = new ArrayList<RewardOrb>();
	public static ArrayList<Scooper> scoopers = new ArrayList<Scooper>();
	public static ArrayList<Scooper> scoopersToRemove = new ArrayList<Scooper>();
	public static ArrayList<PowerOrbEntity> powerOrbEntities = new ArrayList<PowerOrbEntity>();
	public static ArrayList<PowerOrbEntity> powerOrbEntitiesToRemove = new ArrayList<PowerOrbEntity>();
	public static ArrayList<Trail> trails = new ArrayList<Trail>();
	public static ArrayList<Trail> trailsToRemove = new ArrayList<Trail>();
	public static ArrayList<PlusMessage> plusMessages = new ArrayList<PlusMessage>();
	public static ArrayList<PlusMessage> plusMessagesToRemove = new ArrayList<PlusMessage>();
	public static ArrayList<PulseEffect> pulseEffects = new ArrayList<PulseEffect>();
	public static ArrayList<PulseEffect> pulseEffectsToRemove = new ArrayList<PulseEffect>();
	public static ArrayList<Effect> effects = new ArrayList<Effect>();
	public static ArrayList<Effect> effectsToRemove = new ArrayList<Effect>();
	public static ArrayList<Effect> effects_front = new ArrayList<Effect>();
	public static ArrayList<Effect> effects_back = new ArrayList<Effect>();
	public static ArrayList<Gate> gates = new ArrayList<Gate>();
	public static ArrayList<Gate> gatesToRemove = new ArrayList<Gate>();
	public static ArrayList<Pole> poles = new ArrayList<Pole>();
	public static ArrayList<Pole> polesToRemove = new ArrayList<Pole>();
	public static ArrayList<Wall> walls = new ArrayList<Wall>();
	public static ArrayList<Wall> wallsToRemove = new ArrayList<Wall>();
	public static ArrayList<SpikeTrail> spikeTrails = new ArrayList<SpikeTrail>();
	public static ArrayList<SpikeTrail> spikeTrailsToRemove = new ArrayList<SpikeTrail>();
	public static ArrayList<Jelly> jellies = new ArrayList<Jelly>();
	public static ArrayList<Jelly> jelliesToRemove = new ArrayList<Jelly>();

	public static Video video;

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
	public static final int adHeight = 13;
	public static RewardBall rewardBall;

	public static boolean noAds = false; // DONE
	public static boolean noFX = false; // DONE
	public static boolean noMusic = true; // DONE
	public static boolean noCollection = false; // DONE
	public static boolean noLevels = false; // DONE
	public static boolean noFlow = false; // DONE
	public static boolean noScore = false; // DONE

	public static float collectSoundsToPlay;

	public static boolean inHTML;

	public static boolean doFade;
	public static boolean inScreenShotMode;

	public static float shakeAng;
	public static float fadeDir;
	public static float fade;

	public static float dt_slowed;
	public static float dt_one_slowed;

	public static float slowdown = 0;

	public static float count_collectSound;

	public static Vector2 dim_screen;

	public static Res res;

	static float shakeIntensity;

	public static UserData userData;

	public static GameData gameData;

	public static World world;
	public static WorldProperties worldProperties;

	BitmapFont font;

	float elapsedTime;
	long cutFrameID;

	public static float test_float = 1;
	public static boolean test_boolean = true;
	public static boolean isButtonPressed;
	public static int fingersOnScreen;
	public static int scrHD;
	public static float orbs_visual;

	static ActionListener action_peak;
	static ActionListener action_peak_delayed;

	long startTime;

	static float frameRate = 60;
	static float frameDuration = 1 / frameRate;

	public static final int BALLCOLORS = 9;

 /*
    Every time you release check this:
        - DEBUGTOOLS in Main should be false
        - RESEARCHMODE should be false
        - DOSCREENSHOTMODE should be false
        - DOTUTORIALHAND should be false
        - Check for startup crashes when you remove bin/gamedata.json
        - Remove gamedata files
*/

	// GET RELEASE KEY: keytool -list -v -keystore ‪C:\Keys\googlekeys.jks -alias key_ball (Release key is pretty useless)

	// CREATE RUNNABLE JAR: gradlew desktop:dist

	// CONFIG //
	boolean doReportFPS = false;
	public static final int SOUNDCAP = 2;
	public static final boolean RESEARCHMODE = false;
	public static final boolean DOSCREENSHOTMODE = false;
	public static final boolean DODEBUG = false; // corner event triggers, readable gamedata.json, entity position markers
	public static final boolean DOBOX2DRENDER = false;
	public static final boolean INVINCIBLE = false;
	public static final boolean DODEBUGTOOLS = false;
	public static final boolean DOTUTORIALHAND = false;
	public static final boolean DOTESTBANNERAD = false;
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
		batch = new SpriteBatch();
		font = new BitmapFont();

		World.setVelocityThreshold(0);

		fbm.signIn();
		assets = new AssetManager();
		Res.createAtlas();
		Res.preload();
		width = 108;
		height = Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * 108;
		pos_middle = new Vector2(width / 2, height / 2);
		soundRequests = new ArrayList<SoundRequest>();
		soundRequestsToPlay = new SoundRequest[SOUNDCAP];

		pointsPerPixel = Gdx.graphics.getWidth() / width;
		pixelsPerPoint = 1 / pointsPerPixel;

		scrHD = (int) ((Main.height - 192) / 2);

		cam = new OrthographicCamera(width, height);
		cam.position.set(width / 2, height / 2, 0);
		b2dr = new Box2DDebugRenderer();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);

		//game = new Game();
		scenes.add(menu = new Menu());
		scenes.add(splash = new Splash());

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

		orbs_visual = gameData.orbs;

		isMusicMuted = gameData.isMusicMuted;
		isSoundMuted = gameData.isSoundMuted;

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
		pulseEffects = new ArrayList<PulseEffect>();

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

		dt = Math.min(Gdx.graphics.getDeltaTime(), frameDuration * 5);
		dt_one = dt * 60;

		dt_slowed = dt * (1 - slowdown);
		dt_one_slowed = dt_slowed * 60;

		if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
			elapsedTime = 0;
			cutFrameID = Gdx.graphics.getFrameId();
		}
		elapsedTime += Gdx.graphics.getDeltaTime();


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

		fingersOnScreen = 0;
		for (int i = 0; i < 20; i++) {
			if (Gdx.input.isTouched(i)) fingersOnScreen++;
		}

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

		shakeIntensity = Math.max(0, shakeIntensity - .1f * dt_one);

		if (doFade) {
			fade += fadeDir * .03f * dt_one;
			//fade += fadeDir * .0005f;
			fade = Math.max(0, fade);
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

		for (RewardOrb ro : rewardOrbs)
			ro.update();
		rewardOrbs.removeAll(rewardOrbsToRemove);
		rewardOrbsToRemove.clear();

		for (Scooper s : scoopers)
			s.update();
		scoopers.removeAll(scoopersToRemove);
		scoopersToRemove.clear();

		for (Wall w : walls)
			w.update();
		walls.removeAll(wallsToRemove);
		wallsToRemove.clear();

		for (PowerOrbEntity poe : powerOrbEntities)
			poe.update();
		powerOrbEntities.removeAll(powerOrbEntitiesToRemove);
		powerOrbEntitiesToRemove.clear();

		for (PlusMessage pm : plusMessages)
			pm.update();
		plusMessages.removeAll(plusMessagesToRemove);
		plusMessagesToRemove.clear();

		for (Trail trail : trails)
			trail.update();
		trails.removeAll(trailsToRemove);
		trailsToRemove.clear();

		for (PulseEffect pe : pulseEffects)
			pe.update();
		pulseEffects.removeAll(pulseEffectsToRemove);
		pulseEffectsToRemove.clear();

		for (Effect effect : effects)
			effect.update();
		effects.removeAll(effectsToRemove);
		effectsToRemove.clear();

		for (Pole pole : poles)
			pole.update();
		poles.removeAll(polesToRemove);
		polesToRemove.clear();

		for (Gate gate : gates)
			gate.update();
		gates.removeAll(gatesToRemove);
		gatesToRemove.clear();

		for (SpikeTrail spikeTrail : spikeTrails)
			spikeTrail.update();
		spikeTrails.removeAll(spikeTrailsToRemove);
		spikeTrailsToRemove.clear();

		for (Jelly jelly : jellies)
			jelly.update();
		jellies.removeAll(jelliesToRemove);
		jelliesToRemove.clear();

		Ball_Main.glowLoop = MathFuncs.loopRadians(Ball_Main.glowLoop, dt_slowed * 5);

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

		if (rewardBall != null)
			rewardBall.update();
		if (video != null)
			video.update();

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET))
			test_float += .01f * dt_one;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET))
			test_float -= .01f * dt_one;
		if (Gdx.input.isKeyPressed(Input.Keys.BACKSLASH))
			System.out.println("TEST_FLOAT: " + test_float);

		if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD))
			saveScreenshot();

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
			startMovie();
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
			endMovie();

		if (isRecording)
			updateMovie();

		if (!isSoundMuted) {
            /*
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
                if (sr != null) {
                    playSound(sr.soundID, sr.volume, sr.pitch);
                }
            }

            for (int i = 0; i < SOUNDCAP; i++) {
                soundRequestsToPlay[i] = null;
            }
            */
			if (soundRequests.size() > 0) {
				SoundRequest sr = soundRequests.get(0);
				playSound(sr.soundID, sr.volume, sr.pitch);
				soundRequests.remove(0);
			}
		}
		//soundRequests.clear(); // also clear when sound is muted


		if (nextScene_delayed != null && !doFade) {
			startFade(nextScene_delayed);
			nextScene_delayed = null;
		} else if (action_peak_delayed != null && !doFade) {
			startFade(action_peak_delayed);
			action_peak_delayed = null;
		}

		collectSoundsToPlay = Math.min(collectSoundsToPlay, 4);
		if (collectSoundsToPlay > 0) {
			count_collectSound -= Main.dt_one_slowed;
			if (count_collectSound <= 0) {
				count_collectSound = 2;
				collectSoundsToPlay--;
				System.out.println("play collect sound");
				Main.addSoundRequest(ID.Sound.COLLECT, 1, 1, (float) Math.random() * .4f + .8f);
			}
		}

		// Visual amount of orbs
		if (orbs_visual != gameData.orbs) {
			orbs_visual += dt * 100 * Math.signum(gameData.orbs - orbs_visual);
			if (Math.abs(orbs_visual - gameData.orbs) < .5f)
				orbs_visual = gameData.orbs;
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
		if (inGame())
			if (!inScreenShotMode)
				if (shakeIntensity < 3)
					shakeIntensity = 3;
	}

	public static void shake(float impact) {
		if (inGame())
			if (!inScreenShotMode)
				//if (impact > shakeIntensity && impact > 2)
				shakeIntensity = impact;
	}

	public static void shakeSmall() {
		if (inGame())
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
			case ID.Entity.ALL:
				arrayList = entities;
				break;

			case ID.Entity.BALL:
				arrayList = balls; // NO BALLS: 2100 fps
				//arrayList = new ArrayList<Entity>();
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
			case ID.Entity.SCOOPER:
				arrayList = scoopers;
				break;
			case ID.Entity.POWERORBENTITY:
				arrayList = powerOrbEntities;
				break;
			case ID.Entity.POLE:
				arrayList = poles;
				break;
			case ID.Entity.GATE:
				arrayList = gates;
				break;
			case ID.Entity.WALL:
				arrayList = walls;
				break;
			case ID.Entity.JELLY:
				arrayList = jellies;
				break;

			// subtypes
			case ID.Entity.BALL_MAIN:
				arrayList = mainBalls;
				break;
		}
		return (ArrayList<Entity>) arrayList; // going against the laws of java
	}

	@Override
	public void render() {
		update();

		entities.clear();
		entities_sorted.clear();

		for (int i = 0; i < ID.Entity.AMOUNT; i++) entities.addAll(getEntityArrayList(i));

		currentScene.addEntities(entities);
		//entities.addAll(mainBalls);

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

		//draw scene
		setCamEffects();
		//render scenes
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

			currentScene.renderOnTop(batch, sr);

			if (rewardBall != null)
				rewardBall.render(batch);

			if (amm.isBannerVisible() && DOTESTBANNERAD)
				batch.draw(Res.tex_ad, 0, height - Res.tex_ad.getRegionHeight());

			//Gdx.gl.glClearColor(0, 0, 0, 0);
			//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.end();

			if (DODEBUG) {
				sr.begin(ShapeRenderer.ShapeType.Filled);
				for (Entity entity : entities)
					sr.circle(entity.pos.x, entity.pos.y, 1, 10);
				sr.end();
			}

			if (Main.DOBOX2DRENDER) {
				Main.cam.setToOrtho(false, Main.width * Main.MPP, Main.height * Main.MPP);
				Main.cam.position.set(0, 0, 0);
				Main.b2dr.render(world, Main.cam.combined);
				Main.cam.setToOrtho(false, Main.width, Main.height);
			}
		}

		batch.begin();

		//font.draw(batch, String.valueOf((int) ((Gdx.graphics.getFrameId() - cutFrameID) / elapsedTime)), 0, 10);
		if (doReportFPS)
			font.draw(batch, String.valueOf((int) (Gdx.graphics.getFramesPerSecond())), 0, 10);

		batch.end();
	}

	public static void setVideo(int id) {
		video = new Video(id);
	}

	public static void renderVideo(SpriteBatch batch) {
		if (video != null)
			video.render(batch);
	}

	public static boolean canBodyBePushed(Object object) {
		return object != null && object.getClass() != Bullet.class;
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


	public static void drawShadow(ShapeRenderer sr, float x, float y, float shadowWidth) {
		sr.ellipse((int) ((int) x - shadowWidth / 2), (int) ((int) y - shadowWidth / 2 * Game.WIDTHTOHEIGHTRATIO), shadowWidth, shadowWidth * Game.WIDTHTOHEIGHTRATIO);
	}

	public static void drawShadow(ShapeRenderer sr, Vector2 pos, float shadowWidth) {
		drawShadow(sr, pos.x, pos.y, shadowWidth);
	}

	@Override
	public void dispose() { // called on desktop // doesn't get called when restarting an instance of the game
		currentScene.hide();
		batch.dispose();
		if (isLoaded) {
			for (Scene scene : scenes)
				scene.saveData();
			game.dispose();
			menu.dispose();
			shop.dispose();
			splash.dispose();
			welcome.dispose();
			assets.dispose();
			Res.dispose();
			DataManager.getInstance().saveData();
		}
	}

	public void onPause() { // refering to android onPause() when leaving the app // for android // called when starting the game up for some reason
		System.out.println("onPause()");

		if (farm != null) farm.onPause();

		if (isLoaded) {
			userData.timePlayed += (System.currentTimeMillis() - startTime) / 1000;
			startTime = System.currentTimeMillis();
			userData.adsClicked += amm.getAdsClicked();
			userData.orbs = Main.gameData.orbs;

			Main.gameData.userData = userData;

			for (Scene scene : scenes)
				scene.saveData();

			userData.ballsUnlocked.clear();
			userData.highscore = gameData.highscore;

			for (boolean b : Main.gameData.unlocks) {
				userData.ballsUnlocked.add(b);
			}
			fbm.setUserData(userData);
			fbm.leave();
		}

		DataManager.getInstance().saveData();
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

		//Date date = Calendar.getInstance().getTime();

		//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		//String formattedDate = df.format(date);
/*        boolean isNewDate = true;
        for (String s : userData.daysPlayed) {
            if (s.equals(formattedDate)) {
                isNewDate = false;
                break;
            }
        }
        if (isNewDate)
            userData.daysPlayed.add(formattedDate);*/
	}

	public static void onLoaded() {
		createWorld();
		isLoaded = true;
		scenes.add(game = new Game());
		scenes.add(gameOver = new GameOver());
		downGrading = new DownGrading();
		if (DOSCREENSHOTMODE)
			setScreenShotMode();
		scenes.add(menu = new Menu());
		scenes.add(farm = new Farm());
		scenes.add(welcome = new Welcome());
		scenes.add(shop = new Shop());
	}

	public static void onSetScene(Scene scene) {
		if (rewardBall != null)
			rewardBall.onSetScene(scene);
	}

	public static void muteMusic() {
		setNoMusic();
		isMusicMuted = true;
		gameData.isMusicMuted = isMusicMuted;
	}

	public static void unmuteMusic() {
		isMusicMuted = false;
		Game.setMusicCurrentLevel();
		gameData.isMusicMuted = isMusicMuted;
	}

	public static void muteSound() {
		isSoundMuted = true;
		gameData.isSoundMuted = isSoundMuted;
	}

	public static void unmuteSound() {
		isSoundMuted = false;
		gameData.isSoundMuted = isSoundMuted;
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
		} else if (fadeDir == -1 && Main.nextScene != nextScene) {
			nextScene_delayed = nextScene;
		}
	}

	public static void startFade(ActionListener action) {
		if (!doFade) {
			action_peak = action;
			startFade();
		} else if (fadeDir == -1) {
			action_peak_delayed = action;
		}
	}

	public static void startFade() {
		if (!doFade) {
			doFade = true;
			fadeDir = 1;
			addSoundRequest(ID.Sound.WHOOSH_1);
		}
	}

	public static void onFadePeak() {
		//addSoundRequest(ID.Sound.WHOOSH_0);
		if (nextScene != null) {
			setScene(nextScene);
			System.out.println("SETSCENE: " + nextScene.getClass());
			nextScene = null;
			return;
		} else if (action_peak != null)
			action_peak.action();

		Game.onFadePeak();
	}

    /*
    public static void setPalette(Color[] colors) {
        Res.shader_palette.setUniform4fv("colors", new float[]{
                colors[0].r, colors[0].g, colors[0].b, colors[0].a,
                colors[1].r, colors[1].g, colors[1].b, colors[1].a,
                colors[2].r, colors[2].g, colors[2].b, colors[2].a,
                colors[3].r, colors[3].g, colors[3].b, colors[3].a,
        }, 0, 16);
    }

    public static void setPalette(Color color0, Color color1, Color color2, Color color3) {
        if (color0 == null) color0 = Color.CLEAR;
        if (color1 == null) color1 = Color.CLEAR;
        if (color2 == null) color2 = Color.CLEAR;
        if (color3 == null) color3 = Color.CLEAR;

        Res.shader_palette.setUniform4fv("colors", new float[]{
                color0.r, color0.g, color0.b, color0.a,
                color1.r, color1.g, color1.b, color1.a,
                color2.r, color2.g, color2.b, color2.a,
                color3.r, color3.g, color3.b, color3.a,
        }, 0, 16);
    }
*/

	public static void setPalette(Color[] colors) {
		if (colors[0] != null) Res.shader_palette.setUniformf("color_0", colors[0]);
		if (colors[1] != null) Res.shader_palette.setUniformf("color_1", colors[1]);
		if (colors[2] != null) Res.shader_palette.setUniformf("color_2", colors[2]);
		if (colors[3] != null) Res.shader_palette.setUniformf("color_3", colors[3]);
	}

	public static void setPalette(Color color_0, Color color_1, Color color_2, Color color_3) {
		if (color_0 != null) Res.shader_palette.setUniformf("color_0", color_0);
		if (color_1 != null) Res.shader_palette.setUniformf("color_1", color_1);
		if (color_2 != null) Res.shader_palette.setUniformf("color_2", color_2);
		if (color_3 != null) Res.shader_palette.setUniformf("color_3", color_3);
	}

	public static void setPalette(Color color_0, Color color_1, Color color_2, Color color_3, float alpha) {
		if (color_0 != null) Res.shader_palette.setUniformf("color_0", color_0.r, color_0.g, color_0.b, alpha);
		if (color_1 != null) Res.shader_palette.setUniformf("color_1", color_1.r, color_1.g, color_1.b, alpha);
		if (color_2 != null) Res.shader_palette.setUniformf("color_2", color_2.r, color_2.g, color_2.b, alpha);
		if (color_3 != null) Res.shader_palette.setUniformf("color_3", color_3.r, color_3.g, color_3.b, alpha);
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
		startFade(game);
		inGame = true; // not really, because the fade only just began
	}

	public static void setSceneGameOver() {
		popScene();
		startFade(gameOver);
	}

	public static void setSceneDownGrading() {
		startFade(downGrading);
	}

	public static void setSceneMenu() {
		startFade(menu);
	}

	public static void setScenePrevious() {
		if (sceneStack.size() > 2) {
			Scene previousScene = sceneStack.get(sceneStack.size() - 2);
			startFade(previousScene);
			sceneStack.remove(sceneStack.size() - 1);
			sceneStack.remove(sceneStack.size() - 1); // Yes, twice
		}
	}

	public static void popScene() {
		sceneStack.remove(sceneStack.size() - 1);
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

	public static void setScene(Scene scene) {
		sceneStack.add(scene);
		previousScene = currentScene;
		if (currentScene != null)
			currentScene.hide();
		currentScene = scene;
		scene.show();
		onSetScene(scene);
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
		if (body != null) { // WHY && isActive??????
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
		ballSelector.onResourcesLoaded();
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
			case ID.Sound.HIT_SOFT:
				Res.sound_ballHit_soft.play(volume * .5f, pitch, 0);
				break;
			case ID.Sound.SPLAT:
				Res.sound_splat.play(volume, (float) Math.random() * .3f + .8f, 0);
				break;
			case ID.Sound.BOUNCE:
				Res.sound_bounce.play(volume, (float) Math.random() * .3f + .8f, 0);
				break;
			case ID.Sound.BUTTONCLICK_0:
				Res.sound_buttonClick1.play(volume, pitch, 0);
				break;
			case ID.Sound.BUTTONCLICK_1:
				Res.sound_buttonClick2.play(volume, pitch, 0);
				break;
			case ID.Sound.COLLECT:
				System.out.println("soundID: " + Res.sound_collect.play(volume, pitch, 0));
				break;
			case ID.Sound.SUCCESS:
				Res.sound_success.play(volume, pitch, 0);
				break;
			case ID.Sound.PUNCH:
				Res.sound_punch.play(volume, (float) Math.random() * .6f + .7f, 0);
				break;
			case ID.Sound.CORRECT:
				Res.sound_correct.play(volume, pitch, 0);
				break;
			case ID.Sound.SPLIT:
				Res.sound_split.play(volume, pitch, 0);
				break;
			case ID.Sound.POP:
				Res.sound_pop.play(volume, pitch, 0);
				break;
			case ID.Sound.FAIL:
				Res.sound_fail.play(volume, pitch, 0);
				break;
			case ID.Sound.TAP:
				Res.sound_tap.play(.25f, pitch, 0);
				break;
			case ID.Sound.ALARM:
				Res.sound_alarm.play(1, pitch, 0);
				break;
			case ID.Sound.SHIELD:
				Res.sound_shield.play(volume, pitch, 0);
				break;
			case ID.Sound.SLIME_DEATH:
				Res.sound_slime_death.play(volume, pitch, 0);
				break;
			case ID.Sound.BALLBREAK:
				Res.sound_ballBreak.play(volume, (float) Math.random() * .4f + .9f, 0);
				break;
			case ID.Sound.WHOOSH_0:
				Res.sound_whoosh_0.play(volume);
				break;
			case ID.Sound.WHOOSH_1:
				Res.sound_whoosh_1.play(.3f);
				break;
			case ID.Sound.EGGCRACK:
				Res.sound_eggCrack.play(.8f);
				break;
			case ID.Sound.SHOT:
				Res.sound_shot.play(volume, pitch, 0);
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

	public static void drawTutorialHand(SpriteBatch batch) {
		if (DOTUTORIALHAND) {
			if (Gdx.input.isTouched())
				batch.draw(Res.tex_tutorialHand[1], tap[0].x - 5, tap[0].y - 3);
			else
				batch.draw(Res.tex_tutorialHand[0], tap[0].x - 5, tap[0].y - 3);
		}
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


	public static boolean isInScene(Class scene) {
		return Funcs.getClass(currentScene) == scene;
	}

	public void createInputProcessor() {
		InputProcessor inputProcessor = new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				currentScene.onKeyDown(keycode);
				switch (keycode) {
					case Input.Keys.BACKSPACE:
						for (Entity entity : entities)
							if (entity.pos.dst(tap[0]) < 10)
								entity.dispose();
						break;
					case Input.Keys.SLASH:
						test_boolean = !test_boolean;
						break;
				}
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
				if (!Gdx.input.isTouched(0) && isLoaded) { // so the second finger release is not triggering this
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

	public Main setDevMode() {
		noMusic = true;
		return this;
	}

	public Main setHTML() {
		inHTML = true;
		return this;
	}

	private static int counter = 1;

	public static void saveScreenshot() {
		try {
			FileHandle fh;
			do {
				fh = new FileHandle(Gdx.files.getLocalStoragePath() + "screenshots/screenshot" + counter++ + ".png");
			} while (fh.exists());
			Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			PixmapIO.writePNG(fh, pixmap);
			pixmap.dispose();
		} catch (Exception e) {
		}
	}

	public static void saveScreenshot(FileHandle fh) {
		try {
			Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			PixmapIO.writePNG(fh, pixmap);
			pixmap.dispose();
		} catch (Exception e) {
		}
	}

	private static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown) {
		final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
		Pixmap pixmap_small = new Pixmap((int) Main.width, (int) Main.height, Pixmap.Format.RGBA8888);

		if (yDown) {
			// Flip the pixmap upside down
			ByteBuffer pixels = pixmap.getPixels();
			int numBytes = w * h * 4;
			byte[] lines = new byte[numBytes];
			int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
		}

		// reduce size to 1:1 ratio
		Color color;
		float magnification = w / Main.width;
		for (int i = 0; i < (int) Main.width; i++)
			for (int j = 0; j < (int) Main.height; j++) {
				color = new Color(pixmap.getPixel((int) (i * magnification), (int) (j * magnification)));
				// remove the transparency
				pixmap_small.setColor(color.r, color.g, color.b, 1);
				pixmap_small.drawPixel(i, j);
			}


		return pixmap_small;
	}


	public static void saveScreenshotBytes() {
		try {
			FileHandle fh;
			do {
				fh = new FileHandle(Gdx.files.getLocalStoragePath() + "screenshots/screenshot" + counter++ + ".png");
			} while (fh.exists());
			Pixmap pixmap = getScreenshotBytes(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			PixmapIO.writePNG(fh, pixmap);
			pixmap.dispose();
		} catch (Exception e) {
		}
	}

	private static Pixmap getScreenshotBytes(int x, int y, int w, int h, boolean yDown) {
		final byte[] pixels = ScreenUtils.getFrameBufferPixels(x, y, w, h, yDown);
		//final byte[] pixels = ScreenUtils.getFrameBufferPixels(x, y, w, h, yDown);
		Pixmap pixmap_small = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);

		BufferUtils.copy(pixels, 0, pixmap_small.getPixels(), pixels.length);

        /*
        float magnification = w / Main.width;
        for (int i = 0; i < (int) Main.width; i++)
            for (int j = 0; j < (int) Main.height; j++) {
                pixmap_small.setColor(pixmap.getPixel((int) (i * magnification), (int) (j * magnification)));
                pixmap_small.drawPixel(i, j);
            }
        */

		return pixmap_small;
	}

	boolean isRecording;
	String movieFolder;
	int movieCount;
	FileHandle fhMovie;

	void startMovie() {
		isRecording = true;
		movieFolder = Gdx.files.getLocalStoragePath() + "movies//movie_" + System.currentTimeMillis();
		movieCount = 0;
	}

	void endMovie() {
		isRecording = false;
	}

	void updateMovie() {
		fhMovie = new FileHandle(movieFolder + "/frame_" + movieCount + ".png");
		saveScreenshot(fhMovie);
		movieCount++; // at the end so the indexing starts at 0
	}

}
