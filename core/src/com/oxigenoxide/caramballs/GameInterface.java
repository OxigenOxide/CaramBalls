package com.oxigenoxide.caramballs;

public interface GameInterface {
    void showLeaderBoards();
    long getRank();
    void submitScore(int i);
    void signOut();
    void startSignIn();
    void dialog(String s);
}
