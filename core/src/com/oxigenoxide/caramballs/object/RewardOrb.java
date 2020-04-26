package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.MathFuncs;

public class RewardOrb {
    Vector2 pos;
    Vector2 vel;
    Vector2 pos_target;
    Counter counter_spread;
    TextureRegion tex;
    float size;
    int type;
    boolean spread;
    boolean isReward;

    boolean toComboBar;
    boolean toOrbDisplay;
    boolean isEmpty;

    static final float LERP = 0.999999f;


    public RewardOrb(float x, float y, int type) {
        this.type = type;
        tex = Res.getOrbTex(type);
        pos = new Vector2(x, y);
        size = tex.getRegionWidth();

        if (Main.inGame())
            pos_target = ComboBar.pos_orb;
        else
            pos_target = Main.farm.pos_orb;

        Main.addSoundRequest(ID.Sound.COLLECT, 1, 1, MathUtils.random(.8f, 1.2f));

        isReward = true;
    }

    public RewardOrb(float x, float y, Vector2 pos_target) { // for orbs moving from the bar to the object bought
        type = 0;
        tex = Res.getOrbTex(type);
        pos = new Vector2(x, y);
        size = tex.getRegionWidth();

        this.pos_target = pos_target;
    }

    public void update() {
        if (spread) {
            counter_spread.update();
            pos.add(vel);
            vel.scl((float) Math.pow(.95, Main.dt_one));
        } else {
            pos.lerp(pos_target, (1 - (float) Math.pow(1 - LERP, Main.dt)));
            size += (7 - size) * (1 - (float) Math.pow(1 - LERP, Main.dt));
        }
        if (MathFuncs.distanceBetweenPoints(pos, pos_target) < .8f)
            onReachedTarget();
    }

    public void onReachedTarget() {
        dispose();
        if (isReward) {

            if (isEmpty)
                return;

            Game.addOrbs(getValue(type));

            if (toComboBar) {
                Game.comboBar.collectOrb(getValue(type));
            } else if (toOrbDisplay)
                Game.orbDisplay.collectOrb(getValue(type));
        }
    }

    static public int getValue(int type) {
        switch (type) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;
        }
        return 1;
    }

    public RewardOrb goToComboBar() {
        toComboBar = true;
        pos_target = ComboBar.pos_orb;
        return this;
    }

    public RewardOrb goToOrbDisplay() {
        toOrbDisplay = true;
        pos_target = OrbDisplay.pos_orb;
        return this;
    }

    public RewardOrb goToFarmBar() {
        pos_target = Main.farm.pos_orb;
        return this;
    }


    public RewardOrb setEmpty() {
        isEmpty = true;
        return this;
    }

    public RewardOrb spread() {
        counter_spread = new Counter(new ActionListener() {
            @Override
            public void action() {
                endSpread();
            }
        }, .5f).start();
        float angle = (float) (Math.random() * Math.PI * 2);
        vel = new Vector2(.8f * (float) Math.cos(angle), .8f * (float) Math.sin(angle));
        spread = true;
        return this;
    }

    void endSpread() {
        spread = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, pos.x - size / 2, pos.y - size / 2, size, size);
    }

    public void dispose() {
        Main.rewardOrbsToRemove.add(this);
    }

}
