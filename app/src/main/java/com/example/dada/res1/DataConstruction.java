package com.example.dada.res1;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import locdvdv3.API;
import locdvdv3.DatabaseMedia;


public class DataConstruction extends Activity {

    private DatabaseMedia databaseMedia;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), "Activité DataConstruction", Toast.LENGTH_LONG).show();

        databaseMedia = new DatabaseMedia(getBaseContext());
        db = databaseMedia.getWritableDatabase();
        db.setLockingEnabled(false);

        API api = new API(databaseMedia, this);

        api.updateMovie();

        api.updateTvShow();

        api.updateSummary();

        api.updateActor();

        databaseMedia.close();

    }

}
