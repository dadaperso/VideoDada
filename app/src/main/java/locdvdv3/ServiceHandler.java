package locdvdv3;

/**
 * Created by dada on 27/10/2015.
 */

import android.content.Context;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class ServiceHandler {

    static String response = "";
    public final static int GET = 1;
    public final static int POST = 2;

    public static final String API_URL = "http://pc-dada.home/api-locdvd/app_dev.php";
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
                                  List<NameValuePair> params) {
        URL httpConnection = null;
        URLConnection yc = null;

        try {

            httpConnection = new URL(API_URL+request);
            yc = httpConnection.openConnection();
            BufferedReader in;
            String inputLine;
            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            while ((inputLine = in.readLine()) != null)
                response +=inputLine;
            in.close();

            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
