package com.oxigenoxide.caramballs.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.Res;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.ActionListener;
import com.oxigenoxide.caramballs.utils.Counter;
import com.oxigenoxide.caramballs.utils.EventListener;
import com.oxigenoxide.caramballs.utils.EventManager;

public class ProgressBar {
    int level;
    Vector2 pos;
    Vector2 pos_text;
    float a;
    boolean show;
    EventManager eventManager;
    Dot[] dots;

    public ProgressBar() {
        pos = new Vector2(14, 6);
        pos_text = new Vector2(Main.width / 2, pos.y + 12);
        dots = new Dot[]{new Dot(0), new Dot(1), new Dot(2), new Dot(3), new Dot(4), new Dot(5), new Dot(6), new Dot(7), new Dot(8)};
        eventManager = new EventManager(new EventListener() {
            @Override
            public int onEvent(int event) {
                switch (event) {
                    case 0:
                        return 180;
                    case 1:
                        hide();
                        return -1;
                }
                return -1;
            }
        });
    }

    int index_nextDot;

    public void showNextDot() {
        dots[index_nextDot].show();
    }

    public void update() {
        eventManager.update();
        level = (int) Main.test_float;
        if (show)
            a = Math.min(1, a + Main.dt);
        else
            a = Math.max(0, a - Main.dt);

        for (Dot dot : dots)
            dot.update();
    }

    public void show() {
        show = true;
        index_nextDot = Game.level % 9;
        dots[index_nextDot].scheduleShow();
        if (index_nextDot == 0) {
            for (Dot dot : dots)
                dot.hide();
        }
        eventManager.start();
    }

    void hide() {
        System.out.println("hide2");
        show = false;
    }

    boolean isVisible() {
        return a != 0;
    }

    public void render(SpriteBatch batch) {
        if (isVisible()) {
            batch.setShader(Res.shader_a);
            Res.shader_a.setUniformf("a", a);
            batch.draw(Res.tex_progressBar, pos.x, pos.y);
            for (Dot dot : dots)
                dot.render(batch);
            batch.setShader(Res.shader_a);
            Res.shader_a.setUniformf("a", a);
            batch.draw(Res.tex_text_level[0], pos_text.x - Res.tex_text_level[0].getWidth() / 2, pos_text.y);
            batch.setShader(null);
        }
    }

    class Dot {
        float a_dot;
        float sizeFactor = 3;
        float progress_show;
        float width;
        float dis;// (disposition)
        final int WIDTH_TEX = 6;
        int level;
        boolean show;
        Counter counter;

        public Dot(int level) {
            this.level = level;
            counter = new Counter(new ActionListener() {
                @Override
                public void action() {
                    show();
                }
            }, .5f);
        }

        public void update() {
            counter.update();
            if (show) {
                progress_show = Math.min(1, progress_show + Main.dt * 3);
                sizeFactor = 1 + (1 - progress_show) * 2;
                a_dot = progress_show;
                width = WIDTH_TEX * sizeFactor;
                dis = (width - WIDTH_TEX) / 2;
            }
            a_dot = Math.min(a, a_dot);
        }

        public void show() {
            show = true;
        }

        public void scheduleShow(){
            counter.start();
        }

        public void hide() {
            show = false;
            progress_show = 0;
        }

        public void render(SpriteBatch batch) {
            if (show) {
                batch.setShader(Res.shader_palette);
                Res.shader_palette.setUniformf("color1", Res.ballPalette[level][1].r, Res.ballPalette[level][1].g, Res.ballPalette[level][1].b, a_dot);
                Res.shader_palette.setUniformf("color2", Res.ballPalette[level][2].r, Res.ballPalette[level][2].g, Res.ballPalette[level][2].b, a_dot);
                batch.draw(Res.tex_progressBar_ball, pos.x + 1 + level * 9 - dis, pos.y + 3 - dis, width, width);
            }
        }
    }

}
