package model.RecommendationStrategy;

import model.Spotify.SpotifyTrack;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import service.APIService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistRecommendationStrategyTest {

    @Test
    void getRecommendations() {
        APIService api = Mockito.mock(APIService.class);
        List<SpotifyTrack> tracks = Mockito.mock(List.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(tracks).when(api).searchForSongs(Mockito.anyString(), Mockito.anyInt());

            ArtistRecommendationStrategy strategy = new ArtistRecommendationStrategy();
            List<String> artists = new ArrayList<>();
            artists.add("Sabrina Carpenter");
            strategy.getRecommendations(artists);

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForSongs(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("artist:\"Sabrina Carpenter\"", searchStringCaptor.getValue());
            assertEquals(6, limitCaptor.getValue());
        }
    }
}