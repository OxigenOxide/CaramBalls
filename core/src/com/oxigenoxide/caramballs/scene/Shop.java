package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.RewardOrb;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Buy;
import com.oxigenoxide.caramballs.object.button.Button_Return;
import com.oxigenoxide.caramballs.object.button.ShopSpot;
import com.oxigenoxide.caramballs.object.floatingReward.FR_Skin;
import com.oxigenoxide.caramballs.object.floatingReward.FloatingReward;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.Funcs;
import com.oxigenoxide.caramballs.utils.MathFuncs;
import com.oxigenoxide.caramballs.utils.ScrollArea;
import com.oxigenoxide.caramballs.utils.Vector2Mod;

import java.util.ArrayList;

import static com.oxigenoxide.caramballs.Main.tap;

public class Shop extends Scene {
    static ShopSpot[] shopSpots;
    Button button_return;
    int selectedBall;

    float alpha_overlay;
    final float ALPHAMAX_OVERLAY = .5f;

    final int AMOUNT_BALLSKINS = 12; // update when adding a new skin

    final int SIZE_MARGIN_TOP = 33;
    final int SIZE_MARGIN_BOTTOM = 63;

    ScrollArea scrollArea;

    Vector2 pos_orbs;
    Vector2 pos_orb;

    SkinBox skinBox;

    Button_Buy button_buy_skinBox;

    Texture tex_buffer;

    boolean doDarkOverlay;
    boolean isOpeningSkinBox;

    Counter counter_spawnOrb;
    int orbsToSpawn;
    FrameBuffer buffer;

    public Shop() {

        //buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getHeight(), Gdx.graphics.getHeight(), true);
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Main.width, (int) Main.height, true);

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
        shopSpots[0].setRarity(ID.Rarity.COMMON);
        shopSpots[1].setRarity(ID.Rarity.COMMON);
        shopSpots[2].setRarity(ID.Rarity.COMMON);
        shopSpots[3].setRarity(ID.Rarity.COMMON);
        shopSpots[4].setRarity(ID.Rarity.COMMON);
        shopSpots[5].setRarity(ID.Rarity.COMMON);
        shopSpots[6].setRarity(ID.Rarity.RARE);
        shopSpots[7].setRarity(ID.Rarity.RARE);
        shopSpots[8].setRarity(ID.Rarity.RARE);
        shopSpots[9].setRarity(ID.Rarity.LEGENDARY);
        shopSpots[10].setRarity(ID.Rarity.LEGENDARY);
        shopSpots[11].setRarity(ID.Rarity.LEGENDARY);

        for (int i = 0; i < Main.gameData.unlocks.length; i++)
            shopSpots[i].isUnlocked = Main.gameData.unlocks[i];

        shopSpots[0].isUnlocked = true;

        skinBox = new SkinBox();

        button_return = new Button_Return(new Vector2(4, Main.height - SIZE_MARGIN_TOP + 4));
        button_buy_skinBox = new Button_Buy(new Vector2(36, 4), skinBox.price);

        scrollArea = new ScrollArea(0, SIZE_MARGIN_BOTTOM, (int) Main.width, (int) Main.height - SIZE_MARGIN_BOTTOM - SIZE_MARGIN_TOP, (int) (40 * ((AMOUNT_BALLSKINS - 1) / 3 + 1) - (Main.height - SIZE_MARGIN_BOTTOM - SIZE_MARGIN_TOP)));

        counter_spawnOrb = new Counter(new ActionListener() {
            @Override
            public void action() {
                if (orbsToSpawn > 0) {
                    orbsToSpawn--;
                    Main.rewardOrbs.add(new RewardOrb(pos_orb.x + 3.5f, pos_orb.y + 3.5f, skinBox.pos));
                    if (orbsToSpawn > 0) counter_spawnOrb.start();
                }
            }
        }, .1f);

        pos_orbs = new Vector2((int) (Main.width - Res.tex_orbCountBar.getRegionWidth() / 2f) - 2, Main.height - Main.adHeight - 11);
        pos_orb = new Vector2(pos_orbs.x, pos_orbs.y - 1);
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

        scrollArea.update(Main.dt);
        counter_spawnOrb.update();

        if (!isOpeningSkinBox) {
            if (scrollArea.isInArea())
                for (ShopSpot shopSpot : shopSpots)
                    if (shopSpot.isUnlocked && shopSpot.isTouching())
                        shopSpot.update();
            if (button_return.isTouching())
                button_return.update();
            if (button_buy_skinBox.isTouching())
                button_buy_skinBox.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            Main.gameData.orbs += 100;

        setShopSpotPosition();

        if (doDarkOverlay)
            alpha_overlay = Math.min(ALPHAMAX_OVERLAY, alpha_overlay + Main.dt * .5f);
        else
            alpha_overlay = Math.max(0, alpha_overlay - Main.dt * .5f);

        skinBox.update();

    }

