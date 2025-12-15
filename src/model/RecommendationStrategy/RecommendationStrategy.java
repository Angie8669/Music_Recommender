package model.RecommendationStrategy;

import model.Spotify.SpotifyObject;
import model.Spotify.SpotifyTrack;

import java.util.List;

public abstract class RecommendationStrategy {
    public abstract List<SpotifyObject> getRecommendations(List<String> seeds);
}
