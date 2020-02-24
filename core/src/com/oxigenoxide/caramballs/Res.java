package com.oxigenoxide.caramballs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Res {

    public static TextureRegion[][] tex_ball;
    public static TextureRegion[][] tex_numbers;
    public static TextureRegion[] tex_ballShard;
    public static TextureRegion[] tex_title;
    public static TextureRegion[] tex_text_v;
    public static TextureRegion[] tex_badSmile;
    public static TextureRegion[] tex_explosion;
    public static TextureRegion[] tex_particle_ball;
    public static TextureRegion[] tex_confetti;
    public static TextureRegion[] tex_spike;
    public static TextureRegion[] tex_hand;
    public static TextureRegion[] tex_eye;
    public static TextureRegion[] tex_meter_dot;
    public static Texture[] tex_tabletop;
    public static TextureRegion[] tex_text_level;
    public static TextureRegion[] tex_particle_hit;

    public static TextureRegion tex_meter_case;
    public static TextureRegion tex_number_small_percent;
    public static TextureRegion tex_progressBar;
    public static TextureRegion tex_progressBar_ball;
    public static TextureRegion tex_ball_bad;
    public static TextureRegion tex_crown;
    public static TextureRegion tex_meter_ball;
    public static Texture tex_fade;
    public static TextureRegion tex_ttptext;
    public static TextureRegion tex_hleditie;
    public static TextureRegion tex_text_slowdown;
    public static TextureRegion tex_bumper;
    public static Texture tex_tilt;
    public static TextureRegion tex_bomb_white;
    public static TextureRegion tex_text_player;
    public static TextureRegion tex_tutorialMode;
    public static Texture tex_random;
    public static TextureRegion tex_gap;
    public static TextureRegion tex_buttonPressed_balls;
    public static TextureRegion tex_buttonPressed_leaderBoards;
    public static TextureRegion tex_button_balls;
    public static TextureRegion tex_meterDot;
    public TextureRegion tex_watermark;
    public static TextureRegion tex_text_welcome;
    public static TextureRegion tex_text_highscore;
    public static TextureRegion tex_buttonPressed_toGame;
    public static TextureRegion tex_shield;
    public static TextureRegion tex_shield_shine;
    public static TextureRegion tex_button_toGame;
    public static TextureRegion tex_buttonPressed_options;
    public static TextureRegion tex_button_options;
    public static TextureRegion tex_button_tutorial;
    public static TextureRegion tex_buttonPressed_tutorial;
    public static TextureRegion tex_buttonPressed_replay;
    public static TextureRegion tex_button_replay;
    public static TextureRegion tex_button_leaderBoards;
    public static TextureRegion tex_button_cross;
    public static TextureRegion tex_button_play;
    public static TextureRegion tex_buttonPressed_play;
    public static TextureRegion tex_button_return;
    public static TextureRegion tex_buttonPressed_return;
    public static TextureRegion tex_apple;
    public static TextureRegion tex_lemon;
    public static TextureRegion tex_strawberry;
    public static TextureRegion tex_bomb;
    public static TextureRegion tex_buttonPressed_cross;
    public static TextureRegion tex_youlost;
    public static TextureRegion tex_cannon_shine;
    public static TextureRegion tex_darkLayer;
    public static TextureRegion tex_orbCountBackground;
    public static TextureRegion tex_floorButton_danger;
    public static TextureRegion tex_floorButtonPressed_danger;
    public static TextureRegion tex_statisticsBackground;
    public static TextureRegion tex_numberSign;
    public static TextureRegion tex_shopSpot;
    public static TextureRegion tex_shopSpotPressed;
    public static TextureRegion tex_underTitle;
    public static TextureRegion tex_cannon_gun;
    public static TextureRegion tex_cannon_base;
    public static TextureRegion tex_orbCounter;
    public static TextureRegion tex_button_pause;
    public static TextureRegion tex_buttonPressed_pause;
    public static TextureRegion tex_button_resume;
    public static TextureRegion tex_buttonPressed_resume;
    public static TextureRegion tex_button_balls_new;
    public static TextureRegion tex_buttonPressed_balls_new;
    public static TextureRegion tex_button_info;
    public static TextureRegion tex_buttonPressed_info;
    public static TextureRegion tex_button_sound;
    public static TextureRegion tex_buttonPressed_sound;
    public static TextureRegion tex_button_soundMuted;
    public static TextureRegion tex_buttonPressed_soundMuted;
    public static TextureRegion tex_button_music;
    public static TextureRegion tex_buttonPressed_music;
    public static TextureRegion tex_button_musicMuted;
    public static TextureRegion tex_buttonPressed_musicMuted;
    public static TextureRegion tex_button_exit;
    public static TextureRegion tex_buttonPressed_exit;
    public static TextureRegion tex_text_paused;
    public static TextureRegion tex_lockedBall;
    public static TextureRegion tex_ballCapsule;
    public static TextureRegion tex_ballCapsule_shine;
    public static TextureRegion tex_blueEgg;
    public static Texture tex_fullscreen;
    public static TextureRegion tex_symbolOrb;
    public static TextureRegion tex_oxigenoxide;
    public static TextureRegion tex_symbolSelected;
    public static TextureRegion tex_text_comingsoon;
    public static TextureRegion tex_collectable_shield;
    public static TextureRegion tex_goldenEgg;
    public static TextureRegion tex_bullet;
    public static TextureRegion tex_cat;
    public static TextureRegion tex_honey;
    public static TextureRegion tex_tutorialHole;
    public static TextureRegion tex_pin;
    public static float[] ballRadius;
    public static TextureRegion tex_orb;
    public static TextureRegion tex_orb_big;
    public static TextureRegion tex_text_orbs;
    public static TextureRegion tex_text_youare;
    public static TextureRegion tex_symbolPlus;
    public static TextureRegion tex_symbol_checkmark;
    public static TextureRegion tex_symbol_cross;
    public static TextureRegion tex_text_score;
    public static TextureRegion tex_sign_number_small;
    public static TextureRegion tex_versionBarOutline;
    public static TextureRegion tex_versionBarShade;
    public static TextureRegion tex_scrollGroove;
    public static TextureRegion tex_circularBumper;
    public static TextureRegion tex_scrollNotch;
    public static TextureRegion tex_farmField;
    public static TextureRegion tex_jumpingPad;
    public static TextureRegion tex_ballRing;
    public static TextureRegion tex_tire_top;
    public static TextureRegion tex_tire_base;
    public static TextureRegion tex_draggable_pivot;
    public static TextureRegion tex_woodPlank;
    public static TextureRegion tex_stars;
    public static TextureRegion tex_stars_back;
    public static TextureRegion tex_indication_cross;
    public static TextureRegion tex_ball_sun;
    public static TextureRegion tex_ball_blueDwarf;
    public static TextureRegion tex_text_yourBalls;
    public static TextureRegion tex_comboBar_end;
    public static TextureRegion tex_wifiSymbol;
    public static TextureRegion tex_text_noInternet;
    public static TextureRegion tex_loadingBall;
    public static TextureRegion tex_loadingBall_shadow;
    public static TextureRegion tex_loadingBall_filled;

    public static Texture tex_comboBar;

    public Sprite sprite_watermark;
    public static BodyDef bodyDef_dynamic;
    public static BodyDef bodyDef_static;

    public static ShaderProgram shader_pixelate = new ShaderProgram(Gdx.files.internal("shaders/shader_pixelate.vert"), Gdx.files.internal("shaders/shader_pixelate.frag"));
    public static ShaderProgram shader_palette;
    public static ShaderProgram shader_slow;
    public static ShaderProgram shader_trailFade;
    public static ShaderProgram shader_tilt;
    public static ShaderProgram shader_random;
    public static ShaderProgram shader_c;
    public static ShaderProgram shader_a;
    public static ShaderProgram shader_fade;
    public static ShaderProgram shader_overlay;
    public static ShaderProgram shader_floorFade;
    public static ShaderProgram shader_bend;

    public static FixtureDef fixtureDef_border;
    public static FixtureDef fixtureDef_circle;
    public static FixtureDef fixtureDef_circle_noWall;
    public static FixtureDef fixtureDef_badBall_opponents;
    public static FixtureDef fixtureDef_badBall_normal;
    public static FixtureDef fixtureDef_egg;
    public static FixtureDef fixtureDef_gap;
    public static FixtureDef fixtureDef_shield;
    public static FixtureDef fixtureDef_collectable;
    public static FixtureDef fixtureDef_jumpingPad;
    public static FixtureDef fixtureDef_cage;
    public static FixtureDef fixtureDef_spike;
    public static FixtureDef fixtureDef_tire;
    public static FixtureDef fixtureDef_plank;
    public static FixtureDef[] fixtureDef_ball;
    public static Color[][] ballPalette;
    public static Color[][] tableTopPalette;
    public static Color[] ballBadPalette;
    public static Color[] eggPalette;
    public static Color[] palette_ballCapsule;

    public static CircleShape[] shape_ball;

    public static final Color COLOR_HOT = new Color(1, 130 / 255f, 26 / 255f, 1);
    public static final Color COLOR_RED = new Color(113 / 255f, 9 / 255f, 56 / 255f, 1);
    public static final Color COLOR_BOMB_SHADOW = new Color(62 / 255f, 9 / 255f, 113 / 255f, 1);
    public static final Color COLOR_PROJECTION_RED = new Color(255 / 255f, 65 / 255f, 0 / 255f, .6f);
    public static final Color COLOR_PROJECTION_PURPLE = new Color(230 / 255f, 50 / 255f, 150 / 255f, .6f);
    public static final Color COLOR_PROJECTION_YELLOW = new Color(255 / 255f, 205 / 255f, 0 / 255f, .6f);
    public static final Color COLOR_PROJECTION_GREEN = new Color(0 / 255f, 255 / 255f, 68 / 255f, .6f);
    public static final Color COLOR_PLANK = new Color(152 / 255f, 51 / 255f, 22 / 255f, 1);
    public static final Color COLOR_SUNSHINE = new Color(1, 112 / 255f, 0, 3 / 5f);
    public static final Color COLOR_SPLASH_BLUE = new Color(88 / 255f, 179 / 255f, 194 / 255f, 1);

    public static final Color[] COLOR_PROJECTION = new Color[]{COLOR_PROJECTION_RED, COLOR_PROJECTION_GREEN, COLOR_PROJECTION_YELLOW, COLOR_PROJECTION_PURPLE};
    public static final Color[] COLOR_OUTLINE = new Color[]{Color.BLACK, COLOR_PROJECTION_GREEN, COLOR_PROJECTION_YELLOW, COLOR_PROJECTION_PURPLE}; // test colors currently
    public static final Color[] PALETTE_BASICBALL = new Color[]{new Color(0, 0, 0, 1), new Color(62 / 255f, 180 / 255f, 227 / 255f, 1), new Color(1, 1, 1, 1), new Color(1, 1, 1, 1)};

    public static Sound sound_ballHit;
    public static Sound sound_speedup;
    public static Sound sound_buttonClick1;
    public static Sound sound_buttonClick2;
    public static Sound sound_slowdown;
    public static Sound sound_collect;
    public static Sound sound_glassBreak;
    public static Sound sound_plop;
    public static Sound sound_splat;
    public static Sound sound_bounce;

    public static Music[] music;

    public static Shape shape_spike;
    public static Shape shape_pin;
    public static Shape shape_bullet;

    public static TextureAtlas atlas;

    public static long soundID_music;
    public static final short MASK_PASSTHROUGH = 0x0002;
    public static final short MASK_BORDER = 0x0004;
    public static final short MASK_ZERO = 0x0001;
    public static final short MASK_BAD_BAD = 0x0008;
    public static final short MASK_MAINBALL = 0x0016;
    public static final short MASK_CAT = 0x0064;
    public static final short MASK_GAP = 0x0032;
    public static final short MASK_WALL = 0x0128;


    public static void createAtlas() {
        atlas = new TextureAtlas(Gdx.files.internal("images.atlas"));
        //new TextureRegion(atlas.findRegion())
    }

    public Res() {
        sound_ballHit = Main.assets.get("sounds/ballHit.wav");
        sound_speedup = Main.assets.get("sounds/speedup.wav");
        sound_slowdown = Main.assets.get("sounds/slowdown.wav");
        sound_glassBreak = Main.assets.get("sounds/glassBreak.wav");
        sound_buttonClick1 = Main.assets.get("sounds/buttonClick.wav");
        sound_plop = Main.assets.get("sounds/plop.mp3");
        sound_buttonClick2 = Main.assets.get("sounds/buttonClick2.wav");
        sound_collect = Main.assets.get("sounds/collect.wav");
        sound_splat = Main.assets.get("sounds/splat.mp3");
        sound_bounce = Main.assets.get("sounds/bounce.mp3");

        music = new Music[]{
                Main.assets.get("music/1.mp3"),
                Main.assets.get("music/2.mp3"),
                Main.assets.get("music/3.mp3"),
                Main.assets.get("music/4.mp3"),
                Main.assets.get("music/5.mp3"),
        };

        tex_comboBar = Main.assets.get("images/comboBar.png");
        tex_tabletop = new Texture[]{Main.assets.get("images/tabletop_0.png"),Main.assets.get("images/tabletop_1.png"),Main.assets.get("images/tabletop_2.png"),Main.assets.get("images/tabletop_3.png")};

        shader_floorFade.setUniformf("screenSize", Main.width, Main.height);
        /*
        tex_ball = new TextureRegion[]{new TextureRegion("images/ball_small"), new TextureRegion("images/ball_medium"), new TextureRegion("images/ball_large")};
        tex_ball_bad = new TextureRegion("images/ball_bad");
        tex_crown = new TextureRegion("images/crown");
        tex_bumper = new TextureRegion("images/bumper");
        tex_tabletop = new TextureRegion("images/tabletop");
        tex_ballShard = new TextureRegion[]{new TextureRegion("images/ballShard", 0), new TextureRegion("images/ballShard", 1), new TextureRegion("images/ballShard", 2)};
        */

        tex_ball = new TextureRegion[25][];
        tex_ball[0] = new TextureRegion[]{atlas.findRegion("ball_small"), atlas.findRegion("ball_medium"), atlas.findRegion("ball_large"), atlas.findRegion("ball_huge")};
        tex_ball[1] = new TextureRegion[]{atlas.findRegion("ball_face_small"), atlas.findRegion("ball_face_medium"), atlas.findRegion("ball_face_large")};
        tex_ball[2] = new TextureRegion[]{atlas.findRegion("ball_square_small"), atlas.findRegion("ball_square_medium"), atlas.findRegion("ball_square_large")};
        tex_ball[3] = new TextureRegion[]{atlas.findRegion("ball_brain_small"), atlas.findRegion("ball_brain_medium"), atlas.findRegion("ball_brain_large")};
        tex_ball[4] = new TextureRegion[]{atlas.findRegion("ball_egg_small"), atlas.findRegion("ball_egg_medium"), atlas.findRegion("ball_egg_large")};
        tex_ball[5] = new TextureRegion[]{atlas.findRegion("ball_apple_small"), atlas.findRegion("ball_apple_medium"), atlas.findRegion("ball_apple_large")};
        tex_ball[6] = new TextureRegion[]{atlas.findRegion("ball_rasp_small"), atlas.findRegion("ball_rasp_medium"), atlas.findRegion("ball_rasp_large")};
        tex_ball[7] = new TextureRegion[]{atlas.findRegion("ball_hl_small"), atlas.findRegion("ball_hl_medium"), atlas.findRegion("ball_hl_large")};
        tex_ball[8] = new TextureRegion[]{atlas.findRegion("ball_moon_small"), atlas.findRegion("ball_moon_medium"), atlas.findRegion("ball_moon_large")};
        tex_ball[20] = new TextureRegion[]{atlas.findRegion("ball_inflate_small"), atlas.findRegion("ball_inflate_medium"), atlas.findRegion("ball_inflate_large"), atlas.findRegion("ball_inflate_huge")};
        tex_ball[21] = new TextureRegion[]{atlas.findRegion("ball_inflate_small"), atlas.findRegion("ball_inflate_medium"), atlas.findRegion("ball_break1_large"), atlas.findRegion("ball_inflate_huge")};
        tex_ball[22] = new TextureRegion[]{atlas.findRegion("ball_inflate_small"), atlas.findRegion("ball_inflate_medium"), atlas.findRegion("ball_break2_large"), atlas.findRegion("ball_inflate_huge")};
        tex_ball[23] = new TextureRegion[]{atlas.findRegion("ball_inflate_small"), atlas.findRegion("ball_inflate_medium"), atlas.findRegion("ball_inflate_large"), atlas.findRegion("ball_inflate_huge")};

        tex_ball_bad = atlas.findRegion("ball_bad");
        tex_badSmile = new TextureRegion[]{atlas.findRegion("badSmile", 1), atlas.findRegion("badSmile", 2), atlas.findRegion("badSmile", 3), atlas.findRegion("badSmile", 4), atlas.findRegion("badSmile", 5),};
        tex_bomb = atlas.findRegion("bomb");
        tex_bomb_white = atlas.findRegion("bomb_white");
        tex_crown = atlas.findRegion("crown");
        tex_bumper = atlas.findRegion("bumper");
        tex_watermark = atlas.findRegion("watermark");

        tex_buttonPressed_toGame = atlas.findRegion("buttonPressed_toGame");
        tex_button_toGame = atlas.findRegion("button_toGame");
        tex_buttonPressed_replay = atlas.findRegion("buttonPressed_replay");
        tex_button_replay = atlas.findRegion("button_replay");
        tex_button_options = atlas.findRegion("button_options");
        tex_buttonPressed_options = atlas.findRegion("buttonPressed_options");
        tex_buttonPressed_balls = atlas.findRegion("buttonPressed_shop");
        tex_buttonPressed_cross = atlas.findRegion("buttonPressed_cross");
        tex_button_cross = atlas.findRegion("button_cross");
        tex_button_balls = atlas.findRegion("button_shop");
        tex_button_play = atlas.findRegion("button_play");
        tex_buttonPressed_play = atlas.findRegion("buttonPressed_play");
        tex_buttonPressed_return = atlas.findRegion("buttonPressed_return");
        tex_button_return = atlas.findRegion("button_return");

        tex_button_music = atlas.findRegion("button_music");
        tex_buttonPressed_music = atlas.findRegion("buttonPressed_music");
        tex_button_musicMuted = atlas.findRegion("button_musicMuted");
        tex_buttonPressed_musicMuted = atlas.findRegion("buttonPressed_musicMuted");
        tex_button_sound = atlas.findRegion("button_sound");
        tex_buttonPressed_sound = atlas.findRegion("buttonPressed_sound");
        tex_button_soundMuted = atlas.findRegion("button_soundMuted");
        tex_buttonPressed_soundMuted = atlas.findRegion("buttonPressed_soundMuted");
        tex_button_exit = atlas.findRegion("button_exit");
        tex_buttonPressed_exit = atlas.findRegion("buttonPressed_exit");

        tex_text_welcome = atlas.findRegion("text_welcome");
        tex_symbolPlus = atlas.findRegion("symbol_plus");

        tex_text_player = atlas.findRegion("text_player");
        tex_cannon_gun = atlas.findRegion("cannon_gun");
        tex_cannon_base = atlas.findRegion("cannon_base");
        tex_ttptext = atlas.findRegion("ttptext");
        tex_text_highscore = atlas.findRegion("text_highscore");
        tex_button_tutorial = atlas.findRegion("button_tutorial");
        tex_text_youare = atlas.findRegion("text_youare");
        tex_circularBumper = atlas.findRegion("circularBumper");
        tex_cannon_shine = atlas.findRegion("cannon_shine");
        tex_farmField = atlas.findRegion("farmField");
        tex_buttonPressed_tutorial = atlas.findRegion("buttonPressed_tutorial");
        tex_lockedBall = atlas.findRegion("lockedBall");
        tex_statisticsBackground = atlas.findRegion("statisticsBackground");
        tex_ballShard = new TextureRegion[]{atlas.findRegion("ballShard", 0), atlas.findRegion("ballShard", 1), atlas.findRegion("ballShard", 2)};
        tex_numbers = new TextureRegion[5][];
        tex_numbers[ID.Font.NORMAL] = new TextureRegion[]{atlas.findRegion("numbers/number", 0), atlas.findRegion("numbers/number", 1), atlas.findRegion("numbers/number", 2), atlas.findRegion("numbers/number", 3), atlas.findRegion("numbers/number", 4), atlas.findRegion("numbers/number", 5), atlas.findRegion("numbers/number", 6), atlas.findRegion("numbers/number", 7), atlas.findRegion("numbers/number", 8), atlas.findRegion("numbers/number", 9)};
        tex_numbers[ID.Font.FIELD] = new TextureRegion[]{atlas.findRegion("numbers/number_s", 0), atlas.findRegion("numbers/number_s", 1), atlas.findRegion("numbers/number_s", 2), atlas.findRegion("numbers/number_s", 3), atlas.findRegion("numbers/number_s", 4), atlas.findRegion("numbers/number_s", 5), atlas.findRegion("numbers/number_s", 6), atlas.findRegion("numbers/number_s", 7), atlas.findRegion("numbers/number_s", 8), atlas.findRegion("numbers/number_s", 9)};
        tex_numbers[ID.Font.SMALL] = new TextureRegion[]{atlas.findRegion("numbers/number_m", 0), atlas.findRegion("numbers/number_m", 1), atlas.findRegion("numbers/number_m", 2), atlas.findRegion("numbers/number_m", 3), atlas.findRegion("numbers/number_m", 4), atlas.findRegion("numbers/number_m", 5), atlas.findRegion("numbers/number_m", 6), atlas.findRegion("numbers/number_m", 7), atlas.findRegion("numbers/number_m", 8), atlas.findRegion("numbers/number_m", 9)};
        tex_numbers[ID.Font.POP_LARGE] = new TextureRegion[]{atlas.findRegion("numbers/number_h", 0), atlas.findRegion("numbers/number_h", 1), atlas.findRegion("numbers/number_h", 2), atlas.findRegion("numbers/number_h", 3), atlas.findRegion("numbers/number_h", 4), atlas.findRegion("numbers/number_h", 5), atlas.findRegion("numbers/number_h", 6), atlas.findRegion("numbers/number_h", 7), atlas.findRegion("numbers/number_h", 8), atlas.findRegion("numbers/number_h", 9)};
        tex_numbers[ID.Font.POP] = new TextureRegion[]{atlas.findRegion("numbers/number_b", 0), atlas.findRegion("numbers/number_b", 1), atlas.findRegion("numbers/number_b", 2), atlas.findRegion("numbers/number_b", 3), atlas.findRegion("numbers/number_b", 4), atlas.findRegion("numbers/number_b", 5), atlas.findRegion("numbers/number_b", 6), atlas.findRegion("numbers/number_b", 7), atlas.findRegion("numbers/number_b", 8), atlas.findRegion("numbers/number_b", 9)};
        tex_numberSign = atlas.findRegion("number_sign");
        tex_floorButton_danger = atlas.findRegion("button_danger");
        tex_floorButtonPressed_danger = atlas.findRegion("buttonPressed_danger");
        tex_title = new TextureRegion[]{
                atlas.findRegion("title_c"),
                atlas.findRegion("title_a"),
                atlas.findRegion("title_r"),
                atlas.findRegion("title_a"),
                atlas.findRegion("title_m"),
        };
        tex_shield = atlas.findRegion("shield");
        tex_shield_shine = atlas.findRegion("shield_shine");
        tex_underTitle = atlas.findRegion("title_balls");
        tex_ttptext = atlas.findRegion("ttptext");
        tex_collectable_shield = atlas.findRegion("collectable_shield");
        tex_tutorialHole = atlas.findRegion("tutorialHole");
        tex_hleditie = atlas.findRegion("HLeditie");
        tex_button_leaderBoards = atlas.findRegion("button_leaderBoards");
        tex_buttonPressed_leaderBoards = atlas.findRegion("buttonPressed_leaderBoards");
        tex_youlost = atlas.findRegion("youlost");
        tex_symbolOrb = atlas.findRegion("symbol_orb");
        tex_shopSpot = atlas.findRegion("shopSpot");
        tex_shopSpotPressed = atlas.findRegion("shopSpotPressed");
        tex_symbolSelected = atlas.findRegion("symbol_selected");
        tex_pin = atlas.findRegion("pin");
        tex_jumpingPad = atlas.findRegion("jumpingPad");
        tex_text_slowdown = atlas.findRegion("text_slowdown");
        tex_meterDot = atlas.findRegion("meterDot");
        tex_meter_case = atlas.findRegion("meter_case");
        tex_meter_ball = atlas.findRegion("meter_ball");
        tex_meter_dot = new TextureRegion[]{atlas.findRegion("meter_dot", 0), atlas.findRegion("meter_dot", 1), atlas.findRegion("meter_dot", 2)};
        tex_oxigenoxide = atlas.findRegion("oxigenoxide");
        tex_orbCountBackground = atlas.findRegion("orbCountBackground");
        tex_goldenEgg = atlas.findRegion("goldenEgg");
        tex_blueEgg = atlas.findRegion("blueEgg");
        tex_ballCapsule = atlas.findRegion("ballCapsule");
        tex_ballCapsule_shine = atlas.findRegion("ballCapsule_shine");
        tex_orb = atlas.findRegion("orb");
        tex_orb_big = atlas.findRegion("orb_big");
        tex_text_comingsoon = atlas.findRegion("text_comingsoon");
        tex_orbCounter = atlas.findRegion("orbCounter");
        tex_text_orbs = atlas.findRegion("text_orbs");
        tex_bullet = atlas.findRegion("bullet");
        tex_symbol_checkmark = atlas.findRegion("symbol_checkmark");
        tex_symbol_cross = atlas.findRegion("symbol_cross");
        tex_tutorialMode = atlas.findRegion("tutorialMode");
        tex_sign_number_small = atlas.findRegion("sign_number_small");
        tex_text_score = atlas.findRegion("text_score");
        tex_ballRing = atlas.findRegion("ballRing");
        tex_honey = atlas.findRegion("honey");
        tex_button_pause = atlas.findRegion("button_pause");
        tex_buttonPressed_pause = atlas.findRegion("buttonPressed_pause");
        tex_button_resume = atlas.findRegion("button_resume");
        tex_buttonPressed_resume = atlas.findRegion("buttonPressed_resume");
        tex_button_balls_new = atlas.findRegion("button_shop_new");
        tex_buttonPressed_balls_new = atlas.findRegion("buttonPressed_shop_new");
        tex_text_paused = atlas.findRegion("text_paused");
        tex_gap = atlas.findRegion("gap");
        tex_cat = atlas.findRegion("cat");
        tex_versionBarOutline = atlas.findRegion("versionBarOutline");
        tex_versionBarShade = atlas.findRegion("versionBarShade");
        tex_scrollGroove = atlas.findRegion("scrollGroove");
        tex_scrollNotch = atlas.findRegion("scrollNotch");
        tex_button_info = atlas.findRegion("button_info");
        tex_buttonPressed_info = atlas.findRegion("buttonPressed_info");
        tex_apple = atlas.findRegion("apple");
        tex_lemon = atlas.findRegion("lemon");
        tex_strawberry = atlas.findRegion("strawberry");
        tex_tire_top = atlas.findRegion("tire_top");
        tex_tire_base = atlas.findRegion("tire_base");
        tex_draggable_pivot = atlas.findRegion("draggable_pivot");
        tex_woodPlank = atlas.findRegion("woodPlank");
        tex_stars = atlas.findRegion("stars");
        tex_stars_back = atlas.findRegion("stars_back");
        tex_indication_cross = atlas.findRegion("indication_cross");
        tex_ball_sun = atlas.findRegion("ball_sun");
        tex_ball_blueDwarf = atlas.findRegion("ball_blueDwarf");
        tex_progressBar = atlas.findRegion("progressBar");
        tex_progressBar_ball = atlas.findRegion("progressBar_ball");
        tex_text_yourBalls = atlas.findRegion("text_yourBalls");
        tex_comboBar_end = atlas.findRegion("comboBar_end");

        tex_text_level = new TextureRegion[]{
                atlas.findRegion("text_level_home"),
                atlas.findRegion("text_level_space"),
        };
        tex_hand = new TextureRegion[]{
                atlas.findRegion("hand", 0),
                atlas.findRegion("hand", 1),
        };
        tex_confetti = new TextureRegion[]{
                atlas.findRegion("confetti_red"),
                atlas.findRegion("confetti_blue"),
                atlas.findRegion("confetti_green"),
        };
        tex_eye = new TextureRegion[]{
                atlas.findRegion("eye", 0),
                atlas.findRegion("eye_space"),
                atlas.findRegion("eye", 2),
                atlas.findRegion("eye", 3),
        };

        tex_spike = new TextureRegion[]{
                atlas.findRegion("spike", 1),
                atlas.findRegion("spike", 2),
                atlas.findRegion("spike", 3),
                atlas.findRegion("spike", 4),
                atlas.findRegion("spike", 4),
        };
        tex_particle_ball = new TextureRegion[]{
                atlas.findRegion("particle_ball", 0),
                atlas.findRegion("particle_ball", 1),
                atlas.findRegion("particle_ball", 2),
        };
        tex_explosion = new TextureRegion[]{
                atlas.findRegion("explosion", 0),
                atlas.findRegion("explosion", 1),
                atlas.findRegion("explosion", 2),
                atlas.findRegion("explosion", 3),
                atlas.findRegion("explosion", 4),
                atlas.findRegion("explosion", 5),
                atlas.findRegion("explosion", 6),
                atlas.findRegion("explosion", 7),
                atlas.findRegion("explosion", 8),
                atlas.findRegion("explosion", 9),
                atlas.findRegion("explosion_10"),
                atlas.findRegion("explosion_11"),
                atlas.findRegion("explosion_12"),
        };
        tex_text_v = new TextureRegion[]{
                atlas.findRegion("text_v_standard"),
                atlas.findRegion("text_v_noAds"),
                atlas.findRegion("text_v_noLevels"),
                atlas.findRegion("text_v_noShop"),
                atlas.findRegion("text_v_noMusic"),
                atlas.findRegion("text_v_noScore"),
                atlas.findRegion("text_v_noEffects"),
                atlas.findRegion("text_v_hard"),
        };

        tex_particle_hit = new TextureRegion[]{
                atlas.findRegion("particle_hit", 1),
                atlas.findRegion("particle_hit", 2),
                atlas.findRegion("particle_hit", 3),
                atlas.findRegion("particle_hit", 4),
                atlas.findRegion("particle_hit", 5),
                atlas.findRegion("particle_hit", 6),
        };


        sprite_watermark = new Sprite(tex_watermark);
        sprite_watermark.setAlpha(.5f);

        bodyDef_dynamic = new BodyDef();
        bodyDef_dynamic.type = BodyDef.BodyType.DynamicBody;

        bodyDef_static = new BodyDef();
        bodyDef_static.type = BodyDef.BodyType.StaticBody;

        new World(new Vector2(0, 0), false);

        shape_ball = new CircleShape[]{new CircleShape(), new CircleShape(), new CircleShape(), new CircleShape()};
        shape_ball[0].setRadius(4 * Main.METERSPERPIXEL);
        shape_ball[1].setRadius(7 * Main.METERSPERPIXEL);
        shape_ball[2].setRadius(9.5f * Main.METERSPERPIXEL);
        shape_ball[3].setRadius(13.5f * Main.METERSPERPIXEL);


        fixtureDef_ball = new FixtureDef[]{new FixtureDef(), new FixtureDef(), new FixtureDef(), new FixtureDef()};
        for (int i = 0; i < 4; i++) { // ONLY FOR MAINBALLS
            fixtureDef_ball[i].density = 1;
            fixtureDef_ball[i].shape = shape_ball[i];
            fixtureDef_ball[i].restitution = 1;
            fixtureDef_ball[i].filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
            fixtureDef_ball[i].filter.categoryBits = (MASK_ZERO);
        }

        fixtureDef_shield = new FixtureDef();
        fixtureDef_shield.density = 1;
        fixtureDef_shield.restitution = 1;
        fixtureDef_shield.shape = new CircleShape();
        fixtureDef_shield.filter.maskBits = MASK_ZERO;
        fixtureDef_shield.filter.categoryBits = MASK_ZERO;

        fixtureDef_circle = new FixtureDef();
        fixtureDef_circle.density = 1;
        fixtureDef_circle.shape = new CircleShape();
        fixtureDef_circle.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_circle.filter.categoryBits = MASK_ZERO;

        fixtureDef_circle_noWall = new FixtureDef();
        fixtureDef_circle_noWall.density = 1;
        fixtureDef_circle_noWall.shape = new CircleShape();
        fixtureDef_circle_noWall.filter.maskBits = MASK_ZERO;
        fixtureDef_circle_noWall.filter.categoryBits = MASK_ZERO;


        fixtureDef_egg = new FixtureDef();
        fixtureDef_egg.density = 1;
        fixtureDef_egg.restitution = 1;
        fixtureDef_egg.shape = new CircleShape();
        fixtureDef_egg.shape.setRadius(5 * Main.METERSPERPIXEL);
        fixtureDef_egg.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_egg.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_opponents = new FixtureDef();
        fixtureDef_badBall_opponents.density = 1;
        fixtureDef_badBall_opponents.shape = new CircleShape();
        fixtureDef_badBall_opponents.shape.setRadius(2 * Main.METERSPERPIXEL);
        fixtureDef_badBall_opponents.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_badBall_opponents.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_normal = new FixtureDef();
        fixtureDef_badBall_normal.density = 1;
        fixtureDef_badBall_normal.shape = new CircleShape();
        fixtureDef_badBall_normal.shape.setRadius(7 * Main.METERSPERPIXEL);
        fixtureDef_badBall_normal.filter.maskBits = (MASK_ZERO);
        fixtureDef_badBall_normal.filter.categoryBits = MASK_ZERO;

        fixtureDef_collectable = new FixtureDef();
        fixtureDef_collectable.density = 1;
        fixtureDef_collectable.isSensor = true;
        fixtureDef_collectable.shape = new CircleShape();
        fixtureDef_collectable.shape.setRadius(7 * Main.METERSPERPIXEL);
        fixtureDef_collectable.filter.maskBits = (MASK_ZERO);
        fixtureDef_collectable.filter.categoryBits = MASK_ZERO;

        fixtureDef_jumpingPad = new FixtureDef();
        fixtureDef_jumpingPad.isSensor = true;
        fixtureDef_jumpingPad.shape = new CircleShape();
        fixtureDef_jumpingPad.shape.setRadius(7f * Main.METERSPERPIXEL);
        fixtureDef_jumpingPad.filter.maskBits = MASK_ZERO;
        fixtureDef_jumpingPad.filter.categoryBits = MASK_ZERO;

        ChainShape chainShape_gap = new ChainShape();
        chainShape_gap.createLoop(new float[]{Main.METERSPERPIXEL * 29, Main.METERSPERPIXEL * 71, Main.METERSPERPIXEL * 29, Main.METERSPERPIXEL * 121, Main.METERSPERPIXEL * 79, Main.METERSPERPIXEL * 121, Main.METERSPERPIXEL * 79, Main.METERSPERPIXEL * 71});
        fixtureDef_gap = new FixtureDef();
        fixtureDef_gap.density = 1;
        fixtureDef_gap.shape = chainShape_gap;
        fixtureDef_gap.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_gap.filter.categoryBits = MASK_WALL;

        shape_spike = new CircleShape();
        shape_spike.setRadius(3.5f * Main.METERSPERPIXEL);

        fixtureDef_spike = new FixtureDef();
        fixtureDef_spike.density = 1;
        fixtureDef_spike.shape = shape_spike;
        fixtureDef_spike.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_spike.filter.categoryBits = MASK_ZERO;

        CircleShape shape_tire = new CircleShape();
        shape_tire.setRadius(23 * Main.METERSPERPIXEL);

        fixtureDef_tire = new FixtureDef();
        fixtureDef_tire.density = 2;
        fixtureDef_tire.shape = shape_tire;
        fixtureDef_tire.restitution = 2;
        fixtureDef_tire.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_tire.filter.categoryBits = MASK_ZERO;

        PolygonShape shape_plank = new PolygonShape();
        shape_plank.setAsBox(64 * Main.METERSPERPIXEL / 2, 19 * Main.METERSPERPIXEL / 2);
        fixtureDef_plank = new FixtureDef();
        fixtureDef_plank.density = 1;
        fixtureDef_plank.shape = shape_plank;
        fixtureDef_plank.restitution = 1;
        fixtureDef_plank.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_plank.filter.categoryBits = MASK_ZERO;

        shape_pin = new CircleShape();
        shape_pin.setRadius(5 * Main.METERSPERPIXEL);

        shape_bullet = new PolygonShape();
        ((PolygonShape) shape_bullet).setAsBox(3.5f * Main.METERSPERPIXEL, 2.5f * Main.METERSPERPIXEL);

        fixtureDef_border = new FixtureDef();
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{0, 0, Main.width * Main.METERSPERPIXEL, 0, Main.width * Main.METERSPERPIXEL, Main.height * Main.METERSPERPIXEL, 0, Main.height * Main.METERSPERPIXEL});
        fixtureDef_border.shape = chainShape;
        fixtureDef_border.density = 1;
        fixtureDef_border.restitution = 1;
        fixtureDef_border.friction = 0;
        fixtureDef_border.filter.maskBits = (short) (MASK_ZERO | MASK_PASSTHROUGH);
        fixtureDef_border.filter.categoryBits = MASK_WALL;

        fixtureDef_cage = new FixtureDef();
        ChainShape chainShape_cage = new ChainShape();
        chainShape_cage.createLoop(new float[]{0, 0, 100 * Main.METERSPERPIXEL, 0, 100 * Main.METERSPERPIXEL, 100 * Main.METERSPERPIXEL, 0, 100 * Main.METERSPERPIXEL});
        fixtureDef_cage.shape = chainShape_cage;
        fixtureDef_cage.density = 1;
        fixtureDef_cage.restitution = 1;
        fixtureDef_cage.friction = 0;
        fixtureDef_cage.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_cage.filter.categoryBits = MASK_ZERO;


        tableTopPalette = new Color[][]{
                new Color[4], new Color[4], new Color[4], new Color[4]
        };

        tableTopPalette[0][0] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1);
        tableTopPalette[0][1] = new Color(98 / 255f, 158 / 255f, 128 / 255f, 1);
        tableTopPalette[0][2] = new Color(98 / 255f, 158 / 255f, 128 / 255f, 1);
        tableTopPalette[0][3] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1);
        tableTopPalette[1][0] = new Color(142 / 255f, 94 / 255f, 75 / 255f, 1);
        tableTopPalette[1][1] = new Color(158 / 255f, 124 / 255f, 98 / 255f, 1);
        tableTopPalette[1][2] = new Color(158 / 255f, 124 / 255f, 98 / 255f, 1);
        tableTopPalette[1][3] = new Color(142 / 255f, 94 / 255f, 75 / 255f, 1);
        tableTopPalette[2][0] = new Color(75 / 255f, 89 / 255f, 142 / 255f, 1);
        tableTopPalette[2][1] = new Color(75 / 255f, 128 / 255f, 142 / 255f, 1);
        tableTopPalette[2][2] = new Color(75 / 255f, 128 / 255f, 142 / 255f, 1);
        tableTopPalette[2][3] = new Color(75 / 255f, 89 / 255f, 142 / 255f, 1);

        ballBadPalette = new Color[]{
                new Color(0, 0, 0, 1),
                COLOR_RED,
                new Color(206 / 255f, 39 / 255f, 39 / 255f, 1),
                new Color(0, 0, 0, 1)
        };
        eggPalette = new Color[]{
                new Color(0, 0, 0, 1),
                new Color(0, 200 / 255f, 1, 1),
                new Color(131 / 255f, 1, 228 / 255f, 1),
                new Color(1, 1, 1, 1),
        };

        palette_ballCapsule = new Color[]{
                new Color(0, 0, 0, 1),
                new Color(103 / 255f, 171 / 255f, 223 / 255f, 1),
                new Color(65 / 255f, 97 / 255f, 230 / 255f, 1),
                new Color(1, 1, 1, 1),
        };

        ballPalette = new Color[][]{
                new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4]
        };

        /*
        ballPalette[0][0] = new Color(0, 0, 0, 1);
        ballPalette[0][1] = new Color(62 / 255f, 180 / 255f, 227 / 255f, 1);
        ballPalette[0][2] = new Color(1, 1, 1, 1);
        ballPalette[0][3] = new Color(1, 1, 1, 1);
        */


        ballPalette[0][0] = new Color(0, 0, 0, 1);
        ballPalette[0][1] = new Color(191 / 255f, 0, 0, 1);
        ballPalette[0][2] = new Color(255 / 255f, 108 / 255f, 0, 1);
        ballPalette[0][3] = new Color(255 / 255f, 108 / 255f, 0, 1);

        ballPalette[1][0] = new Color(0, 0, 0, 1);
        ballPalette[1][1] = new Color(16 / 255f, 148 / 255f, 63 / 255f, 1);
        ballPalette[1][2] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);
        ballPalette[1][3] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);

        ballPalette[2][0] = new Color(0, 0, 0, 1);
        ballPalette[2][1] = new Color(24 / 255f, 13 / 255f, 157 / 255f, 1);
        ballPalette[2][2] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);
        ballPalette[2][3] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);

        ballPalette[3][0] = new Color(0, 0, 0, 1);
        ballPalette[3][1] = new Color(19 / 255f, 123 / 255f, 202 / 255f, 1);
        ballPalette[3][2] = new Color(52 / 255f, 187 / 255f, 209 / 255f, 1);
        ballPalette[3][3] = new Color(1, 1, 1, 1);

        ballPalette[4][0] = new Color(0, 0, 0, 1);
        ballPalette[4][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        ballPalette[4][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        ballPalette[4][3] = new Color(1, 1, 1, 1);

        ballPalette[5][0] = new Color(0, 0, 0, 1);
        ballPalette[5][1] = new Color(53 / 255f, 59 / 255f, 72 / 255f, 1);
        ballPalette[5][2] = new Color(130 / 255f, 167 / 255f, 157 / 255f, 1);
        ballPalette[5][3] = new Color(1, 1, 1, 1);

        ballPalette[6][0] = new Color(0, 0, 0, 1);
        ballPalette[6][1] = new Color(29 / 255f, 177 / 255f, 82 / 255f, 1);
        ballPalette[6][2] = new Color(71 / 255f, 221 / 255f, 189 / 255f, 1);
        ballPalette[6][3] = new Color(1, 1, 1, 1);

        ballPalette[7][0] = new Color(0, 0, 0, 1);
        ballPalette[7][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        ballPalette[7][2] = new Color(204 / 255f, 49 / 255f, 255 / 255f, 1);
        ballPalette[7][3] = new Color(1, 1, 1, 1);

        ballPalette[8][0] = new Color(0, 0, 0, 1);
        ballPalette[8][1] = new Color(14 / 255f, 108 / 255f, 44 / 255f, 1);
        ballPalette[8][2] = new Color(0 / 255f, 221 / 255f, 32 / 255f, 1);
        ballPalette[8][3] = new Color(1, 1, 1, 1);


        /*
        ballPalette[1][0] = new Color(0, 0, 0, 1);
        ballPalette[1][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        ballPalette[1][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        ballPalette[1][3] = new Color(1, 1, 1, 1);
        ballPalette[2][0] = new Color(0, 0, 0, 1);
        ballPalette[2][1] = new Color(8 / 255f, 100 / 255f, 38 / 255f, 1);
        ballPalette[2][2] = new Color(41 / 255f, 199 / 255f, 156 / 255f, 1);
        ballPalette[2][3] = new Color(135 / 255f, 228 / 255f, 247 / 255f, 1);
        ballPalette[3][0] = new Color(0, 0, 0, 1);
        ballPalette[3][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        ballPalette[3][2] = new Color(208 / 255f, 8 / 255f, 251 / 255f, 1);
        ballPalette[3][3] = new Color(229 / 255f, 192 / 255f, 1, 1);
        */

        Pixmap pixmap_fullscreen = new Pixmap((int) Main.width, (int) Main.height + 3, Pixmap.Format.RGBA8888);

        tex_tilt = new Texture(pixmap_fullscreen);
        tex_random = new Texture(pixmap_fullscreen);
        tex_fade = new Texture(pixmap_fullscreen);
        pixmap_fullscreen.setColor(0, 0, 0, 1);
        pixmap_fullscreen.fill();
        tex_fullscreen = new Texture(pixmap_fullscreen);

        ballRadius = new float[]{4, 7, 9.5f};
    }

    public static void queueAssets() {
        Main.assets.load("music/1.mp3", Music.class);
        Main.assets.load("music/2.mp3", Music.class);
        Main.assets.load("music/3.mp3", Music.class);
        Main.assets.load("music/4.mp3", Music.class);
        Main.assets.load("music/5.mp3", Music.class);

        Main.assets.load("sounds/ballHit.wav", Sound.class);
        Main.assets.load("sounds/speedup.wav", Sound.class);
        Main.assets.load("sounds/slowdown.wav", Sound.class);
        Main.assets.load("sounds/glassBreak.wav", Sound.class);
        Main.assets.load("sounds/buttonClick.wav", Sound.class);
        Main.assets.load("sounds/buttonClick2.wav", Sound.class);
        Main.assets.load("sounds/collect.wav", Sound.class);

        Main.assets.load("sounds/plop.mp3", Sound.class);
        Main.assets.load("sounds/splat.mp3", Sound.class);
        Main.assets.load("sounds/bounce.mp3", Sound.class);

        Main.assets.load("images/comboBar.png", Texture.class);
        Main.assets.load("images/tabletop_0.png", Texture.class);
        Main.assets.load("images/tabletop_1.png", Texture.class);
        Main.assets.load("images/tabletop_2.png", Texture.class);
        Main.assets.load("images/tabletop_3.png", Texture.class);
    }

    public static void preload() {
        tex_wifiSymbol = atlas.findRegion("wifiSymbol");
        tex_text_noInternet = atlas.findRegion("nointernet");
        tex_loadingBall = atlas.findRegion("loadingBall");
        System.out.println("loadingball: "+tex_loadingBall);
        tex_loadingBall_shadow = atlas.findRegion("loadingBall_shadow");
        tex_loadingBall_filled = atlas.findRegion("loadingBallFilled");
        tex_oxigenoxide = atlas.findRegion("oxigenoxide");
        tex_numbers = new TextureRegion[5][];
        tex_numbers[ID.Font.SMALL] = new TextureRegion[]{atlas.findRegion("numbers/number_m",0), atlas.findRegion("numbers/number_m",1), atlas.findRegion("numbers/number_m", 2), atlas.findRegion("numbers/number_m", 3), atlas.findRegion("numbers/number_m", 4), atlas.findRegion("numbers/number_m", 5), atlas.findRegion("numbers/number_m", 6), atlas.findRegion("numbers/number_m", 7), atlas.findRegion("numbers/number_m", 8), atlas.findRegion("numbers/number_m", 9)};
        tex_number_small_percent = atlas.findRegion("numbers/number_m_percent");
        shader_c = new ShaderProgram(Gdx.files.internal("shaders/shader_c.vert"), Gdx.files.internal("shaders/shader_c.frag"));
        sound_bounce = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.mp3"));
        sound_ballHit = Gdx.audio.newSound(Gdx.files.internal("sounds/ballHit.wav"));
        shader_palette = new ShaderProgram(Gdx.files.internal("shaders/shader_palette.vert"), Gdx.files.internal("shaders/shader_palette.frag"));
        shader_slow = new ShaderProgram(Gdx.files.internal("shaders/shader_slow.vert"), Gdx.files.internal("shaders/shader_slow.frag"));
        shader_trailFade = new ShaderProgram(Gdx.files.internal("shaders/shader_trailFade.vert"), Gdx.files.internal("shaders/shader_trailFade.frag"));
        shader_tilt = new ShaderProgram(Gdx.files.internal("shaders/shader_tilt.vert"), Gdx.files.internal("shaders/shader_tilt.frag"));
        shader_random = new ShaderProgram(Gdx.files.internal("shaders/shader_random.vert"), Gdx.files.internal("shaders/shader_random.frag"));
        shader_a = new ShaderProgram(Gdx.files.internal("shaders/shader_a.vert"), Gdx.files.internal("shaders/shader_a.frag"));
        shader_fade = new ShaderProgram(Gdx.files.internal("shaders/shader_fade.vert"), Gdx.files.internal("shaders/shader_fade.frag"));
        shader_overlay = new ShaderProgram(Gdx.files.internal("shaders/shader_overlay.vert"), Gdx.files.internal("shaders/shader_overlay.frag"));
        shader_floorFade = new ShaderProgram(Gdx.files.internal("shaders/shader_floorFade.vert"), Gdx.files.internal("shaders/shader_floorFade.frag"));
        shader_bend = new ShaderProgram(Gdx.files.internal("shaders/shader_bend.vert"), Gdx.files.internal("shaders/shader_bend.frag"));


    }

    public static void dispose(){
        atlas.dispose();

        shader_c.dispose();
        sound_bounce.dispose();
        sound_ballHit.dispose();
        shader_palette.dispose();
        shader_slow.dispose();
        shader_trailFade.dispose();
        shader_tilt.dispose();
        shader_random.dispose();
        shader_a.dispose();
        shader_fade.dispose();
        shader_overlay.dispose();
        shader_floorFade.dispose();
        shader_bend.dispose();
    }

    static public TextureRegion getRandomFruitTex() {
        int fruitType = (int) (Math.random() * 3);
        switch (fruitType) {
            case 0:
                return Res.tex_apple;
            case 1:
                return Res.tex_lemon;
            case 2:
                return Res.tex_strawberry;
        }
        return null;
    }

    public static TextureRegion getOrbTex(int type){
        switch(type){
            case 0:
                return Res.tex_orb;
            case 1:
                return Res.tex_orb_big;
        }
        return null;
    }

}
