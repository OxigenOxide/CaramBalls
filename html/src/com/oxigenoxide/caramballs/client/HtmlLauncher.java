package com.oxigenoxide.caramballs.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.oxigenoxide.caramballs.AdMobInterface;
import com.oxigenoxide.caramballs.FirebaseInterface;
import com.oxigenoxide.caramballs.GameInterface;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.scene.Game;
import com.oxigenoxide.caramballs.utils.UserData;


public class HtmlLauncher extends GwtApplication {

    // Build HTML command: gradlew.bat html:dist

    // Notes
    // - had to use Math.floor once instead of (int) to make something work the same way on HTML as on desktop and android
    // - don't save files local

    private static final int PADDING = 0;
    private GwtApplicationConfiguration cfg;

    @Override
    public GwtApplicationConfiguration getConfig() {
        int w = Window.getClientWidth() - PADDING;
        int h = Window.getClientHeight() - PADDING;

        if (2 * w > h) w = h / 2;
        else h = w * 2;

        cfg = new GwtApplicationConfiguration(w, h);
        Window.enableScrolling(false);
        Window.setMargin("0");
        Window.addResizeHandler(new ResizeListener());
        cfg.preferFlash = false;
        return cfg;
    }

    class ResizeListener implements ResizeHandler {
        @Override
        public void onResize(ResizeEvent event) {
            int w = Window.getClientWidth() - PADDING;
            int h = Window.getClientHeight() - PADDING;

            if (2 * w > h) w = h / 2;
            else h = w * 2;
//            int width = event.getWidth() - PADDING;
//            int height = event.getHeight() - PADDING;
            getRootPanel().setWidth("" + w + "px");
            getRootPanel().setHeight("" + h + "px");
            getApplicationListener().resize(w, h);
            Gdx.graphics.setWindowedMode(w, h);
        }
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        return createPreloaderPanel(GWT.getHostPageBaseURL() + "preloadlogo.png");
    }

    @Override
    protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
        meterPanel.setStyleName("gdx-meter");
        meterPanel.addStyleName("nostripes");
        meterStyle.setProperty("backgroundColor", "#ffffff");
        meterStyle.setProperty("backgroundImage", "none");
    }


    @Override
    public ApplicationListener createApplicationListener() {

        FirebaseInterface fbm = new FirebaseInterface() {
            @Override
            public void writeMerges() {

            }

            @Override
            public void addHit() {

            }

            public String getUID() {
                return "";
            }

            @Override
            public void signIn() {

            }

            @Override
            public void signOut() {

            }

            @Override
            public boolean isUpToDate() {
                return true;
            }

            @Override
            public boolean isSignedIn() {
                return true;
            }

            @Override
            public void onSignIn() {

            }

            @Override
            public boolean isSnapshotLoaded() {
                return true;
            }

            @Override
            public int getTesterID() {
                return 2;
            }

            @Override
            public void leave() {

            }

            @Override
            public void setUserData(UserData ud) {

            }

            @Override
            public UserData getUserData() {
                return null;
            }
        };
        AdMobInterface amm = new AdMobInterface() {

            boolean bannerVisible;

            @Override
            public void show() {
                bannerVisible = true;
            }

            @Override
            public void hide() {
                bannerVisible = false;
            }

            @Override
            public void showAd() {
            }

            @Override
            public void loadAd() {
            }

            @Override
            public void showInterstitial() {
            }

            @Override
            public void loadInterstitial() {
            }

            @Override
            public boolean isBannerVisible() {
                return bannerVisible;
            }

            @Override
            public int getAdsClicked() {
                return 0;
            }

            @Override
            public boolean isInterstitialLoaded() {
                return false;
            }
        };
        GameInterface gm = new GameInterface() {
            @Override
            public void showLeaderBoards() {

            }

            @Override
            public long getRank() {
                return 0;
            }

            @Override
            public void submitScore(int i) {

            }

            @Override
            public void startSignIn() {

            }

            @Override
            public void signOut() {

            }

            @Override
            public void dialog(String s) {

            }
        };


        //return new Test();
        return new Main(fbm, amm, gm).setHTML();
    }
}