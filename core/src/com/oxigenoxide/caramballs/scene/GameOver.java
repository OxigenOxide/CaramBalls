package com.oxigenoxide.caramballs.scene;

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
    Button_Balls button_balls;
    Button button_leaderBoards;
    float alpha;
    boolean visible;
    Vector2 pos_numScore;
    Vector2 pos_numOrbs;
    Vector2 pos_rank;
    float a_highscoreColor;
    float count_highscoreColor;
    float height;

    public GameOver() {

        height = (Main.height - 192) / 2;
        youLost = new YouLost(height);
        button_replay = new Button_Replay(new Vector2(Main.width / 2 - Res.tex_button_replay.getWidth() / 2, 45 + height));
        button_balls = new Button_Balls(new Vector2(Main.width / 2 - Res.tex_button_replay.getWidth() / 2, 1 + height));
        button_leaderBoards = new Button_LeaderBoards(new Vector2(Main.width / 2 - Res.tex_button_replay.getWidth() / 2, 23 + height));
        pos_numScore = new Vector2(Main.width / 2, 92 + height);
        pos_numOrbs = new Vector2(Main.width / 2, 75 + height);
        pos_rank = new Vector2(Main.width * 1 / 3f, Main.height / 2 + 25);

        if (Main.noScore) {
            pos_numOrbs.y = Main.height * 3 / 5f;
            button_balls.pos.y = Main.height / 2 - 74;
        }
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


        if (!Main.noCollection) {
            if (button_balls.isTouching())
                button_balls.update();
        }
        if (visible) {
            alpha = Math.min(1, alpha + .025f);
        } else {
            //alpha = Math.max(0, alpha - .05f);
            alpha = 0;
        }

        count_highscoreColor=(float)((count_highscoreColor+Main.dt_one*.1f)%(Math.PI*2));
        a_highscoreColor = .50f+.10f*(float)Math.sin(count_highscoreColor);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        if (alpha > 0) {
            batch.begin();
            batch.setShader(Res.shader_a);
            Res.shader_a.setUniformf("a", alpha);
            button_replay.render(batch);
            if (!Main.noScore)
                button_leaderBoards.render(batch);
            if (!Main.noCollection)
                button_balls.render(batch);

            if (!Main.noScore) {
                ArrayList<Integer> scoreDigits = Main.getDigits(Game.score);
                int score_width = Main.getTextWidth(scoreDigits, 4);
                batch.draw(Res.tex_text_score, Main.width / 2 - (score_width + 55) / 2, Main.height / 2 + 4);
                Main.drawNumber(batch, scoreDigits, new Vector2(Main.width / 2 - (score_width + 55) / 2 + 55, Main.height / 2 + 4), ID.Font.POP);
            }

            if (!Main.noCollection)
                Main.drawNumberSign(batch, Game.orbsCollected, pos_numOrbs, ID.Font.POP, Res.tex_symbolPlus, 0);

            if (!Main.noScore) {
                ArrayList<Integer> rank_digits = Main.getDigits((int) Main.gm.getRank());
                int rank_width = Main.getTextWidth(rank_digits, 0) + 1 + Res.tex_text_youare.getWidth();
                Main.drawNumber(batch, rank_digits, new Vector2(Main.width / 2 - rank_width / 2 + Res.tex_text_youare.getWidth() + 1, pos_rank.y), ID.Font.NORMAL);
                batch.draw(Res.tex_text_youare, Main.width / 2 - rank_width / 2, pos_rank.y);

                batch.setShader(Res.shader_overlay);
                Res.shader_overlay.setUniformf("newColor",1,0,0,a_highscoreColor);
                Main.drawNumber(batch, Main.gameData.highscore, new Vector2(Main.width / 2, Main.height / 2 + 35), ID.Font.POP_LARGE);
                batch.setShader(null);

                batch.draw(Res.tex_text_highscore, Main.width / 2 - Res.tex_text_highscore.getWidth() / 2, Main.height / 2 + 56);

            }
            batch.setShader(null);
            batch.end();
        }
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
}
