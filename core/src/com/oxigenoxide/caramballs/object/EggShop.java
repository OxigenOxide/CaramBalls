package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;

import java.util.ArrayList;
import java.util.HashMap;

import static com.oxigenoxide.caramballs.Main.gameData;
import static com.oxigenoxide.caramballs.Main.tap;

public class EggShop {

    Vector2 pos_shop;
    Vector2 pos_price;
    Vector2 pos_rotationCenter;
    Vector2 pos_leftArrow;
    Vector2 pos_rightArrow;

    float r_rotation;
    static final float ROTATIONMAX = (float) Math.PI / 4;
    HashMap<Integer, ShopEgg> shopEggs = new HashMap<Integer, ShopEgg>();
    int selectedEgg;
    static final int ARROWWIDTH = 3;
    static final int ARROWHEIGHT = 5;
    static final int EGGSELECTWIDTH = 14;
    static final int EGGSELECTHEIGHT = 15;
    static final int COLORS = 9;
    static final int ARROWTOUCHBUFFER = 3;
    static final int LOCKEDEGGS = 1;
    static final int UNLOCKEGGDELAY = 2; // how much higher your max level ball is than the max egg level you can buy

    static int eggsUnlocked = 1;

    public static ArrayList<Integer> price = new ArrayList<Integer>();
    public static ArrayList<Integer> firstPrice = new ArrayList<Integer>();
    public static ArrayList<Integer> eggsBought = new ArrayList<Integer>();

    boolean isScrolling;

    boolean doAutoScroll;
    int autoScrollTarget;

    float count_cantBuy;

    public EggShop() {
        pos_shop = new Vector2(84, 36 + Main.scrHD);
        pos_price = new Vector2(pos_shop.x, pos_shop.y - 6);
        pos_leftArrow = new Vector2(pos_shop.x - 16, pos_shop.y + 6);
        pos_rightArrow = new Vector2(pos_shop.x + 16 - 3, pos_shop.y + 6);

        r_rotation = 10;
        pos_rotationCenter = new Vector2(pos_shop.x, pos_shop.y + r_rotation);

        eggsUnlocked = gameData.eggsUnlocked;

        if (eggsUnlocked == 0)
            unlockEgg();
        else {
            int level;
            for (int i = 0; i < eggsUnlocked; i++) {
                eggsBought.add(gameData.eggsBought.get(i));
                firstPrice.add(10 * (int) Math.pow(2, i));
                price.add(0);
                updatePrice(i);
                level = i;
                shopEggs.put(level, new ShopEgg(level));
            }
        }

        for (int i = 0; i < LOCKEDEGGS; i++)
            shopEggs.put(eggsUnlocked + i, new ShopEgg(eggsUnlocked + i));
    }

    public void onNewHighestBall(int level) {
        int level_maxUnlock = level - UNLOCKEGGDELAY;
        int level_currentMaxUnlock = eggsUnlocked - 1;
        for (int i = 0; i < level_maxUnlock - level_currentMaxUnlock; i++)
            unlockEgg();
    }
//
//    public void unlockEgg() {
//        eggsBought.add(0);
//        shopEggs.put(eggsUnlocked, new ShopEgg(eggsUnlocked));
//        firstPrice.add(10 * (int) Math.pow(2, eggsUnlocked));
//        System.out.println(firstPrice.get(eggsUnlocked));
//        price.add(0);
//        updatePrice(eggsUnlocked);
//        doAutoScroll = true;
//        autoScrollTarget = eggsUnlocked;
//        eggsUnlocked++; // keep at the end
//    }

    public void unlockEgg() {

        eggsUnlocked++;

        eggsBought.add(0);
        shopEggs.put(eggsUnlocked - 1, new ShopEgg(eggsUnlocked - 1));
        firstPrice.add(10 * (int) Math.pow(2, eggsUnlocked - 1));
        price.add(0);
        updatePrice(eggsUnlocked - 1);
        doAutoScroll = true;
        autoScrollTarget = eggsUnlocked - 1;

        for (int i = 0; i < LOCKEDEGGS; i++)
            shopEggs.put(eggsUnlocked + i, new ShopEgg(eggsUnlocked + i));
    }

