package com.example.dada.res1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import locdvdv3.API;
import locdvdv3.DatabaseMedia;


public class SplashScreen extends Activity {

    private static final long SPLASH_TIME = 1500;
    private static final int SPLASH_STOP = 1;
    private Handler splashHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SPLASH_STOP:
                    Intent intent = new Intent(SplashScreen.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        splash = (ImageView) findViewById(R.id.img_slapsh);
        Message msg = new Message();
        msg.what = SPLASH_STOP;
        splashHandler.sendMessageDelayed(msg, SPLASH_TIME);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        splash.setAnimation(anim);

        DatabaseMedia databaseMedia = new DatabaseMedia(getBaseContext());

        API api = new API(databaseMedia, this);

        api.updateMovie();

        api.updateTvShow();

        api.updateSummary();

        api.updateActor();

        api.updateMapper();

        api.updateVideoFile();


    }
}
