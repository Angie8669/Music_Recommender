package model.Spotify;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyArtistTest {
    // Test artist
    SpotifyArtist artist = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");

    @Test
    void getImage() {
        String image = artist.getImage();
        assert image.equals("https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");
    }

    @Test
    void testGetandAddGenres() {
        artist.addGenre("pop");
        artist.addGenre("emo");
        artist.addGenre("rock");

        assert artist.getGenres().size() == 3;
        assert artist.getGenres().contains("pop");
        assert artist.getGenres().contains("emo");
        assert artist.getGenres().contains("rock");
    }

    @Test
    void testToString() {
        // Add a genre to test genres output
        artist.addGenre("pop");

        assert artist.toString().equals("Artist: Sydney Rose, Genres: pop, ");
    }
}