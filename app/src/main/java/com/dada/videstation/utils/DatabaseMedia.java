
package com.dada.videstation.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dada.videstation.model.Actor;
import com.dada.videstation.model.Genre;
import com.dada.videstation.model.Item;
import com.dada.videstation.model.Mapper;
import com.dada.videstation.model.Movie;
import com.dada.videstation.model.Summary;
import com.dada.videstation.model.TvZod;
import com.dada.videstation.model.Tvshow;
import com.dada.videstation.model.VideoFile;

import org.json.JSONArray;
import org.json.JSONException;

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
    private static final String TABLE_MAPPER = "t_mapper" ;
    private static final String TABLE_GENRE = "t_genre";
    private static final String TABLE_WATCH = "t_watch_status";
    private static final int DATABASE_VERSION = 7;

    private boolean where = false;


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

        // Create MOVIE table
        strReq.add("CREATE TABLE "+TABLE_MOVIE+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, "+API.TAG_TITLE+" TEXT ,"+API.TAG_TAG_LINE+" TEXT ,"+API.TAG_YEAR+" INT, "+
                API.TAG_RELEASE_DATE+" DATETIME,"+API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME, "
                +API.TAG_RATING+" TEXT)");

        // Create VideoFile table
        strReq.add("CREATE TABLE " + TABLE_VIDEO_FILE + " ("+API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                API.TAG_MAPPER_ID+" INT, "+API.TAG_PATH+" TEXT, "+API.TAG_FILE_SIZE+" INT, "+
                API.TAG_DURATION+" INT, "+API.TAG_CONTAINER_TYPE+" TEXT, " + API.TAG_VIDEO_CODEC+" TEXT, "+
                API.TAG_VIDEO_BITERATE+" INT, "+API.TAG_RESOLUTIONX+" INT, "+API.TAG_RESOLUTIONY+" INT, " +
                API.TAG_AUDIO_CODEC+" TEXT, "+API.TAG_AUDIO_BITRATE+" INT, "+API.TAG_CHANNEL+" INT, "+
                API.TAG_CREATE_DATE+" DATETIME, " +API.TAG_MODIFY_DATE+ " DATETIME)");

        // Create Mapper table
        strReq.add("CREATE TABLE "+TABLE_MAPPER+" ("+API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                API.TAG_MAPPER_TYPE+" TEXT)");

        // Create TvShow table
        strReq.add("CREATE TABLE "+TABLE_TV_SHOW+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, "+API.TAG_TITLE+" TEXT, "+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

        // Create TvZod table
        strReq.add("CREATE TABLE " + TABLE_TV_ZOD + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tvshow_id INT, mapper_id INT, " + API.TAG_TAG_LINE + " TEXT, " + API.TAG_SEASON +
                " INT, " + API.TAG_EPISODE + " INT, " + API.TAG_YEAR + " INT, " + API.TAG_RELEASE_DATE + " DATETIME," +
                API.TAG_CREATE_DATE + " DATETIME, " + API.TAG_MODIFY_DATE + " DATETIME, "+
                API.TAG_RATING+" TEXT)");

        // Create Summary table
        strReq.add("CREATE TABLE " + TABLE_SUMMARY + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, " + API.TAG_SUMMARY + " TEXT, " + API.TAG_CREATE_DATE + " DATETIME, " +
                API.TAG_MODIFY_DATE + " DATETIME )");

        // Create Actor table
        strReq.add("CREATE TABLE " + TABLE_ACTOR + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mapper_id INT, " + API.TAG_ACTOR + " TEXT, " + API.TAG_CREATE_DATE + " DATETIME, " +
                API.TAG_MODIFY_DATE + " DATETIME )");

        // Create Genre table
        strReq.add("CREATE TABLE "+TABLE_GENRE+" ("+API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                API.TAG_MAPPER_ID+" INT, "+API.TAG_GENRE+" TEXT, "+API.TAG_CREATE_DATE+" DATETIME,"+
                API.TAG_MODIFY_DATE+" DATETIME )");

        strReq.add("CREATE TABLE "+TABLE_WATCH+" ("+API.TAG_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                API.TAG_UID+" INT, "+API.TAG_VIDEO_FILE_ID+" INT, "+API.TAG_MAPPER_ID+" INT, "+
                API.TAG_POSITION+" INT, "+API.TAG_CREATE_DATE+" DATETIME, "+ API.TAG_MODIFY_DATE+" DATETIME)");

        for(String query: strReq){
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int currentVersion, int newVersion) {

        if (newVersion == 2){
            ArrayList<String> strReq = new ArrayList<>();

            // Add tvshow table
            strReq.add("CREATE TABLE "+TABLE_TV_SHOW+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "mapper_id INT, "+API.TAG_TITLE+" TEXT, "+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                    API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

            // Add tvshow_episode table
            strReq.add("CREATE TABLE " + TABLE_TV_ZOD + " (" + API.TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tvshow_id INT, mapper_id INT, " + API.TAG_TAG_LINE + " TEXT, " + API.TAG_SEASON +
                    " INT, " + API.TAG_EPISODE + " INT, " + API.TAG_YEAR + " INT, " + API.TAG_RELEASE_DATE + " DATETIME," +
                    API.TAG_CREATE_DATE + " DATETIME, " + API.TAG_MODIFY_DATE + " DATETIME, "+
                    API.TAG_RATING+" TEXT)");

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
        }else if (newVersion == 5){
            db.execSQL("CREATE TABLE "+TABLE_GENRE+" ("+API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    API.TAG_MAPPER_ID+" INT, "+API.TAG_GENRE+" TEXT, "+API.TAG_CREATE_DATE+" DATETIME,"+
                    API.TAG_MODIFY_DATE+" DATETIME)");
        }else if(newVersion == 6){
            db.execSQL("CREATE TABLE "+TABLE_WATCH+" ("+API.TAG_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    API.TAG_UID+" INT, "+API.TAG_VIDEO_FILE_ID+" INT, "+API.TAG_MAPPER_ID+" INT, "+
                    API.TAG_POSITION+" INT, "+API.TAG_CREATE_DATE+" DATETIME, "+ API.TAG_MODIFY_DATE+" DATETIME)");
        }else if (newVersion == 7){
            db.execSQL("CREATE INDEX t_movie_mapper_id ON "+TABLE_MOVIE+"("+API.TAG_MAPPER_ID+");");
            db.execSQL("CREATE INDEX t_video_flie_mapper_id ON "+TABLE_VIDEO_FILE+"("+API.TAG_MAPPER_ID+");");
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
        String field="";
        switch (table){
            case TABLE_TV_SHOW:
            case TABLE_MOVIE:
                field = API.TAG_TITLE;
                break ;
            case TABLE_TV_ZOD:
                field = API.TAG_TAG_LINE;
                break;
            case TABLE_ACTOR:
                field = API.TAG_ACTOR;
                break;
            case TABLE_VIDEO_FILE:
                field = API.TAG_PATH;
                break;
            case TABLE_GENRE:
                field = API.TAG_GENRE;
        }


        Log.d("DatabaseMedia", "Update " + table + ": " + values.getAsString(field));


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
     * @return String
     */
    public String getLastUpdateDateOrId(String table){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[1];
        if (table.equals(TABLE_MAPPER)){
            columns[0] = "MAX("+ API.TAG_ID +") as last_id";
        }else {
            columns[0] = "CASE when max("+API.TAG_CREATE_DATE+") > max("+API.TAG_MODIFY_DATE+") then"+
                    " max("+API.TAG_CREATE_DATE+") else max("+API.TAG_MODIFY_DATE+") end "+
                    " as last_update_date";
        }

        Cursor result = db.query(table, columns,null,null, null, null, null);

        String lastUpdate = null;
        result.moveToFirst();
        while (!result.isAfterLast()){
            if (table.equals(TABLE_MAPPER)){
                lastUpdate = Integer.toString(result.getInt(0));
            }else {
                lastUpdate = result.getString(0);
            }
            result.moveToNext();
        }

        result.close();
        return lastUpdate;
    }

    public String getTagToTable(String tag) throws Exception {
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
            case API.TAG_MAPPER:
                table = TABLE_MAPPER;
                break;
            case API.TAG_VIDEO_FILE:
                table = TABLE_VIDEO_FILE;
                break;
            case API.TAG_GENRE:
                table = TABLE_GENRE;
                break;
            case API.TAG_WATCH:
                table = TABLE_WATCH;
                break;
            default:
                throw new Exception("Unable to find a table for '"+tag+"' tag");
        }

        return table;
    }

    public ArrayList<Movie> getMovies() {

        ArrayList<Movie> lstMovies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_TAG_LINE, API.TAG_YEAR,
                        API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE,API.TAG_RATING};

        Cursor result =  db.query(TABLE_MOVIE, columns, null, null, null, null, API.TAG_CREATE_DATE + " DESC");

        result.moveToFirst();
        while (!result.isAfterLast()){
            Movie movie = this.hydrateMovie(result);

            lstMovies.add(movie);

            result.moveToNext();
        }

        result.close();

        return lstMovies;
    }

    public ArrayList<Movie> getMoviesByActor(Actor actor){
        ArrayList<Movie> lstMovie = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_MOVIE+
                        " WHERE mapper_id IN (" +
                "           SELECT mapper_id FROM "+TABLE_ACTOR+"" +
                "           WHERE actor LIKE ?" +
                "       )" +
                "        ORDER BY year DESC";
        String[] args = {"%"+actor.getActor()+"%"};
        Cursor result = db.rawQuery(query, args);

        result.moveToFirst();
        while(!result.isAfterLast()) {
            Movie movie = this.hydrateMovie(result);

            lstMovie.add(movie);

            result.moveToNext();
        }

        result.close();

        return lstMovie;
    }

    private Movie hydrateMovie(Cursor result){

        Movie movie = new Movie();

        movie.setId(result.getInt(0));

        Mapper mapper = new Mapper();
        mapper.setId(result.getInt(1));
        mapper.setType(API.TAG_MOVIE);
        movie.setMapper(mapper);

        movie.setTitle(result.getString(2));
        movie.setTagLine(result.getString(3));
        movie.setYear(result.getInt(4));
        movie.setReleaseDate(StringConversion.stringToDate(result.getString(5)));
        movie.setCreateDate(StringConversion.stringToDate(result.getString(6)));
        movie.setModifyDate(StringConversion.stringToDate(result.getString(7)));
        movie.setRating(result.getString(8));

        return movie;
    }

    public ArrayList<Actor> getActorsByMapper(Mapper mapper){
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
            actor.setActor(result.getString(1));
            actor.setCreateDate(StringConversion.stringToDate(result.getString(2)));
            actor.setModifyDate(StringConversion.stringToDate(result.getString(3)));

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

        result.moveToFirst();
        while (!result.isAfterLast()){
            summary = new Summary();
            summary.setId(result.getInt(0));
            summary.setMapper(mapper);;
            summary.setSummary(result.getString(1));
            summary.setCreateDate(StringConversion.stringToDate(result.getString(2)));
            summary.setModifyDate(StringConversion.stringToDate(result.getString(3)));

            result.moveToNext();
        }

        result.close();

        return summary;
    }

    public Object[] getTvZodByActor(Actor actor){

        Object[] response = new Object[2];
        ArrayList<TvZod> lstZod = null;
        HashMap<String, ArrayList<TvZod>> listZod = new HashMap<>();
        ArrayList<Tvshow> listTvShow = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String columns1 = API.TAG_ID+", tvshow_id, mapper_id,"+ API.TAG_TAG_LINE+", "+ API.TAG_SEASON+
                ","+ API.TAG_EPISODE+", "+ API.TAG_YEAR+", "+ API.TAG_RELEASE_DATE+", "+
                API.TAG_CREATE_DATE+", "+ API.TAG_MODIFY_DATE+", "+API.TAG_RATING;
        String query = "SELECT "+columns1+" FROM "+TABLE_TV_ZOD+ " as zod"+
                      " WHERE zod.mapper_id IN ("+
                            " SELECT mapper_id FROM "+TABLE_ACTOR+
                            " WHERE "+API.TAG_ACTOR+" LIKE ? )"+
                      " ORDER BY "+API.TAG_SEASON+" ASC, "+API.TAG_EPISODE+" ASC";
        String[] args = {"%"+actor.getActor()+"%"};


        Cursor result = db.rawQuery(query, args);
        Log.d("DataMedia", "SQL getZodByActor = " + query + "\n args = " + args[0]);


        Tvshow tvshow = null;
        result.moveToFirst();
        int currentTvShow = 0;
        while (!result.isAfterLast()){

            // récupération de l'objet TvShow
            if (result.getInt(1) != currentTvShow) {

                if (tvshow != null)
                    listZod.put(tvshow.getTitle(), lstZod);

                currentTvShow = result.getInt(1);
                String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_YEAR,
                        API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE,};
                String where = "id=?";
                String[] whereArgs = {Integer.toString(currentTvShow)};
                Cursor result2 = db.query(TABLE_TV_SHOW, columns, where, whereArgs, null, null, null);

                result2.moveToFirst();

                while (!result2.isAfterLast()) {
                    tvshow = hydrateTvShow(result2);
                    result2.moveToNext();
                }

                result2.close();

                listTvShow.add(tvshow);

                if (listZod.containsKey(tvshow.getTitle())){
                    lstZod = listZod.get(tvshow.getTitle());
                }else {
                    lstZod = new ArrayList<>();
                }
            }

            TvZod tvZod = hydrateTvZod(result);

            tvZod.setTvshow(tvshow);

            lstZod.add(tvZod);

            result.moveToNext();
        }

        result.close();

        if (tvshow != null)
            listZod.put(tvshow.getTitle(), lstZod);

        response[0]= listTvShow;
        response[1] = listZod;

        return response;
    }

    public Object[] getTvZodByTvShow(Tvshow tvshow, HashMap<String, ArrayList<TvZod>> listZod, ArrayList<String> dataListSeason){

        Object[] response = new Object[2];
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {API.TAG_ID, "tvshow_id", "mapper_id", API.TAG_TAG_LINE, API.TAG_SEASON,
                        API.TAG_EPISODE, API.TAG_YEAR, API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE,
                        API.TAG_MODIFY_DATE, API.TAG_RATING};
        String where = "tvshow_id=?";
        String[] whereArgs = {Integer.toString(tvshow.getId())};
        String order = API.TAG_SEASON+" ASC, "+API.TAG_EPISODE+" ASC";
        Cursor result = db.query(TABLE_TV_ZOD, columns, where, whereArgs, null, null, order);

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

            TvZod tvZod = hydrateTvZod(result);
            tvZod.setTvshow(tvshow);

            lstZod.add(tvZod);

            result.moveToNext();
        }

        dataListSeason.add(Integer.toString(currentSeason));
        listZod.put(Integer.toString(currentSeason), lstZod);


        response[0] = dataListSeason;
        response[1] = listZod;

        return response;
    }

    private TvZod hydrateTvZod(Cursor result){
        TvZod tvZod = new TvZod();
        tvZod.setId(result.getInt(0));


        Mapper mapper =new Mapper();
        mapper.setId(result.getInt(2));
        tvZod.setMapper(mapper);

        String titre = result.getString(3);
        if (titre == null){
            titre = "Episode "+result.getInt(5);
        }
        tvZod.setTagLine(titre);
        tvZod.setSaison(result.getInt(4));
        tvZod.setEpisode(result.getInt(5));
        tvZod.setYear(result.getInt(6));
        tvZod.setReleaseDate(StringConversion.stringToDate(result.getString(7)));
        tvZod.setCreateDate(StringConversion.stringToDate(result.getString(8)));
        tvZod.setModifyDate(StringConversion.stringToDate(result.getString(9)));
        tvZod.setRating(result.getString(10));

        return tvZod;

    }

    public ArrayList<Tvshow> getTvShow(){
        ArrayList<Tvshow> lstTvShow = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {API.TAG_ID, "mapper_id", API.TAG_TITLE, API.TAG_YEAR,
                API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE};
        Cursor result = db.query(TABLE_TV_SHOW,columns,null,null,null,null,API.TAG_MODIFY_DATE+ " DESC");

        result.moveToFirst();

        while (!result.isAfterLast()){

            Tvshow tvshow = hydrateTvShow(result);

            lstTvShow.add(tvshow);

            result.moveToNext();
        }

        return lstTvShow;
    }

    private Tvshow hydrateTvShow(Cursor cursor){

            Tvshow tvshow = new Tvshow();
            tvshow.setId(cursor.getInt(0));

            Mapper mapper = new Mapper();
            mapper.setId(cursor.getInt(1));
            mapper.setType(API.TAG_TV_SHOW);
            tvshow.setMapper(mapper);

            tvshow.setTitle(cursor.getString(2));
            tvshow.setYear(cursor.getInt(3));
            tvshow.setReleaseDate(StringConversion.stringToDate(cursor.getString(4)));
            tvshow.setCreateDate(StringConversion.stringToDate(cursor.getString(5)));
            tvshow.setModifyDate(StringConversion.stringToDate(cursor.getString(6)));

        return tvshow;
    }

    public ArrayList<Actor> getActors() {
        ArrayList<Actor> lstActor = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select "+API.TAG_ACTOR+", "+API.TAG_MAPPER_TYPE+", count("+API.TAG_MAPPER_TYPE+")" +
                " FROM "+TABLE_ACTOR+" as a"+
                " INNER JOIN "+TABLE_MAPPER+" as map ON map."+API.TAG_ID+" = a."+API.TAG_MAPPER_ID+
                " GROUP BY "+API.TAG_ACTOR+", "+API.TAG_MAPPER_TYPE+" ORDER BY "+API.TAG_ACTOR+" ASC";

        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();

        if(result.getCount() >0 )
        {
            String currentActor = result.getString(0);
            Actor actor = new Actor();
            actor.setActor(currentActor);

            while (!result.isAfterLast()){
                String actorName = result.getString(0);
                String type = result.getString(1);
                int nbType = result.getInt(2);

                if (!currentActor.equals(actorName)){
                    lstActor.add(actor);
                    actor = new Actor();
                    // add name into Object
                    actor.setActor(result.getString(0));
                    currentActor = actorName;
                }

                // add nb aparence by media type
                if (type.equals(API.TAG_MOVIE)){
                    actor.setNbMovie(nbType);
                }else if (type.equals("tvshow_episode")){
                    actor.setNbZod(nbType);
                }

                result.moveToNext();
            }
        }

        result.close();

        return lstActor;
    }

    public VideoFile getVideoFileByMapper(Mapper mapper) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {API.TAG_ID,API.TAG_MAPPER_ID,API.TAG_PATH, API.TAG_FILE_SIZE,
                            API.TAG_DURATION, API.TAG_CONTAINER_TYPE, API.TAG_VIDEO_CODEC,
                            API.TAG_VIDEO_BITERATE, API.TAG_RESOLUTIONX, API.TAG_RESOLUTIONY,
                            API.TAG_AUDIO_CODEC, API.TAG_AUDIO_BITRATE, API.TAG_CHANNEL};
        String where = API.TAG_MAPPER_ID+"=?";
        String[] whereArgs = {Integer.toString(mapper.getId())};

        Cursor result = db.query(TABLE_VIDEO_FILE, columns, where, whereArgs, null, null, null);

        VideoFile videoFile = null;

        result.moveToFirst();

        while (!result.isAfterLast()){
            videoFile = new VideoFile();

            videoFile.setPath(result.getString(2));
            videoFile.setFileSize(result.getInt(3));
            videoFile.setDuration(result.getInt(4));
            videoFile.setContainerType(result.getString(5));
            videoFile.setVideoCodec(result.getString(6));
            videoFile.setVideoBitrate(result.getInt(7));
            videoFile.setResolutionx(result.getInt(8));
            videoFile.setResolutiony(result.getInt(9));
            videoFile.setAudioCodec(result.getString(10));
            videoFile.setAudioBitrate(result.getInt(11));
            videoFile.setChannel(result.getInt(12));

            result.moveToNext();
        }

        result.close();

        return videoFile;
    }

    public int getCountAllByTable(String table) {
        int nbRows = 0;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns ={"COUNT(id)"};

        Cursor result = db.query(table, columns, null, null, null, null, null);
        result.moveToFirst();

        while (!result.isAfterLast()){
            nbRows = result.getInt(0);
            result.moveToNext();
        }

        result.close();

        return nbRows;
    }

    public ArrayList<Integer> getAllIdByTable(String table) {
        ArrayList<Integer> ids = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id"};
        Cursor result = db.query(table, columns, null, null, null, null, null);

        result.moveToFirst();

        while (!result.isAfterLast()){
            ids.add(result.getInt(0));
            result.moveToNext();
        }
        result.close();

        return ids;
    }

    public void deleteData(String table, JSONArray data) {
        SQLiteDatabase db = getWritableDatabase();

        String mapperIds = "";

        for (int i=0;i<data.length();i++){
            try {
                mapperIds += Integer.toString(this.getMapperIdByEntityId((int)data.get(i),table));
                if (i != data.length()-1){
                    mapperIds+=",";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String   whereArg = mapperIds;
        String   where = "mapper_id IN ("+whereArg+")";
        String   whereMapper = "id IN ("+whereArg+")";

        Log.d("delteData", "delete from " + table + " where mapper_id IN (" + whereArg + ")");

        // debut transaction for delete child of Entity

        try{
            db.beginTransaction();

            //delelte genre, actor, summary, mapper, videofile MAPPER_ID ?
            int resultG = db.delete(TABLE_GENRE, where,null);
            Log.d("delteData", "delete from " + TABLE_GENRE + " where mapper_id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultG + " row affected");
            int resultA = db.delete(TABLE_ACTOR, where, null);
            Log.d("delteData", "delete from " + TABLE_ACTOR + " where mapper_id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultA + " row affected");
            int resultS = db.delete(TABLE_SUMMARY, where, null);
            Log.d("delteData", "delete from " + TABLE_SUMMARY + " where mapper_id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultS + " row affected");
            int resultV = db.delete(TABLE_VIDEO_FILE, where, null);
            Log.d("delteData", "delete from " + TABLE_VIDEO_FILE + " where mapper_id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultV + " row affected");
            int resultE = db.delete(table, where, null);
            Log.d("delteData", "delete from " + table + " where mapper_id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultE + " row affected");
            int resultMap = db.delete(TABLE_MAPPER, whereMapper, null);
            Log.d("delteData", "delete from " + TABLE_MAPPER + " where id IN (" + whereArg + ")");
            Log.d("delteData", "result:" + resultMap+" row affected");

            db.setTransactionSuccessful();

        }catch (SQLiteException e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }


    }

    private int getMapperIdByEntityId(int id, String table) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {API.TAG_MAPPER_ID};
        String where = "id=?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor result = db.query(table, columns, where, whereArgs, null, null, null);

        int mapper_id = 0;

        result.moveToFirst();
        while (!result.isAfterLast()){
            mapper_id = result.getInt(0);
            result.moveToNext();
        }

        result.close();

        return mapper_id;
    }

    public ArrayList<Genre> getGenreByMapper(Mapper mapper) {
        ArrayList<Genre> lstGenre = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {API.TAG_ID,API.TAG_MAPPER_ID,API.TAG_GENRE,API.TAG_CREATE_DATE,
                            API.TAG_MODIFY_DATE};
        String where = API.TAG_MAPPER_ID+"=?";
        String[] whereArgs = {Integer.toString(mapper.getId())};

        Cursor result = db.query(TABLE_GENRE, columns,where,whereArgs,null,null,null);

        Genre genre;
        result.moveToFirst();
        while (!result.isAfterLast()) {
            genre = hydrateGenre(result);
            genre.setMapper(mapper);

            lstGenre.add(genre);

            result.moveToNext();
        }
        result.close();

        return lstGenre;
    }

    private Genre hydrateGenre(Cursor result) {
        Genre genre = new Genre();

        genre.setId(result.getInt(0));
        genre.setGenre(result.getString(2));
        genre.setCreateDate(StringConversion.stringToDate(result.getString(3)));
        genre.setModifyDate(StringConversion.stringToDate(result.getString(4)));

        return genre;
    }

    /**
     * require itemType parameter
     * @return
     */
    private String queryViewed()
    {
        String query = "SELECT "+TABLE_WATCH+"."+API.TAG_MAPPER_ID+" FROM "+TABLE_WATCH;


        // TODO adapt query to tvshow item : join tvZod table
        query += " INNER JOIN " + TABLE_MAPPER + " ON " +
                TABLE_MAPPER + "." + API.TAG_ID + "=" + TABLE_WATCH + "." + API.TAG_MAPPER_ID+
                " AND "+TABLE_MAPPER+"."+API.TAG_MAPPER_TYPE+" = ?";

        query+= " LEFT JOIN "+TABLE_VIDEO_FILE+ " ON "+
                TABLE_VIDEO_FILE+"."+API.TAG_MAPPER_ID+"="+TABLE_WATCH+"."+API.TAG_MAPPER_ID+
                " WHERE "+TABLE_WATCH+"."+API.TAG_POSITION+">="+TABLE_VIDEO_FILE+"."+API.TAG_DURATION;

        return query;
    }

    private String queryAudio(CheckBox channel5_1, CheckBox codec_DTS)
    {
        String strAudio="";
        if (this.where){
            strAudio += " AND ";
        }else {
            strAudio += " WHERE";
        }

        if (codec_DTS.isChecked()){
            strAudio += " v."+API.TAG_AUDIO_CODEC+"='dts'";
        }

        if (channel5_1.isChecked()){
            strAudio += " v."+API.TAG_CHANNEL+" >= 5";
        }

        return  strAudio;
    }

    private Object[] buildSearchQuery(String typeItem, CheckBox cbSearchNotWatch, CheckBox cbSearchWatch,
                                    CheckBox cbSearchResHD1080, CheckBox cbSearchResHD720,
                                    CheckBox cbSearchResSD, CheckBox cbSearchAudio5_1,
                                    CheckBox cbSearchAudioDTS, EditText editTextKeyword)
    {
        Object[] result = new Object[2];
        String query = null;
        String[] args = null;

        try {
            String table  = this.getTagToTable(typeItem);

            if (editTextKeyword.getText().length() > 2)
            {
                String keyword = editTextKeyword.getText().toString();
                table = "("+
                        " SELECT m1.* FROM "+TABLE_MOVIE+" as m1"+
                        " WHERE m1."+API.TAG_TITLE+" LIKE '%"+keyword+"%'"+
                        " UNION "+
                        " SELECT m2.* FROM "+TABLE_MOVIE+" as m2"+
                        " INNER JOIN "+TABLE_ACTOR+" as a ON a."+API.TAG_MAPPER_ID+"=m2."+API.TAG_MAPPER_ID+
                        "   AND a."+API.TAG_ACTOR+" LIKE '%"+keyword+"%'"+
                        " UNION "+
                        " SELECT m3.* FROM "+TABLE_MOVIE+" as m3"+
                        " INNER JOIN "+TABLE_SUMMARY+" as s ON s."+API.TAG_MAPPER_ID+"=m3."+API.TAG_MAPPER_ID+
                        "  AND s."+API.TAG_SUMMARY+" LIKE '%"+keyword+"%'"+
                        ")"
                ;
            }


            query = "SELECT i.* FROM "+TABLE_VIDEO_FILE+" as v";

            //add type into query
            query += " INNER JOIN "+table+" as i ON i."+API.TAG_MAPPER_ID+" = v."+API.TAG_MAPPER_ID;

            String strWhere="";

            // add resolution into query
            if (cbSearchNotWatch.isChecked()){
                if (this.where){
                    strWhere += " AND ";
                }else {
                    strWhere = " WHERE ";
                    this.where = true;
                }
                strWhere += "i."+API.TAG_MAPPER_ID+ " NOT IN ("+this.queryViewed() +")";
                args = new String[]{typeItem};

            }else if (cbSearchWatch.isChecked())
            {
                if (this.where){
                    strWhere += " AND ";
                }else {
                    strWhere = " WHERE ";
                }
                strWhere = "i."+API.TAG_MAPPER_ID+ "IN ("+this.queryViewed() +")";
                args = new String[]{typeItem};
            }
            query += strWhere;

            // Audio Criteria
            if (cbSearchAudio5_1.isChecked() || cbSearchAudioDTS.isChecked()) {
                query += this.queryAudio(cbSearchAudio5_1, cbSearchAudioDTS);
            }

            // Order Criteria
            query += " ORDER BY "+API.TAG_CREATE_DATE+" DESC";

            Log.d(null, "query => "+query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        result[0] = query;
        result[1] = args;


        return result;
    }

    public ArrayList<Item> searchedItems(CheckBox itemMovie, CheckBox itemTv, CheckBox cbSearchNotWatch,
                                         CheckBox cbSearchWatch, CheckBox cbSearchResHD1080,
                                         CheckBox cbSearchResHD720, CheckBox cbSearchResSD,
                                         CheckBox cbSearchAudio5_1, CheckBox cbSearchAudioDTS,
                                         EditText editTextKeyword) {

        Object[] resultQuery = this.buildSearchQuery(API.TAG_MOVIE, cbSearchNotWatch, cbSearchWatch,
                cbSearchResHD1080, cbSearchResHD720, cbSearchResSD, cbSearchAudio5_1,
                cbSearchAudioDTS, editTextKeyword);

        ArrayList<Item> lstItem = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor result = db.rawQuery((String)resultQuery[0],(String[])resultQuery[1]);
            result.moveToFirst();


            while (!result.isAfterLast())
            {
                Item item;
                if(itemMovie.isChecked()){
                    item = this.hydrateMovie(result);
                }else {
                    item = this.hydrateTvShow(result);
                }

                lstItem.add(item);

                result.moveToNext();
            }
        }catch (SQLiteException e){

            e.printStackTrace();
            Toast.makeText(context, "Erreur SQL !",Toast.LENGTH_LONG).show();
        }finally {
            this.where = false;
        }

        return lstItem;
    }
}
