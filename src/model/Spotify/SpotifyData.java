package model.Spotify;

import org.json.JSONArray;
import org.json.JSONObject;
import service.APIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpotifyData {
    private static Map<String, SpotifyArtist> artists = new HashMap<>();
    private static Map<String, SpotifyAlbum> albums = new HashMap<>();
    private static Map<String, SpotifyTrack> tracks = new HashMap<>();

    private static Map<String, List<SpotifyArtist>> artistsByName = new HashMap<>();
    private static Map<String, List<SpotifyAlbum>> albumsByName = new HashMap<>();
    private static Map<String, List<SpotifyTrack>> tracksByName = new HashMap<>();

    private static APIService api = APIService.getInstance();

    // Get an artist by ID. If necessary, calls APIService to get the data.
    public static SpotifyArtist getArtist(String artistId) {
        // Only create a new artist if the current artistId has not yet been created.
        if (artists.get(artistId) == null) {
            JSONObject data = api.getArtist(artistId);

            System.out.println(data.toString(4));

            String name = data.getString("name");
            JSONArray images = data.getJSONArray("images");
            String image = "";
            if (!images.isEmpty()) {
                image = images.getJSONObject(0).getString("url");
            }

            SpotifyArtist artist = new SpotifyArtist(artistId, name, image);

            // Add genres
            JSONArray genresArray = data.getJSONArray("genres");
            for (int i = 0; i < genresArray.length(); i++) {
                artist.addGenre(genresArray.getString(i));
            }

            artists.put(artistId, artist);
            if (artistsByName.containsKey(name)) {
                // Add new track to existing List
                artistsByName.get(name).add(artist);
            } else {
                // Create new List if one does not exist
                List<SpotifyArtist> list = new ArrayList<>();
                list.add(artist);
                artistsByName.put(name, list);
            }

            return artist;
        }

        return artists.get(artistId);
    }

    // Get an album by id. If necessary, calls APIService to get the data.
    public static SpotifyAlbum getAlbum(String albumId) {
        // Only create a new album if the current albumId has not yet been created.
        if (albums.get(albumId) == null) {
            JSONObject data = api.getAlbum(albumId);
            String name = data.getString("name");
            String image = data.getJSONArray("images").getJSONObject(0).getString("url");

            SpotifyAlbum album = new SpotifyAlbum(albumId, name, image);

            // Add genres
            JSONArray genresArray = data.getJSONArray("genres");
            for (int i = 0; i < genresArray.length(); i++) {
                album.addGenre(genresArray.getString(i));
            }

            // Add the album to the map before getting tracks to prevent infinite loops.
            albums.put(albumId, album);
            if (albumsByName.containsKey(name)) {
                // Add new track to existing List
                albumsByName.get(name).add(album);
            } else {
                // Create new List if one does not exist
                List<SpotifyAlbum> list = new ArrayList<>();
                list.add(album);
                albumsByName.put(name, list);
            }

            // Get and add tracks
            JSONArray tracksArray = data.getJSONObject("tracks").getJSONArray("items");
            for (int i = 0; i < tracksArray.length(); i++) {
                album.addTrack(getTrack(tracksArray.getJSONObject(i).getString("id")));
            }



            return album;
        }

        return albums.get(albumId);
    }

    public static SpotifyTrack getTrack(String trackId) {
        // Only create a new track if the current trackId has not yet been created.
        if (tracks.get(trackId) == null) {
            JSONObject data = api.getTrack(trackId);
            String name = data.getString("name");

            String albumId = data.getJSONObject("album").getString("id");
            SpotifyAlbum album = getAlbum(albumId);

            SpotifyTrack track = new SpotifyTrack(trackId, name, album);

            JSONArray artistsArray = data.getJSONArray("artists");
            for (int i = 0; i < artistsArray.length(); i++) {
                String artistId = artistsArray.getJSONObject(i).getString("id");
                track.addArtist(getArtist(artistId));
            }

            tracks.put(trackId, track);
            if (tracksByName.containsKey(name)) {
                // Add new track to existing List
                tracksByName.get(name).add(track);
            } else {
                // Create new List if one does not exist
                List<SpotifyTrack> list = new ArrayList<>();
                list.add(track);
                tracksByName.put(name, list);
            }

            return track;
        }

        return tracks.get(trackId);
    }

    public static List<SpotifyArtist> getArtistByName(String name) {
        return artistsByName.get(name);
    }

    public static List<SpotifyAlbum> getAlbumByName(String name) {
        return albumsByName.get(name);
    }

    public static List<SpotifyTrack> getTrackByName(String name) {
        return tracksByName.get(name);
    }
}
