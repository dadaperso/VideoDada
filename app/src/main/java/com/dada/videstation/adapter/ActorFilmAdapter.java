package com.dada.videstation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dada.videstation.model.Movie;
import com.example.dada.res1.R;

import java.util.ArrayList;

/**
 * Created by dada on 03/11/2015.
 */
public class ActorFilmAdapter extends ArrayAdapter<Movie> {
    private final Context mContext;

    public ActorFilmAdapter(Context context, int resource, ArrayList<Movie> lstMovie) {
        super(context, resource, lstMovie);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Here we inflate the layout to view (linear in my case)
        View view = inflater.inflate(R.layout.item_actor_movie, parent, false);

        Movie movie = getItem(position);

        TextView txtActorMovieDate = (TextView) view.findViewById(R.id.txtActorMovieDate);
        txtActorMovieDate.setText(Integer.toString(movie.getYear()));

        TextView txtActorMovieTitle = (TextView) view.findViewById(R.id.txtActorMovieTitle);
        txtActorMovieTitle.setText(movie.getTitle());

        return view;
    }
}
