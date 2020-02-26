package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Cross;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.button.ShopSpot;

public class Shop extends Scene {
    static ShopSpot[] shopSpots;
    Button button_return;
    int selectedBall;

    public Shop() {
        button_return = new Button_Return(new Vector2(4, Main.height - 18 - Main.adHeight));
        shopSpots = new ShopSpot[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                shopSpots[i * 3 + j] = new ShopSpot(new Vector2(30 * j + 9, -40 * i + Main.height - 60 - Main.adHeight));
            }
        }
        for (int i = 0; i < 9; i++) {
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

        shopSpots[1].setType(1);
        shopSpots[2].setType(2);
        shopSpots[3].setType(7);
        shopSpots[4].setType(8);
        shopSpots[5].setType(5);
        shopSpots[6].setType(4);
        shopSpots[7].setType(3);
        shopSpots[8].setType(6);

        for (int i = 0; i < 9; i++) {
            shopSpots[i].isUnlocked = Main.gameData.unlocks[i];
        }
        shopSpots[0].isUnlocked = true;
    }

    @Override
    public void show() {
        super.show();
    }

    public boolean canAffordSomething(){
        boolean canAffordSomething=false;
        for (ShopSpot shopSpot : shopSpots)
            if (shopSpot.getPrice() <= Main.gameData.orbs && !shopSpot.isUnlocked){
                canAffordSomething=true;
                break;
            }

        return canAffordSomething;
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void update() {
        super.update();
        for (ShopSpot shopSpot : shopSpots)
            if (shopSpot.isTouching())
                shopSpot.update();
        if (button_return.isTouching())
            button_return.update();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.tableTopPalette[0]);
        batch.draw(Res.tex_tabletop[0], Main.width / 2 - Res.tex_tabletop[0].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[0].getHeight() / 2);
        batch.setShader(null);
        batch.draw(Res.tex_orbCountBackground, Main.width / 2 - Res.tex_orbCountBackground.getRegionWidth() / 2, 0);
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 1, 1, 1, 1);
        Main.drawNumberSign(batch, Main.gameData.orbs, new Vector2(Main.width / 2, 1), ID.Font.SMALL, Res.tex_symbolOrb, 0);
        batch.setShader(null);
        //Main.drawNumber(batch,Main.gameData.orbs,new Vector2(Main.width/2,1),2);
        for (ShopSpot shopSpot : shopSpots)
            shopSpot.render(batch);
        button_return.render(batch);
        batch.end();
    }

    public void onPurchase(){
        if(!canAffordSomething())
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

    public static void select(int type) {
        Shop.deselect();
        Game.ballType = type;
        Main.gameData.selectedBall = type;
    }

    @Override
    public void dispose() {
        saveData();
        super.dispose();
    }

    public static void saveData() {
        if (shopSpots != null) {
            for (int i = 0; i < 9; i++) {
                Main.gameData.unlocks[i] = shopSpots[i].isUnlocked;
            }
        }
    }
}
