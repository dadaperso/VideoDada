package com.dada.videstation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.dada.videstation.utils.API;
import com.dada.videstation.utils.DatabaseMedia;
import com.example.dada.res1.R;


public class SplashScreen extends Activity {

    private static final long SPLASH_TIME = 2500;
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

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            if (mWifi.getExtraInfo().equals("\"Livebox-0964\"")) {
                DatabaseMedia databaseMedia = new DatabaseMedia(getBaseContext());

                API api = new API(databaseMedia, this);

                api.updateMovie();

                api.updateTvShow();

                api.updateSummary();

                api.updateActor();

                api.updateMapper();

                api.updateVideoFile();

                api.updateGenre();

                api.updateWatch();

                api.updatePoster();
            }
            else
            {
                Toast.makeText(this,"La mise à jour est pour est moment impossible en dehors du réseau privé au serveur.",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Aucun connection WI-FI détecter.\n Mise à Jour impossible",Toast.LENGTH_LONG).show();
        }

    }
}
