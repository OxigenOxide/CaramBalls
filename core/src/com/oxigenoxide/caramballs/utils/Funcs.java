package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Res;

import java.util.ArrayList;

public class Funcs {
    public static void drawTriangle(ShapeRenderer sr, Vector2 v0, Vector2 v1, Vector2 v2) {
        sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
    }

    public static boolean justTouched() {
        return Gdx.input.justTouched() && !Gdx.input.isTouched(1);
    }

    public static Class getClass(Object object) {
        if (object == null)
            return null;
        return object.getClass();
    }

    //static final Color COLOR_SHINE = new Color(1, 1, 200 / 255f, 210 / 255f);
    static final Color COLOR_SHINE = new Color(1, 1, .8f, .75f);

    public static void drawShine(ShapeRenderer sr, Vector2 pos, float radius, float progress) {
        float angle = progress * 360;
        float offset = 2.5f * (float) Math.cos(progress * 2 * Math.PI);
        sr.setColor(COLOR_SHINE);
        for (int i = 0; i < 6; i++)
            sr.arc(pos.x, pos.y, radius + offset, angle + 60 * i, 30, 5);
    }

    public static int drawNumberSign(SpriteBatch batch, int number, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width += tex_sign.getRegionWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getRegionWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        return textWidth;
    }

    public static int drawNumberSignWhiteText(SpriteBatch batch, int number, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width += tex_sign.getRegionWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getRegionWidth() + 2;
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 1, 1, 1, 1);

        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        batch.setShader(null);
        return textWidth;
    }

    public static int drawNumberSignColor(SpriteBatch batch, int number, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition, Color c) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width += tex_sign.getRegionWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getRegionWidth() + 2;

        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", c);
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        batch.setShader(null);

        return textWidth;
    }

    public static int drawNumberSignAfter(SpriteBatch batch, int number, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width += tex_sign.getRegionWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;


        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }

        iWidth += 2;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);

        return textWidth;
    }

    public static int drawNumberSignAfterColor(SpriteBatch batch, int number, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition, Color c) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width += tex_sign.getRegionWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;

        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", c);
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        batch.setShader(null);

        iWidth += 2;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);

        return textWidth;
    }

    public static ArrayList<Integer> getDigits(int number) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        return digits;
    }

    public static ArrayList<TextureRegion> getDigitTextures(int number, int font) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        ArrayList<TextureRegion> digitTextures = new ArrayList<TextureRegion>();
        for (Integer i : digits) {
            digitTextures.add(Res.tex_numbers[font][i]);
        }
        return digitTextures;
    }

    public static int getTextWidth(ArrayList<Integer> digits, int font) {
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width--;
        return width;
    }

    public static int getNumberWidth(ArrayList<TextureRegion> textures) {
        int width = 0;
        for (TextureRegion tex : textures) {
            width += tex.getRegionWidth() + 1;
        }
        width--;
        return width;
    }

    public static void drawNumberSign(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int iWidth = 0;
        batch.draw(tex_sign, pos.x + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font) {
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, int number, Vector2 pos, int font) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        width--;
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
    }
}
