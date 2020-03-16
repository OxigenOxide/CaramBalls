package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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


    public RewardOrb(float x, float y, int type) {
        this.type = type;
        tex = Res.getOrbTex(type);
        pos = new Vector2(x, y);
        size = tex.getRegionWidth();

        if (Main.inGame())
            pos_target = ComboBar.pos_orb;
        else
            pos_target = Main.farm.pos_orb;

        Main.addSoundRequest(ID.Sound.COLLECT);
    }

    public void update() {
        if (spread) {
            counter_spread.update();
            pos.add(vel);
            vel.scl((float) Math.pow(.95, Main.dt_one));
        }
        else {
            pos.lerp(pos_target, .1f);
            size += (7 - size) * .1f;
        }
        if (MathFuncs.distanceBetweenPoints(pos, pos_target) < .8f) {
            dispose();
            Main.gameData.orbs++;

            Main.userData.orbsCollected++;
            if(Main.inGame()) {
                Game.comboBar.collectOrb(RewardOrb.getValue(type));
                Game.orbsCollected++;
            }
        }
    }

    static public int getValue(int type) {
        switch (type) {
            case 0:
                return 1;
            case 1:
                return 3;
        }
        return 0;
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

    public void dispose(){
        Main.rewardOrbsToRemove.add(this);
    }

}
