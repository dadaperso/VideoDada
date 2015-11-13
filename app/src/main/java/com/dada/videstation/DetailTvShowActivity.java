package com.dada.videstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.dada.videstation.adapter.TvShowZodAdapter;
import com.dada.videstation.model.TvZod;
import com.dada.videstation.model.Tvshow;
import com.dada.videstation.utils.API;
import com.dada.videstation.utils.DatabaseMedia;
import com.example.dada.res1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailTvShowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> dataListSeason = new ArrayList();
    HashMap<String,ArrayList<TvZod>> dataListZod = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Tvshow tvshow = (Tvshow) getIntent().getSerializableExtra(API.TAG_TV_SHOW);

        setTitle(tvshow.getTitle());

        // TODO getSummary

        // TODO getZod
        Object[] data = DatabaseMedia.getInstance(this).getTvZodByTvShow(tvshow, dataListZod, dataListSeason);
        dataListSeason = (ArrayList<String>) data[0];
        dataListZod = (HashMap<String, ArrayList<TvZod>>) data[1];
        final TvShowZodAdapter adapter = new TvShowZodAdapter(this,dataListSeason,dataListZod);


        ExpandableListView listTvZod = (ExpandableListView) findViewById(R.id.lstTvShowZod);
        listTvZod.setAdapter(adapter);

        listTvZod.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TvZod tvZod = adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(DetailTvShowActivity.this, DetailTvZodActivity.class);
                intent.putExtra(API.TAG_TV_ZOD, tvZod);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_tv_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_film) {
            Intent intent = new Intent(DetailTvShowActivity.this, FilmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_serie) {
            Intent intent = new Intent(DetailTvShowActivity.this, TvShowActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_acteur) {
            Intent intent = new Intent(DetailTvShowActivity.this, ActorActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
