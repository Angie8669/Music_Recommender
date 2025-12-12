package service;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class APIService {
    private static APIService api;
    private final String authUrl = "https://accounts.spotify.com/api/token";
    private final String baseUrl = "https://api.spotify.com/v1/";
    private String accessToken = "";

    // Implement Singleton pattern to ensure only one instance of the APIService is created.
    // Keeping the constructor private ensures no one else can create another instance.
    private APIService() {
        if (!authenticate()) {
            System.out.println("Authentication failed :(");
        }
    }

    //Create a public facing static method that allows external callers to get the single instance of APIService
    public static  APIService getInstance() {
        if (api == null) {
            api = new APIService();
        }

        return api;
    }

    private JSONObject callPostAPI(String urlString, String urlParameters) {
        // Inspired by https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = URI.create(urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            // Return false to indicate authentication failed
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private JSONObject callGetAPI(String urlString) {
        // Inspired by https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = URI.create(urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization",
                    "Bearer " + this.accessToken);

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            // Return false to indicate authentication failed
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean authenticate() {
        // Get properties from file
        // Inspired by https://stackoverflow.com/questions/8285595/reading-properties-file-in-java
        Properties prop = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("config.properties");
            prop.load(stream);
        } catch(IOException e) {
            // Return false to indicate authentication failed
            e.printStackTrace();
            return false;
        }

        String urlParameters = "grant_type=client_credentials&client_id=" + prop.getProperty("client_id") + "&client_secret=" + prop.getProperty("client_secret");
        JSONObject response = callPostAPI(authUrl, urlParameters);

        this.accessToken = response.getString("access_token");

        return true;
    }

    public JSONObject searchForSongs(String searchString) {
        searchString = searchString.replace(" ", "+");
        String urlParameters = "q=" + searchString + "&type=track&limit=5";

        return callGetAPI(this.baseUrl + "search?" + urlParameters);
    }

    public JSONObject getArtist(String id) {
        return callGetAPI(this.baseUrl + "artists/" + id);
    }

    public JSONObject getAlbum(String id) {
        return callGetAPI(this.baseUrl + "albums/" + id);
    }

    public JSONObject getTrack(String id) {
        return callGetAPI(this.baseUrl + "tracks/" + id);
    }
}
