package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.entity.particle.Particle_OrbPickup;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;

public class ComboBar {
    TextureRegion tex_bar;
    int width = 72;
    float filled;
    float filled_visual;
    Vector2 pos_num;
    float count;
    float count_idle;
    float countMax_idle = 8;
    float ampFactor;
    float alwaysFilled = .1f;
    public static Vector2 pos_orb;
    int orbs;
    boolean inFlow;
    float flowFactor;
    float idleFactor;
    int combo;
    float combo_number;
    float combo_visual;
    float ampFactor_collect;
    Counter counter_collect;
    Counter counter_releaseRewardOrb;
    int amount_rewardOrbsToRelease;

    final static int MAXRELEASEORBS = 5;
    static final float LERP = .05f;
    /*
        How it works:
        The combo bar appears as soon as an orb is collected. You continue to collect orbs. However, if you fail to collect another orb in around 4 seconds, the bar will be set to zero again.
        When about 5 orbs are collected 'flow' mode is enabled. This state comes with a number of benefits. It will wear off when a successive orb isn't collected in less than around 4 seconds.

        Idea:
        When the combo is done, all orbs will be collected. Maybe a multiplier could be applied for high combos.
    */

    public ComboBar() {
        tex_bar = new TextureRegion(Res.tex_comboBar);
        pos_num = new Vector2(Main.width / 2, Main.height - 23);
        pos_orb = new Vector2(0, pos_num.y + 2.5f);
        pos_orb.x = Main.width / 2;
        counter_collect = new Counter(.25f);
        counter_releaseRewardOrb = new Counter(new ActionListener() {
            @Override
            public void action() {
                amount_rewardOrbsToRelease--;
                Main.rewardOrbs.add(new RewardOrb(pos_orb.x, pos_orb.y, 0).goToOrbDisplay().setEmpty());
                if (amount_rewardOrbsToRelease > 0)
                    counter_releaseRewardOrb.start();
            }
        }, .1f);
    }

    public void update() {
        counter_collect.update();
        counter_releaseRewardOrb.update();

        filled = alwaysFilled + (Math.min(combo / 5f, 1)) * idleFactor * (1 - alwaysFilled);
        filled_visual += (filled - filled_visual) * (1 - (float) Math.pow(.9f, Main.dt_one));
        tex_bar.setRegion(Res.tex_comboBar);
        tex_bar.setRegionWidth((int) (filled_visual * width / 2) * 2);

        count = (count + Main.dt * 10 * idleFactor * ampFactor_collect) % (2 * (float) Math.PI);

        if (combo > 0 && count_idle <= 0)
            endCombo();
        else if (!Game.inBreatherLevel)
            count_idle -= Main.dt;

        idleFactor = count_idle / countMax_idle;

        if (inFlow)
            flowFactor = Math.min(1, flowFactor + Main.dt * 2);
        else
            flowFactor = Math.max(0, flowFactor - Main.dt * 2);

        ampFactor_collect = 1 + (float) Math.sin(counter_collect.getProgress() * Math.PI) * .5f;
        ampFactor = idleFactor * flowFactor * ampFactor_collect;

        combo_number += (combo - combo_number) * .1f;
        //digitTextures = Main.getDigitTextures(orbs, ID.Font.SMALL);
        //pos_orb.x = Main.width / 2 - (Main.getNumberWidth(digitTextures) + 9)/2f;

        combo_visual += (combo_number - combo_visual) * (1 - (float) Math.pow(LERP, Main.dt));
    }


