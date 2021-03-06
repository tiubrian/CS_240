package cs240.benjamin.familymap.client;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import android.util.Log;

/**
 * Created by benjamin on 3/14/16.
 */
public class HTTPClient {
    public static final String tag = "HTTPClient";
    private static String serverIP = "";
    private static String serverPort = "";

    public static void setIP(String IP) {serverIP = IP;}
    public static void setPort(String port) {serverPort = port;}

    public static JSONObject doAuthAction(String action, String auth_token)
    {
        Log.e(tag, "called with action "+action+" token "+auth_token);
        URL url;
        JSONObject result;
        try {
            url = new URL("http://" + serverIP + ":" + serverPort + "/" + action);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", auth_token);
            Log.e(tag, "connecting");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.e(tag, "got good response code");
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                return new JSONObject(responseBodyData);
            }
            else
            {
                Log.e(tag, "got bad response code");
            }
        }

        catch (Exception e) { Log.e(tag, "exception in auth action msg" + e.getMessage()+" tostring "+e.toString());}

        return null;
    }

    public static JSONObject doPostAction(String action, JSONObject payload)
    {
        Log.e(tag, "called with action "+action+" payload "+payload.toString());
        URL url;
        try {
            url = new URL("http://" + serverIP + ":" + serverPort + "/" + action);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            OutputStream reqBody = connection.getOutputStream();
            reqBody.write(payload.toString().getBytes());
            Log.e(tag, "connecting");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.e(tag, "got good response code");
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                return new JSONObject(responseBodyData);
            }
            else
            {
                Log.e(tag, "got bad response code");
            }
        }
        catch (Exception e) { Log.e(tag, "exception in post action "+ e.getMessage());}

        return null;

    }
}
