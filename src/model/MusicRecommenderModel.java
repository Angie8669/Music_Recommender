package model;

import model.Spotify.SpotifyData;
import model.Spotify.SpotifyTrack;
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

        JSONArray items = response.getJSONObject("tracks").getJSONArray("items");
        List<SpotifyTrack> tracks = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            // Get Artist objects first to make sure Artist is created.
            JSONArray artists = item.getJSONArray("artists");
            for (int j = 0; j < artists.length(); j++) {
                SpotifyData.getArtist(artists.getJSONObject(j).getString("id"));
            }

            // Next, get the Album object. This will also create up to 20 tracks, likely including the current track.
            SpotifyData.getAlbum(item.getJSONObject("album").getString("id"));

            // With that, we should have the track created, so let's get it. If it doesn't exist, it'll be created.
            SpotifyTrack track = SpotifyData.getTrack(item.getString("id"));
            tracks.add(track);
        }

        return tracks;
    }
}
