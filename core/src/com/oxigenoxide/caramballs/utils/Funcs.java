package com.oxigenoxide.caramballs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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

    public static void combinePalettes(Color[] palette, Color[] palette0, Color[] palette1, float weight) {
        for (int i = 0; i < 4; i++)
            palette[i].set(palette0[i].r * weight + palette1[i].r * (1 - weight), palette0[i].g * weight + palette1[i].g * (1 - weight), palette0[i].b * weight + palette1[i].b * (1 - weight), 1);
    }

    public static void combineColors(Color color, Color color0, Color color1, float weight) {
        color.set(color0.r * weight + color1.r * (1 - weight), color0.g * weight + color1.g * (1 - weight), color0.b * weight + color1.b * (1 - weight), 1);
    }

    public static void enableBlendGL() {
        Gdx.gl20.glEnable(GL20.GL_BLEND);
    }

    public static void disableBlendGL() {
        Gdx.gl20.glDisable(GL20.GL_BLEND);
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

    public static void setShader(SpriteBatch batch, ShaderProgram shader) {
        if (batch.getShader() != shader) {
            //System.out.println("switch shader to " + shader + " frameID: " + Gdx.graphics.getFrameId());
            batch.setShader(shader);
        }
    }

    public static void setShaderNull(SpriteBatch batch) {
        if (batch.getShader() != Res.defaultShader) {
            //System.out.println("switch shader to null  frameID: " + Gdx.graphics.getFrameId());
            batch.setShader(Res.defaultShader);
        }
    }

    public static Object[] clone(Object[] array) {
        Object[] newArray = new Object[array.length];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];
        return newArray;
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


    public static int drawNumberSignColor(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font, TextureRegion tex_sign, int yDisposition, Color c) {
        int iWidth = 0;
        batch.draw(tex_sign, pos.x + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getRegionWidth() + 2;
        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", c);
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getRegionWidth() + 1;
        }
        batch.setShader(null);
        return iWidth;
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
