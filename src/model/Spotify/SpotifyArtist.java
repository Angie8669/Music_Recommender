package model.Spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyArtist extends SpotifyObject {
    private final String image;
    private final List<String> genres;

    public SpotifyArtist(String id, String n, String img) {
        super(id, n);
        this.image = img;
        this.genres = new ArrayList<>();
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
