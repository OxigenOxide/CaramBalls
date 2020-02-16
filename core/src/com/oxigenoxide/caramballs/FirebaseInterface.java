package com.oxigenoxide.caramballs;

import com.oxigenoxide.caramballs.utils.UserData;

public interface FirebaseInterface {
    public void writeMerges();
    public void addHit();
    public String getUID();
    public void signIn();
    public void signOut();
    public boolean isSignedIn();
    public void onSignIn();
    public void leave();
    public void setUserData(UserData ud);
    public boolean isSnapshotLoaded();
    public int getTesterID();
    public UserData getUserData();
    boolean isUpToDate();
}
