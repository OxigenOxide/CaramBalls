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
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;

public class Menu extends Scene {
    Title title;
    TTPText ttpText;
    Button button_options;
    Button button_tutorial;
    boolean buttonTouched;
    boolean optionsMenuOpen;
    EventManager eventManager_options;
    boolean musicButtonOut, soundButtonOut;

    Vector2 pos_musicButton_hidden, pos_soundButton_hidden;
    Vector2 pos_musicButton_out, pos_soundButton_out;

    public Menu() {
        title = new Title();
        ttpText = new TTPText(Main.height / 2);
        eventManager_options = new EventManager(new EventListener() {
            @Override
            public int onEvent(int event) {
                switch (event) {
                    case 0:
                        musicButtonOut = optionsMenuOpen;
                        return 20;
                    case 1:
                        soundButtonOut = optionsMenuOpen;
                }
                return -1;
            }
        });

        pos_musicButton_out =new Vector2(76,3);
        pos_soundButton_out=new Vector2(61,3);

        pos_musicButton_hidden = new Vector2();
        //pos_soundButton_hidden

    }

    @Override
    public void show() {

        optionsMenuOpen = false;
        Main.setAdVisibility(true);
        button_options = new Button_Options(new Vector2(Main.width - 2 - Res.tex_button_options.getRegionWidth(), 2));
        button_tutorial = new Button_Tutorial(new Vector2(12, 10));
        button_tutorial.setVisibility(false);

    }

    @Override
    public void update() {

        title.update();
        ttpText.update();
        buttonTouched = false;
        if (button_options.isTouching()) {
            button_options.update();
            buttonTouched = true;
        }

        if (button_tutorial.isTouching()) {
            button_tutorial.update();
            buttonTouched = true;
        }

        if (!button_options.isTouched && !buttonTouched && Gdx.input.justTouched()) {
            Main.amm.hide();
            Main.setSceneFarm();
        }

        //if (soundButtonOut)
        //    button_tutorial.pos.

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.palette_table[0]);
        batch.draw(Res.tex_tabletop[0], Main.width / 2 - Res.tex_tabletop[0].getWidth() / 2, Main.height / 2 - Res.tex_tabletop[0].getHeight() / 2);
        batch.setShader(null);
        button_options.render(batch);
        ttpText.render(batch);
        title.render(batch);
        button_tutorial.render(batch);
        batch.end();

    }

    public void onOptionsPressed() {
        optionsMenuOpen = !optionsMenuOpen;
    }

    @Override
    public void dispose() {

    }
}
