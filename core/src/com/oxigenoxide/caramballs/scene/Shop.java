package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.button.ShopSpot;
import com.oxigenoxide.caramballs.utils.ScrollArea;

public class Shop extends Scene {
    static ShopSpot[] shopSpots;
    Button button_return;
    int selectedBall;

    final int AMOUNT_BALLSKINS = 12;
    final int SIZE_MARGIN_TOP = 33;
    final int SIZE_MARGIN_BOTTOM = 63;

    ScrollArea scrollArea;

    public Shop() {
        button_return = new Button_Return(new Vector2(4, Main.height - SIZE_MARGIN_TOP + 4));

        shopSpots = new ShopSpot[AMOUNT_BALLSKINS];

        for (int i = 0; i < AMOUNT_BALLSKINS; i++) {
            int px = i % 3;
            int py = i / 3;
            shopSpots[i] = new ShopSpot(new Vector2(30 * px + 9, -40 * py + Main.height - 36 - SIZE_MARGIN_TOP - 2), i);
            if (i < Main.gameData.unlocks.length) // check if index is in the array of gameData.unlocks
                shopSpots[i].isUnlocked = Main.gameData.unlocks[i];
        }

        selectedBall = Main.gameData.selectedBall;

        shopSpots[selectedBall].isSelected = true;
        shopSpots[1].setPrice(25);
        shopSpots[2].setPrice(50);
        shopSpots[3].setPrice(100);
        shopSpots[4].setPrice(150);
        shopSpots[5].setPrice(200);
        shopSpots[6].setPrice(500);
        shopSpots[7].setPrice(1000);
        shopSpots[8].setPrice(2000);

        for (int i = 0; i < 9; i++) {
            shopSpots[i].isUnlocked = Main.gameData.unlocks[i];
        }
        shopSpots[0].isUnlocked = true;

        scrollArea = new ScrollArea(0, SIZE_MARGIN_BOTTOM, (int) Main.width, (int) Main.height - SIZE_MARGIN_BOTTOM -SIZE_MARGIN_TOP, (int) (40 * ((AMOUNT_BALLSKINS-1) / 3 + 1) - (Main.height - SIZE_MARGIN_BOTTOM - SIZE_MARGIN_TOP)));
    }

    @Override
    public void show() {
        super.show();
        Main.amm.show();
    }

    public boolean canAffordSomething() {
        boolean canAffordSomething = false;
        for (ShopSpot shopSpot : shopSpots)
            if (shopSpot.getPrice() <= Main.gameData.orbs && !shopSpot.isUnlocked) {
                canAffordSomething = true;
                break;
            }

        return canAffordSomething;
    }

    @Override
    public void hide() {
        super.hide();
        Main.amm.hide();
    }

    @Override
    public void update() {
        super.update();

        scrollArea.update();

        if (scrollArea.isInArea())
            for (ShopSpot shopSpot : shopSpots)
                if (shopSpot.isTouching())
                    shopSpot.update();

        if (button_return.isTouching())
            button_return.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.gameData.orbs += 100;
        }


        for (ShopSpot shopSpot : shopSpots)
            shopSpot.setScrolled((int) scrollArea.getScrolled());
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        // clear background
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // background
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.tableTopPalette[0]);
        batch.draw(Res.tex_tabletop[0], Main.width / 2 - Res.tex_tabletop[0].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[0].getHeight() / 2);
        batch.setShader(null);

        // shop spots
        for (ShopSpot shopSpot : shopSpots)
            shopSpot.render(batch);

        // margins
        batch.draw(Res.tex_shopMarginBottom, 0, 0);
        batch.draw(Res.tex_shopMarginTop, 0, Main.height - SIZE_MARGIN_TOP);

        // orb counter
        batch.draw(Res.tex_orbCountBackground, Main.width / 2 - Res.tex_orbCountBackground.getRegionWidth() / 2, 0);
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 1, 1, 1, 1);
        Main.drawNumberSign(batch, Main.gameData.orbs, new Vector2(Main.width / 2, 1), ID.Font.SMALL, Res.tex_symbolOrb, 0);
        batch.setShader(null);

        // button
        button_return.render(batch);

        batch.end();
    }

    public void onPurchase() {
        if (!canAffordSomething())
            Main.farm.button_shop.setNormal();
    }

    public static void deselect() {
        for (ShopSpot shopSpot : shopSpots) {
            shopSpot.isSelected = false;
        }
    }

    public static void unlockAll() {
        for (ShopSpot shopSpot : shopSpots) {
            shopSpot.isUnlocked = true;
        }
    }

    public static void lockAll() {
        for (ShopSpot shopSpot : shopSpots) {
            shopSpot.isUnlocked = false;
        }
    }

    public void select(int type) {
        Shop.deselect();
        Game.ballType = type;
        selectedBall = type;
        Main.gameData.selectedBall = type;
    }

    @Override
    public void dispose() {
        saveData();
        super.dispose();
    }

    public void saveData() {
        if (shopSpots != null) {
            boolean[] unlocks = new boolean[AMOUNT_BALLSKINS];

            for (int i = 0; i < AMOUNT_BALLSKINS; i++)
                unlocks[i] = shopSpots[i].isUnlocked;

            Main.gameData.unlocks = unlocks;
        }
    }
}
