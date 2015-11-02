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
            strReq.add("CREATE TABLE "+TABLE_TV_ZOD+" ("+ API.TAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tvshow_id INT, mapper_id INT, "+API.TAG_TAG_LINE+" TEXT, "+API.TAG_SEASON+
                    " INT, "+API.TAG_EPISODE+" INT, "+API.TAG_YEAR+" INT, "+API.TAG_RELEASE_DATE+" DATETIME," +
                    API.TAG_CREATE_DATE+" DATETIME, "+API.TAG_MODIFY_DATE+" DATETIME)");

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

        String[] columns = {API.TAG_ID, API.TAG_TITLE, API.TAG_TAG_LINE, API.TAG_YEAR,
                        API.TAG_RELEASE_DATE, API.TAG_CREATE_DATE, API.TAG_MODIFY_DATE, "mapper_id"};

        Cursor result =  db.query(TABLE_MOVIE, columns, null, null, null, null, API.TAG_CREATE_DATE + " DESC");

        ArrayList<Movie> lstMovies = new ArrayList<>();

        result.moveToFirst();
        while(!result.isAfterLast()){

            Movie movie = new Movie();

            movie.setId(result.getInt(0));
            movie.setTitle(result.getString(1));
            movie.setTagLine(result.getString(2));
            movie.setYear(result.getInt(3));

            DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

            try {
               movie.setOriginallyAvailable(date.parse(result.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                movie.setCreateDate(date.parse(result.getString(5)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                movie.setModifyDate(date.parse(result.getString(6)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Mapper mapper = new Mapper();
            mapper.setId(result.getInt(7));
            mapper.setType(API.TAG_MOVIE);
            movie.setMapper(mapper);

            lstMovies.add(movie);

            result.moveToNext();
        }

        result.close();

        db.close();


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



        return lstActor;
    }
}
