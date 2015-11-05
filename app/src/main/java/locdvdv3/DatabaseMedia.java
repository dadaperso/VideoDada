package locdvdv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by damien.lejart on 13/05/2015.
 */
public class DatabaseMedia extends SQLiteOpenHelper {

    private static DatabaseMedia mInstance;
    Context context;

    private static final String DATABASE_NAME = "dbMedia.db";
    private static final String TABLE_MOVIE = "t_movie";
    private static final String TABLE_VIDEO_FILE = "t_video_file";
    private static final String TABLE_TV_SHOW = "t_tvshow";
    private static final String TABLE_TV_ZOD = "t_tvzod";
    private static final String TABLE_SUMMARY = "t_summary" ;
    private static final String TABLE_ACTOR = "t_actor" ;
    private static final int DATABASE_VERSION = 4;


    public static DatabaseMedia getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseMedia(ctx);

        }
        return mInstance;
    }

    public DatabaseMedia(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;

    }

    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> strReq = new ArrayList<>();

        strReq.add("CREATE TABLE "+TABLE_MOVIE+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, "+API.TAG_TITLE+" TEXT ,"+API.TAG_TAG_LINE+" TEXT ,"+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

        strReq.add("CREATE TABLE " + TABLE_VIDEO_FILE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, path TEXT, file_size INT, duration INT, container_type TEXT, " +
                "video_codec TEXT, video_bitrate INT, resolutionx INT, resolutiony INT, " +
                "audio_codec TEXT, audio_bitrate INR, channel INT, create_date DATETIME, " +
                "modify_date DATETIME)");

        strReq.add("CREATE TABLE t_mapper (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT)");

        strReq.add("CREATE TABLE "+TABLE_TV_SHOW+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, "+API.TAG_TITLE+" TEXT, "+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

        // Add tvshow_episode table
        strReq.add("CREATE TABLE " + TABLE_TV_ZOD + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tvshow_id INT, mapper_id INT, " + API.TAG_TAG_LINE + " TEXT, " + API.TAG_SEASON +
                " INT, " + API.TAG_EPISODE + " INT, " + API.TAG_YEAR + " INT, " + API.TAG_RELEASE_DATE + " DATETIME," +
                API.TAG_CREATE_DATE + " DATETIME, " + API.TAG_MODIFY_DATE + " DATETIME)");

        // Add summary table
        strReq.add("CREATE TABLE " + TABLE_SUMMARY + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, " + API.TAG_SUMMARY + " TEXT, " + API.TAG_CREATE_DATE + " DATETIME, " +
                API.TAG_MODIFY_DATE + " DATETIME )");

        // Add actor table
        strReq.add("CREATE TABLE " + TABLE_ACTOR + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, " + API.TAG_ACTOR + " TEXT, " + API.TAG_CREATE_DATE + " DATETIME, " +
                API.TAG_MODIFY_DATE + " DATETIME )");



        for(String query: strReq){
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int currentVersion, int newVersion) {

        if (newVersion == 2){
            ArrayList<String> strReq = new ArrayList();

            // Add tvshow table
            strReq.add("CREATE TABLE "+TABLE_TV_SHOW+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "mapper_id INT, "+API.TAG_TITLE+" TEXT, "+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                    API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

            // Add tvshow_episode table
            strReq.add("CREATE TABLE " + TABLE_TV_ZOD + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tvshow_id INT, mapper_id INT, " + API.TAG_TAG_LINE + " TEXT, " + API.TAG_SEASON +
                    " INT, " + API.TAG_EPISODE + " INT, " + API.TAG_YEAR + " INT, " + API.TAG_RELEASE_DATE + " DATETIME," +
                    API.TAG_CREATE_DATE + " DATETIME, " + API.TAG_MODIFY_DATE + " DATETIME)");

            for(String query: strReq){
                db.execSQL(query);
            }
        }else if (newVersion == 3){
            // Add summary table
            db.execSQL("CREATE TABLE "+TABLE_SUMMARY+" ("+API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "mapper_id INT, "+API.TAG_SUMMARY+" TEXT, "+API.TAG_CREATE_DATE+" DATETIME, "+
                    API.TAG_MODIFY_DATE+" DATETIME )");
        }else if (newVersion == 4){
            // Add actor table
            db.execSQL("CREATE TABLE " + TABLE_ACTOR + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "mapper_id INT, " + API.TAG_ACTOR + " TEXT, " + API.TAG_CREATE_DATE + " DATETIME, " +
                    API.TAG_MODIFY_DATE + " DATETIME )");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_MOVIE);
        db.execSQL("DROP TABLE " + TABLE_VIDEO_FILE);

    }

    public void updateData(SQLiteDatabase db, String table, ContentValues values) {

        // Si existe
        String[] columns = {"id"};
        String whereCond = "id=?";
        String[] whereArg = {Integer.toString(values.getAsInteger("id"))};
        Cursor result = db.query(table, columns, whereCond, whereArg, null, null, null);

        //Alors UPDATE
        Log.d("DatabaseMedia", "Update " + table + ": " + values.getAsString("title"));
        if (result.getCount() > 0){
            db.update(table, values, whereCond, whereArg);
            if (table.equals(TABLE_MOVIE) || table.equals(TABLE_TV_ZOD)){
                String where = "create_date < ? and mapper_id =? ";
                String[] whereArgs = {values.getAsString("modify_date"), Integer.toString(values.getAsInteger("mapper_id") )};
                db.delete(TABLE_ACTOR, where, whereArgs);
            }

        }else{  //Sinon CREATE
            db.insert(table, null, values);
        }

        result.close();
    }


    /**
     *
     * @param table Name of table to check update
     * @return
     */
    public String getLastUpdateDate(String table){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"CASE when max("+API.TAG_CREATE_DATE+") > max("+API.TAG_MODIFY_DATE+") then max("+API.TAG_CREATE_DATE+") else max("+API.TAG_MODIFY_DATE+") end  as last_update_date"};

        Cursor result = db.query(table, columns,null,null, null, null, null);

        String lastUpdate = null;
        result.moveToFirst();
        while (!result.isAfterLast()){
            lastUpdate = result.getString(0);
            result.moveToNext();
        }

        result.close();

        db.close();

        return lastUpdate;
    }

    public String getTagToTable(String tag){
        String table;

        switch (tag){
            case API.TAG_MOVIE:
                table = TABLE_MOVIE;
                break;
            case API.TAG_TV_SHOW:
                table = TABLE_TV_SHOW;
                break;
            case API.TAG_TV_ZOD:
                table = TABLE_TV_ZOD;
                break;
            case API.TAG_SUMMARY:
                table = TABLE_SUMMARY;
                break;
            case API.TAG_ACTOR:
                table = TABLE_ACTOR;
                break;
            default:
                table = TABLE_MOVIE;
        }

        return table;
    }

    public ArrayList<Movie> getMovies() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_TAG_LINE, API.TAG_YEAR,
                        API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};

        Cursor result =  db.query(TABLE_MOVIE, columns, null, null, null, null, API.TAG_CREATE_DATE + " DESC");

        ArrayList<Movie> lstMovies = this.hydrateMovie(result);

        db.close();

        return lstMovies;
    }

    public ArrayList<Movie> getMoviesByActor(Actor actor){
        ArrayList<Movie> lstMovie;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_MOVIE+
                        " WHERE mapper_id IN (" +
                "           SELECT mapper_id FROM "+TABLE_ACTOR+"" +
                "           WHERE actor LIKE ?" +
                "       )";
        String[] args = {"%"+actor.getActor()+"%"};
        Cursor result = db.rawQuery(query, args);

        lstMovie = this.hydrateMovie(result);

        db.close();

        return lstMovie;
    }

    private ArrayList<Movie> hydrateMovie(Cursor result){
        ArrayList<Movie> lstMovies = new ArrayList<>();

        result.moveToFirst();
        while(!result.isAfterLast()){

            Movie movie = new Movie();

            movie.setId(result.getInt(0));

            Mapper mapper = new Mapper();
            mapper.setId(result.getInt(1));
            mapper.setType(API.TAG_MOVIE);
            movie.setMapper(mapper);

            movie.setTitle(result.getString(2));
            movie.setTagLine(result.getString(3));
            movie.setYear(result.getInt(4));

            DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

            try {
                movie.setOriginallyAvailable(date.parse(result.getString(5)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                movie.setCreateDate(date.parse(result.getString(6)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                movie.setModifyDate(date.parse(result.getString(7)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lstMovies.add(movie);

            result.moveToNext();
        }

        result.close();

        return lstMovies;
    }

    public ArrayList<Actor> getActorsByMovie(Mapper mapper){
        ArrayList<Actor> lstActor = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {API.TAG_ID, API.TAG_ACTOR, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};
        String where = "mapper_id =?";
        String[] whereArgs = {Integer.toString(mapper.getId())};
        Cursor result = db.query(TABLE_ACTOR, columns, where, whereArgs, null, null, null);

        result.moveToFirst();
        while (!result.isAfterLast()){
            Actor actor = new Actor();
            actor.setId(result.getInt(0));
            actor.setMapper(mapper);
            actor.setActor(result.getString(1));;

            DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            try {
                actor.setCreateDate(date.parse(result.getString(2) ));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                actor.setModifyDate(date.parse(result.getString(3) ));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lstActor.add(actor);
            result.moveToNext();
        }

        result.close();

        db.close();

        return lstActor;
    }

    public Summary getSummaryByMapper(Mapper mapper){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {API.TAG_ID, API.TAG_SUMMARY, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};
        String where = "mapper_id=?";
        String[] whereArgs = {Integer.toString(mapper.getId())};
        Cursor result = db.query(TABLE_SUMMARY, columns, where, whereArgs, null, null, null);

        Summary summary = null;
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

        result.moveToFirst();
        while (!result.isAfterLast()){
            summary = new Summary();
            summary.setId(result.getInt(0));
            summary.setMapper(mapper);;
            summary.setSummary(result.getString(1));

            try {
                summary.setCreateDate(date.parse(result.getString(2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                summary.setModifyDate(date.parse(result.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result.moveToNext();
        }

        return summary;
    }

    public void getTvZodByActor(Actor actor, ArrayList<Tvshow> listTvShow, HashMap<String, ArrayList<TvZod>> listZod){

        ArrayList<TvZod> lstZod = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns1 = {API.TAG_ID, "tvshow_id", "mapper_id", API.TAG_TAG_LINE, API.TAG_SEASON,
                API.TAG_EPISODE, API.TAG_YEAR, API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE,
                API.TAG_MODIFY_DATE};
        String query = "SELECT "+columns1.toString()+" FROM "+TABLE_TV_ZOD+ " as zod"+
                      " WHERE zod.mapper_id IN ("+
                            " SELECT mapper_id FROM "+TABLE_ACTOR+
                            " WHERE "+API.TAG_ACTOR+" LIKE ? )";
        String[] args = {"%"+actor.getActor()+"%"};


        Cursor result = db.rawQuery(query, args);
        Log.d("DataMedia", "SQL getZodByActor = " + query + "\n args = " + args.toString());

        int currentTvShow = 0;
        Tvshow tvshow = null;
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        result.moveToFirst();
        while (!result.isAfterLast()){

            // récupération de l'objet TvShow
            if (result.getInt(1) == currentTvShow) {

                if (tvshow != null)
                    listZod.put(tvshow.getTitle(), lstZod);

                currentTvShow = result.getInt(1);
                String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_YEAR,
                        API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};
                String where = "id=?";
                String[] whereArgs = {Integer.toString(currentTvShow)};
                Cursor result2 = db.query(TABLE_TV_SHOW, columns, where, whereArgs, null, null, null);

                result2.moveToFirst();

                while (!result2.isAfterLast()) {
                    tvshow = hydrateTvShow(result2, date);
                    result2.moveToNext();
                }



                listTvShow.add(tvshow);

                if (listZod.containsKey(tvshow.getTitle())){
                    lstZod = listZod.get(tvshow.getTitle());
                }else {
                    lstZod = new ArrayList<>();
                }
            }

            TvZod tvZod = hydrateTvZod(result,date);

            tvZod.setTvshow(tvshow);

            lstZod.add(tvZod);

            result.moveToNext();
        }


    }

    // TODO revoir les donnée recupérer (saison manquante)
    public Object[] getTvZodByTvShow(Tvshow tvshow, HashMap<String, ArrayList<TvZod>> listZod, ArrayList<String> dataListSeason){

        Object[] response = new Object[2];
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {API.TAG_ID, "tvshow_id", "mapper_id", API.TAG_TAG_LINE, API.TAG_SEASON,
                        API.TAG_EPISODE, API.TAG_YEAR, API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE,
                        API.TAG_MODIFY_DATE};
        String where = "tvshow_id=?";
        String[] whereArgs = {Integer.toString(tvshow.getId())};
        String order = API.TAG_SEASON+" ASC, "+API.TAG_EPISODE+" ASC";
        Cursor result = db.query(TABLE_TV_ZOD, columns, where, whereArgs, null, null, order);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        int currentSeason = 0;
        ArrayList<TvZod> lstZod = null;

        result.moveToFirst();
        while (!result.isAfterLast()){

            if (currentSeason != result.getInt(4)){
                if (currentSeason != 0){
                    listZod.put(Integer.toString(currentSeason), lstZod);
                    dataListSeason.add(Integer.toString(currentSeason) );
                }

                currentSeason = result.getInt(4);

                if (listZod.containsKey(currentSeason)){
                    lstZod = listZod.get(currentSeason);
                }else {
                    lstZod = new ArrayList<TvZod>();
                }

            }

            TvZod tvZod = hydrateTvZod(result,date);
            lstZod.add(tvZod);

            result.moveToNext();
        }

        response[0] = dataListSeason;
        response[1] = listZod;

        return response;
    }

    private TvZod hydrateTvZod(Cursor result, DateFormat date){
        TvZod tvZod = new TvZod();
        tvZod.setId(result.getInt(0));


        Mapper mapper =new Mapper();
        mapper.setId(result.getInt(2));
        tvZod.setMapper(mapper);

        String titre = result.getString(3);
        if (titre == null){
            titre = "Episode "+result.getInt(5);
        }
        tvZod.setTagLine(result.getString(3));
        tvZod.setSaison(result.getInt(4));
        tvZod.setEpisode(result.getInt(5));
        tvZod.setYear(result.getInt(6));

        try {
            tvZod.setReleaseDate(date.parse(result.getString(7)));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            tvZod.setCreateDate(date.parse(result.getString(8)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            tvZod.setModifyDate(date.parse(result.getString(9)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tvZod;

    }

    public ArrayList<Tvshow> getTvShow(){
        ArrayList<Tvshow> lstTvShow = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_YEAR,
                API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};
        Cursor result = db.query(TABLE_TV_SHOW,columns,null,null,null,null,API.TAG_MODIFY_DATE+ " DESC");

        result.moveToFirst();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

        while (!result.isAfterLast()){

            Tvshow tvshow = hydrateTvShow(result, date);

            lstTvShow.add(tvshow);

            result.moveToNext();
        }

        return lstTvShow;
    }

    private Tvshow hydrateTvShow(Cursor cursor, DateFormat date){

            Tvshow tvshow = new Tvshow();
            tvshow.setId(cursor.getInt(0));

            Mapper mapper = new Mapper();
            mapper.setId(cursor.getInt(1));
            mapper.setType(API.TAG_TV_SHOW);
            tvshow.setMapper(mapper);

            tvshow.setTitle(cursor.getString(2));
            tvshow.setYear(cursor.getInt(3));

            try {
                tvshow.setReleaseDate(date.parse(cursor.getString(4)));
            } catch (ParseException|NullPointerException e) {
                e.printStackTrace();
            }

            try {
                tvshow.setCreateDate(date.parse(cursor.getString(5)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                tvshow.setModifyDate(date.parse(cursor.getString(6)));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        return tvshow;
    }
}
