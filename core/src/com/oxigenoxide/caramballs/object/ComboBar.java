package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;

import java.util.ArrayList;

public class ComboBar {
    TextureRegion tex_bar;
    int width = 72;
    float filled;
    Vector2 pos_num;
    float count;
    float ampFactor;
    float score;
    float score_visual;
    public static Vector2 pos_orb;
    int orbs;
    ArrayList<TextureRegion> digitTextures;


    public ComboBar() {
        tex_bar = new TextureRegion(Res.tex_comboBar);
        pos_num = new Vector2(Main.width / 2, Main.height - 12);
        pos_orb = new Vector2(0, pos_num.y + 2.5f);
    }

    public void update() {
        removeScore(Main.dt * .1f);
        score_visual += (score - score_visual) * .1f;
        filled = .4f + score_visual * .6f;
        tex_bar.setRegion(Res.tex_comboBar);
        tex_bar.setRegionWidth((int) (filled * width / 2) * 2);
        count = (count + Main.dt * 10 * score_visual) % (2 * (float) Math.PI);
        ampFactor = score_visual;
        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            addScore(.5f);
        //digitTextures = Main.getDigitTextures(orbs, ID.Font.SMALL);
        //pos_orb.x = Main.width / 2 - (Main.getNumberWidth(digitTextures) + 9)/2f;
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_bend);
        Res.shader_bend.setUniformf("time", count);
        Res.shader_bend.setUniformf("texSize", tex_bar.getRegionWidth(), tex_bar.getRegionHeight());
        Res.shader_bend.setUniformf("size", filled);
        Res.shader_bend.setUniformf("ampFactor", ampFactor);
        batch.draw(tex_bar, Main.width / 2 - tex_bar.getRegionWidth() / 2, Main.height - 12 - 8);
        batch.setShader(null);
        batch.draw(Res.tex_comboBar_end, Main.width / 2 - tex_bar.getRegionWidth() / 2 - 1, Main.height - 11);
        batch.draw(Res.tex_comboBar_end, Main.width / 2 + (int) Math.ceil(tex_bar.getRegionWidth() / 2f), Main.height - 11);

        int width = Main.drawNumberSignWhiteText(batch, orbs, pos_num, ID.Font.SMALL, Res.tex_orb, -1);
        pos_orb.x = Main.width / 2 - width / 2f + 3.5f;
    }

    void addScore(float dscore) {
        score = Math.min(1, score + dscore);
    }

    void collectOrb(int value) {
        addScore(value * .2f);
        orbs += value;
    }

    public void reset() {
        orbs = 0;
        score = 0;
    }

    void removeScore(float dscore) {
        score = Math.max(0, score - dscore);
    }

    public void onBallCombined() {
        addScore(.5f);
    }

    public void onMainBallDestroyed() {
        removeScore(.3f);
    }

    public void onOrbCollected() {

    }


}
