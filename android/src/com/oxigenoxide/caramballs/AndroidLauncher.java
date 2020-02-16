package com.oxigenoxide.caramballs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.oxigenoxide.caramballs.R;
import com.oxigenoxide.caramballs.utils.UserData;

import java.io.File;

import static android.content.ContentValues.TAG;


public class AndroidLauncher extends AndroidApplication implements GoogleApiClient.OnConnectionFailedListener {

    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    public static RewardedVideoAd rewardedVideoAd;
    public static InterstitialAd interstitialAd;
    FirebaseManager fbm;
    AdMobManager amm;
    GameManager gm;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_LEADERBOARD_UI = 9004;
    public static AndroidApplication androidApplication;
    Main main;
    public static int versionCode;
    GoogleSignInOptions gso;

    public AndroidLauncher() {
        amm = new AdMobManager("ca-app-pub-1235754856548433/9540824853");
        //fbm = new FirebaseManager(this); //removed firebase compatability
        gm = new GameManager(this);

        gm = createEmptyGameManager();
        amm = createEmptyAdMobManager();
        fbm = createEmptyFireBaseManager();

        main = new Main(fbm, amm, gm);

        androidApplication = this;
    }

    public static AndroidApplication getInstance() {
        return androidApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        amm.init(this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = pInfo.versionCode;
            System.out.println("Version: " + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println(e);
        }

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;

        View gameView = initializeForView(main, config);

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView);
        if (amm.adView != null)
            layout.addView(amm.adView, amm.adParams);

        setContentView(layout);

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken("1059170856086-o78c4cvfvgokdpkga8dip1irimjjcg98.apps.googleusercontent.com")
                //.requestIdToken("1059170856086-eh0b3aj6p7k5r1kcvihvl4mg2alou77v.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        interstitialAd = new InterstitialAd(this);
        //interstitialAd.setAdUnitId("ca-app-pub-1235754856548433/9507468595");
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-1235754856548433/9507468595");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice("1F6CD9CD10347732E1456E8FCC22D236")
                        .build());
            }

            @Override
            public void onAdLoaded() {
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("1F6CD9CD10347732E1456E8FCC22D236")
                .build());

        startSignInIntent(gso);

    }

    public void startSignInIntent() {
        startSignInIntent(gso);
    }

    private void startSignInIntent(GoogleSignInOptions gso) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                gso);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void showLeaderBoards() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_scores))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }

    public void goToPlayStore() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.oxigenoxide.caramballs"));
        startActivity(browserIntent);
    }

    public void submitScore(int i) {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScore(getString(R.string.leaderboard_scores), i);
        getRank();
    }

    public void getPlayer() {
        Games.getPlayersClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getCurrentPlayer().addOnSuccessListener(new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player player) {
                gm.player = player;
                getRank();
            }
        });
    }

    public void getRank() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .loadPlayerCenteredScores(getString(R.string.leaderboard_scores), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC, 1)
                .addOnSuccessListener(new OnSuccessListener<AnnotatedData<LeaderboardsClient.LeaderboardScores>>() {
                    @Override
                    public void onSuccess(AnnotatedData<LeaderboardsClient.LeaderboardScores> leaderboardScoresAnnotatedData) {
                        try {
                            for (LeaderboardScore ls : leaderboardScoresAnnotatedData.get().getScores()) {
                                //if(ls.getScoreHolderDisplayName()==gm.player.getDisplayName()){
                                gm.rank = ls.getRank();
                                //}
                            }
                        } catch (IllegalStateException e) {
                            System.out.println(e);
                        }
                    }
                });
        //gm.rank=0;
    }

    public void setUpGame() {
        getPlayer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data); // Sometimes throws and exception here
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                setUpGame();
            } catch (ApiException e) {
                //gm.onSignInFailed();
                Log.w(TAG, "Google sign in failed", e);
                // ...
                signOut();
            }
            Log.w(TAG, "RC_SING_IN");
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "SHPONGLEbob: " + acct.getDisplayName());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //showLeaderBoards();
                            Task<GoogleSignInAccount> taskG = GoogleSignIn.getSignedInAccountFromIntent(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient));
                            handleSignInResult(taskG);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    GoogleSignInAccount gsia;

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            gsia = completedTask.getResult(ApiException.class);
            Games.getLeaderboardsClient(this, gsia).getLeaderboardIntent("CgkIltmC3OkeEAIQAQ");
        } catch (ApiException e) {
            Log.println(4, "LOSER", e.toString() + " " + e.getMessage() + " " + e.getLocalizedMessage());
        }
    }

    void signIn() {
        //Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // at this point, the user is signed out.
                    }
                });
    }

    void signOutFirebase() {
        mAuth.signOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        main.onPause();
        //deleteCache(this);
        //if(fbm.isSignedIn())
        //   finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        main.onResume();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    GameManager createEmptyGameManager() {
        return new GameManager(this) {
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
            public void signOut() {

            }

            @Override
            public void startSignIn() {

            }

            @Override
            public void dialog(String s) {

            }
        };
    }

    AdMobManager createEmptyAdMobManager() {

        return new AdMobManager("") {
            @Override
            public void init(Context context) {
            }

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
    }

    FirebaseManager createEmptyFireBaseManager() {
        return new FirebaseManager(this) {
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

    }
}
