package com.dada.videstation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dada.videstation.model.Actor;
import com.dada.videstation.model.Genre;
import com.dada.videstation.model.Movie;
import com.dada.videstation.model.VideoFile;
import com.dada.videstation.utils.API;
import com.dada.videstation.utils.DatabaseMedia;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;

import java.util.ArrayList;

public class DetailMovieActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // TODO create getActorByMovie to get actors on this Movie
    ArrayList<Actor> lsActor;
    Movie movie;
    VideoFile mVideoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
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

        movie = (Movie) getIntent().getSerializableExtra("movie");
        DatabaseMedia dbMedia = DatabaseMedia.getInstance(this);

        mVideoFile = dbMedia.getVideoFileByMapper(movie.getMapper());


        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x - (2*16);
        int screenHeight = size.y - (2*16);
        int tiersScreenWidth = (int)(screenWidth * 0.33);
        int quarterScreenHeigth = (int)(screenHeight * 0.25);
        int sixOfQuaterHeight = (int) (quarterScreenHeigth * 0.16);

        GridLayout.Spec row1 = GridLayout.spec(0);  // title
        GridLayout.Spec row2a6 = GridLayout.spec(1,5); // affiche
        GridLayout.Spec row2 = GridLayout.spec(1); // real
        GridLayout.Spec row3 = GridLayout.spec(2); // release date
        GridLayout.Spec row4 = GridLayout.spec(3); // ratting
        GridLayout.Spec row5 = GridLayout.spec(4); // genre
        GridLayout.Spec row6 = GridLayout.spec(5); // duration
        GridLayout.Spec row7 = GridLayout.spec(6); // synopsys
        GridLayout.Spec row8 = GridLayout.spec(7); // actor
        GridLayout.Spec row9 = GridLayout.spec(8);
        GridLayout.Spec row10 = GridLayout.spec(9);


        GridLayout.Spec col0 = GridLayout.spec(0);
        GridLayout.Spec col1 = GridLayout.spec(1);
        GridLayout.Spec colspan2 = GridLayout.spec(1, 2);
        GridLayout.Spec colspan3 = GridLayout.spec(0, 3);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.glMovie);


        GridLayout.LayoutParams first = new GridLayout.LayoutParams(row1,colspan3);
        first.width = screenWidth;
        first.height = (int) (sixOfQuaterHeight*1.50);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitreFiche);
        txtTitle.setText(movie.getTitle());
        txtTitle.setLayoutParams(first);

        // TODO add tag-line

        GridLayout.LayoutParams two = new GridLayout.LayoutParams(row2a6, col0);
        two.width = tiersScreenWidth;
        two.height = sixOfQuaterHeight*5;
        ImageView imgAffiche = (ImageView) findViewById(R.id.imgAfficheFiche);
        imgAffiche.setLayoutParams(two);
        // TODO implement poster table

        GridLayout.LayoutParams three = new GridLayout.LayoutParams(row2, colspan2);
        three.width = tiersScreenWidth *2;
        three.height = sixOfQuaterHeight;
        TextView txtReal = (TextView) findViewById(R.id.txtRealFiche);
        txtReal.setLayoutParams(three);
        // TODO implement real table
        txtReal.setText(String.format(getResources().getString(
                R.string.locdvd_movie_real), ""));

        GridLayout.LayoutParams four = new GridLayout.LayoutParams(row3, colspan2);
        four.width = tiersScreenWidth*2;
        four.height = sixOfQuaterHeight;
        TextView txtReleaseDate = (TextView) findViewById(R.id.txtDateSortieFiche);
        txtReleaseDate.setLayoutParams(four);
        String releaseDate = StringConversion.dateToString(movie.getReleaseDate());
        txtReleaseDate.setText(String.format(getResources().getString(
                R.string.locdvd_movie_releasedate),releaseDate));


        GridLayout.LayoutParams five = new GridLayout.LayoutParams(row4, colspan2);
        five.width = tiersScreenWidth*2;
        five.height = sixOfQuaterHeight;
        TextView txtRating = (TextView) findViewById(R.id.txtClassifictionFiche);
        txtRating.setLayoutParams(five);
        if(!movie.getRating().equals("")){
            txtRating.setVisibility(View.VISIBLE);
            txtRating.setText(
                    String.format(getResources().getString(R.string.locdvd_movie_rating)
                    ,movie.getRating())
            );
        }else {
            txtRating.setVisibility(View.GONE);
        }

        GridLayout.LayoutParams six = new GridLayout.LayoutParams(row5, colspan2);
        six.width = tiersScreenWidth*2;
        six.height = sixOfQuaterHeight;
        final ArrayList<Genre> lstGenre = dbMedia.getGenreByMapper(movie.getMapper());
        final TextView txtGenre = (TextView) findViewById(R.id.txtGenreFiche);
        // TODO implement genre table
        txtGenre.setText(String.format(getResources().getString(
                R.string.locdvd_movie_genre), ""));

        final LinearLayout llGenre1 = (LinearLayout)findViewById(R.id.llGenre1);
        ((LinearLayout)llGenre1.getParent()).setLayoutParams(six);
        llGenre1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    llGenre1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    llGenre1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                //Dimmention layout
                int width = llGenre1.getMeasuredWidth();
                int currentWidth = txtGenre.getMeasuredWidth();
                int i = 0;
                LinearLayout parentLayout = llGenre1, layout;
                for (final Genre genre : lstGenre) {
                    TextView txtGenre = new TextView(DetailMovieActivity2.this);

                    i++;

                    txtGenre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent intent = new Intent(DetailMovieActivity.this,DetailActorActivity.class);
                            intent.putExtra("genre", genre);
                            startActivity(intent);*/
                        }
                    });

                    if (i < lstGenre.size()) {
                        txtGenre.setText(genre.getGenre() + ", ");

                    } else {
                        txtGenre.setText(genre.getGenre());
                    }

                    txtGenre.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    currentWidth += txtGenre.getMeasuredWidth();

                    // TODO add link to Actor activity on textView
                    if (currentWidth > width) {
                        layout = new LinearLayout(DetailMovieActivity2.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        parentLayout = layout;
                        ((LinearLayout) llGenre1.getParent()).addView(parentLayout);
                        width = txtGenre.getMeasuredWidth();
                    }

                    parentLayout.addView(txtGenre);
                }


            }
        });

        GridLayout.LayoutParams seven = new GridLayout.LayoutParams(row6,colspan2);
        seven.width = tiersScreenWidth;
        seven.height = sixOfQuaterHeight;
        TextView txtDuration = (TextView) findViewById(R.id.txtDureeFiche);
        txtDuration.setLayoutParams(seven);
        String duration = StringConversion.timeConversion(mVideoFile.getDuration());
        txtDuration.setText(String.format(getResources().getString(
                R.string.locdvd_movie_duration), duration));


        GridLayout.LayoutParams eigth = new GridLayout.LayoutParams(row7,colspan3);
        eigth.width = screenWidth;
        eigth.height = sixOfQuaterHeight;
        TextView lblSumary = (TextView) findViewById(R.id.lblSynopsis);
        lblSumary.setLayoutParams(eigth);

        //TODO add scrollView
        GridLayout.LayoutParams nine = new GridLayout.LayoutParams(row8,colspan3);
        nine.width = screenWidth - 50; //TODO fix mot coupe au saut de ligne
        nine.height = quarterScreenHeigth;
        TextView txtSummary = (TextView) findViewById(R.id.txtResumerFiche);
        txtSummary.setLayoutParams(nine);
        txtSummary.setText(dbMedia.getSummaryByMapper(movie.getMapper()).getSummary());

        GridLayout.LayoutParams ten = new GridLayout.LayoutParams(row9, colspan3);
        ten.width = screenWidth;
        ten.height = quarterScreenHeigth;
        final LinearLayout actorGroup = (LinearLayout) findViewById(R.id.actorGroup);
        actorGroup.setLayoutParams(ten);

        lsActor = dbMedia.getActorsByMapper(movie.getMapper());

        //TODO add scrollView
        actorGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                // Ensure you call it only once :
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    actorGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    actorGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                LinearLayout layout = new LinearLayout(DetailMovieActivity2.this);
                layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                actorGroup.addView(layout);
                LinearLayout parentLayout = layout;

                int width = 0;
                int i = 0;
                // Here you can get the size :)
                // TODO resize actorGroup on click on txtActorLabel
                for (final Actor actor : lsActor){
                    i++;
                    TextView txtActor = new TextView(DetailMovieActivity2.this);
                    txtActor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DetailMovieActivity2.this,DetailActorActivity.class);
                            intent.putExtra("actor", actor);
                            startActivity(intent);
                        }
                    });

                    if (i < lsActor.size()){
                       txtActor.setText(actor+ ", ");

                   }else {
                       txtActor.setText(actor.toString());
                   }

                    txtActor.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    width += txtActor.getMeasuredWidth();

                    // TODO add link to Actor activity on textView
                    if(width > actorGroup.getWidth() ){
                        layout = new LinearLayout(DetailMovieActivity2.this);
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
        }else if(id == R.id.action_video_propriety){
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
            Intent intent = new Intent(DetailMovieActivity2.this, FilmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_serie) {
            Intent intent = new Intent(DetailMovieActivity2.this, TvShowActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_acteur) {
            Intent intent = new Intent(DetailMovieActivity2.this, ActorActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
