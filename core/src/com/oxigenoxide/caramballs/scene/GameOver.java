package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.YouLost;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Balls;
import com.oxigenoxide.caramballs.object.button.Button_LeaderBoards;
import com.oxigenoxide.caramballs.object.button.Button_Replay;

import java.util.ArrayList;

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
    float a_highscoreColor;
    float count_highscoreColor;
    float height;
    float yHighscore;


    public GameOver() {

        height = (Main.height - 192) / 2;
        System.out.println("height: " + Main.height + " " + height);
        youLost = new YouLost(height);
        button_replay = new Button_Replay(new Vector2(Main.width / 2 - Res.tex_button_replay.getRegionWidth() / 2, 27 + height));
        button_leaderBoards = new Button_LeaderBoards(new Vector2(Main.width / 2 - Res.tex_button_replay.getRegionWidth() / 2, 5 + height));

        pos_textHighscore = new Vector2(Main.width / 2, height + 174);
        yHighscore = height + 153;
        pos_rank = new Vector2(Main.width * 1 / 3f, height + 142);
        pos_numScore = new Vector2(Main.width / 2, 121 + height);
        pos_numOrbs = new Vector2(Main.width / 2, 102 + height);
        pos_ball = new Vector2(Main.width / 2, 81 + height);
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
        System.out.println("render gameover");
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        button_replay.render(batch);
        button_leaderBoards.render(batch);

        ArrayList<Integer> scoreDigits = Main.getDigits(Game.score);
        int score_width = Main.getTextWidth(scoreDigits, 4);
        batch.draw(Res.tex_text_score, Main.width / 2 - (score_width + 55) / 2, pos_numScore.y);
        Main.drawNumber(batch, scoreDigits, new Vector2(Main.width / 2 - (score_width + 55) / 2 + 55, pos_numScore.y), ID.Font.POP);

        Main.drawNumberSign(batch, Game.orbsCollected, pos_numOrbs, ID.Font.POP, Res.tex_symbolPlus, 0);

        ArrayList<Integer> rank_digits = Main.getDigits((int) Main.gm.getRank());
        int rank_width = Main.getTextWidth(rank_digits, 0) + 1 + Res.tex_text_youare.getRegionWidth();
        Main.drawNumber(batch, rank_digits, new Vector2(Main.width / 2 - rank_width / 2 + Res.tex_text_youare.getRegionWidth() + 1, pos_rank.y), ID.Font.NORMAL);
        batch.draw(Res.tex_text_youare, Main.width / 2 - rank_width / 2, pos_rank.y);

        batch.setShader(Res.shader_overlay);
        Res.shader_overlay.setUniformf("newColor", 1, 0, 0, a_highscoreColor);
        Main.drawNumber(batch, Main.gameData.highscore, new Vector2(Main.width / 2, yHighscore), ID.Font.POP_LARGE);
        batch.setShader(null);
        batch.draw(Res.tex_text_highscore, pos_textHighscore.x - Res.tex_text_highscore.getRegionWidth() / 2, pos_textHighscore.y);

        batch.end();

    }

    public void show() {
        visible = true;
        Game.setDarkOverlay();
    }

    public void hide() {
        visible = false;
        Game.setNoDarkOverlay();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public Vector2 getBallPos() {
        return pos_ball;
    }
}
