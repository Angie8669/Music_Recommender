package service;

import Utils.APIUtils;
import model.Spotify.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class APIService {
    private static APIService api;
    private final String authUrl = "https://accounts.spotify.com/";
    private final String webUrl = "https://api.spotify.com/";
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

    private JSONObject callAPI(String method, String baseUrl, String path, String params) {
        // Inspired by https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java
        HttpURLConnection connection = null;

        try {
            //Create connection
            connection = RequestFactory.createConnection(method, baseUrl, path, params, this.accessToken);

            if (method.equals("POST")) {
                //Add params to request body and send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(params);
                wr.close();
            }

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
            e.printStackTrace(System.out);
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

        String params = "grant_type=client_credentials&client_id=" + prop.getProperty("client_id") + "&client_secret=" + prop.getProperty("client_secret");
        JSONObject response = callAPI("POST", this.authUrl, "api/token/", params);

        this.accessToken = response.getString("access_token");

        return true;
    }

    public List<SpotifyObject> searchForArtists(String searchString, int limit) {
        String urlParameters = APIUtils.encodeParams("?q=" + searchString + "&type=artist&limit=" + limit);

        JSONObject response = callAPI("GET", this.webUrl, "v1/search", urlParameters);

        if (response != null) {
            JSONArray items = response.getJSONObject("artists").getJSONArray("items");
            List<SpotifyObject> artists = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                // Get the Artist object
                SpotifyData spotifyData = SpotifyData.getInstance();
                SpotifyArtist artist = spotifyData.getArtist(item.getString("id"));
                artists.add(artist);
            }

            return artists;
        }

        // No results found or error occurred, return empty list.
        return new ArrayList<>();
    }

    public List<SpotifyObject> searchForAlbums(String searchString, int limit) {
        String urlParameters = APIUtils.encodeParams("?q=" + searchString + "&type=album&limit=" + limit);

        JSONObject response = callAPI("GET", this.webUrl, "v1/search", urlParameters);

        if (response != null) {
            JSONArray items = response.getJSONObject("albums").getJSONArray("items");
            List<SpotifyObject> albums = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                // Get Artist objects first to make sure Artist is created.
                SpotifyData spotifyData = SpotifyData.getInstance();
                JSONArray artists = item.getJSONArray("artists");
                for (int j = 0; j < artists.length(); j++) {
                    spotifyData.getArtist(artists.getJSONObject(j).getString("id"));
                }

                // Next, get the Album object. This will also create up to 20 tracks.
                SpotifyAlbum album = spotifyData.getAlbum(item.getString("id"));

                albums.add(album);
            }

            return albums;
        }

        // No results found or error occurred, return empty list.
        return new ArrayList<>();
    }

    public List<SpotifyObject> searchForSongs(String searchString, int limit) {
        String urlParameters = APIUtils.encodeParams("?q=" + searchString + "&type=track&limit=" + limit);

        JSONObject response = callAPI("GET", this.webUrl, "v1/search", urlParameters);

        if (response != null) {
            JSONArray items = response.getJSONObject("tracks").getJSONArray("items");
            List<SpotifyObject> tracks = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                // Get Artist objects first to make sure Artist is created.
                SpotifyData spotifyData = SpotifyData.getInstance();
                JSONArray artists = item.getJSONArray("artists");
                for (int j = 0; j < artists.length(); j++) {
                    spotifyData.getArtist(artists.getJSONObject(j).getString("id"));
                }

                // Next, get the Album object. This will also create up to 20 tracks, likely including the current track.
                spotifyData.getAlbum(item.getJSONObject("album").getString("id"));

                // With that, we should have the track created, so let's get it. If it doesn't exist, it'll be created.
                SpotifyTrack track = spotifyData.getTrack(item.getString("id"));
                tracks.add(track);
            }

            return tracks;
        }

        // No results found or error occurred, return empty list.
        return new ArrayList<>();
    }

    public JSONObject getArtist(String id) {
        return callAPI("GET", this.webUrl, "v1/artists/" + id, "");
    }

    public JSONObject getAlbum(String id) {
        return callAPI("GET", this.webUrl, "v1/albums/" + id, "");
    }

    public JSONObject getTrack(String id) {
        return callAPI("GET", this.webUrl, "v1/tracks/" + id, "");
    }
}
