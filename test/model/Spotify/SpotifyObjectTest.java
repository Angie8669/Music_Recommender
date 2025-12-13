package model.Spotify;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyObjectTest {
    SpotifyObject object = new SpotifyObject("74KM79TiuVKeVCqs8QtB0B","Sabrina Carpenter");

    SpotifyObject artist1 = new SpotifyArtist("74KM79TiuVKeVCqs8QtB0B","Sabrina Carpenter", "https://i.scdn.co/image/ab6761610000e5eb78e45cfa4697ce3c437cb455");
    SpotifyObject artist2 = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "https://i.scdn.co/image/ab6761610000e5eb58cf0937223e435e34e29afb");

    @Test
    void getId() {
        assert object.getId().equals("74KM79TiuVKeVCqs8QtB0B");
    }

    @Test
    void getName() {
        assert object.getName().equals("Sabrina Carpenter");
    }

    @Test
    void testEquals() {
        assert object.equals(artist1);
        assert !object.equals(artist2);
        assert !object.equals("74KM79TiuVKeVCqs8QtB0B");
    }

    @Test
    void testToString() {
        assert object.toString().equals("SpotifyObject: id: 74KM79TiuVKeVCqs8QtB0B, name: Sabrina Carpenter");
    }
}