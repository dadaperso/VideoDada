package com.dada.videstation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dada.videstation.DetailMovieActivity2;
import com.dada.videstation.model.Movie;
import com.dada.videstation.model.ViewHolder;
import com.dada.videstation.utils.DatabaseMedia;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;

import java.util.ArrayList;

/**
 * Created by damien.lejart on 13/05/2015.
 */
public class FilmAdapter extends BaseAdapter {

    private final Context mContext;
    private ArrayList<Movie> mListMovie;

    public FilmAdapter(Context context) {
        this.mContext = context;

        DatabaseMedia dbMedia = new DatabaseMedia(context);

        this.mListMovie = dbMedia.getMovies();

    }

    @Override
    public int getCount() {
        return this.mListMovie.size();
    }

    @Override
    public Movie getItem(int position) {
        return this.mListMovie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Movie) getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            //Here we inflate the layout to view (linear in my case)
            result = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.imgAffiche = (ImageView) result.findViewById(R.id.imgLstAffiche);
            holder.txtTitle = (TextView) result.findViewById(R.id.txtTiltle);
            holder.txtReleaseDate = (TextView) result.findViewById(R.id.txtDateRelease);
            result.setTag(holder);
        } else {
            result = convertView;
            holder = (ViewHolder) result.getTag();
        }

        Movie film = (Movie) getItem(position);

        holder.txtTitle.setText(film.getTitle());

        holder.txtReleaseDate.setText(StringConversion.dateToString(film.getReleaseDate()));

        //holder.imgAffiche.setImageResource(film.getiImg());

        //Now get the id or whatever needed
        result.setId(position);
        // Now set the onClickListener
        result.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(mContext, DetailMovieActivity.class);
                Intent intent = new Intent(mContext, DetailMovieActivity2.class);
                intent.putExtra("movie",getItem(result.getId()));

                mContext.startActivity(intent);
                Toast.makeText(mContext, "Clicked" + result.getId() + "!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return result;

    }


}
