package com.dada.videstation.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dada on 26/10/2015.
 */

public class API
{

    private final DatabaseMedia dbMedia;
    private ProgressDialog pDialog;
    private Context context;

    // JSON Node names
    public static final String TAG_MOVIE = "movie";
    public static final String TAG_TV_SHOW = "tvshow";
    public static final String TAG_TV_ZOD = "tvZod";
    public static final String TAG_ID = "id";
    public static final String TAG_MAPPER = "mapper";
    public static final String TAG_MAPPER_TYPE = "type";
    public static final String TAG_TITLE = "title";
    public static final String TAG_TAG_LINE = "tag_line";
    public static final String TAG_SORT_TITLE = "sort_title";
    public static final String TAG_YEAR = "year";
    public static final String TAG_RELEASE_DATE = "originally_available";
    public static final String TAG_CREATE_DATE = "create_date";
    public static final String TAG_MODIFY_DATE = "modify_date";
    public static final String TAG_SEASON = "season";
    public static final String TAG_EPISODE = "episode";
    public static final String TAG_SUMMARY = "summary";
    public static final String TAG_ACTOR = "actor";
    public static final String TAG_MAPPER_ID = "mapper_id";
    public static final String TAG_VIDEO_FILE = "video_file";
    public static final String TAG_FILE_SIZE = "filesize";
    public static final String TAG_PATH = "path";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_CONTAINER_TYPE = "container_type";
    public static final String TAG_VIDEO_CODEC = "video_codec";
    public static final String TAG_RESOLUTIONY = "resolutiony";
    public static final String TAG_RESOLUTIONX = "resolutionx";
    public static final String TAG_VIDEO_BITERATE = "video_bitrate";
    public static final String TAG_AUDIO_CODEC = "audio_codec";
    public static final String TAG_AUDIO_BITRATE = "audio_bitrate";
    public static final String TAG_CHANNEL = "channel";
    public static final String TAG_RATING = "certificate";
    public static final String TAG_GENRE = "gnere" ;
    public static final String TAG_POSITION = "position";
    public static final String TAG_UID = "uid";
    public static final String TAG_VIDEO_FILE_ID = "video_file_id";
    public static final String TAG_WATCH = "watch_status";
    public static final String TAG_POSTER = "poster";
    public static final String TAG_LO_ID = "lo_oid";
    public static final String TAG_MD5 = "md5";


    //TODO create table for listing all field of tables DB
    private Map<String,String[][]> fieldsTables;


    // movies JSONArray
    JSONArray  data   = null;
    JSONObject entity = null;

