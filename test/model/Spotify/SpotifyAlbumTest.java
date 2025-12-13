package model.Spotify;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyAlbumTest {
    SpotifyArtist artist1 = new SpotifyArtist("74KM79TiuVKeVCqs8QtB0B","Sabrina Carpenter", "https://i.scdn.co/image/ab6761610000e5eb78e45cfa4697ce3c437cb455");
    SpotifyArtist artist2 = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");

    SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "https://i.scdn.co/image/ab67616d0000b273fd8d7a8d96871e791cb1f626");

    SpotifyTrack track1 = new SpotifyTrack("7zFio8WT0tTBqLs1pXYKqy", "We Hug Now", album);
    SpotifyTrack track2 = new SpotifyTrack("5quMTd5zeI9yW5UDua8wS4", "Espresso", album);

    @Test
    void getImage() {
        assert album.getImage().equals("https://i.scdn.co/image/ab67616d0000b273fd8d7a8d96871e791cb1f626");
    }

    @Test
    void testGetAndAddArtists() {
        album.addArtist(artist1);
        album.addArtist(artist2);

        assert album.getArtists().size() == 2;
        assert album.getArtists().contains(artist1);
        assert album.getArtists().contains(artist2);
    }

    @Test
    void testGetAndAddTracks() {
        album.addTrack(track1);
        album.addTrack(track2);

        assert album.getTracks().size() == 2;
        assert album.getTracks().contains(track1);
        assert album.getTracks().contains(track2);
    }

    @Test
    void testGetAndAddGenres() {
        album.addGenre("pop");
        album.addGenre("emo");
        album.addGenre("rock");

        assert album.getGenres().size() == 3;
        assert album.getGenres().contains("pop");
        assert album.getGenres().contains("emo");
        assert album.getGenres().contains("rock");
    }

    @Test
    void testToString() {
        // Add genre and artist to verify behavior
        album.addArtist(artist1);
        album.addGenre("pop");

        assert album.toString().equals("Album: Short 'n Sweet, Artists: Sabrina Carpenter, Genres: pop, ");
    }
}