    void setShopSpotPosition() {
        for (ShopSpot shopSpot : shopSpots)
            shopSpot.setScrolled((int) scrollArea.getScrolled());
    }

    ArrayList<Integer> getLockedSkins() {
        ArrayList<Integer> lockedSkins = new ArrayList<Integer>();
        for (int i = 0; i < shopSpots.length; i++) {
            if (!shopSpots[i].isUnlocked)
                lockedSkins.add(i);
        }
        return lockedSkins;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {

        // clear background
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // buffer start
        buffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // background
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.palette_table[0]);
        batch.draw(Res.tex_tabletop[0], Main.width / 2 - Res.tex_tabletop[0].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[0].getHeight() / 2);
        batch.setShader(null);

        // shop spots
        for (ShopSpot shopSpot : shopSpots)
            shopSpot.render(batch);

        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.PALETTE_WHITEBALL);
        for (ShopSpot shopSpot : shopSpots)
            shopSpot.renderSkin(batch);
        batch.setShader(null);


        // margins
        batch.draw(Res.tex_shopMarginBottom, 0, 0);
        batch.draw(Res.tex_shopMarginTop, 0, Main.height - SIZE_MARGIN_TOP);

        // orb counter
        batch.draw(Res.tex_orbCountBar, pos_orbs.x - Res.tex_orbCountBar.getRegionWidth() / 2, pos_orbs.y - 2);
        int width = Funcs.drawNumberSignColor(batch, (int)Main.orbs_visual, pos_orbs, ID.Font.SMALL, Res.tex_orb, -1, Res.COLOR_ORBNUMBER);


        pos_orb.x = pos_orbs.x - width / 2;


        // buttons
        button_return.render(batch);
        button_buy_skinBox.render(batch);


