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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import com.dada.videstation.model.Item;
import com.dada.videstation.utils.DatabaseMedia;
import com.example.dada.res1.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    public ItemAdapter adapter;
    public SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ativity);
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


        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.param_search);


        ArrayList<Item>lstItems = new ArrayList<>();

        GridView gridview = (GridView) findViewById(R.id.searchView);
        adapter = new ItemAdapter(this,0,lstItems);
        gridview.setAdapter(adapter);

        Button btnRefresh = (Button) findViewById(R.id.btnSearchRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refleshList();
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
        getMenuInflater().inflate(R.menu.search_ativity, menu);
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
            Intent intent = new Intent(SearchActivity.this, FilmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_serie) {
            Intent intent = new Intent(SearchActivity.this, TvShowActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_acteur) {
            Intent intent = new Intent(SearchActivity.this, ActorActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void refleshList()
    {
        String type;

        // TODO fix pb detection check type
        CheckBox cbSearchTypeTv = (CheckBox)findViewById(R.id.cbSearchTypeTv);
        CheckBox cbSearchTypeMovie = (CheckBox)findViewById(R.id.cbSearchTypeMovie);


        ArrayList<Item> newItems;
        if (cbSearchTypeMovie.isChecked()|| cbSearchTypeTv.isChecked()){

            CheckBox cbSearchNotWatch = (CheckBox)findViewById(R.id.cbSearchNotWatch);
            CheckBox cbSearchWatch = (CheckBox)findViewById(R.id.cbSearchWatch);

            // HD or Not
            CheckBox cbSearchResHD1080 = (CheckBox) findViewById(R.id.cbSearchResHD1080);
            CheckBox cbSearchResHD720 = (CheckBox) findViewById(R.id.cbSearchResHD720);
            CheckBox cbSearchResSD = (CheckBox) findViewById(R.id.cbSearchResSD);

            // Audoi codec + channel
            CheckBox cbSearchAudio5_1 = (CheckBox) findViewById(R.id.cbSearchAudio5_1);
            CheckBox cbSearchAudioDTS = (CheckBox) findViewById(R.id.cbSearchAudioDTS);


            newItems = DatabaseMedia.getInstance(this).searchedItems(cbSearchTypeMovie, cbSearchTypeTv,
                    cbSearchNotWatch, cbSearchWatch, cbSearchResHD1080, cbSearchResHD720,
                    cbSearchResSD, cbSearchAudio5_1, cbSearchAudioDTS);

            adapter.clear();
            adapter.addAll(newItems);
            adapter.notifyDataSetChanged();

            menu.toggle();

        }
    }
}
