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
    public static TextureRegion[] tex_text_level;
    public static TextureRegion[] tex_particle_hit;
    public static TextureRegion[] tex_particle_megaHit;
    public static TextureRegion[] tex_clock;
    public static TextureRegion[] tex_powerOrb;
    public static TextureRegion[] tex_smallFruit;
    public static TextureRegion[] tex_fruit;
    public static TextureRegion[] tex_plusMessage;
    public static TextureRegion[] tex_bulletExplosion;
    public static TextureRegion[] tex_ballExplosion;
    public static TextureRegion[] tex_mediumSplit;
    public static TextureRegion[] tex_largeSplit;
    public static TextureRegion[] tex_orbPickup;
    public static TextureRegion[] tex_pulse;

    public static TextureRegion tex_buttonPressed_toGame;
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
    public static TextureRegion tex_button_sell;
    public static TextureRegion tex_buttonPressed_sell;
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
    public static TextureRegion tex_button_buy;
    public static TextureRegion tex_buttonPressed_buy;

    public static TextureRegion tex_meter_case;
    public static TextureRegion tex_meter_fruit;
    public static TextureRegion tex_number_small_percent;
    public static TextureRegion tex_progressBar;
    public static TextureRegion tex_progressBar_ball;
    public static TextureRegion tex_ball_bad;
    public static TextureRegion tex_crown;
    public static TextureRegion tex_meter_ball;
    public static TextureRegion tex_ttptext;
    public static TextureRegion tex_hleditie;
    public static TextureRegion tex_text_slowdown;
    public static TextureRegion tex_bumper;
    public static TextureRegion tex_bomb_white;
    public static TextureRegion tex_text_player;
    public static TextureRegion tex_tutorialMode;
    public static TextureRegion tex_gap;
    public static TextureRegion tex_buttonPressed_balls;
    public static TextureRegion tex_buttonPressed_leaderBoards;
    public static TextureRegion tex_button_balls;
    public static TextureRegion tex_meterDot;
    public static TextureRegion tex_text_welcome;
    public static TextureRegion tex_text_highscore;
    public static TextureRegion tex_shield;
    public static TextureRegion tex_shield_shine;
    public TextureRegion tex_watermark;
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
    public static TextureRegion[] tex_shopSpot;
    public static TextureRegion[] tex_shopSpotEmpty;
    public static TextureRegion[] tex_shopSpotPressed;
    public static TextureRegion tex_underTitle;
    public static TextureRegion tex_cannon_gun;
    public static TextureRegion tex_cannon_base;
    public static TextureRegion tex_orbCounter;
    public static TextureRegion tex_orbCountBar;

    public static TextureRegion tex_text_paused;
    public static TextureRegion tex_lockedBall;
    public static TextureRegion tex_ballCapsule;
    public static TextureRegion tex_ballCapsule_shine;
    public static TextureRegion tex_blueEgg;
    public static TextureRegion tex_symbolOrb;
    public static TextureRegion tex_oxigenoxide;
    public static TextureRegion tex_symbolSelected;
    public static TextureRegion tex_text_comingsoon;
    public static TextureRegion tex_collectable_shield;
    public static TextureRegion tex_goldenEgg;
    public static TextureRegion[] tex_bullet;
    public static TextureRegion tex_cat;
    public static TextureRegion tex_honey;
    public static TextureRegion tex_tutorialHole;
    public static TextureRegion tex_pin;
    public static TextureRegion tex_scooper;
    public static TextureRegion tex_scooper_0;
    public static TextureRegion tex_scooperShine_0;
    public static TextureRegion tex_scooper_2;
    public static TextureRegion tex_scooperShine_2;
    public static TextureRegion tex_scooper_3;
    public static float[] ballRadius;
    public static TextureRegion tex_orb;
    public static TextureRegion tex_orb_big;
    public static TextureRegion tex_orb_crystal;
    public static TextureRegion tex_orb_bigCrystal;
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
    public static TextureRegion tex_speechBubbleOrb;
    public static TextureRegion tex_pedestal;
    public static TextureRegion tex_shopMarginBottom;
    public static TextureRegion tex_shopMarginTop;
    public static TextureRegion tex_ad;
    public static TextureRegion tex_capsule_top;
    public static TextureRegion tex_capsule_bottom;
    public static TextureRegion tex_capsule;
    public static TextureRegion tex_fountain;
    public static TextureRegion tex_ballDepot_ball;
    public static TextureRegion tex_ballDepot_back;
    public static TextureRegion tex_ballDepot_bar;
    public static TextureRegion tex_ballDepot_frame;
    public static TextureRegion tex_ballDepot_line;
    public static TextureRegion tex_orbDisplay;


    public static Texture tex_comboBar;
    public static Texture tex_fade;
    public static Texture tex_tilt;
    public static Texture tex_fullscreen;
    public static Texture tex_random;

    public static Texture[] tex_tabletop;

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
    public static ShaderProgram shader_circle;
    public static ShaderProgram shader_c_over;
    public static ShaderProgram shader_filledCircle;
    public static ShaderProgram shader_clock;
    public static ShaderProgram defaultShader;

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
    public static FixtureDef fixtureDef_box;
    public static FixtureDef fixtureDef_scooperSpike;
    public static FixtureDef fixtureDef_powerOrb;
    public static FixtureDef[] fixtureDef_ball;
    public static FixtureDef[] fixtureDef_ball_passThrough;
    public static Color[][] palette_mainBall;
    public static Color[][] palette_table;
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
    public static final Color COLOR_ORBNUMBER = new Color(0, 73 / 255f, 128 / 255f, 1);

    public static final Color[] COLOR_PROJECTION = new Color[]{COLOR_PROJECTION_RED, COLOR_PROJECTION_GREEN, COLOR_PROJECTION_YELLOW, COLOR_PROJECTION_PURPLE};
    public static final Color[] COLOR_OUTLINE = new Color[]{Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.PURPLE, Color.GOLD}; // test colors currently
    public static final Color[] PALETTE_BASICBALL = new Color[]{new Color(0, 0, 0, 1), new Color(62 / 255f, 180 / 255f, 227 / 255f, 1), new Color(1, 1, 1, 1), new Color(1, 1, 1, 1)};
    public static final Color[][] PALETTE_SCORE = new Color[][]{
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(162 / 255f, 162 / 255f, 162 / 255f, 1), Color.WHITE}, // white
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(51 / 255f, 174 / 255f, 58 / 255f, 1), new Color(95 / 255f, 205 / 255f, 111 / 255f, 1)}, // light green
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(27 / 255f, 131 / 255f, 33 / 255f, 1), new Color(51 / 255f, 174 / 255f, 58 / 255f, 1)}, // dark green
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(88 / 255f, 96 / 255f, 194 / 255f, 1), new Color(99 / 255f, 179 / 255f, 194 / 255f, 1)}, // light blue
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(131 / 255f, 26 / 255f, 72 / 255f, 1), new Color(191 / 255f, 53 / 255f, 205 / 255f, 1)}, // purple
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(213 / 255f, 109 / 255f, 0, 1), new Color(227 / 255f, 202 / 255f, 22 / 255f, 1)}, // gold
            new Color[]{Color.CLEAR, Color.CLEAR, new Color(177 / 255f, 15 / 255f, 145 / 255f, 1), new Color(1, 28 / 255f, 161 / 255f, 1)}, // pink
    };
    public static Color[] PALETTE_WHITEBALL = new Color[]{Color.BLACK, new Color(62 / 255f, 180 / 255f, 227 / 255f, 1), Color.WHITE, Color.WHITE};
    public static Color[] PALETTE_WHITE = new Color[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
    public static Color[] PALETTE_POWERORB_0 = new Color[]{Color.BLACK, new Color(64 / 255f, 9 / 255f, 150 / 255f, 1), new Color(44 / 255f, 116 / 255f, 1, 1), Color.WHITE};
    public static Color[] PALETTE_POWERORB_1 = new Color[]{Color.BLACK, new Color(9 / 255f, 150 / 255f, 109 / 255f, 1), new Color(65 / 255f, 1, 44 / 255f, 1), Color.WHITE};

    public static Sound sound_ballHit;
    public static Sound sound_speedup;
    public static Sound sound_buttonClick1;
    public static Sound sound_buttonClick2;
    public static Sound sound_slowdown;
    public static Sound sound_collect;
    public static Sound sound_glassBreak;
    public static Sound sound_plop;
    public static Sound sound_splat;
    public static Sound sound_success;
    public static Sound sound_bounce;
    public static Sound sound_punch;
    public static Sound sound_correct;
    public static Sound sound_split;
    public static Sound sound_pop;
    public static Sound sound_fail;
    public static Sound sound_tap;
    public static Sound sound_alarm;

    public static Music[] music;

    public static Shape shape_spike;
    public static Shape shape_pin;
    public static Shape shape_bullet;

    public static TextureAtlas atlas;

    public static long soundID_music;
    public static final short MASK_PASSTHROUGH = 0x0002;
    public static final short MASK_BORDER = 0x0004;
    public static final short MASK_ZERO = 0x0001;
    public static final short MASK_BAD_BALL = 0x0008;
    public static final short MASK_MAINBALL = 0x0016;
    public static final short MASK_CAT = 0x0064;
    public static final short MASK_GAP = 0x0032;
    public static final short MASK_WALL = 0x0128;

    public static void createAtlas() {
        atlas = new TextureAtlas(Gdx.files.internal("images.atlas"));
        //new TextureRegion(atlas.findRegion())
    }

    public Res() {
        Main.batch.setShader(null);
        defaultShader = Main.batch.getShader();

        sound_ballHit = Main.assets.get("sounds/ballHit.mp3");
        sound_speedup = Main.assets.get("sounds/speedup.mp3");
        sound_slowdown = Main.assets.get("sounds/slowdown.mp3");
        sound_glassBreak = Main.assets.get("sounds/glassBreak.mp3");
        sound_buttonClick1 = Main.assets.get("sounds/buttonClick.mp3");
        sound_plop = Main.assets.get("sounds/plop.mp3");
        sound_buttonClick2 = Main.assets.get("sounds/buttonClick2.mp3");
        sound_collect = Main.assets.get("sounds/collect.mp3");
        sound_splat = Main.assets.get("sounds/splat.mp3");
        sound_success = Main.assets.get("sounds/success.mp3");
        sound_bounce = Main.assets.get("sounds/bounce.mp3");
        sound_punch = Main.assets.get("sounds/punch.mp3");
        sound_correct = Main.assets.get("sounds/correct.mp3");
        sound_split = Main.assets.get("sounds/split.mp3");
        sound_pop = Main.assets.get("sounds/pop.mp3");
        sound_fail = Main.assets.get("sounds/fail.mp3");
        sound_tap = Main.assets.get("sounds/tap.mp3");
        sound_alarm = Main.assets.get("sounds/alarm.mp3");

        music = new Music[]{
                Main.assets.get("music/1.mp3"),
                Main.assets.get("music/2.mp3"),
                Main.assets.get("music/3.mp3"),
                Main.assets.get("music/4.mp3"),
                Main.assets.get("music/5.mp3"),
        };

        tex_comboBar = Main.assets.get("images/comboBar.png");
        tex_tabletop = new Texture[]{Main.assets.get("images/tabletop_0.png"), Main.assets.get("images/tabletop_1.png"), Main.assets.get("images/tabletop_2.png"), Main.assets.get("images/tabletop_3.png")};

        tex_ball = new TextureRegion[25][];
        tex_ball[0] = new TextureRegion[]{atlas.findRegion("ball_small"), atlas.findRegion("ball_medium"), atlas.findRegion("ball_large"), atlas.findRegion("ball_huge")};
        tex_ball[1] = new TextureRegion[]{atlas.findRegion("ball_face_small"), atlas.findRegion("ball_face_medium"), atlas.findRegion("ball_face_large")};
        tex_ball[2] = new TextureRegion[]{atlas.findRegion("ball_square_small"), atlas.findRegion("ball_square_medium"), atlas.findRegion("ball_square_large")};
        tex_ball[3] = new TextureRegion[]{atlas.findRegion("ball_brain_small"), atlas.findRegion("ball_brain_medium"), atlas.findRegion("ball_brain_large")};
        tex_ball[4] = new TextureRegion[]{atlas.findRegion("ball_egg_small"), atlas.findRegion("ball_egg_medium"), atlas.findRegion("ball_egg_large")};
        tex_ball[5] = new TextureRegion[]{atlas.findRegion("ball_apple_small"), atlas.findRegion("ball_apple_medium"), atlas.findRegion("ball_apple_large")};
        tex_ball[6] = new TextureRegion[]{atlas.findRegion("ball_rasp_small"), atlas.findRegion("ball_rasp_medium"), atlas.findRegion("ball_rasp_large")};
        tex_ball[7] = new TextureRegion[]{atlas.findRegion("ball_scales_small"), atlas.findRegion("ball_scales_medium"), atlas.findRegion("ball_scales_large")};
        tex_ball[8] = new TextureRegion[]{atlas.findRegion("ball_moon_small"), atlas.findRegion("ball_moon_medium"), atlas.findRegion("ball_moon_large")};
        tex_ball[9] = new TextureRegion[]{atlas.findRegion("ball_ring_small"), atlas.findRegion("ball_ring_medium"), atlas.findRegion("ball_ring_large")};
        tex_ball[10] = new TextureRegion[]{atlas.findRegion("ball_virus_small"), atlas.findRegion("ball_virus_medium"), atlas.findRegion("ball_virus_large")};
        tex_ball[11] = new TextureRegion[]{atlas.findRegion("ball_cd_small"), atlas.findRegion("ball_cd_medium"), atlas.findRegion("ball_cd_large")};
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
        tex_button_sell = atlas.findRegion("button_sell");
        tex_buttonPressed_sell = atlas.findRegion("buttonPressed_sell");
        tex_button_buy = atlas.findRegion("button_buy");
        tex_buttonPressed_buy = atlas.findRegion("buttonPressed_buy");

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
        tex_shopSpot = new TextureRegion[]{atlas.findRegion("shopSpot"), atlas.findRegion("shopSpot_rare"), atlas.findRegion("shopSpot_legendary")};
        tex_shopSpotPressed = new TextureRegion[]{atlas.findRegion("shopSpotPressed"), atlas.findRegion("shopSpotPressed_rare"), atlas.findRegion("shopSpotPressed_legendary")};
        tex_shopSpotEmpty = new TextureRegion[]{atlas.findRegion("shopSpotEmpty"), atlas.findRegion("shopSpotEmpty_rare"), atlas.findRegion("shopSpotEmpty_legendary")};
        tex_symbolSelected = atlas.findRegion("symbol_selected");
        tex_pin = atlas.findRegion("pin");
        tex_jumpingPad = atlas.findRegion("jumpingPad");
        tex_text_slowdown = atlas.findRegion("text_slowdown");
        tex_meterDot = atlas.findRegion("meterDot");
        tex_meter_case = atlas.findRegion("meter_case");
        tex_meter_ball = atlas.findRegion("meter_ball");
        tex_meter_fruit = atlas.findRegion("meter_fruit");
        tex_meter_dot = new TextureRegion[]{atlas.findRegion("meter_dot", 0), atlas.findRegion("meter_dot", 1), atlas.findRegion("meter_dot", 2)};
        tex_oxigenoxide = atlas.findRegion("oxigenoxide");
        tex_orbCountBackground = atlas.findRegion("orbCountBackground");
        tex_goldenEgg = atlas.findRegion("goldenEgg");
        tex_blueEgg = atlas.findRegion("blueEgg");
        tex_ballCapsule = atlas.findRegion("ballCapsule");
        tex_ballCapsule_shine = atlas.findRegion("ballCapsule_shine");
        tex_orb = atlas.findRegion("orb");
        tex_orb_big = atlas.findRegion("orb_big");
        tex_orb_crystal = atlas.findRegion("orb_crystal");
        tex_orb_bigCrystal = atlas.findRegion("orb_bigCrystal");
        tex_text_comingsoon = atlas.findRegion("text_comingsoon");
        tex_orbCounter = atlas.findRegion("orbCounter");
        tex_text_orbs = atlas.findRegion("text_orbs");
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
        tex_speechBubbleOrb = atlas.findRegion("speechBubbleOrb");
        tex_orbCountBar = atlas.findRegion("orbCountBar");
        tex_pedestal = atlas.findRegion("pedestal");
        tex_shopMarginBottom = atlas.findRegion("shopMarginBottom");
        tex_shopMarginTop = atlas.findRegion("shopMarginTop");
        tex_ad = atlas.findRegion("ad");
        tex_scooper = atlas.findRegion("scooper");
        tex_scooper_0 = atlas.findRegion("scooper", 0);
        tex_scooperShine_0 = atlas.findRegion("scooperShine", 0);
        tex_scooper_2 = atlas.findRegion("scooper", 2);
        tex_scooperShine_2 = atlas.findRegion("scooperShine", 2);
        tex_scooper_3 = atlas.findRegion("scooper", 3);
        tex_capsule = atlas.findRegion("capsule");
        tex_capsule_bottom = atlas.findRegion("capsule_bottom");
        tex_capsule_top = atlas.findRegion("capsule_top");
        tex_fountain = atlas.findRegion("fountain");
        tex_ballDepot_ball = atlas.findRegion("ballDepot_ball");
        tex_ballDepot_frame = atlas.findRegion("ballDepot_frame");
        tex_ballDepot_line = atlas.findRegion("ballDepot_line");
        tex_ballDepot_back = atlas.findRegion("ballDepot_back");
        tex_ballDepot_bar = atlas.findRegion("ballDepot_bar");
        tex_orbDisplay = atlas.findRegion("orbDisplay");

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
                atlas.findRegion("explosion", 10),
                atlas.findRegion("explosion", 11),
                atlas.findRegion("explosion", 12),
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

        tex_clock = new TextureRegion[]{
                atlas.findRegion("clock_small"),
                atlas.findRegion("clock_medium"),
                atlas.findRegion("clock_large"),
        };

        tex_particle_hit = new TextureRegion[]{
                atlas.findRegion("particle_hit", 1),
                atlas.findRegion("particle_hit", 2),
                atlas.findRegion("particle_hit", 3),
                atlas.findRegion("particle_hit", 4),
                atlas.findRegion("particle_hit", 5),
                atlas.findRegion("particle_hit", 6),
                atlas.findRegion("particle_hit", 7),
        };

        tex_particle_megaHit = new TextureRegion[]{
                atlas.findRegion("megaHit", 1),
                atlas.findRegion("megaHit", 2),
                atlas.findRegion("megaHit", 3),
                atlas.findRegion("megaHit", 4),
                atlas.findRegion("megaHit", 5),
                atlas.findRegion("megaHit", 6),
                atlas.findRegion("megaHit", 7),
                atlas.findRegion("megaHit", 8),
                atlas.findRegion("megaHit", 9),
        };

        tex_powerOrb = new TextureRegion[]{
                atlas.findRegion("powerOrb", 0),
                atlas.findRegion("powerOrb", 1),
        };

        tex_smallFruit = new TextureRegion[]{
                atlas.findRegion("apple_small"),
                atlas.findRegion("lemon_small"),
                atlas.findRegion("strawberry_small"),
        };

        tex_plusMessage = new TextureRegion[]{
                atlas.findRegion("plusMessage", 1),
                atlas.findRegion("plusMessage", 2),
                atlas.findRegion("plusMessage", 5),
                atlas.findRegion("plusMessage", 10),
        };

        tex_bullet = new TextureRegion[]{
                atlas.findRegion("bullet", 1),
                atlas.findRegion("bullet", 2),
        };

        tex_bulletExplosion = new TextureRegion[]{
                atlas.findRegion("bulletExplosion", 1),
                atlas.findRegion("bulletExplosion", 2),
                atlas.findRegion("bulletExplosion", 3),
                atlas.findRegion("bulletExplosion", 4),
                atlas.findRegion("bulletExplosion", 5),
        };

        tex_ballExplosion = new TextureRegion[]{
                atlas.findRegion("ballExplosion", 1),
                atlas.findRegion("ballExplosion", 2),
                atlas.findRegion("ballExplosion", 3),
                atlas.findRegion("ballExplosion", 4),
                atlas.findRegion("ballExplosion", 5),
                atlas.findRegion("ballExplosion", 6),
        };


        tex_mediumSplit = new TextureRegion[]{
                atlas.findRegion("mediumSplit", 1),
                atlas.findRegion("mediumSplit", 2),
                atlas.findRegion("mediumSplit", 3),
                atlas.findRegion("mediumSplit", 4),
                atlas.findRegion("mediumSplit", 5),
                atlas.findRegion("mediumSplit", 6),
                atlas.findRegion("mediumSplit", 7),
        };

        tex_largeSplit = new TextureRegion[]{
                atlas.findRegion("largeSplit", 1),
                atlas.findRegion("largeSplit", 2),
                atlas.findRegion("largeSplit", 3),
                atlas.findRegion("largeSplit", 4),
                atlas.findRegion("largeSplit", 5),
                atlas.findRegion("largeSplit", 6),
                atlas.findRegion("largeSplit", 7),
                atlas.findRegion("largeSplit", 8),
        };

        tex_orbPickup = new TextureRegion[]{
                atlas.findRegion("orbPickup", 1),
                atlas.findRegion("orbPickup", 2),
                atlas.findRegion("orbPickup", 3),
                atlas.findRegion("orbPickup", 4),
                atlas.findRegion("orbPickup", 5),
                atlas.findRegion("orbPickup", 6),
                atlas.findRegion("orbPickup", 7),
                atlas.findRegion("orbPickup", 8),
                atlas.findRegion("orbPickup", 9),
                atlas.findRegion("orbPickup", 10),
                atlas.findRegion("orbPickup", 11),
        };

        tex_pulse = new TextureRegion[]{
                atlas.findRegion("pulse", 1),
                atlas.findRegion("pulse", 2),
                atlas.findRegion("pulse", 3),
                atlas.findRegion("pulse", 4),
                atlas.findRegion("pulse", 5),
                atlas.findRegion("pulse", 6),
                atlas.findRegion("pulse", 7),
                atlas.findRegion("pulse", 8),
                atlas.findRegion("pulse", 9),
                atlas.findRegion("pulse", 10),
                atlas.findRegion("pulse", 11),
                atlas.findRegion("pulse", 12),
                atlas.findRegion("pulse", 13),
                atlas.findRegion("pulse", 14),
                atlas.findRegion("pulse", 15),
                atlas.findRegion("pulse", 16),
        };

        tex_fruit = new TextureRegion[]{
                tex_apple, tex_lemon, tex_strawberry
        };

        sprite_watermark = new Sprite(tex_watermark);
        sprite_watermark.setAlpha(.5f);

        bodyDef_dynamic = new BodyDef();
        bodyDef_dynamic.type = BodyDef.BodyType.DynamicBody;

        bodyDef_static = new BodyDef();
        bodyDef_static.type = BodyDef.BodyType.StaticBody;

        new World(new Vector2(0, 0), false);

        shape_ball = new CircleShape[]{new CircleShape(), new CircleShape(), new CircleShape(), new CircleShape()};
        shape_ball[0].setRadius(4 * Main.MPP);
        shape_ball[1].setRadius(7 * Main.MPP);
        shape_ball[2].setRadius(9.5f * Main.MPP);
        shape_ball[3].setRadius(13.5f * Main.MPP);


        fixtureDef_ball = new FixtureDef[]{new FixtureDef(), new FixtureDef(), new FixtureDef(), new FixtureDef()};
        for (int i = 0; i < 4; i++) { // ONLY FOR MAINBALLS
            fixtureDef_ball[i].density = 1;
            fixtureDef_ball[i].shape = shape_ball[i];
            fixtureDef_ball[i].restitution = 1;
            fixtureDef_ball[i].filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
            fixtureDef_ball[i].filter.categoryBits = (MASK_ZERO);
        }

        fixtureDef_ball_passThrough = new FixtureDef[]{new FixtureDef(), new FixtureDef(), new FixtureDef(), new FixtureDef()};
        for (int i = 0; i < 4; i++) { // ONLY FOR MAINBALLS
            fixtureDef_ball_passThrough[i].density = 1;
            fixtureDef_ball_passThrough[i].shape = shape_ball[i];
            fixtureDef_ball_passThrough[i].restitution = 1;
            fixtureDef_ball_passThrough[i].filter.maskBits = (MASK_ZERO | MASK_WALL);
            fixtureDef_ball_passThrough[i].filter.categoryBits = (MASK_PASSTHROUGH);
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

        fixtureDef_scooperSpike = new FixtureDef();
        fixtureDef_scooperSpike.density = 1;
        fixtureDef_scooperSpike.restitution = 1;
        fixtureDef_scooperSpike.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_scooperSpike.filter.categoryBits = MASK_ZERO;

        fixtureDef_egg = new FixtureDef();
        fixtureDef_egg.density = 1;
        fixtureDef_egg.restitution = 1;
        fixtureDef_egg.shape = new CircleShape();
        fixtureDef_egg.shape.setRadius(5 * Main.MPP);
        fixtureDef_egg.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_egg.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_opponents = new FixtureDef();
        fixtureDef_badBall_opponents.density = 1;
        fixtureDef_badBall_opponents.shape = new CircleShape();
        fixtureDef_badBall_opponents.shape.setRadius(4 * Main.MPP);
        fixtureDef_badBall_opponents.restitution = 1;
        fixtureDef_badBall_opponents.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_badBall_opponents.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_normal = new FixtureDef();
        fixtureDef_badBall_normal.density = 1;
        fixtureDef_badBall_normal.shape = new CircleShape();
        fixtureDef_badBall_normal.shape.setRadius(7 * Main.MPP);
        fixtureDef_badBall_normal.restitution = 1;
        fixtureDef_badBall_normal.filter.maskBits = MASK_BAD_BALL;
        fixtureDef_badBall_normal.filter.categoryBits = MASK_BAD_BALL;

        fixtureDef_collectable = new FixtureDef();
        fixtureDef_collectable.density = 1;
        fixtureDef_collectable.isSensor = true;
        fixtureDef_collectable.shape = new CircleShape();
        fixtureDef_collectable.shape.setRadius(7 * Main.MPP);
        fixtureDef_collectable.filter.maskBits = (MASK_ZERO);
        fixtureDef_collectable.filter.categoryBits = MASK_ZERO;

        fixtureDef_jumpingPad = new FixtureDef();
        fixtureDef_jumpingPad.isSensor = true;
        fixtureDef_jumpingPad.shape = new CircleShape();
        fixtureDef_jumpingPad.shape.setRadius(7f * Main.MPP);
        fixtureDef_jumpingPad.filter.maskBits = MASK_ZERO;
        fixtureDef_jumpingPad.filter.categoryBits = MASK_ZERO;

        fixtureDef_powerOrb = new FixtureDef();
        fixtureDef_powerOrb.shape = new CircleShape();
        fixtureDef_powerOrb.shape.setRadius(3.5f * Main.MPP);
        fixtureDef_powerOrb.density = 1;
        fixtureDef_powerOrb.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_powerOrb.filter.categoryBits = MASK_ZERO;


        ChainShape chainShape_gap = new ChainShape();
        chainShape_gap.createLoop(new float[]{Main.MPP * 29, Main.MPP * 71, Main.MPP * 29, Main.MPP * 121, Main.MPP * 79, Main.MPP * 121, Main.MPP * 79, Main.MPP * 71});
        fixtureDef_gap = new FixtureDef();
        fixtureDef_gap.density = 1;
        fixtureDef_gap.shape = chainShape_gap;
        fixtureDef_gap.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_gap.filter.categoryBits = MASK_WALL;

        shape_spike = new CircleShape();
        shape_spike.setRadius(1f * Main.MPP);

        fixtureDef_spike = new FixtureDef();
        fixtureDef_spike.density = 1;
        fixtureDef_spike.shape = shape_spike;
        fixtureDef_spike.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_spike.filter.categoryBits = MASK_ZERO;

        CircleShape shape_tire = new CircleShape();
        shape_tire.setRadius(23 * Main.MPP);

        fixtureDef_tire = new FixtureDef();
        fixtureDef_tire.density = 2;
        fixtureDef_tire.shape = shape_tire;
        fixtureDef_tire.restitution = 2;
        fixtureDef_tire.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_tire.filter.categoryBits = MASK_ZERO;

        PolygonShape shape_plank = new PolygonShape();
        shape_plank.setAsBox(64 * Main.MPP / 2, 19 * Main.MPP / 2);
        fixtureDef_plank = new FixtureDef();
        fixtureDef_plank.density = 1;
        fixtureDef_plank.shape = shape_plank;
        fixtureDef_plank.restitution = 1;
        fixtureDef_plank.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_plank.filter.categoryBits = MASK_ZERO;

        PolygonShape shape_box = new PolygonShape();
        shape_box.setAsBox(1, 1);
        fixtureDef_box = new FixtureDef();
        fixtureDef_box.density = 1;
        fixtureDef_box.shape = shape_box;
        fixtureDef_box.restitution = 1;
        fixtureDef_box.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_box.filter.categoryBits = MASK_ZERO;

        shape_pin = new CircleShape();
        shape_pin.setRadius(5 * Main.MPP);

        shape_bullet = new PolygonShape();
        ((PolygonShape) shape_bullet).setAsBox(3.5f * Main.MPP, 2.5f * Main.MPP);

        fixtureDef_border = new FixtureDef();
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{0, 0, Main.width * Main.MPP, 0, Main.width * Main.MPP, Main.height * Main.MPP, 0, Main.height * Main.MPP});
        fixtureDef_border.shape = chainShape;
        fixtureDef_border.density = 1;
        fixtureDef_border.restitution = 1;
        fixtureDef_border.friction = 0;
        fixtureDef_border.filter.maskBits = (short) (MASK_ZERO | MASK_PASSTHROUGH);
        fixtureDef_border.filter.categoryBits = MASK_WALL;

        fixtureDef_cage = new FixtureDef();
        ChainShape chainShape_cage = new ChainShape();
        chainShape_cage.createLoop(new float[]{0, 0, 100 * Main.MPP, 0, 100 * Main.MPP, 100 * Main.MPP, 0, 100 * Main.MPP});
        fixtureDef_cage.shape = chainShape_cage;
        fixtureDef_cage.density = 1;
        fixtureDef_cage.restitution = 1;
        fixtureDef_cage.friction = 0;
        fixtureDef_cage.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_cage.filter.categoryBits = MASK_ZERO;

        palette_table = new Color[8][4]; // update the length when adding or removing a palette !

        palette_table[0][0] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1); //clasic
        palette_table[0][1] = new Color(98 / 255f, 158 / 255f, 118 / 255f, 1);
        palette_table[0][2] = new Color(98 / 255f, 158 / 255f, 118 / 255f, 1);
        palette_table[0][3] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1);

        palette_table[1][0] = new Color(193 / 255f, 71 / 255f, 89 / 255f, 1); // red/pink
        palette_table[1][1] = new Color(204 / 255f, 87 / 255f, 129 / 255f, 1);
        palette_table[1][2] = new Color(204 / 255f, 87 / 255f, 129 / 255f, 1);
        palette_table[1][3] = new Color(193 / 255f, 71 / 255f, 89 / 255f, 1);

        palette_table[2][0] = new Color(200 / 255f, 36 / 255f, 36 / 255f, 1); // red
        palette_table[2][1] = new Color(213 / 255f, 77 / 255f, 40 / 255f, 1);
        palette_table[2][2] = new Color(213 / 255f, 77 / 255f, 40 / 255f, 1);
        palette_table[2][3] = new Color(200 / 255f, 36 / 255f, 36 / 255f, 1);

        palette_table[3][0] = new Color(196 / 255f, 116 / 255f, 56 / 255f, 1); // orange
        palette_table[3][1] = new Color(211 / 255f, 158 / 255f, 60 / 255f, 1);
        palette_table[3][2] = new Color(211 / 255f, 158 / 255f, 60 / 255f, 1);
        palette_table[3][3] = new Color(196 / 255f, 116 / 255f, 56 / 255f, 1);

        palette_table[4][0] = new Color(153 / 255f, 191 / 255f, 57 / 255f, 1); // yellow/green
        palette_table[4][1] = new Color(178 / 255f, 204 / 255f, 80 / 255f, 1);
        palette_table[4][2] = new Color(178 / 255f, 204 / 255f, 80 / 255f, 1);
        palette_table[4][3] = new Color(153 / 255f, 191 / 255f, 57 / 255f, 1);

        /*
        palette_table[5][0] = new Color(6 / 255f, 181 / 255f, 94 / 255f, 1); // bright green
        palette_table[5][1] = new Color(14 / 255f, 202 / 255f, 81 / 255f, 1);
        palette_table[5][2] = new Color(14 / 255f, 202 / 255f, 81 / 255f, 1);
        palette_table[5][3] = new Color(6 / 255f, 181 / 255f, 94 / 255f, 1);
*/
        palette_table[5][0] = new Color(13 / 255f, 185 / 255f, 125 / 255f, 1); // aqua
        palette_table[5][1] = new Color(14 / 255f, 202 / 255f, 148 / 255f, 1);
        palette_table[5][2] = new Color(14 / 255f, 202 / 255f, 148 / 255f, 1);
        palette_table[5][3] = new Color(13 / 255f, 185 / 255f, 125 / 255f, 1);

        palette_table[6][0] = new Color(1801892607); //blue
        palette_table[6][1] = new Color(2139284223);
        palette_table[6][2] = new Color(1801892607);
        palette_table[6][3] = new Color(1801892607);

        palette_table[7][0] = new Color(111 / 255f, 69 / 255f, 152 / 255f, 1); // purple
        palette_table[7][1] = new Color(170 / 255f, 92 / 255f, 183 / 255f, 1);
        palette_table[7][2] = new Color(170 / 255f, 92 / 255f, 183 / 255f, 1);
        palette_table[7][3] = new Color(111 / 255f, 69 / 255f, 152 / 255f, 1);

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

        palette_mainBall = new Color[][]{
                new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4]
        };

