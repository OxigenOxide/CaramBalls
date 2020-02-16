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
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.entity.Entity;
import com.oxigenoxide.caramballs.object.entity.ball.Ball;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Balls;
import com.oxigenoxide.caramballs.object.button.Button_Play;
import com.oxigenoxide.caramballs.object.entity.ball.Ball_Main;
import com.oxigenoxide.caramballs.object.entity.hole.Hole;
import com.oxigenoxide.caramballs.object.entity.hole.Hole_Reward;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.RepeatCounter;

public class Farm extends Scene {
    Vector2 pos_field;
    Button_Play button_play;
    Button button_shop;
    Button button_return;
    FrameBuffer buffer;
    Texture tex_buffer;
    Body cage;
    Hole_Reward hole_reward;
    boolean isSpawningBalls;
    RepeatCounter counter_save;
    static boolean hasBeenInFarm;

    public Farm() {
        pos_field = new Vector2(4, 62);
        button_play = new Button_Play(new Vector2(22, 29));
        button_shop = new Button_Balls(new Vector2(12, 4));
        button_return = new Button_Return(new Vector2(5, Main.height - 19));
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            hole_reward = new Hole_Reward(54, 43, new int[]{1, 2, 3});
            Main.holes.add(hole_reward);
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
        batch.draw(Res.tex_text_yourBalls, 17, pos_field.y + 104);
        batch.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);


        Main.ballSelector.render(sr);
        for (Hole h : Main.holes)
            h.render(sr);
        sr.setColor(0, 0, 0, .8f);
        for (Ball b : Main.balls)
            b.renderShadow(sr);
        sr.end();
        batch.begin();
        for (Entity e : Main.entities_sorted)
            e.render(batch);
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

        Main.b2dr.render(Main.world, Main.cam.combined);
        batch.end();
        Main.setCamEffects();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {
        cage = Main.world.createBody(Res.bodyDef_static);
        cage.createFixture(Res.fixtureDef_cage);
        cage.setTransform(pos_field.x * Main.METERSPERPIXEL, pos_field.y * Main.METERSPERPIXEL, 0);

        int amount_rewardBalls = Main.rewardBalls.size();
        if (amount_rewardBalls > 0) {
            int[] levels = new int[amount_rewardBalls];

            for (int i = 0; i < amount_rewardBalls; i++) {
                levels[i] = Main.rewardBalls.get(i);
            }
            hole_reward = new Hole_Reward(54, 43, levels);
            Main.holes.add(hole_reward);
            isSpawningBalls = true;
            button_play.placeOutOfScreen();
            Main.rewardBalls.clear();
        }

        int i = 0;
        for (Ball_Main.Ball_Main_Data ball_data : Main.gameData.farmBalls) {
            if (!hasBeenInFarm)
                Main.balls.add(new Ball_Main(ball_data.x, ball_data.y, Main.height * 2 + i * 25, ball_data.size, ball_data.level));
            else
                Main.balls.add(new Ball_Main(ball_data.x, ball_data.y, 0, ball_data.size, ball_data.level));
            i++;
        }
        hasBeenInFarm = true;

    }

    public void saveBalls() {
        Main.gameData.farmBalls.clear();
        for (Ball_Main bm : Main.mainBalls) {
            Main.gameData.farmBalls.add(new Ball_Main.Ball_Main_Data(bm.pos.x, bm.pos.y, bm.size, bm.level));
        }
        // Lesson learned, again, most variables you shouldn't just set to an object
        // Second lesson learned, clear a list before adding objects again if you want to copy
    }

    @Override
    public void hide() {
        saveBalls();
        cage = Main.destroyBody(cage);
        Main.clearEntities();
    }
}
