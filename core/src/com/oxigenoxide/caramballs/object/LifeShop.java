package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import static com.oxigenoxide.caramballs.Main.tap;

public class LifeShop {

    Vector2 pos_shop;
    Vector2 pos_price;
    Vector2 pos_balls;
    Vector2[] pos_ball;

    int[] price;
    float count_cantBuy;
    final static int SELECTWIDTH = 30;
    final static int SELECTHEIGHT = 20;

    public LifeShop() {
        pos_shop = new Vector2(Main.width / 2 - 30, 36 + Main.scrHD);
        pos_price = new Vector2(pos_shop.x, pos_shop.y - 6);
        pos_balls = new Vector2(pos_shop.x, pos_shop.y + 2);
        pos_ball = new Vector2[]{
                new Vector2(pos_balls.x - 11, pos_balls.y + 8),
                new Vector2(pos_balls.x - 11 + 8, pos_balls.y + 8),
                new Vector2(pos_balls.x - 11 + 8 * 2, pos_balls.y + 8),
                new Vector2(pos_balls.x - 11, pos_balls.y),
                new Vector2(pos_balls.x - 11 + 8, pos_balls.y),
                new Vector2(pos_balls.x - 11 + 8 * 2, pos_balls.y),
        };
        price = new int[]{
                100,
                200,
                500,
                1000,
                2000,
                5000,
        };
    }

    public void update() {
        count_cantBuy = Math.max(count_cantBuy - Main.dt * 10, 0);

        if (Gdx.input.justTouched()) {
            if (MathFuncs.pointInRectangle(tap[0].x, tap[0].y, pos_shop.x - SELECTWIDTH / 2, pos_shop.y, SELECTWIDTH, SELECTHEIGHT))
                tryBuy();
        }
    }

    void tryBuy() {
        if (Main.gameData.orbs >= price[Main.gameData.livesBought])
            buy();
        else {
            count_cantBuy = (float) Math.PI;
            Main.addSoundRequest(ID.Sound.FAIL);
        }
    }

    void buy() {
        Main.gameData.orbs -= price[Main.gameData.livesBought];
        Main.gameData.livesBought++;
        Main.addSoundRequest(ID.Sound.PLOP);
    }

    public void render(SpriteBatch batch) {
        batch.setColor(1, 1 - (float) Math.sin(count_cantBuy), 1 - (float) Math.sin(count_cantBuy), 1);
        Funcs.drawNumberSign(batch, price[Main.gameData.livesBought], pos_price, ID.Font.SMALL, Res.tex_orb_shop, 0);
        batch.setColor(Color.WHITE);
        batch.draw(Res.tex_lifeShop_back, pos_balls.x - Res.tex_lifeShop_back.getRegionWidth() / 2, pos_balls.y);
        for (int i = 0; i < Main.gameData.livesBought; i++)
            batch.draw(Res.tex_lifeShop_ball, pos_ball[i].x, pos_ball[i].y);
    }
}
