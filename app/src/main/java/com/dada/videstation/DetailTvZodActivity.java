package com.dada.videstation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dada.videstation.model.Actor;
import com.dada.videstation.model.Genre;
import com.dada.videstation.model.TvZod;
import com.dada.videstation.model.VideoFile;
import com.dada.videstation.utils.API;
import com.dada.videstation.utils.DatabaseMedia;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;

import java.util.ArrayList;

public class DetailTvZodActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    VideoFile mVideoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_zod);
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

        TvZod zod = (TvZod) getIntent().getSerializableExtra(API.TAG_TV_ZOD);

        DatabaseMedia dbMedia = DatabaseMedia.getInstance(this);

        mVideoFile = dbMedia.getVideoFileByMapper(zod.getMapper());

        String title = zod.getTvshow().getTitle()+" S"+zod.getSaison()+"E"+zod.getEpisode();
        setTitle(title);

        // TODO add style to text
        TextView txtZodTitle = (TextView) findViewById(R.id.txtTvZodTitle);
        txtZodTitle.setText(zod.getTagLine());

        TextView txtTvZodSeason = (TextView) findViewById(R.id.txtTvZodSeason);
        Resources res = getResources();
        String lblSeason = res.getString(R.string.locdvd_tv_zod_season);
        txtTvZodSeason.setText(String.format(lblSeason,zod.getSaison()));


        TextView txtTvZodEpisode = (TextView) findViewById(R.id.txtTvZodEpisode);
        String lblZod = res.getString(R.string.locdvd_tv_zod_episode);
        txtTvZodEpisode.setText(String.format(lblZod,zod.getEpisode()));

        TextView txtTvZodRelaseDate = (TextView) findViewById(R.id.txtTvZodReleaseDate);
        String releaseDate = StringConversion.dateToString(zod.getReleaseDate());
        txtTvZodRelaseDate.setText(String.format(getResources().getString(
                R.string.locdvd_movie_releasedate),releaseDate));

        TextView txtTvZodDuration = (TextView) findViewById(R.id.txtTvZodDuration);
        txtTvZodDuration.setText(String.format(getString(R.string.locdvd_movie_duration),
                StringConversion.timeConversion(mVideoFile.getDuration())));

        TextView txtTvZodRating = (TextView) findViewById(R.id.txtTvZodRating);
        if (zod.getRating() != null) {
            txtTvZodRating.setVisibility(View.VISIBLE);
            txtTvZodRating.setText(String.format(getString(R.string.locdvd_movie_rating), zod.getRating()));
        }else {
            txtTvZodRating.setVisibility(View.GONE);
        }

        // TODO add genre
        final TextView txtGenre = (TextView) findViewById(R.id.lblTvZodGenre);
        final ArrayList<Genre> lstGenre = dbMedia.getGenreByMapper(zod.getMapper());

        final LinearLayout llGenre1 = (LinearLayout)findViewById(R.id.llTvZodGenre1);
        llGenre1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    llGenre1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    llGenre1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                //Dimmention layout
                int width = llGenre1.getMeasuredWidth();
                int currentWidth = txtGenre.getMeasuredWidth();
                int i=0 ;
                LinearLayout parentLayout = llGenre1, layout;
                for (final Genre genre: lstGenre){
                    TextView txtGenre = new TextView(DetailTvZodActivity.this);

                    i++;

                    txtGenre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent intent = new Intent(DetailMovieActivity.this,DetailActorActivity.class);
                            intent.putExtra("genre", genre);
                            startActivity(intent);*/
                        }
                    });

                    if (i < lstGenre.size()){
                        txtGenre.setText(genre.getGenre()+ ", ");

                    }else {
                        txtGenre.setText(genre.getGenre());
                    }

                    txtGenre.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    currentWidth += txtGenre.getMeasuredWidth();

                    // TODO add link to Actor activity on textView
                    if(currentWidth > width ){
                        layout = new LinearLayout(DetailTvZodActivity.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        parentLayout = layout;
                        ((LinearLayout)llGenre1.getParent()).addView(parentLayout);
                        width = txtGenre.getMeasuredWidth();
                    }

                    parentLayout.addView(txtGenre);
                }
             }
        });

        final LinearLayout actorGroup = (LinearLayout) findViewById(R.id.tvZodActors);

        final ArrayList<Actor> lsActor = dbMedia.getActorsByMapper(zod.getMapper());

        actorGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                // Ensure you call it only once :
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    actorGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    actorGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                LinearLayout layout = new LinearLayout(DetailTvZodActivity.this);
                layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                actorGroup.addView(layout);
                LinearLayout parentLayout = layout;

                int width = 0;
                int i = 0;
                // Here you can get the size :)
                // TODO resize actorGroup on click on txtActorLabel
                for (final Actor actor : lsActor) {
                    i++;
                    TextView txtActor = new TextView(DetailTvZodActivity.this);
                    txtActor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DetailTvZodActivity.this, DetailActorActivity.class);
                            intent.putExtra("actor", actor);
                            startActivity(intent);
                        }
                    });

                    if (i < lsActor.size()) {
                        txtActor.setText(actor + ", ");

                    } else {
                        txtActor.setText(actor.toString());
                    }

                    txtActor.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    width += txtActor.getMeasuredWidth();

                    // TODO add link to Actor activity on textView
                    if (width > actorGroup.getWidth()) {
                        layout = new LinearLayout(DetailTvZodActivity.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        parentLayout = layout;
                        actorGroup.addView(parentLayout);
                        width = txtActor.getMeasuredWidth();
                    }

                    parentLayout.addView(txtActor);

                }
            }
        });


        // TODO txtRsumer
        TextView txtTvZodResumer = (TextView) findViewById(R.id.txtTvZodSummary);
        txtTvZodResumer.setText(dbMedia.getSummaryByMapper(zod.getMapper()).getSummary());


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
        getMenuInflater().inflate(R.menu.detail_item, menu);
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
        }else if (id == R.id.action_video_propriety){
            Bundle bundle = new Bundle();
            bundle.putSerializable(API.TAG_VIDEO_FILE, mVideoFile);
            DialogFragment newFragment = new DetailVideoFileFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "video_details");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_film) {
            Intent intent = new Intent(DetailTvZodActivity.this, FilmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_serie) {
            Intent intent = new Intent(DetailTvZodActivity.this, TvShowActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_acteur) {
            Intent intent = new Intent(DetailTvZodActivity.this, ActorActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
