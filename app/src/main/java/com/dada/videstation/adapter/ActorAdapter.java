package com.dada.videstation.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dada.videstation.DetailActorActivity;
import com.dada.videstation.model.Actor;
import com.dada.videstation.model.ViewHolder;
import com.example.dada.res1.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by dada on 07/11/2015.
 */
public class ActorAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Actor> mData;

    public ActorAdapter(Context context, ArrayList<Actor> data)
    {
        this.mContext = context;
        this.mData = data;


    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Actor getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            //Here we inflate the layout to view (linear in my case)
            result = inflater.inflate(R.layout.item_actor, parent, false);
            holder = new ViewHolder();
            holder.imgAffiche = (ImageView) result.findViewById(R.id.imgLstAffiche);
            holder.txtTitle = (TextView) result.findViewById(R.id.txtTiltle);
            holder.txtNbAparencie = (TextView) result.findViewById(R.id.txtNbAparencie);
            result.setTag(holder);
        } else {
            result = convertView;
            holder = (ViewHolder) result.getTag();
        }

        Actor actor = (Actor) getItem(position);


        // TODO fix show name of actor
        holder.txtTitle.setText(actor.getActor());

        Resources res = result.getResources();

        String lbtNbMovie,lblNbZod, lblNbMoveZod = "";

        if (actor.getNbMovie()>0 && actor.getNbZod() > 0) {
            lbtNbMovie = res.getQuantityString(R.plurals.locdvd_actor_nb_movie, actor.getNbMovie(), actor.getNbMovie());
            lblNbZod = res.getQuantityString(R.plurals.locdvd_actor_nb_zod, actor.getNbZod(), actor.getNbZod());

            lblNbMoveZod = lbtNbMovie + "\n" + lblNbZod;

        }else if (actor.getNbMovie() > 0 ){
            lblNbMoveZod = res.getQuantityString(R.plurals.locdvd_actor_nb_movie, actor.getNbMovie(), actor.getNbMovie());

        }else if (actor.getNbZod() > 0){
            lblNbMoveZod = res.getQuantityString(R.plurals.locdvd_actor_nb_zod, actor.getNbZod(), actor.getNbZod());
        }

        holder.txtNbAparencie.setText(lblNbMoveZod);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // TODO show birthDate

        //holder.imgAffiche.setImageResource(film.getiImg());

        //Now get the id or whatever needed
        result.setId(position);
        // Now set the onClickListener
        result.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, DetailActorActivity.class);
                intent.putExtra("actor",getItem(result.getId()));

                mContext.startActivity(intent);
                Toast.makeText(mContext, "Clicked" + result.getId() + "!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return result;
    }
}
