package model.RecommendationStrategy;

import model.Spotify.SpotifyObject;
import model.Spotify.SpotifyTrack;
import service.APIService;

import java.util.List;
import java.util.Random;

public class ArtistRecommendationStrategy extends RecommendationStrategy {
    public List<SpotifyObject> getRecommendations(List<String> seeds) {
        APIService api = APIService.getInstance();

        // Pick a random artist to search for recommendations
        Random random = new Random();
        int index = random.nextInt(seeds.size());
        String searchString = "artist:\"" + seeds.get(index) + "\"";

        return api.searchForSongs(searchString, 6);
    }
}