    public API(DatabaseMedia databaseMedia, Context context) {
        this.dbMedia = databaseMedia;
        this.context = context;

        this.fieldsTables = new HashMap<>();

        String[] id = {TAG_ID, "int"};
        String[] mapper = {TAG_MAPPER, "object"};
        String[] type = {TAG_MAPPER_TYPE, "string"};
        String[] title = {TAG_TITLE, "string"};
        String[] sortTitle = {TAG_SORT_TITLE, "string"};
        String[] tagLine = {TAG_TAG_LINE, "string"};
        String[] year = {TAG_YEAR, "int"};
        String[] releaseDate = {TAG_RELEASE_DATE, "string"};
        String[] createDate = {TAG_CREATE_DATE, "string"};
        String[] modifyDate = {TAG_MODIFY_DATE, "string"};
        String[] season = {TAG_SEASON, "int"};
        String[] episode = {TAG_EPISODE, "int"};
        String[] tvShow = {TAG_TV_SHOW, "object"};
        String[] summary = {TAG_SUMMARY, "string"};
        String[] actor = {TAG_ACTOR, "string"};
        String[] fileSize = {TAG_FILE_SIZE, "int"};
        String[] path = {TAG_PATH, "string"};
        String[] duration = {TAG_DURATION, "int"};
        String[] containerType = {TAG_CONTAINER_TYPE, "string"};
        String[] videoCodec = {TAG_VIDEO_CODEC, "string"};
        String[] videoBirate = {TAG_VIDEO_BITERATE, "int"};
        String[] resolutionX = {TAG_RESOLUTIONX, "int"};
        String[] resolutionY = {TAG_RESOLUTIONY, "int"};
        String[] audioCodec = {TAG_AUDIO_CODEC, "string"};
        String[] audioBitrate = {TAG_AUDIO_BITRATE, "int"};
        String[] channel = {TAG_CHANNEL, "int"};
        String[] rating = {TAG_RATING, "string"};
        String[] genre = {TAG_GENRE, "string"};
        String[] uid = {TAG_UID, "int"};
        String[] videoFile = {TAG_VIDEO_FILE, "object"};
        String[] position = {TAG_POSITION, "int"};
        String[] loId = {TAG_LO_ID, "int"};
        String[] md5 = {TAG_MD5, "string"};



        String[][] movieFields = {id, mapper, title, tagLine,
                    year, releaseDate, createDate, modifyDate, rating};
        fieldsTables.put(TAG_MOVIE, movieFields);

        String[][] tvShowFields = {id, mapper, title, year,
                    releaseDate, createDate, modifyDate};
        fieldsTables.put(TAG_TV_SHOW, tvShowFields);

        String[][] tvZodFields = {id, mapper, tvShow, tagLine, season,
                    episode, year, releaseDate, createDate, modifyDate, rating};
        fieldsTables.put(TAG_TV_ZOD, tvZodFields);

        String[][] summaryFields = {id , mapper, summary, createDate, modifyDate};
        fieldsTables.put(TAG_SUMMARY, summaryFields);

        String[][] actorFields = {id , mapper, actor, createDate, modifyDate};
        fieldsTables.put(TAG_ACTOR, actorFields);

        String[][] mapperFields = {id, type};
        fieldsTables.put(TAG_MAPPER,mapperFields);

        String[][] videoFileFields = {id, mapper, path, fileSize, duration, containerType, videoCodec,
                                    videoBirate, resolutionX, resolutionY, audioCodec, audioBitrate,
                                    channel, createDate, modifyDate};
        fieldsTables.put(TAG_VIDEO_FILE,videoFileFields);

        String[][] genreFields = {id, mapper, genre, createDate, modifyDate};
        fieldsTables.put(TAG_GENRE, genreFields);


        String[][] watchFields = {id,uid , videoFile, mapper, position, createDate, modifyDate};
        fieldsTables.put(TAG_WATCH, watchFields);

        String[][] posterFields = {id, mapper, loId, md5, createDate, modifyDate};
        fieldsTables.put(TAG_POSTER, posterFields);

    }

    public void updateMovie(){
        new GetData(TAG_MOVIE).execute();
    }

    public void updateTvShow(){
        new GetData(TAG_TV_SHOW).execute();
        new GetData(TAG_TV_ZOD).execute();
    }

    public void updateSummary(){
        new GetData(TAG_SUMMARY).execute();
    }

    public void updateActor() {
        new GetData(TAG_ACTOR).execute();
    }

    public void updateMapper(){
        new GetData(TAG_MAPPER).execute();
    }

    public void updateVideoFile(){
        new GetData(TAG_VIDEO_FILE).execute();
    }

    public void updateGenre() {
        new GetData(TAG_GENRE).execute();
    }

    public void updateWatch() {
        new GetData(TAG_WATCH).execute();
    }

