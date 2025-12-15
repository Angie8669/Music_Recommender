package model.Spotify;

public abstract class SpotifyObject {
    protected String id;
    protected String name;

    public SpotifyObject(String id, String n) {
        this.id = id;
        this.name = n;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpotifyObject spotifyObject) {
            return spotifyObject.getId().equals(this.getId());
        }
        return false;
    }
}
