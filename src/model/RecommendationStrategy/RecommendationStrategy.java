package model.RecommendationStrategy;

import model.Spotify.SpotifyTrack;

import java.util.List;

public abstract class RecommendationStrategy {
    public abstract List<SpotifyTrack> getRecommendations(List<String> seeds);
}
