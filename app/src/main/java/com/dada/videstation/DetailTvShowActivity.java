package com.dada.videstation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dada.videstation.adapter.TvShowZodAdapter;
import com.dada.videstation.model.Poster;
import com.dada.videstation.model.TvZod;
import com.dada.videstation.model.Tvshow;
import com.dada.videstation.utils.API;
import com.dada.videstation.utils.DatabaseMedia;
import com.dada.videstation.utils.ServiceHandler;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;
import com.squareup.picasso.Picasso;

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


        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x - (2*16);
        int screenHeight = size.y - (2*16);
        int tiersScreenWidth = (int)(screenWidth * 0.33);
        int quarterScreenHeigth = (int)(screenHeight * 0.25);

        GridLayout.Spec row1 = GridLayout.spec(0);
        GridLayout.Spec row2 = GridLayout.spec(1);
        GridLayout.Spec row3 = GridLayout.spec(2,2);


        GridLayout.Spec col0 = GridLayout.spec(0);
        GridLayout.Spec col1 = GridLayout.spec(1);
        GridLayout.Spec colspan2 = GridLayout.spec(0, 2);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.glTvShow);

        ImageView twoByTwo1 = (ImageView) findViewById(R.id.imgAfficheFiche);
        GridLayout.LayoutParams first = new GridLayout.LayoutParams(row1, col0);
        first.width = tiersScreenWidth;
        first.height = quarterScreenHeigth;
        twoByTwo1.setLayoutParams(first);
        twoByTwo1.setBackgroundColor(Color.BLUE);

        Poster poster = DatabaseMedia.getInstance(this).getPosterByMapper(tvshow.getMapper());
        if (poster != null)
            Picasso.with(this).load(ServiceHandler.API_URL + "/poster/" + poster.getLo_oid() + ".jpeg").into(twoByTwo1);



        TextView txtReleaseDate = (TextView) findViewById(R.id.txtDateSortieFiche);
        GridLayout.LayoutParams two = new GridLayout.LayoutParams(row1, col1);
        two.width = tiersScreenWidth*2;
        two.height = quarterScreenHeigth;
        txtReleaseDate.setLayoutParams(two);
        txtReleaseDate.setBackgroundColor(Color.CYAN);
        gridLayout.getChildAt(1).setLayoutParams(two);


        String releaseDate = StringConversion.dateToString(tvshow.getReleaseDate());
        txtReleaseDate.setText(String.format(getResources().getString(
                R.string.locdvd_movie_releasedate), releaseDate));

        // TODO getSummary
        LinearLayout llSummary = (LinearLayout) findViewById(R.id.lltvShowSummary);
        GridLayout.LayoutParams three = new GridLayout.LayoutParams(row2,colspan2);
        three.width = screenWidth;
        three.height = quarterScreenHeigth;
        llSummary.setLayoutParams(three);
        llSummary.setBackgroundColor(Color.GREEN);
        gridLayout.getChildAt(2).setLayoutParams(three);

        DatabaseMedia dbMedia = DatabaseMedia.getInstance(this);
        TextView txtSummary = (TextView) findViewById(R.id.txtResumerFiche);
        txtSummary.setText(dbMedia.getSummaryByMapper(tvshow.getMapper()).getSummary());


        Object[] data = DatabaseMedia.getInstance(this).getTvZodByTvShow(tvshow, dataListZod, dataListSeason);
        dataListSeason = (ArrayList<String>) data[0];
        dataListZod = (HashMap<String, ArrayList<TvZod>>) data[1];
        final TvShowZodAdapter adapter = new TvShowZodAdapter(this,dataListSeason,dataListZod);


        GridLayout.LayoutParams four = new GridLayout.LayoutParams(row3,colspan2);
        four.width = screenWidth;
        four.height = quarterScreenHeigth*2;
        ExpandableListView listTvZod = (ExpandableListView) findViewById(R.id.lstTvShowZod);
        listTvZod.setLayoutParams(four);
        gridLayout.getChildAt(3).setLayoutParams(four);
        listTvZod.setBackgroundColor(Color.RED);
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
        if(adapter.getGroupCount()>0)
            listTvZod.expandGroup(0);

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
