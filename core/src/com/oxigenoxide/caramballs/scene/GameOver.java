package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.YouLost;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_LeaderBoards;
import com.oxigenoxide.caramballs.object.button.Button_Replay;
import com.oxigenoxide.caramballs.utils.Funcs;

import java.util.ArrayList;

import static com.oxigenoxide.caramballs.Main.gameData;

public class GameOver extends Scene {
    YouLost youLost;
    Button button_replay;
    Button button_leaderBoards;
    float alpha;
    boolean visible;
    Vector2 pos_numScore;
    Vector2 pos_numOrbs;
    Vector2 pos_rank;
    Vector2 pos_textHighscore;
    Vector2 pos_ball;
    Vector2 pos_textScore;
    Vector2 pos_pedestal;
    float a_highscoreColor;
    float count_highscoreColor;
    float height;
    float yHighscore;
    FrameBuffer buffer;
    Texture tex_buffer;
    int scoreLevel;
    int highscoreLevel;

    public GameOver() {
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getHeight(), Gdx.graphics.getHeight(), true);
        height = Main.scrHD;
        youLost = new YouLost(height);
        button_replay = new Button_Replay(new Vector2(Main.width / 2 - Res.tex_button_replay.getRegionWidth() / 2, 27 + height));
        button_leaderBoards = new Button_LeaderBoards(new Vector2(Main.width / 2 - Res.tex_button_replay.getRegionWidth() / 2, 5 + height));

        pos_textHighscore = new Vector2(Main.width / 2, height + 174);
        yHighscore = height + 153;
        pos_rank = new Vector2(Main.width * 1 / 3f, height + 142);
        pos_textScore = new Vector2(Main.width / 2, 125 + height);
        pos_numScore = new Vector2(Main.width / 2, 109 + height);
        pos_numOrbs = new Vector2(Main.width / 2, 102 + height);
        pos_ball = new Vector2(Main.width / 2, 87 + height);
        pos_pedestal = new Vector2(40, 65 + height);

    }

    @Override
    public void update() {
        super.update();
        youLost.update();

        if (button_replay.isTouching())
            button_replay.update();

        if (!Main.noScore)
            if (button_leaderBoards.isTouching())
                button_leaderBoards.update();

        if (visible) {
            alpha = Math.min(1, alpha + .025f);
        } else {
            //alpha = Math.max(0, alpha - .05f);
            alpha = 0;
        }

        count_highscoreColor = (float) ((count_highscoreColor + Main.dt_one * .1f) % (Math.PI * 2));
        a_highscoreColor = .50f + .10f * (float) Math.sin(count_highscoreColor);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Buttons
        button_replay.render(batch);
        button_leaderBoards.render(batch);

        // Score
        batch.draw(Res.tex_text_score, pos_textScore.x - Res.tex_text_score.getRegionWidth() / 2, pos_textScore.y);

        // Numbers
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.PALETTE_SCORE[scoreLevel]);
        Funcs.drawNumber(batch, Game.orbsCollected, pos_numScore, ID.Font.POP);
        batch.setShader(null);
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.PALETTE_SCORE[highscoreLevel]);
        Funcs.drawNumber(batch, Main.gameData.highscore, new Vector2(Main.width / 2, yHighscore), ID.Font.POP_LARGE);
        batch.setShader(null);

        // Ball
        batch.draw(Res.tex_pedestal, pos_pedestal.x, pos_pedestal.y);

        // Rank
        ArrayList<Integer> rank_digits = Funcs.getDigits((int) Main.gm.getRank());
        int rank_width = Funcs.getTextWidth(rank_digits, 0) + 1 + Res.tex_text_youare.getRegionWidth();
        Funcs.drawNumber(batch, rank_digits, new Vector2(Main.width / 2 - rank_width / 2 + Res.tex_text_youare.getRegionWidth() + 1, pos_rank.y), ID.Font.NORMAL);
        batch.draw(Res.tex_text_youare, Main.width / 2 - rank_width / 2, pos_rank.y);

        // Highscore
        batch.draw(Res.tex_text_highscore, pos_textHighscore.x - Res.tex_text_highscore.getRegionWidth() / 2, pos_textHighscore.y);

        batch.end();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        Main.rewardBall.render(sr);
        sr.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

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

    public void show() {
        visible = true;

        Game.setDarkOverlay();

        if (Game.orbsCollected > gameData.highscore) {
            gameData.highscore = Game.orbsCollected;
            if (!Main.noScore)
                Main.gm.submitScore(Game.orbsCollected);
        }
        Main.setAdVisibility(true);
        Main.setNoMusic();

        scoreLevel = getScoreLevel(Game.orbsCollected);
        highscoreLevel = getScoreLevel(Main.gameData.highscore);
    }

    public void hide() {
        visible = false;
        Game.setNoDarkOverlay();
    }

    int getScoreLevel(float score) {
        if (score > 10000) return 6;
        if (score > 5000) return 5;
        if (score > 1000) return 4;
        if (score > 500) return 3;
        if (score > 100) return 2;
        if (score > 50) return 1;
        return 0;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public Vector2 getBallPos() {
        return pos_ball;
    }
}
