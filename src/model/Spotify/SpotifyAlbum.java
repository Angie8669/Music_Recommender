package model.Spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyAlbum extends SpotifyObject {
    private final String image;
    private final List<SpotifyArtist> artists;
    private final List<SpotifyTrack> tracks;
    private final List<String> genres;

    public SpotifyAlbum(String id, String n, String img) {
        this.id = id;
        this.name = n;
        this.image = img;
        this.artists = new ArrayList<>();
        this.tracks = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public String getImage() {
        return image;
    }

    public List<SpotifyArtist> getArtists() {
        return artists;
    }

    public List<SpotifyTrack> getTracks() {
        return tracks;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void addArtist(SpotifyArtist artist) {
        this.artists.add(artist);
    }

    public void addTrack(SpotifyTrack track) {
        this.tracks.add(track);
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public String toString() {
        StringBuilder artistString = new StringBuilder();
        for(SpotifyArtist artist : this.artists) {
            artistString.append(artist.getName()).append(", ");
        }

        StringBuilder genreString = new StringBuilder();
        for(String genre : this.genres) {
            genreString.append(genre).append(", ");
        }

        return "Album: " + this.name + ", Artists: " + artistString + "Genres: " + genreString;
    }
}