    public void render(SpriteBatch batch) {
        if (combo > 0) {
            batch.setShader(Res.shader_bend);
            Res.shader_bend.setUniformf("time", count);
            Res.shader_bend.setUniformf("texSize", tex_bar.getRegionWidth(), tex_bar.getRegionHeight());
            Res.shader_bend.setUniformf("size", filled_visual);
            Res.shader_bend.setUniformf("ampFactor", ampFactor);
            batch.draw(tex_bar, Main.width / 2 - tex_bar.getRegionWidth() / 2, pos_num.y - 8);
            batch.setShader(null);
            batch.draw(Res.tex_comboBar_end, Main.width / 2 - tex_bar.getRegionWidth() / 2 - 1, pos_num.y + 1);
            batch.draw(Res.tex_comboBar_end, Main.width / 2 + (int) Math.ceil(tex_bar.getRegionWidth() / 2f), pos_num.y + 1);
        }
        if (combo > 0 || amount_rewardOrbsToRelease > 0) {
            int width = Funcs.drawNumberSign(batch, Math.round(combo_visual), pos_num, ID.Font.SMALL, Res.tex_orb, -1);
            pos_orb.x = Main.width / 2 - width / 2f + 3.5f;
        }
    }

    void beginFlow() {
        Main.addSoundRequest(ID.Sound.SLOWDOWN);
        inFlow = true;
        //for (Ball ball : Main.mainBalls) {
        //    ball.setParticleTwinkle();
        //}
    }

    void endFlow() {
        Main.addSoundRequest(ID.Sound.SPEEDUP);
        inFlow = false;
    }

    public void collectOrb(int value) { // only visual
        addComboNumber(value);
    }

    public void pickupOrb(int value, float x, float y) { // has effect
        if (combo < 5 && combo + value >= 5) // a little sloppy because this is double-checked
            Main.particles.add(new Particle_OrbPickup(x, y));
        addCombo(value);
        orbs += value;
    }

    void endCombo() {
        amount_rewardOrbsToRelease = Math.min(MAXRELEASEORBS, combo);
        counter_releaseRewardOrb.start();
        counter_releaseRewardOrb.setProgress(1); //so one orb gets released immediately
        Game.orbDisplay.collectOrb(combo);
        combo = 0;
        combo_number = 0;
        if (inFlow)
            endFlow();
    }

    void addCombo(int amount) {
        counter_collect.start();
        if (combo < 5 && combo + amount >= 5)
            beginFlow();
        combo += amount;
        count_idle = countMax_idle;

        if (combo < 6) {
            int number = (combo - 1) % 5;
            float pitch = 0;
            float volume = .25f;
            switch (number) {
                case 0:
                    pitch = .75f;
                    break;
                case 1:
                    pitch = 1f;
                    break;
                case 2:
                    pitch = 1.25f;
                    break;
                case 3:
                    pitch = 1.5f;
                    break;
                case 4:
                    pitch = 2f;
                    volume = .5f;
                    break;
            }
            Main.addSoundRequest(ID.Sound.CORRECT, 0, volume, pitch);
            return;
        }

        int number = (combo - 1) % 5;
        float pitch = 0;
        float volume = .25f;
        switch (number) {
            case 0:
                pitch = .75f;
                volume = .1f;
                break;
            case 1:
                pitch = 1f;
                break;
            case 2:
                pitch = 1.25f;
                break;
            case 3:
                pitch = 1.5f;
                break;
            case 4:
                pitch = 2f;
                volume = .1f;
                //volume = .5f;
                break;
        }
        Main.addSoundRequest(ID.Sound.CORRECT, 0, volume, pitch);

        switch (number) {
            case 0:
                pitch = 1.5f;
                break;
            case 1:
                pitch = 2;
                volume = .1f;

                break;
            case 2:
                pitch = .75f;
                volume = .1f;
                break;
            case 3:
                pitch = 1f;
                break;
            case 4:
                pitch = 1.25f;
                //volume = .5f;
                break;
        }
        Main.addSoundRequest(ID.Sound.CORRECT, 0, volume, pitch);
    }

    void addComboNumber(int amount) {
        combo_number += amount;
        combo_number = MathUtils.clamp(combo_number, 0, combo); // so it a collection doesnt get counted when the combo gets reset between picking up and collecting an orb.
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
