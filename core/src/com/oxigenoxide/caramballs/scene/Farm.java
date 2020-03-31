package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.button.Button_Sell;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Balls;
import com.oxigenoxide.caramballs.object.button.Button_Play;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Reward;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Sell;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.RepeatCounter;

import static com.oxigenoxide.caramballs.Main.balls;
import static com.oxigenoxide.caramballs.Main.holes;
import static com.oxigenoxide.caramballs.Main.rewardOrbs;
import static com.oxigenoxide.caramballs.Main.scrHD;
import static com.oxigenoxide.caramballs.Main.tap;

public class Farm extends Scene {
    public Vector2 pos_field;
    public Vector2 pos_orbs;
    public Vector2 pos_orb;
    Button_Play button_play;
    Button_Balls button_shop;
    Button button_return;
    Button_Sell button_sell;
    FrameBuffer buffer;
    Texture tex_buffer;
    Body cage;
    Hole_Reward hole_reward;
    boolean isSpawningBalls;
    RepeatCounter counter_save;
    static boolean hasBeenInFarm;
    Hole_Sell hole_sell;

    public static final int FIELDWIDTH = 100;

    public Farm() {
        pos_field = new Vector2(4, 62 + scrHD);
        button_return = new Button_Return(new Vector2(5, Main.height - 17));
        button_play = new Button_Play(new Vector2(22, 29));
        button_shop = new Button_Balls(new Vector2(4, 6));
        pos_orbs = new Vector2(88, Main.height - 10);
        pos_orb = new Vector2(0, pos_orbs.y - 1 + 3.5f);
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getHeight(), Gdx.graphics.getHeight(), true);
        counter_save = new RepeatCounter(new ActionListener() {
            @Override
            public void action() {
                saveBalls();
            }
        }, 5);
    }

    @Override
    public void update() {
        counter_save.update();
        if (button_play.isTouching())
            button_play.update();
        if (hole_reward == null)
            if (button_shop.isTouching())
                button_shop.update();
        if (button_return.isTouching())
            button_return.update();
        if (button_sell.isTouching())
            button_sell.update();
        button_play.slide();

        if (hole_reward != null && hole_reward.isDisposed) {
            hole_reward = null;
            isSpawningBalls = true;
            button_play.slideIntoScreen();
        }

        for (Ball_Main bm : Main.mainBalls)
            if (bm.doPermaPassthrough)
                if (bm.pos.y > pos_field.y + bm.radius + 1)
                    bm.setPermaPassthrough(false);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            hole_reward = new Hole_Reward(54, 43, new int[]{1, 2, 3});
            holes.add(hole_reward);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            balls.add(new Ball_Main(tap[0].x, tap[0].y, 0, 0, 0));
        }

        Main.world.step(Main.dt, 3, 8);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(Res.tex_farmField, 2, pos_field.y - 6);
        button_play.render(batch);
        button_shop.render(batch);
        button_return.render(batch);
        button_sell.render(batch);
        batch.draw(Res.tex_text_yourBalls, 17, pos_field.y + 104);
        batch.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Hole h : holes)
            h.render(sr);
        sr.setColor(0, 0, 0, .8f);
        for (Ball b : balls)
            b.renderShadow(sr);
        Main.ballSelector.render(sr);
        sr.end();

        batch.begin();
        for (Entity e : Main.entities_sorted)
            e.render(batch);

        // orb display
        batch.draw(Res.tex_orbCountBar, pos_orbs.x - Res.tex_orbCountBar.getRegionWidth() / 2, pos_orbs.y - 2);
        int width = Funcs.drawNumberSignColor(batch, Main.gameData.orbs, pos_orbs, ID.Font.SMALL, Res.tex_orb, -1, Res.COLOR_ORBNUMBER);
        pos_orb.x = pos_orbs.x - width / 2 + 3.5f;

        for (RewardOrb ro : rewardOrbs)
            ro.render(batch);

        batch.end();
        buffer.end();

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Main.setNoCamEffects();
        batch.begin();
        batch.setShader(Res.shader_pixelate);
        Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);

        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        batch.setShader(null);
        if (Main.DODEBUGRENDER)
            Main.b2dr.render(Main.world, Main.cam.combined);
        batch.end();
        Main.setCamEffects();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

        super.show();
        //cage
        cage = Main.world.createBody(Res.bodyDef_static);
        cage.createFixture(Res.fixtureDef_cage);
        cage.setTransform(pos_field.x * Main.MPP, pos_field.y * Main.MPP, 0);

        //reward (legacy)
        int amount_rewardBalls = Main.rewardBalls.size();
        if (amount_rewardBalls > 0) {
            int[] levels = new int[amount_rewardBalls];

            for (int i = 0; i < amount_rewardBalls; i++) {
                levels[i] = Main.rewardBalls.get(i);
            }
            hole_reward = new Hole_Reward(54, 43, levels);
            holes.add(hole_reward);
            isSpawningBalls = true;
            button_play.placeOutOfScreen();
            Main.rewardBalls.clear();
        }

        //add elapsed time to farm balls
        for (Ball_Main.Ball_Main_Data ball_main_data : Main.gameData.farmBalls) {
            System.out.println(ball_main_data.timeElapsed + " : " + System.currentTimeMillis() + " - " + Main.gameData.time_leftFarm);
            if (Main.gameData.time_leftFarm != 0)
                ball_main_data.timeElapsed += System.currentTimeMillis() - Main.gameData.time_leftFarm;
        }

        //spawn farm balls
        int i = 0;
        for (Ball_Main.Ball_Main_Data ball_data : Main.gameData.farmBalls) {
            System.out.println("timelapsed: " + ball_data.timeElapsed);
            if (!hasBeenInFarm)
                balls.add(new Ball_Main(ball_data.x, ball_data.y, Main.height * 2 + i * 25, ball_data.size, ball_data.level).setTimeElapsed(ball_data.timeElapsed));
            else
                balls.add(new Ball_Main(ball_data.x, ball_data.y, 0, ball_data.size, ball_data.level).setTimeElapsed(ball_data.timeElapsed));
            i++;
        }

        button_sell = new Button_Sell(new Vector2(55, 6));

        hasBeenInFarm = true;

        hole_sell = new Hole_Sell(85, 80 + scrHD);
        holes.add(hole_sell);
    }

    public void saveBalls() {
        Main.gameData.farmBalls.clear();
        for (Ball_Main bm : Main.mainBalls) {
            System.out.println(" save: " + bm.timeElapsed);
            Main.gameData.farmBalls.add(new Ball_Main.Ball_Main_Data(bm.pos.x, bm.pos.y, bm.size, bm.level, bm.timeElapsed));
        }
        // Lesson learned, again, most variables you shouldn't just set to an object
        // Second lesson learned, clear a list before adding objects again if you want to copy
    }

    public void onPause() {
        Main.gameData.time_leftFarm = System.currentTimeMillis();
    }

    public void startSelling() {
        hole_sell.open();
    }

    public void endSelling() {
        hole_sell.close();
    }

    @Override
    public void hide() {
        super.hide();

        saveBalls();
        cage = Main.destroyBody(cage);
        Main.clearEntities();
        Main.gameData.time_leftFarm = System.currentTimeMillis();
    }
}