/*
        palette_mainBall[0][0] = new Color(0, 0, 0, 1);
        palette_mainBall[0][1] = new Color(191 / 255f, 0, 0, 1);
        palette_mainBall[0][2] = new Color(255 / 255f, 108 / 255f, 0, 1);
        palette_mainBall[0][3] = palette_mainBall[0][2];

        palette_mainBall[1][0] = new Color(0, 0, 0, 1);
        palette_mainBall[1][1] = new Color(16 / 255f, 148 / 255f, 63 / 255f, 1);
        palette_mainBall[1][2] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);
        palette_mainBall[1][3] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);

        palette_mainBall[2][0] = new Color(0, 0, 0, 1);
        palette_mainBall[2][1] = new Color(24 / 255f, 13 / 255f, 157 / 255f, 1);
        palette_mainBall[2][2] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);
        palette_mainBall[2][3] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);

        palette_mainBall[3][0] = new Color(0, 0, 0, 1);
        palette_mainBall[3][1] = new Color(19 / 255f, 123 / 255f, 202 / 255f, 1);
        palette_mainBall[3][2] = new Color(52 / 255f, 187 / 255f, 209 / 255f, 1);
        palette_mainBall[3][3] = new Color(1, 1, 1, 1);

        palette_mainBall[4][0] = new Color(0, 0, 0, 1);
        palette_mainBall[4][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        palette_mainBall[4][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        palette_mainBall[4][3] = new Color(1, 1, 1, 1);

        palette_mainBall[5][0] = new Color(0, 0, 0, 1);
        palette_mainBall[5][1] = new Color(53 / 255f, 59 / 255f, 72 / 255f, 1);
        palette_mainBall[5][2] = new Color(130 / 255f, 167 / 255f, 157 / 255f, 1);
        palette_mainBall[5][3] = new Color(1, 1, 1, 1);

        palette_mainBall[6][0] = new Color(0, 0, 0, 1);
        palette_mainBall[6][1] = new Color(29 / 255f, 177 / 255f, 82 / 255f, 1);
        palette_mainBall[6][2] = new Color(71 / 255f, 221 / 255f, 189 / 255f, 1);
        palette_mainBall[6][3] = new Color(1, 1, 1, 1);

        palette_mainBall[7][0] = new Color(0, 0, 0, 1);
        palette_mainBall[7][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        palette_mainBall[7][2] = new Color(204 / 255f, 49 / 255f, 255 / 255f, 1);
        palette_mainBall[7][3] = new Color(1, 1, 1, 1);

        palette_mainBall[8][0] = new Color(0, 0, 0, 1);
        palette_mainBall[8][1] = new Color(14 / 255f, 108 / 255f, 44 / 255f, 1);
        palette_mainBall[8][2] = new Color(0 / 255f, 221 / 255f, 32 / 255f, 1);
        palette_mainBall[8][3] = new Color(1, 1, 1, 1);
*/

        palette_mainBall[0][0] = Color.BLACK;
        palette_mainBall[0][1] = new Color(0 / 255f, 193 / 255f, 124 / 255f, 1);
        palette_mainBall[0][2] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);
        palette_mainBall[0][3] = palette_mainBall[0][2];

        palette_mainBall[1][0] = Color.BLACK;
        palette_mainBall[1][1] = new Color(255 / 255f, 18 / 255f, 18 / 255f, 1);
        palette_mainBall[1][2] = new Color(255 / 255f, 128 / 255f, 0 / 255f, 1);
        palette_mainBall[1][3] = palette_mainBall[1][2];

        palette_mainBall[2][0] = Color.BLACK;
        palette_mainBall[2][1] = new Color(236 / 255f, 1 / 255f, 96 / 255f, 1);
        palette_mainBall[2][2] = new Color(255 / 255f, 39 / 255f, 240 / 255f, 1);
        palette_mainBall[2][3] = palette_mainBall[2][2];

        palette_mainBall[3][0] = Color.BLACK;
        palette_mainBall[3][1] = new Color(230 / 255f, 117 / 255f, 0 / 255f, 1);
        palette_mainBall[3][2] = new Color(240 / 255f, 231 / 255f, 26 / 255f, 1);
        palette_mainBall[3][3] = Color.WHITE;

        palette_mainBall[4][0] = Color.BLACK;
        palette_mainBall[4][1] = new Color(0 / 255f, 193 / 255f, 83 / 255f, 1);
        palette_mainBall[4][2] = new Color(64 / 255f, 225 / 255f, 241 / 255f, 1);
        palette_mainBall[4][3] = Color.WHITE;

        palette_mainBall[5][0] = Color.BLACK;
        palette_mainBall[5][1] = new Color(126 / 255f, 0 / 255f, 221 / 255f, 1);
        palette_mainBall[5][2] = new Color(222 / 255f, 21 / 255f, 255 / 255f, 1);
        palette_mainBall[5][3] = new Color(1, 155 / 255f, 198 / 255f, 1);

        palette_mainBall[6][0] = Color.BLACK;
        palette_mainBall[6][1] = new Color(0 / 255f, 162 / 255f, 92 / 255f, 1);
        palette_mainBall[6][2] = new Color(0 / 255f, 255 / 255f, 0 / 255f, 1);
        palette_mainBall[6][3] = new Color(229 / 255f, 1, 182 / 255f, 1);

        palette_mainBall[7][0] = Color.BLACK;
        palette_mainBall[7][1] = new Color(75 / 255f, 91 / 255f, 125 / 255f, 1);
        palette_mainBall[7][2] = new Color(128 / 255f, 195 / 255f, 177 / 255f, 1);
        palette_mainBall[7][3] = new Color(1, 1, 1, 1);

        palette_mainBall[8][0] = Color.BLACK;
        palette_mainBall[8][1] = new Color(30 / 255f, 0 / 255f, 208 / 255f, 1);
        palette_mainBall[8][2] = new Color(101 / 255f, 39 / 255f, 255 / 255f, 1);
        palette_mainBall[8][3] = new Color(137 / 255f, 162 / 255f, 255 / 255f, 1);

        /*
        palette_mainBall[1][0] = new Color(0, 0, 0, 1);
        palette_mainBall[1][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        palette_mainBall[1][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        palette_mainBall[1][3] = new Color(1, 1, 1, 1);
        palette_mainBall[2][0] = new Color(0, 0, 0, 1);
        palette_mainBall[2][1] = new Color(8 / 255f, 100 / 255f, 38 / 255f, 1);
        palette_mainBall[2][2] = new Color(41 / 255f, 199 / 255f, 156 / 255f, 1);
        palette_mainBall[2][3] = new Color(135 / 255f, 228 / 255f, 247 / 255f, 1);
        palette_mainBall[3][0] = new Color(0, 0, 0, 1);
        palette_mainBall[3][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        palette_mainBall[3][2] = new Color(208 / 255f, 8 / 255f, 251 / 255f, 1);
        palette_mainBall[3][3] = new Color(229 / 255f, 192 / 255f, 1, 1);
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

        Main.assets.load("sounds/ballHit.mp3", Sound.class);
        Main.assets.load("sounds/speedup.mp3", Sound.class);
        Main.assets.load("sounds/slowdown.mp3", Sound.class);
        Main.assets.load("sounds/glassBreak.mp3", Sound.class);
        Main.assets.load("sounds/buttonClick.mp3", Sound.class);
        Main.assets.load("sounds/buttonClick2.mp3", Sound.class);
        Main.assets.load("sounds/collect.mp3", Sound.class);
        Main.assets.load("sounds/success.mp3", Sound.class);
        Main.assets.load("sounds/plop.mp3", Sound.class);
        Main.assets.load("sounds/splat.mp3", Sound.class);
        Main.assets.load("sounds/bounce.mp3", Sound.class);
        Main.assets.load("sounds/punch.mp3", Sound.class);
        Main.assets.load("sounds/correct.mp3", Sound.class);
        Main.assets.load("sounds/split.mp3", Sound.class);
        Main.assets.load("sounds/pop.mp3", Sound.class);
        Main.assets.load("sounds/fail.mp3", Sound.class);
        Main.assets.load("sounds/tap.mp3", Sound.class);
        Main.assets.load("sounds/alarm.mp3", Sound.class);

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
        System.out.println("loadingball: " + tex_loadingBall);
        tex_loadingBall_shadow = atlas.findRegion("loadingBall_shadow");
        tex_loadingBall_filled = atlas.findRegion("loadingBallFilled");
        tex_oxigenoxide = atlas.findRegion("oxigenoxide");
        tex_numbers = new TextureRegion[5][];
        tex_numbers[ID.Font.SMALL] = new TextureRegion[]{atlas.findRegion("numbers/number_m", 0), atlas.findRegion("numbers/number_m", 1), atlas.findRegion("numbers/number_m", 2), atlas.findRegion("numbers/number_m", 3), atlas.findRegion("numbers/number_m", 4), atlas.findRegion("numbers/number_m", 5), atlas.findRegion("numbers/number_m", 6), atlas.findRegion("numbers/number_m", 7), atlas.findRegion("numbers/number_m", 8), atlas.findRegion("numbers/number_m", 9)};
        tex_number_small_percent = atlas.findRegion("numbers/number_m_percent");
        shader_c = new ShaderProgram(Gdx.files.internal("shaders/shader_c.vert"), Gdx.files.internal("shaders/shader_c.frag"));
        sound_bounce = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.mp3"));
        sound_ballHit = Gdx.audio.newSound(Gdx.files.internal("sounds/ballHit.mp3"));
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
        shader_circle = new ShaderProgram(Gdx.files.internal("shaders/shader_circle.vert"), Gdx.files.internal("shaders/shader_circle.frag"));
        shader_c_over = new ShaderProgram(Gdx.files.internal("shaders/shader_c_over.vert"), Gdx.files.internal("shaders/shader_c_over.frag"));
        shader_filledCircle = new ShaderProgram(Gdx.files.internal("shaders/shader_filledCircle.vert"), Gdx.files.internal("shaders/shader_filledCircle.frag"));
        shader_clock = new ShaderProgram(Gdx.files.internal("shaders/shader_clock.vert"), Gdx.files.internal("shaders/shader_clock.frag"));
    }

    public static void dispose() {
        atlas.dispose();

        sound_bounce.dispose();
        sound_ballHit.dispose();
        sound_collect.dispose();
        sound_buttonClick1.dispose();
        sound_buttonClick2.dispose();
        sound_glassBreak.dispose();
        sound_plop.dispose();
        sound_slowdown.dispose();
        sound_speedup.dispose();
        sound_splat.dispose();
        sound_success.dispose();
        sound_punch.dispose();
        sound_correct.dispose();
        sound_split.dispose();
        sound_pop.dispose();
        sound_fail.dispose();
        sound_tap.dispose();
        sound_alarm.dispose();

        shader_c.dispose();
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

    public static TextureRegion getOrbTex(int type) {
        switch (type) {
            case 0:
                return Res.tex_orb;
            case 1:
                return Res.tex_orb_big;
            case 2:
                return Res.tex_orb_crystal;
            case 3:
                return Res.tex_orb_bigCrystal;
        }
        return null;
    }

}
