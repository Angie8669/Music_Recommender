package model.Spotify;

import org.junit.jupiter.api.Test;

class SpotifyTrackTest {
    SpotifyArtist artist1 = new SpotifyArtist("74KM79TiuVKeVCqs8QtB0B","Sabrina Carpenter", "https://i.scdn.co/image/ab6761610000e5eb78e45cfa4697ce3c437cb455");
    SpotifyArtist artist2 = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");

    SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "https://i.scdn.co/image/ab67616d0000b273fd8d7a8d96871e791cb1f626");

    SpotifyTrack track = new SpotifyTrack("7zFio8WT0tTBqLs1pXYKqy", "We Hug Now", album);

    @Test
    void testGetAndAddArtists() {
        track.addArtist(artist1);
        track.addArtist(artist2);

        assert track.getArtists().size() == 2;
        assert track.getArtists().contains(artist1);
        assert track.getArtists().contains(artist2);
    }

    @Test
    void getAlbum() {
        assert track.getAlbum().equals(album);
    }

    @Test
    void testToString() {
        // Add artist to verify behavior
        track.addArtist(artist2);

        assert track.toString().equals("Track: We Hug Now, Artists: Sydney Rose, Album: Short 'n Sweet");
    }
}