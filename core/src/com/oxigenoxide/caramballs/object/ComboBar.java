package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Counter;

import java.util.ArrayList;

public class ComboBar {
    TextureRegion tex_bar;
    int width = 72;
    float filled;
    float filled_visual;
    Vector2 pos_num;
    float count;
    float count_idle;
    float countMax_idle = 5;
    float ampFactor;
    float alwaysFilled = .1f;
    public static Vector2 pos_orb;
    int orbs;
    boolean inFlow;
    float flowFactor;
    float idleFactor;
    int combo;
    int combo_visual;
    float ampFactor_collect;
    Counter counter_collect;

    /*
        How it works:
        The combo bar appears as soon as an orb is collected. You continue to collect orbs. However, if you fail to collect another orb in around 4 seconds, the bar will be set to zero again.
        When about 5 orbs are collected 'flow' mode is enabled. This state comes with a number of benefits. It will wear off when a successive orb isn't collected in less than around 4 seconds.

        Idea:
        When the combo is done, all orbs will be collected. Maybe a multiplier could be applied for high combos.
     */

    public ComboBar() {
        tex_bar = new TextureRegion(Res.tex_comboBar);
        pos_num = new Vector2(Main.width / 2, Main.height - 12);
        pos_orb = new Vector2(0, pos_num.y + 2.5f);
        pos_orb.x = Main.width / 2;
        counter_collect = new Counter(.25f);
    }

    public void update() {
        counter_collect.update();

        filled = alwaysFilled + (Math.min(combo / 5f, 1)) * idleFactor * (1 - alwaysFilled);
        filled_visual += (filled - filled_visual) * .1f;
        tex_bar.setRegion(Res.tex_comboBar);
        tex_bar.setRegionWidth((int) (filled_visual * width / 2) * 2);

        count = (count + Main.dt * 10 * idleFactor * ampFactor_collect) % (2 * (float) Math.PI);

        if (count_idle <= 0)
            endCombo();
        else
            count_idle -= Main.dt_slowed;

        idleFactor = count_idle / countMax_idle;

        if (inFlow)
            flowFactor = Math.min(1, flowFactor + Main.dt * 2);
        else
            flowFactor = Math.max(0, flowFactor - Main.dt * 2);

        ampFactor_collect = 1 + (float) Math.sin(counter_collect.getProgress() * Math.PI) * .5f;
        ampFactor = idleFactor * flowFactor * ampFactor_collect;


        //digitTextures = Main.getDigitTextures(orbs, ID.Font.SMALL);
        //pos_orb.x = Main.width / 2 - (Main.getNumberWidth(digitTextures) + 9)/2f;
    }


    public void render(SpriteBatch batch) {
        if (combo > 0) {
            batch.setShader(Res.shader_bend);
            Res.shader_bend.setUniformf("time", count);
            Res.shader_bend.setUniformf("texSize", tex_bar.getRegionWidth(), tex_bar.getRegionHeight());
            Res.shader_bend.setUniformf("size", filled_visual);
            Res.shader_bend.setUniformf("ampFactor", ampFactor);
            batch.draw(tex_bar, Main.width / 2 - tex_bar.getRegionWidth() / 2, Main.height - 12 - 8);
            batch.setShader(null);
            batch.draw(Res.tex_comboBar_end, Main.width / 2 - tex_bar.getRegionWidth() / 2 - 1, Main.height - 11);
            batch.draw(Res.tex_comboBar_end, Main.width / 2 + (int) Math.ceil(tex_bar.getRegionWidth() / 2f), Main.height - 11);

            int width = Main.drawNumberSignWhiteText(batch, combo_visual, pos_num, ID.Font.SMALL, Res.tex_orb, -1);
            pos_orb.x = Main.width / 2 - width / 2f + 3.5f;
        }
    }

    void beginFlow() {
        Main.addSoundRequest(ID.Sound.SLOWDOWN);
        inFlow = true;
    }

    void endFlow() {
        Main.addSoundRequest(ID.Sound.SPEEDUP);
        inFlow = false;
    }

    public void collectOrb(int value) { // only visual
        addComboNumber(value);
    }

    public void pickupOrb(int value) { // has effect
        addCombo(value);
        orbs += value;
    }

    void endCombo() {
        combo = 0;
        combo_visual = 0;
        if (inFlow)
            endFlow();
    }

    void addCombo(int amount) {
        counter_collect.start();
        if (combo < 5 && combo + amount >= 5)
            beginFlow();
        combo += amount;
        count_idle = countMax_idle;
    }

    void addComboNumber(int amount) {
        combo_visual += amount;
        combo_visual = MathUtils.clamp(combo_visual, 0, combo); // so it a collection doesnt get counted when the combo gets reset between picking up and collecting an orb.
    }

    public void reset() {
        endCombo();
    }

    public void onBallCombined() {
        //addScore(.5f);
    }

    public void onMainBallDestroyed() {
        //removeScore(.3f);
    }

    public void onOrbCollected() {

    }

    public boolean inFlow() {
        return inFlow;
    }

    public float getFlowFactor() {
        return flowFactor;
    }


}
