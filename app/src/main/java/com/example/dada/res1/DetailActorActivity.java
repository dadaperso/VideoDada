package com.example.dada.res1;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import locdvdv3.API;
import locdvdv3.Actor;
import locdvdv3.ActorFilmAdapter;
import locdvdv3.ActorTvShowAdapter;
import locdvdv3.DatabaseMedia;
import locdvdv3.Movie;
import locdvdv3.TvZod;
import locdvdv3.Tvshow;

public class DetailActorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actor);
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

        Actor actor = (Actor) getIntent().getSerializableExtra("actor");

        setTitle(actor.getActor());


        // TODO coriger l'aligement des textView
        ListView lstMovieByActor = (ListView) findViewById(R.id.lstVMovieByActor);
        // Si provenance ActorActivity test nbAparencie
        if (actor.getNbMovie() == -1 || actor.getNbMovie()> 0) {
            ArrayList<Movie> lstMovie = DatabaseMedia.getInstance(this).getMoviesByActor(actor);
            if (lstMovie.size() > 0) {
                lstMovieByActor.setAdapter(new ActorFilmAdapter(this, R.layout.item_actor_movie, lstMovie));
            } else {
                findViewById(R.id.txtMovieLabel).setVisibility(View.GONE);
                lstMovieByActor.setVisibility(View.GONE);
            }
        }else {
            findViewById(R.id.txtMovieLabel).setVisibility(View.GONE);
            lstMovieByActor.setVisibility(View.GONE);
        }

        // TODO fixed groug Item (http://stackoverflow.com/questions/10613552/pinned-groups-in-expandablelistview)
        ExpandableListView lstTvZodByActor = (ExpandableListView) findViewById(R.id.lstTvShowByActor);
        // Si provenance ActorActivity test nbAparencie
        if (actor.getNbZod() == -1 || actor.getNbZod() > 0 ){
            Object[] data = DatabaseMedia.getInstance(this).getTvZodByActor(actor);

            final ActorTvShowAdapter adapter = new ActorTvShowAdapter(this, (ArrayList<Tvshow>) data[0],(HashMap)data[1]);

            lstTvZodByActor.setAdapter(adapter);
            lstTvZodByActor.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    TvZod zod = adapter.getChild(groupPosition,childPosition);
                    Intent intent = new Intent(DetailActorActivity.this, DetailTvZodActivity.class);
                    intent.putExtra(API.TAG_TV_ZOD, zod);
                    startActivity(intent);

                    return true;
                }
            });
        }else {
            lstTvZodByActor.setVisibility(View.GONE);

            TextView lblTvShow = (TextView) findViewById(R.id.lblActorTvShowList);
            lblTvShow.setVisibility(View.GONE);
        }


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
        getMenuInflater().inflate(R.menu.actor, menu);
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

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
