package com.oxigenoxide.caramballs.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.object.TTPText;
import com.oxigenoxide.caramballs.object.Title;
import com.oxigenoxide.caramballs.object.button.Button;
import com.oxigenoxide.caramballs.object.button.Button_Options;
import com.oxigenoxide.caramballs.object.button.Button_Tutorial;

public class Menu extends Scene {
    Title title;
    TTPText ttpText;
    Button button_options;
    Button button_tutorial;
    boolean buttonTouched;
    public Menu(){
        title=new Title();
        ttpText=new TTPText(Main.height/2);
        //button_options=new Button_Options(new Vector2(2,2));

    }

    @Override
    public void show() {
        Main.setAdVisibility(true);
        button_options=new Button_Options(new Vector2(2,Main.height-2-Res.tex_button_options.getHeight()));
        button_tutorial=new Button_Tutorial(new Vector2(12,10));
    }

    @Override
    public void update() {
        title.update();
        ttpText.update();
        buttonTouched=false;
        if(button_options.isTouching()) {
            button_options.update();
            buttonTouched=true;
        }

        if(button_tutorial.isTouching()) {
            button_tutorial.update();
            buttonTouched=true;
        }

        if(!button_options.isTouched && !buttonTouched && Gdx.input.justTouched()) {
            Main.amm.hide();
            Main.setSceneFarm();
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.tableTopPalette[0]);
        batch.draw(Res.tex_tabletop[0], Main.width/2-Res.tex_tabletop[0].getWidth()/2, Main.height/2-Res.tex_tabletop[0].getHeight()/2);
        batch.setShader(null);
        button_options.render(batch);
        ttpText.render(batch);
        title.render(batch);
        button_tutorial.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
