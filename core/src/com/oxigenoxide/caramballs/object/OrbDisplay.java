package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.Funcs;

public class OrbDisplay {
    Vector2 pos;
    Vector2 pos_num;

    static Vector2 pos_orb;

    int orbs;
    float orbs_visual;
    float lerp = 0.05f;

    public OrbDisplay() {
        pos = new Vector2(2, Main.height - 2 - 8);
        pos_num = new Vector2(pos.x + 11, pos.y + 2);
        pos_orb = new Vector2(pos.x + 24 + 3.5f, pos.y + 1 + 3.5f);
    }

    public void update() {
        //orbs_visual += (orbs - orbs_visual) * lerpAlpha * Main.dt;
        orbs_visual += (orbs - orbs_visual) * (1 - (float) Math.pow(lerp, Main.dt));
        //orbs_visual = orbs_visual + (orbs - orbs_visual) * lerpAlpha + (orbs - orbs_visual) * lerpAlpha;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_orbDisplay, pos.x, pos.y);

        batch.setColor(Res.COLOR_ORBNUMBER);
        Funcs.drawNumber(batch, Math.round(orbs_visual), pos_num, ID.Font.SMALL);
        batch.setColor(Color.WHITE);
    }

    public void collectOrb(int add) {
        orbs += add;
    }

    public void reset() {
        orbs = 0;
        orbs_visual = 0;
    }
}