        // dark overlay
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 0, 0, 0, alpha_overlay);
        batch.draw(Res.tex_fullscreen, 0, 0);
        batch.setShader(null);

        // orbs
        for (RewardOrb rewardOrb : Main.rewardOrbs)
            rewardOrb.render(batch);

        // skin box
        skinBox.render(batch);


        batch.end();


        // floating reward (skin)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Don't enable blending when you don't need it
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (FloatingReward fr : Main.floatingRewards)
            fr.drawShine(sr);
        sr.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        for (FloatingReward fr : Main.floatingRewards)
            fr.render(batch);
        batch.end();


        buffer.end();

        //draw buffer texture

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Main.setNoCamEffects();
        batch.begin();
        //batch.setShader(Res.shader_pixelate);
        //Res.shader_pixelate.setUniformf("texDim", Main.dim_screen);
        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        //batch.setShader(null);
        batch.end();
        Main.setCamEffects();
    }

    public void buySkinBox() {
        if (Main.gameData.orbs < skinBox.price) return; // can't buy if insufficient funds
        if (getLockedSkins().size() == 0) return;

        Main.gameData.orbs -= skinBox.price;
        skinBox.buy();
        isOpeningSkinBox = true;
        startDarkOverlay();
    }

    public void startDarkOverlay() {
        doDarkOverlay = true;
    }

    public void endDarkOverlay() {
        doDarkOverlay = false;
    }

    void getNewSkin() {
        // find all locked skins
        ArrayList<Integer> lockedSkins = new ArrayList<Integer>();
        for (int i = 0; i < shopSpots.length; i++)
            if (!shopSpots[i].isUnlocked)
                lockedSkins.add(i);

        // choose a random locked skin and reward that skin
        if (lockedSkins.size() > 0) {
            int newSkin = lockedSkins.get((int) (Math.random() * lockedSkins.size()));
            scrollArea.setScrolledTarget(-shopSpots[newSkin].pos.y + shopSpots[0].pos.y);
            Main.floatingRewards.add(new FR_Skin(skinBox.pos.x, skinBox.pos.y, newSkin, new Vector2Mod(shopSpots[newSkin].pos).trans(Res.tex_shopSpot[0].getRegionWidth() / 2, 22)));
        }
    }

    public void unlockSkin(int skin) {
        shopSpots[skin].unlock();
        scrollArea.unlock();
        isOpeningSkinBox = false;
        skinBox.reset();
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
        Game.selectedSkin = type;
        selectedBall = type;
        Main.gameData.selectedBall = type;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void saveData() {

        System.out.println("saveData SHOP");
        if (shopSpots != null) {
            boolean[] unlocks = new boolean[AMOUNT_BALLSKINS];

            for (int i = 0; i < AMOUNT_BALLSKINS; i++)
                unlocks[i] = shopSpots[i].isUnlocked;

            Main.gameData.unlocks = unlocks;
        }
    }

    class SkinBox {
        Vector2 pos;
        Vector2 pos_target;
        Sprite sprite_full;
        float bobbing;
        Counter counter_bob;
        float bobAmp = .25f;
        float bobPeriod = (float) Math.PI * 4;
        boolean opened;
        int taps;
        int price = 100;
        float alpha = 1;
        float maxAlpha = 1;

        SkinBoxHalf skinBoxBottom;
        SkinBoxHalf skinBoxTop;

        boolean cantBuy;

        SkinBox() {
            sprite_full = new Sprite(Res.tex_capsule);
            pos = new Vector2(Main.width / 2, 20 + sprite_full.getHeight() / 2);
            pos_target = new Vector2(pos);
            counter_bob = new Counter(new ActionListener() {
                @Override
                public void action() {
                    bob();
                    counter_bob.start();
                    counter_bob.setTime(MathUtils.random(2f, 5f));
                }
            }, 3);
            counter_bob.start();
            cantBuy = getLockedSkins().size() == 0;
            if (cantBuy) {
                maxAlpha = .3f;
                alpha = .3f;
            }
        }

        void bob() {
            if (!cantBuy)
                bobbing = 1;
        }

        void buy() {
            pos_target.set(Main.width / 2, Main.height / 2);
            orbsToSpawn = 15;
            counter_spawnOrb.start();
            scrollArea.lock();
        }

        public void update() {
            if (!isOpeningSkinBox)
                counter_bob.update();

            pos.lerp(pos_target, 1 - (float) Math.pow(.9f, Main.dt_one));
            bobbing = Math.max(0, bobbing - Main.dt * 3);
            float bobFactor = (1 + bobAmp * bobbing * (float) Math.sin(bobbing * bobPeriod));
            sprite_full.setSize(sprite_full.getRegionWidth() / bobFactor, sprite_full.getRegionHeight() * bobFactor);
            sprite_full.setPosition(pos.x - sprite_full.getWidth() / 2, pos.y - sprite_full.getHeight() / 2);
            alpha = Math.min(maxAlpha, alpha + Main.dt * 2);
            sprite_full.setAlpha(alpha);

            if (!opened && Gdx.input.justTouched() && pos.dst(pos_target) < 3)
                if (MathFuncs.pointInRectangle(tap[0].x, tap[0].y, pos.x - sprite_full.getWidth() / 2, pos.y - sprite_full.getHeight() / 2, sprite_full.getWidth(), sprite_full.getHeight()))
                    tap();

            if (skinBoxBottom != null) skinBoxBottom.update();
            if (skinBoxTop != null) skinBoxTop.update();
        }

        void open() {
            opened = true;
            getNewSkin();
            skinBoxBottom = new SkinBoxHalf(false);
            skinBoxTop = new SkinBoxHalf(true);
        }

        void tap() {
            bob();
            if (isOpeningSkinBox) {
                taps++;
                if (taps >= 3) open();
            }
        }

        void reset() {
            opened = false;
            taps = 0;
            pos.set(Main.width / 2, 20 + sprite_full.getHeight() / 2);
            sprite_full.setPosition(pos.x - sprite_full.getWidth() / 2, pos.y - sprite_full.getHeight() / 2);
            pos_target.set(pos);
            sprite_full.setAlpha(0);
            alpha = 0;
            cantBuy = getLockedSkins().size() == 0;

            if (cantBuy)
                maxAlpha = .3f;
        }

        public void render(SpriteBatch batch) {
            if (skinBoxBottom != null) skinBoxBottom.render(batch);
            if (skinBoxTop != null) skinBoxTop.render(batch);
            if (!opened)
                sprite_full.draw(batch);
        }
    }

    class SkinBoxHalf {
        Sprite sprite;
        Vector2 vel;
        Vector2 pos;
        float vel_init = 2;
        static final float GRAVITY = .25f;
        boolean isTop;

        public SkinBoxHalf(boolean isTop) {
            this.isTop = isTop;
            float randomAng = (float) (Math.random() * Math.PI);
            pos = new Vector2(skinBox.pos);
            pos.y -= skinBox.sprite_full.getHeight() / 2;
            if (isTop) {
                sprite = new Sprite(Res.tex_capsule_top);
                pos.y += 13;
                randomAng *= .5f; // shot to the right
            } else {
                sprite = new Sprite(Res.tex_capsule_bottom);
                randomAng *= .5f;
                randomAng += Math.PI * .5f; // shot to the left
            }

            vel = new Vector2((float) Math.cos(randomAng) * vel_init, (float) Math.sin(randomAng) * vel_init); // practically, the particle will always be shot upwards

            if (isTop) vel.y += 4; // top half gets shot extra high
        }

        public void update() {
            //update position
            vel.add(0, -GRAVITY * Main.dt_one);
            pos.add(vel.x * Main.dt_one, vel.y * Main.dt_one);

            //update sprite
            sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y);
            sprite.rotate(-vel.x * Main.dt_one);

            //dispose
            if (pos.y < -200) { // just a value low enough
                if (isTop)
                    skinBox.skinBoxTop = null;
                else skinBox.skinBoxBottom = null;
            }
        }

        public void render(SpriteBatch batch) {
            sprite.draw(batch);
        }
    }
}
