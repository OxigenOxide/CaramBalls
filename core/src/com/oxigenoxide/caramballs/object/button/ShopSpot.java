package com.oxigenoxide.caramballs.object.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.ID;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Shop;

public class ShopSpot extends Button {

    public boolean isUnlocked;
    public boolean isSelected;
    int type = 0;
    int price = 420;
    Vector2 pos_num;

    public ShopSpot(Vector2 pos) {
        super(pos);
        tex = Res.tex_shopSpot;
        tex_pressed = Res.tex_shopSpotPressed;
        pos_num = new Vector2(pos.x + 14, pos.y + 6);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void action() {

        if (isUnlocked) {
            Shop.select(type);
            isSelected = true;
        }

        if (!isUnlocked) {
            if (Main.gameData.orbs > price) {
                isUnlocked = true;
                Main.gameData.orbs-=price;
                Main.shop.onPurchase();

            }
        }

    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        pos_num.set(pos.x + 14, pos.y + 6 - touchOffset);
        if (isUnlocked) {
            int level=0;
            batch.setShader(Res.shader_palette);
            Res.shader_palette.setUniformf("color0", Res.ballPalette[level][0].r, Res.ballPalette[level][0].g, Res.ballPalette[level][0].b, 1);
            Res.shader_palette.setUniformf("color1", Res.ballPalette[level][1].r, Res.ballPalette[level][1].g, Res.ballPalette[level][1].b, 1);
            Res.shader_palette.setUniformf("color2", Res.ballPalette[level][2].r, Res.ballPalette[level][2].g, Res.ballPalette[level][2].b, 1);
            Res.shader_palette.setUniformf("color3", Res.ballPalette[level][3].r, Res.ballPalette[level][3].g, Res.ballPalette[level][3].b, 1);
            batch.draw(Res.tex_ball[type][1], pos.x + 7, pos.y + 24 - Res.tex_ball[type][1].getHeight() / 2 - touchOffset);
            batch.setShader(null);
            if (isSelected)
                batch.draw(Res.tex_symbolSelected, pos.x + 7, pos.y + 4 - touchOffset);
        } else {
            batch.draw(Res.tex_lockedBall, pos.x + 7, pos.y + 17 - touchOffset);
            Main.drawNumberSign(batch, price, pos_num, ID.Font.SMALL, Res.tex_symbolOrb, 0);
        }
    }
}
