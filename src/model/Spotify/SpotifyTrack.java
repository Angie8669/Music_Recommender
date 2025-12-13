package model.Spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyTrack extends SpotifyObject {
    private final List<SpotifyArtist> artists;
    private final SpotifyAlbum album;

    public SpotifyTrack(String id, String n, SpotifyAlbum alb) {
        this.id = id;
        this.name = n;
        this.artists = new ArrayList<>();
        this.album = alb;
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
