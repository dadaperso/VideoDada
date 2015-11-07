package locdvdv3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        // TODO complet view
        return null;
    }
}