    public void update() {
        for (ShopEgg shopEgg : shopEggs.values()) {
            shopEgg.update();
        }

        if (!isScrolling) {
            if (doAutoScroll) {
                if (autoScrollTarget - selectedEgg != 0)
                    scroll(-(int) Math.signum(autoScrollTarget - selectedEgg));
                else
                    doAutoScroll = false;
            }
        }

        if (Gdx.input.justTouched()) {
            if (MathFuncs.pointInRectangle(tap[0].x, tap[0].y, pos_leftArrow.x - ARROWTOUCHBUFFER, pos_leftArrow.y - ARROWTOUCHBUFFER, ARROWWIDTH + ARROWTOUCHBUFFER * 2, ARROWHEIGHT + ARROWTOUCHBUFFER * 2))
                previousEgg();

            if (MathFuncs.pointInRectangle(tap[0].x, tap[0].y, pos_rightArrow.x - ARROWTOUCHBUFFER, pos_rightArrow.y - ARROWTOUCHBUFFER, ARROWWIDTH + ARROWTOUCHBUFFER * 2, ARROWHEIGHT + ARROWTOUCHBUFFER * 2))
                nextEgg();

            if (MathFuncs.pointInRectangle(tap[0].x, tap[0].y, pos_shop.x - EGGSELECTWIDTH / 2f, pos_shop.y, EGGSELECTWIDTH, EGGSELECTHEIGHT))
                trySelect();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O))
            unlockEgg();

