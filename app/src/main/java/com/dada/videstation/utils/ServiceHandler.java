package com.dada.videstation.utils;

/**
 * Created by dada on 27/10/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServiceHandler {

    static String response = "";
    public final static int GET = 1;
    public final static int POST = 2;
    private static final String USER_AGENT = "Mozilla/5.0";

    public static final String API_URL = "http://pc-dada.home/api-locdvd";
    private Context context;

    public ServiceHandler(Context ctx) {
        this.context = ctx;
        response = "";
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String request, int method,
                                  ContentValues params) {

        try {

            URL obj = new URL(API_URL + request);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            Log.d("ServiceHandler", "URL : " + obj.toString());
            con.setRequestProperty("User-Agent", USER_AGENT);

            if (method == ServiceHandler.POST) {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                // For POST only - START
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();

                String urlParams = "{\"ids\":"+params.get("ids").toString()+"}";
                Log.d("ServiceHandler POST", urlParams);
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                // For POST only - END
            }else {
                con.setRequestMethod("GET");
            }

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
               return response.toString();
            } else {
                System.out.println("request not worked");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
