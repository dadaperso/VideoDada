package com.dada.videstation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dada.videstation.model.Item;
import com.dada.videstation.model.Movie;
import com.dada.videstation.model.Tvshow;
import com.dada.videstation.model.ViewHolder;
import com.dada.videstation.utils.API;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;

import java.util.List;

/**
 * Created by dada on 26/11/2015.
 */

public class ItemAdapter  extends ArrayAdapter{
    private final Context _context;

    public ItemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

        this._context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(_context);
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

        Item item;
        if (getItem(position).getClass().toString().equals("Movie")){
            item = (Movie) getItem(position);
        }else{
            item = (Tvshow) getItem(position);
        }


        holder.txtTitle.setText(item.getTitle());

        holder.txtReleaseDate.setText(StringConversion.dateToString(item.getReleaseDate()));

        //holder.imgAffiche.setImageResource(film.getiImg());

        //Now get the id or whatever needed
        result.setId(position);

        // Now set the onClickListener
        result.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Item item1 = (Item) getItem(result.getId());

                Intent intent;
                if (item1.getType().equals(API.TAG_MOVIE)){
                    intent = new Intent(_context, DetailMovieActivity2.class);
                    intent.putExtra(API.TAG_MOVIE, item1);
                }else {
                    intent = new Intent(_context, DetailTvShowActivity.class);
                    intent.putExtra(API.TAG_TV_SHOW, item1);
                }

                _context.startActivity(intent);
                Toast.makeText(_context, "Clicked" + result.getId() + "!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return result;
    }
}
