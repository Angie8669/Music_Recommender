package model.Spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyTrack {
    private String trackId;
    private String name;
    private List<SpotifyArtist> artists;
    private SpotifyAlbum album;

    public SpotifyTrack(String id, String n, SpotifyAlbum alb) {
        this.trackId = id;
        this.name = n;
        this.artists = new ArrayList<>();
        this.album = alb;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getName() {
        return name;
    }

    public List<SpotifyArtist> getArtists() {
        return artists;
    }

    public SpotifyAlbum getAlbum() {
        return album;
    }

    public void addArtist(SpotifyArtist artist) {
        this.artists.add(artist);
    }

    public String toString() {
        StringBuilder artistsString = new StringBuilder();
        for(SpotifyArtist artist : this.artists) {
            artistsString.append(artist.toString()).append(", ");
        }
        return "Track: " + this.name + ", Artist: " + artistsString + "Album: " + this.album;
    }
}
