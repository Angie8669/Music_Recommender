package model;

import org.json.JSONArray;
import org.json.JSONObject;
import service.APIService;

import java.util.ArrayList;
import java.util.List;

public class MusicRecommenderModel {
    private APIService api = APIService.getInstance();

    public MusicRecommenderModel() {

    }

    public List<SpotifyTrack> searchForTracks(String searchQuery) {
        JSONObject response = api.searchForSongs(searchQuery);

        List<SpotifyTrack> tracks = new ArrayList<>();

        JSONArray items = response.getJSONObject("tracks").getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String trackId = item.getString("id");
            String name = item.getString("name");
            String artistName = item.getJSONArray("artists").getJSONObject(0).getString("name");
            String albumName = item.getJSONObject("album").getString("name");

            tracks.add(new SpotifyTrack(trackId, name, artistName, albumName));
        }

        return tracks;
    }
}
