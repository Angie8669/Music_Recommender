package model;

public class SpotifyTrack {
    private String trackId;
    private String name;
    private String artist;
    private String album;

    public SpotifyTrack(String id, String n, String art, String alb) {
        this.trackId = id;
        this.name = n;
        this.artist = art;
        this.album = alb;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String toString() {
        return "Track: " + this.name + ", Artist: " + this.artist + ", Album: " + this.album;
    }
}