    public void updatePoster() { new GetData(TAG_POSTER).execute(); }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetData extends AsyncTask<Void, Void, Integer> {

        private String type;

        public GetData(String type) {

            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*// Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler(context);

            String table = null;
            try {
                table = dbMedia.getTagToTable(this.type);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            String lastUpdate = dbMedia.getLastUpdateDateOrId(table);

            // Making a request to url and getting response
            String urlQuery = "/update/check.json?entities[]="+this.type;

            if (lastUpdate != null){
                urlQuery+= "&lastUpdate="+lastUpdate;
            }

            Log.d("Query: ", "> " + urlQuery);

            String jsonStr = sh.makeServiceCall(urlQuery, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) try {
                JSONObject jsonObj = new JSONObject(jsonStr);


                entity = jsonObj.getJSONObject(this.type);

                if (entity.getInt("nbUpDate")>0){
                    // Getting JSON Array node
                    data = entity.getJSONArray("data");

                    // TODO add progres bar
                    String[][] fieldsTable = fieldsTables.get(this.type);
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        ContentValues values = new ContentValues();

                        try{

                            JSONObject d = data.getJSONObject(i);

                            for (String[] field: fieldsTable){
                                try {
                                    String type = field[1];
                                    String name = field[0];

                                    String value;
                                    int valueInt;

                                    switch (type){
                                        case "string":
                                            value = d.getString(name);
                                            values.put(name, value);
                                            break;
                                        case "int":
                                            valueInt = d.getInt(name);
                                            values.put(name, valueInt);
                                            break;
                                        case "object":
                                            JSONObject object = d.getJSONObject(name);
                                            valueInt = object.getInt("id");
                                            values.put(name+"_id", valueInt);
                                    }


                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }

                            // Add into DataBase
                            dbMedia.updateData(dbMedia.getWritableDatabase(),table,  values);
                            // progressHandler.sendMessage(progressHandler.obtainMessage());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }

                return entity.getInt("nbTotal");



            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /*if (pDialog.isShowing())
                pDialog.dismiss();
                */
            /**
             * Updating parsed JSON data into ListView
             * */

            String table = null;
            try {
                table = dbMedia.getTagToTable(this.type);

                int nbRows = dbMedia.getCountAllByTable(table);

                if(result != null && result != nbRows){
                    ArrayList<Integer> ids = dbMedia.getAllIdByTable(table);

                    data = new JSONArray(ids);

                    ContentValues params = new ContentValues();
                    params.put("ids", data.toString());

                    new PutData(this.type,params).execute();

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private class PutData extends AsyncTask<Void, Void, Integer> {

        private ContentValues params;
        private String type;

        public PutData(String type, ContentValues params) {

            this.type = type;
            this.params = params;
        }

        @Override
        protected void onPreExecute() {

           super.onPreExecute();
           // TODO use WeakReference to acess Activity
            /*
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler(context);

            String table = null;
            try {
                table = dbMedia.getTagToTable(this.type);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            // Making a request to url and getting response
            String urlQuery = "/update/maj.json?entities[]="+this.type;

            Log.d("Query: ", "> " + urlQuery);

            String jsonStr = sh.makeServiceCall(urlQuery, ServiceHandler.POST, this.params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) try {
                JSONObject jsonObj = new JSONObject(jsonStr);


                entity = jsonObj.getJSONObject(this.type);

                String[][] fieldsTable = fieldsTables.get(this.type);

                if(entity.getInt("nbMissing")> 0) {
                    data = entity.getJSONObject("data").getJSONArray("missing");

                    for (int i = 0; i < data.length(); i++) {
                        ContentValues values = new ContentValues();

                        try {

                            JSONObject d = data.getJSONObject(i);

                            for (String[] field : fieldsTable) {
                                try {
                                    String type = field[1];
                                    String name = field[0];

                                    String value;
                                    int valueInt;

                                    switch (type) {
                                        case "string":
                                            value = d.getString(name);
                                            values.put(name, value);
                                            break;
                                        case "int":
                                            valueInt = d.getInt(name);
                                            values.put(name, valueInt);
                                            break;
                                        case "object":
                                            JSONObject object = d.getJSONObject(name);
                                            valueInt = object.getInt("id");
                                            values.put(name + "_id", valueInt);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // Add into DataBase
                            dbMedia.updateData(dbMedia.getWritableDatabase(), table, values);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (entity.getInt("nbDelete") > 0){
                    data = entity.getJSONObject("data").getJSONArray("delete");
                    dbMedia.deleteData(table,data);

                }
            }catch (JSONException e){
                e.printStackTrace();
            }



                /*if (entity.getInt("nbUpdate")>0){
                    // Getting JSON Array node
                    data = entity.getJSONArray("data");

                    // TODO add progres bar
                    String[][] fieldsTable = fieldsTables.get(this.type);
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        ContentValues values = new ContentValues();

                        try{

                            JSONObject d = data.getJSONObject(i);

                            for (String[] field: fieldsTable){
                                try {
                                    String type = field[1];
                                    String name = field[0];

                                    String value;
                                    int valueInt;

                                    switch (type){
                                        case "string":
                                            value = d.getString(name);
                                            values.put(name, value);
                                            break;
                                        case "int":
                                            valueInt = d.getInt(name);
                                            values.put(name, valueInt);
                                            break;
                                        case "object":
                                            JSONObject object = d.getJSONObject(name);
                                            valueInt = object.getInt("id");
                                            values.put(name+"_id", valueInt);
                                    }


                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }

                            // Add into DataBase
                            dbMedia.updateData(dbMedia.getWritableDatabase(),table,  values);
                            // progressHandler.sendMessage(progressHandler.obtainMessage());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }

                int nbRows = dbMedia.getCountAllByTable(table);

                if(nbRows != entity.getInt("nbAll")){
                    ArrayList<Integer> ids = dbMedia.getAllIdByTable(table);

                    data = new JSONArray(ids);


                }
*/


            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
           /* if (pDialog.isShowing())
                pDialog.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }




}