        count_cantBuy = Math.max(count_cantBuy - Main.dt * 10, 0);
    }

    void trySelect() {
        if (isEggUnlocked(selectedEgg)) {
            if (Main.gameData.orbs >= price.get(selectedEgg)) {
                Main.farm.placeEgg.select(selectedEgg, pos_shop);
            } else {
                count_cantBuy = (float) Math.PI;
                Main.addSoundRequest(ID.Sound.FAIL);
            }
        }
        Main.isButtonPressed = true;
    }

    public void onBuy(int level) {
        eggsBought.set(level, eggsBought.get(level) + 1);
        updatePrice(level);
    }

    void updatePrice(int level) {
        price.set(level, (int) (firstPrice.get(level) * (1 + eggsBought.get(level) * .1f)));
    }

    public void render(SpriteBatch batch) {
        if (isEggUnlocked(selectedEgg)) {
            batch.setColor(1, 1 - (float) Math.sin(count_cantBuy), 1 - (float) Math.sin(count_cantBuy), 1);
            Funcs.drawNumberSign(batch, price.get(selectedEgg), pos_price, ID.Font.SMALL, Res.tex_orb_shop, 0);
        }
        batch.setColor(Color.WHITE);
        drawShopEgg(batch, selectedEgg - 2);
        drawShopEgg(batch, selectedEgg + 2);
        drawShopEgg(batch, selectedEgg + 1);
        drawShopEgg(batch, selectedEgg - 1);
        drawShopEgg(batch, selectedEgg);
        drawLowestShopEgg(batch);
        if (shopEggs.containsKey(selectedEgg - 1))
            batch.draw(Res.tex_eggShop_arrow, pos_leftArrow.x, pos_leftArrow.y);
        if (shopEggs.containsKey(selectedEgg + 1))
            batch.draw(Res.tex_eggShop_arrow, pos_rightArrow.x + ARROWWIDTH, pos_rightArrow.y, -ARROWWIDTH, ARROWHEIGHT);
    }

    void drawShopEgg(SpriteBatch batch, int level) {
        ShopEgg shopEgg = shopEggs.get(level);
        if (shopEgg != null)
            shopEgg.render(batch);
    }

    void drawLowestShopEgg(SpriteBatch batch) {
        ShopEgg lowestShopEgg;
        if (shopEggs.containsKey(selectedEgg - 1))
            lowestShopEgg = shopEggs.get(selectedEgg - 1);
        else
            lowestShopEgg = shopEggs.get(selectedEgg);

        if (getEggHeight(selectedEgg) < lowestShopEgg.pos.y)
            lowestShopEgg = shopEggs.get(selectedEgg);
        if (getEggHeight(selectedEgg + 1) < lowestShopEgg.pos.y)
            lowestShopEgg = shopEggs.get(selectedEgg + 1);

        lowestShopEgg.render(batch);
    }

    float getEggHeight(int level) {
        if (shopEggs.containsKey(level))
            return shopEggs.get(level).pos.y;
        return 100;
    }

    void setEggGoLeft(int level) {
        ShopEgg shopEgg = shopEggs.get(level);
        if (shopEgg != null)
            shopEgg.goLeft();
    }

    void setEggGoRight(int level) {
        ShopEgg shopEgg = shopEggs.get(level);
        if (shopEgg != null)
            shopEgg.goRight();
    }

    void nextEgg() {
        scroll(-1);
        Main.isButtonPressed = true;
    }

    void previousEgg() {
        scroll(1);
        Main.isButtonPressed = true;
    }

    void resetEggDir(int level) {
        if (shopEggs.containsKey(level)) {
            shopEggs.get(level).goLeft = false;
            shopEggs.get(level).goRight = false;
        }
    }

    void scroll(int dir) {
        // dir is opposite to the change of selectedEgg
        if (!isScrolling && selectedEgg - dir >= 0 && selectedEgg - dir < shopEggs.size()) {
            isScrolling = true;
            if (dir > 0) {
                setEggGoRight(selectedEgg);
                setEggGoRight(selectedEgg - 1);
            } else {
                setEggGoLeft(selectedEgg);
                setEggGoLeft(selectedEgg + 1);
            }
            selectedEgg -= dir;
        }
    }

    void endScroll() {
        isScrolling = false;
        resetEggDir(selectedEgg - 1);
        resetEggDir(selectedEgg);
        resetEggDir(selectedEgg + 1);
    }

    boolean isEggUnlocked(int level) {
        return level < eggsUnlocked;
    }

    class ShopEgg {
        int level;
        Sprite sprite;
        Vector2 pos = new Vector2();
        float move;
        float rot;

        boolean goRight, goLeft;

        public ShopEgg(int level) {
            this.level = level;
            sprite = new Sprite(Res.tex_egg);

            if (!isEggUnlocked(level))
                sprite.setRegion(Res.tex_egg_locked);

            if (level < selectedEgg)
                move = 0;
            else if (level > selectedEgg)
                move = 1;
            else
                move = .5f;
        }

        public void update() {
            if (isScrolling) {
                if (goLeft) {
                    move -= Main.dt;
                    if (selectedEgg == level)
                        move = Math.max(.5f, move);
                    else {
                        if (move < 0)
                            endScroll();
                        move = Math.max(0, move);
                    }
                }
                if (goRight) {
                    move += Main.dt;
                    if (selectedEgg == level)
                        move = Math.min(.5f, move);
                    else {
                        if (move > 1)
                            endScroll();
                        move = Math.min(1, move);
                    }
                }
            }

            rot = -(float) Math.PI / 4 - ROTATIONMAX * 2 * (1 - move);

            pos.set(pos_rotationCenter.x + r_rotation * (float) Math.cos(rot), pos_rotationCenter.y + r_rotation * (float) Math.sin(rot));

            sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y);
        }

        void goLeft() {
            goLeft = true;
            goRight = false;
        }

        void goRight() {
            goRight = true;
            goLeft = false;
        }

        public void render(SpriteBatch batch) {
            if (isEggUnlocked(level)) {
                batch.setShader(Res.shader_palette);
                Main.setPalette(Res.palette_egg[level % COLORS]);
                sprite.draw(batch);
                Funcs.setShaderNull(batch);
            } else {
                sprite.draw(batch);
            }
        }
    }

    public void dispose() {

    }

    public void saveData() {
        gameData.eggsUnlocked = eggsUnlocked;
        gameData.eggsBought = eggsBought;
    }
}
