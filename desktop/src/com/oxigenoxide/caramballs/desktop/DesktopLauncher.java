package com.oxigenoxide.caramballs.desktop;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oxigenoxide.caramballs.AdMobInterface;
import com.oxigenoxide.caramballs.FirebaseInterface;
import com.oxigenoxide.caramballs.GameInterface;
import com.oxigenoxide.caramballs.Main;
import com.oxigenoxide.caramballs.utils.UserData;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        TexturePacker.process(settings, "C:\\CaramBalls\\desktop\\images", "../assets", "images");

        // Default
        config.width = 108 * 5;
        config.height = 192 * 5;

        // Mi A2
        config.width = 100 * 5;
        config.height = 200 * 5;

        // Poster Phone Screens
        config.width = 36 * 14;
        config.height = 66 * 14;

        // Old iPad
        //config.width = 64 * 10;
        //config.height = 92 * 10;

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
            @Override
            public void show() {

            }

            @Override
            public void hide() {

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
        new LwjglApplication(new Main(fbm, amm, gm).setDevMode(), config);
    }
}
