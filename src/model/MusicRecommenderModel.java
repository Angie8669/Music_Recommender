package model;

import model.RecommendationStrategy.ArtistRecommendationStrategy;
import model.RecommendationStrategy.GenreRecommendationStrategy;
import model.RecommendationStrategy.RecommendationStrategy;
import model.Spotify.*;
import service.APIService;

import java.util.ArrayList;
import java.util.List;

public class MusicRecommenderModel {
    private APIService api = APIService.getInstance();
    private List<SpotifyObject> seedObjects;

    public MusicRecommenderModel() {
        this.seedObjects = new ArrayList<>();
    }

    public List<SpotifyArtist> searchForArtists(String searchQuery) {
        return api.searchForArtists(searchQuery, 3);
    }

    public List<SpotifyAlbum> searchForAlbums(String searchQuery) {
        return api.searchForAlbums(searchQuery, 3);
    }

    public List<SpotifyTrack> searchForTracks(String searchQuery) {
        return api.searchForSongs(searchQuery, 3);
    }

    public List<SpotifyTrack> getRecommendations() {
        // Need at least one seed to get recommendations
        if (this.seedObjects.isEmpty()) {
            return null;
        }

        // First, get all of the genres from tracks, artists, and albums in seeds
        List<String> seeds = new ArrayList<>();

        for (SpotifyObject obj : this.seedObjects) {
            if (obj instanceof SpotifyArtist artist) {
                seeds.addAll(artist.getGenres());
            } else if (obj instanceof SpotifyAlbum album) {
                seeds.addAll(album.getGenres());
            } else if (obj instanceof SpotifyTrack track) {
                // For tracks, get the genres from the artists/album
                for (SpotifyArtist artist : track.getArtists()) {
                    seeds.addAll(artist.getGenres());
                }

                seeds.addAll(track.getAlbum().getGenres());
            }
        }

        RecommendationStrategy strategy;

        // Pick strategy based on list of genres being empty or not.
        if (seeds.isEmpty()) {
            // Replace genres with artist IDs
            seeds.clear();
            for (SpotifyObject obj : this.seedObjects) {
                if (obj instanceof SpotifyArtist artist) {
                    seeds.addAll(artist.getGenres());
                } else if (obj instanceof SpotifyAlbum album) {
                    for (SpotifyArtist artist : album.getArtists()) {
                        seeds.add(artist.getName());
                    }
                } else if (obj instanceof SpotifyTrack track) {
                    for (SpotifyArtist artist : track.getArtists()) {
                        seeds.add(artist.getName());
                    }
                }
            }
            strategy = new ArtistRecommendationStrategy();
        } else {
            strategy = new GenreRecommendationStrategy();
        }

        // Make sure recommendations don't contain seeds
        List<SpotifyTrack> recs = strategy.getRecommendations(seeds);

        for (SpotifyObject obj : this.seedObjects) {
            if (obj instanceof SpotifyTrack track) {
                recs.remove(track);
            }
        }

        return recs;
    }

    public List<SpotifyObject> getSeedObjects() {
        return seedObjects;
    }

    public void addSeed(SpotifyObject obj) {
        this.seedObjects.add(obj);
    }

    public void clearSeeds() {
        this.seedObjects.clear();
    }
}
