package com.example.dada.res1;

import android.annotation.SuppressLint;
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
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import locdvdv3.Movie;

public class DetailItemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // TODO create getActorByMovie to get actors on this Movie
    String[] lsActor = {"Actor 1", "Actor 2", "Actor 3", "Actor 4",
            "Actor 1", "Actor 2", "Actor 3", "Actor 4",
            "Actor 1", "Actor 2", "Actor 3", "Actor 4"};

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

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitreFiche);
        txtTitle.setText(movie.getTitle());

        // TODO add tag-line

        ImageView imgAffiche = (ImageView) findViewById(R.id.imgAfficheFiche);
        // TODO implement poster table

        TextView txtSummary = (TextView) findViewById(R.id.txtResumerFiche);
        // TODO getSummary method

        final LinearLayout actorGroup = (LinearLayout) findViewById(R.id.actorGroup);

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

                LinearLayout layout = new LinearLayout(DetailItemActivity.this);
                layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                actorGroup.addView(layout);
                LinearLayout parentLayout = layout;

                int width = 0;
                int i = 0;
                // Here you can get the size :)
                for (String actor : lsActor){
                    i++;
                    TextView txtActor = new TextView(DetailItemActivity.this);

                    if (i < lsActor.length){
                       txtActor.setText(actor+ ", ");
                   }else {
                       txtActor.setText(actor);
                   }

                    txtActor.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    width += txtActor.getMeasuredWidth();

                    // TODO add link to Actor activity on textView
                    if(width > actorGroup.getWidth() ){
                        layout = new LinearLayout(DetailItemActivity.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        parentLayout = layout;
                        actorGroup.addView(parentLayout);
                        width = 0;
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
