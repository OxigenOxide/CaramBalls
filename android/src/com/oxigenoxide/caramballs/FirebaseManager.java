package com.oxigenoxide.caramballs;


import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oxigenoxide.caramballs.utils.UserData;

import java.util.ArrayList;

public class FirebaseManager implements FirebaseInterface {
    FirebaseDatabase database;
    DatabaseReference ref;
    static DataSnapshot dataSnapshot;
    String uid;
    FirebaseAuth mAuth;
    static FirebaseUser user;
    AndroidLauncher androidLauncher;
    long testerID;
    UserData userData;
    boolean isBot;
    boolean needsUpdate = true;

    public FirebaseManager(final AndroidLauncher androidLauncher) {
        this.androidLauncher = androidLauncher;
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    ref = database.getReference();

                    String email = user.getEmail();
                    //String email="myballs@cloudtestlabaccounts.com";
                    if (email.contains("@cloudtestlabaccounts.com")) {
                        isBot = true;
                    }
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            setDataSnapshot(dataSnapshot);
                            needsUpdate = androidLauncher.versionCode < (long) dataSnapshot.child("versionCode").getValue();
                            if (needsUpdate) {
                                AlertDialog alertDialog = new AlertDialog.Builder(androidLauncher, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                                        .setTitle("New Update Available!")
                                        .setMessage("The latest update is required to play Caram Balls.")
                                        .setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                androidLauncher.goToPlayStore();
                                            }
                                        }).create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    ref.addValueEventListener(postListener);
                }
            }
        });
        //uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void writeMerges() {
        //ref.child("merges").setValue(getNumber(dataSnapshot.child("merges").getValue()) + 1);
    }

    @Override
    public void addHit() {
        //ref.child("hits").setValue(getNumber(dataSnapshot.child("hits").getValue()) + 1);
    }

    @Override
    public void signIn() {
        androidLauncher.signIn();
    }

    @Override
    public void signOut() {
        androidLauncher.signOutFirebase();
    }

    @Override
    public boolean isSignedIn() {
        //return user != null && GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(androidLauncher));
        return user != null;
    }

    @Override
    public boolean isUpToDate() {
        return !needsUpdate;
    }

    @Override
    public boolean isSnapshotLoaded() {
        return dataSnapshot != null;
    }

    @Override
    public void onSignIn() {

        if (!dataSnapshot.child("users").child(uid).exists()) {
            if (isBot) {
                testerID = -1;
                ref.child("users").child(uid).child("isBot").setValue(true);
            } else {
                ArrayList<DataSnapshot> children=new ArrayList<DataSnapshot>();

                for (DataSnapshot p : dataSnapshot.child("pending").getChildren()) {
                    children.add(p);
                }

                if(children.size()==0){
                    testerID = getNumber(dataSnapshot.child("testerAmount").getValue()) + 1;
                    ref.child("testerAmount").setValue(testerID);
                } else {
                    testerID = getNumber(children.get(0).getValue());
                    ref.child("pending").child(children.get(0).getKey()).removeValue();
                }

            }
            ref.child("users").child(uid).child("testerID").setValue(testerID);
            ref.child("users").child(uid).child("email").setValue(user.getEmail());

        } else {
            testerID = getNumber(dataSnapshot.child("users").child(uid).child("testerID").getValue());
        }

        if (getVersionCode() != androidLauncher.versionCode)
            ref.child("users").child(uid).child("versionCode").setValue(androidLauncher.versionCode);

        userData = dataSnapshot.child("users").child(uid).child("data").getValue(UserData.class);
    }

    int getVersionCode() {
        Object object = dataSnapshot.child("users").child(uid).child("versionCode").getValue();
        if (object != null) {
            long value = (long) dataSnapshot.child("users").child(uid).child("versionCode").getValue();
            return (int) (value);
        }
        return 0;
    }

    @Override
    public String getUID() {
        if (uid != null)
            return uid;
        return "";
    }


    @Override
    public int getTesterID() {
        return (int) testerID;
    }

    private static long getNumber(Object obj) {

        if (obj == null || !(obj instanceof Long))
            return 0;
        return (long) obj;
    }

    static void setDataSnapshot(DataSnapshot dataSnapshot) {
        FirebaseManager.dataSnapshot = dataSnapshot;
    }

    @Override
    public UserData getUserData() {
        return userData;
    }

    @Override
    public void setUserData(UserData ud) {
        userData = ud;
    }

    @Override
    public void leave() {
        ref.child("users").child(uid).child("data").setValue(userData);
    }
}
