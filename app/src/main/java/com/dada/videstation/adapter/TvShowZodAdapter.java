package com.dada.videstation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dada.videstation.model.TvZod;
import com.example.dada.res1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dada on 04/11/2015.
 */
public class TvShowZodAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<TvZod>> _listDataChild;


    public TvShowZodAdapter(Context context, ArrayList<String> listDataHeader,
                            HashMap<String, ArrayList<TvZod>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public TvZod getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final TvZod zod = (TvZod) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_actor_tvshow_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.txtActorTvZodTitle);
        txtListChild.setText(zod.getTagLine());

        TextView txtNumZod = (TextView) convertView.findViewById(R.id.txtActorTvZodNum);
        txtNumZod.setText("S"+zod.getSaison()+"E"+zod.getEpisode());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_tvshow_zod_head, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txtTvShowZodSeason);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        String lblSaison =  _context.getResources().getString(R.string.locdvd_season);
        lblListHeader.setText(lblSaison + headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
