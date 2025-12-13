package model.Spotify;

import java.util.Objects;

public class SpotifyObject {
    protected String id;
    protected String name;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpotifyObject) {
            SpotifyObject spotifyObject = (SpotifyObject) obj;
            return spotifyObject.getId().equals(this.getId());
        }
        return false;
    }

    public String toString() {
        if (this instanceof SpotifyArtist artist) {
            return "Spotifyartist: " + artist;
        } else if (this instanceof SpotifyAlbum album) {
            return "SpotifyAlbum: " + album;
        } else if (this instanceof SpotifyTrack track) {
            return "SpotifyTrack: " + track;
        }

        return "SpotifyObject: id: " + this.id + ", name: " + this.name;
    }
}
