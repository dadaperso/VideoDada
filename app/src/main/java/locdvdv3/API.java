package locdvdv3;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dada.res1.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static final String TAG_MAPPER_ID = "id";
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


    //TODO create table for listing all field of tables DB
    private Map<String,String[][]> fieldsTables;


    // movies JSONArray
    JSONArray data = null;

    public API(DatabaseMedia databaseMedia, Context context) {
        this.dbMedia = databaseMedia;
        this.context = context;

        this.fieldsTables = new HashMap<>();

        String[] id = {TAG_ID, "int"};
        String[] mapper = {TAG_MAPPER, "object"};
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


        String[][] movieFields = {id, mapper, title, tagLine,
                    year, releaseDate, createDate, modifyDate};
        fieldsTables.put(TAG_MOVIE, movieFields);

        String[][] tvShowFields = {id, mapper, title, year,
                    releaseDate, createDate, modifyDate};
        fieldsTables.put(TAG_TV_SHOW, tvShowFields);

        String[][] tvZodFields = {id, mapper, tvShow, tagLine, season,
                    episode, year, releaseDate, createDate, modifyDate};
        fieldsTables.put(TAG_TV_ZOD, tvZodFields);

        String[][] summaryFields = {id , mapper, summary, createDate, modifyDate};
        fieldsTables.put(TAG_SUMMARY, summaryFields);

        String[][] actorFields = {id , mapper, actor, createDate, modifyDate};
        fieldsTables.put(TAG_ACTOR, actorFields);


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

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetData extends AsyncTask<Void, Void, Void> {

        private String type;

        public GetData(String type) {

            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler(context);

            String table = dbMedia.getTagToTable(this.type);

            String lastUpdate = dbMedia.getLastUpdateDate(table);

            // Making a request to url and getting response
            String urlQuery = "/update/check.json?entities[]="+this.type;

            if (lastUpdate != null){
                urlQuery+= "&lastUpdate="+lastUpdate;
            }
            Log.d("Query: ", "> "+urlQuery);

            String jsonStr = sh.makeServiceCall(urlQuery, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                data = jsonObj.getJSONObject("update").getJSONArray(this.type);

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Intent intent = new Intent(context, Main2Activity.class);
            //Intent intent = new Intent(DataConstruction.this, LocDVD.class);
            context.startActivity(intent);
        }

    }




}
