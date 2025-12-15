package model.Spotify;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyObjectTest {
    SpotifyObject artist1 = new SpotifyArtist("74KM79TiuVKeVCqs8QtB0B","Sabrina Carpenter", "https://i.scdn.co/image/ab6761610000e5eb78e45cfa4697ce3c437cb455");
    SpotifyObject artist2 = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");

    SpotifyObject track = new SpotifyTrack("74KM79TiuVKeVCqs8QtB0B","We Hug Now", null);

    @Test
    void getId() {
        assert track.getId().equals("74KM79TiuVKeVCqs8QtB0B");
    }

    @Test
    void getName() {
        assert track.getName().equals("We Hug Now");
    }

    @Test
    void testEquals() {
        assert track.equals(artist1);
        assert !track.equals(artist2);
        assert !track.equals("74KM79TiuVKeVCqs8QtB0B");
    }
}