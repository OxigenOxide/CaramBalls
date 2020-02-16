package com.oxigenoxide.caramballs;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AdMobManager implements AdMobInterface {

    private final int SHOW_AD = 1;
    private final int HIDE_AD = 0;
    private final int SHOW_VIDAD = 2;
    private final int LOAD_VIDAD = 3;
    private final int SHOW_INTER = 4;
    private final int LOAD_INTER = 5;

    private final String id;



    int adsClicked;

    //private final String TEST_DEVICE1 = "073D9C239F33183A0076A4EE5AF62B70";

    public AdView adView = null;
    public RelativeLayout.LayoutParams adParams = null;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_AD:
                    adView.setVisibility(View.VISIBLE);
                    break;
                case HIDE_AD:
                    adView.setVisibility(View.INVISIBLE);
                    break;
                case SHOW_VIDAD:
                    //if (AndroidLauncher.rewardedVideoAd.isLoaded())
                    //    AndroidLauncher.rewardedVideoAd.show();
                    break;
                case LOAD_VIDAD:
                    AndroidLauncher.rewardedVideoAd.loadAd("ca-app-pub-1235754856548433/9507468595", new AdRequest.Builder().build());
                    break;
                case SHOW_INTER:

                    AndroidLauncher.interstitialAd.show();
                    break;
                case LOAD_INTER:

                    AdRequest request = new AdRequest.Builder()
                            .build();
                    AndroidLauncher.interstitialAd.loadAd(request);
                    break;
            }
        }
    };

    public AdMobManager(String id) {
        this.id = id;
    }

    public void init(Context context) {

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        //adParams.addRule( RelativeLayout.ALIGN_PARENT_TOP );
        adView = new AdView(context);
        //adView.setAdSize(new AdSize(AdSize.FULL_WIDTH,50));
        adView.setAdSize(new AdSize(AdSize.FULL_WIDTH,50));
        //adView.setAdSize(AdSize.SMART_BANNER);
        //adView.setAlpha(.75f);
        adView.setAdUnitId(id);
        AdRequest.Builder requestBuilder = new AdRequest.Builder();
        requestBuilder.addTestDevice("1F6CD9CD10347732E1456E8FCC22D236");
        adView.loadAd(requestBuilder.build());

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                handler.sendEmptyMessage(HIDE_AD);
                handler.sendEmptyMessage(SHOW_AD);
            }
            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                AdRequest.Builder requestBuilder = new AdRequest.Builder();
                requestBuilder.addTestDevice("1F6CD9CD10347732E1456E8FCC22D236");
                adView.loadAd(requestBuilder.build());
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                adsClicked++;
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    @Override
    public void show() {
        handler.sendEmptyMessage(SHOW_AD);
    }

    @Override
    public void hide() {
        handler.sendEmptyMessage(HIDE_AD);
    }

    @Override
    public void showAd() {
        handler.sendEmptyMessage(SHOW_VIDAD);
    }

    @Override
    public void loadAd() {
        handler.sendEmptyMessage(LOAD_VIDAD);
    }

    @Override
    public void showInterstitial() {
        handler.sendEmptyMessage(SHOW_INTER);
    }

    public void loadInterstitial() {
        handler.sendEmptyMessage(LOAD_INTER);
    }

    public boolean isInterstitialLoaded() {
        //return AndroidLauncher.interstitialAd.isLoaded();
        return false;
    }
    public int getAdsClicked() {
        int c=adsClicked;
        adsClicked=0;
        return c;
    }

}
