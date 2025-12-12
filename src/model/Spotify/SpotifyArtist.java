package model.Spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyArtist {
    private final String artistId;
    private final String name;
    private final String image;
    private final List<String> genres;

    public SpotifyArtist(String id, String n, String img) {
        this.artistId = id;
        this.name = n;
        this.image = img;
        this.genres = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public String toString() {
        StringBuilder genreString = new StringBuilder();
        for(String genre : this.genres) {
            genreString.append(genre).append(", ");
        }

        return "Artist: " + this.name + ", Genres: " + genreString;
    }
}
