package com.dada.videstation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dada.videstation.DetailTvShowActivity;
import com.dada.videstation.model.Poster;
import com.dada.videstation.model.Tvshow;
import com.dada.videstation.model.ViewHolder;
import com.dada.videstation.utils.DatabaseMedia;
import com.dada.videstation.utils.ServiceHandler;
import com.dada.videstation.utils.StringConversion;
import com.example.dada.res1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dada on 04/11/2015.
 */
public class TvShowAdapter extends ArrayAdapter<Tvshow> {
    private final Context _context;

    public TvShowAdapter(Context context, int resource, ArrayList<Tvshow> objects) {
        super(context, resource, objects);

        this._context = context;
    }

    @Override
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

        Tvshow tvshow = (Tvshow) getItem(position);

        holder.txtTitle.setText(tvshow.getTitle());

        holder.txtReleaseDate.setText(StringConversion.dateToString(tvshow.getReleaseDate()));

        Poster poster = (Poster) DatabaseMedia.getInstance(_context).getPosterByMapper(tvshow.getMapper());
        if (poster != null)
            Picasso.with(_context).load(ServiceHandler.API_URL + "/poster/" + poster.getLo_oid() + ".jpeg").into(holder.imgAffiche);

        //Now get the id or whatever needed
        result.setId(position);
        // Now set the onClickListener
        result.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(_context, DetailTvShowActivity.class);
                intent.putExtra("tvshow",getItem(result.getId()));

                _context.startActivity(intent);
                Toast.makeText(_context, "Clicked" + result.getId() + "!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return result;
    }
}
