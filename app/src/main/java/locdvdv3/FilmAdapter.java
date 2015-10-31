package locdvdv3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dada.res1.DetailItemActivity;
import com.example.dada.res1.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try{
            holder.txtReleaseDate.setText(df.format(film.getOriginallyAvailable()));
        }catch (NullPointerException e){
            e.printStackTrace();
            holder.txtReleaseDate.setText("0000-00-00");
        }

        //holder.imgAffiche.setImageResource(film.getiImg());

        //Now get the id or whatever needed
        result.setId(position);
        // Now set the onClickListener
        result.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, DetailItemActivity.class);
                intent.putExtra("movie",getItem(result.getId()));

                mContext.startActivity(intent);
                Toast.makeText(mContext, "Clicked" + result.getId() + "!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return result;

    }


}
