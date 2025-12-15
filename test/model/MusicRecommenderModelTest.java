package model;

import model.RecommendationStrategy.ArtistRecommendationStrategy;
import model.Spotify.SpotifyAlbum;
import model.Spotify.SpotifyArtist;
import model.Spotify.SpotifyObject;
import model.Spotify.SpotifyTrack;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import service.APIService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MusicRecommenderModelTest {

    @Test
    void searchForArtists() {
        APIService api = Mockito.mock(APIService.class);
        List<SpotifyArtist> artists = Mockito.mock(List.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(artists).when(api).searchForArtists(Mockito.anyString(), Mockito.anyInt());

            MusicRecommenderModel model = new MusicRecommenderModel();

            model.searchForArtists("Sabrina Carpenter");

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForArtists(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("Sabrina Carpenter", searchStringCaptor.getValue());
            assertEquals(3, limitCaptor.getValue());
        }
    }

    @Test
    void searchForAlbums() {
        APIService api = Mockito.mock(APIService.class);
        List<SpotifyAlbum> albums = Mockito.mock(List.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(albums).when(api).searchForAlbums(Mockito.anyString(), Mockito.anyInt());

            MusicRecommenderModel model = new MusicRecommenderModel();

            model.searchForAlbums("Short n' Sweet");

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForAlbums(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("Short n' Sweet", searchStringCaptor.getValue());
            assertEquals(3, limitCaptor.getValue());
        }
    }

    @Test
    void searchForTracks() {
        APIService api = Mockito.mock(APIService.class);
        List<SpotifyTrack> tracks = Mockito.mock(List.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(tracks).when(api).searchForSongs(Mockito.anyString(), Mockito.anyInt());

            MusicRecommenderModel model = new MusicRecommenderModel();

            model.searchForTracks("We Hug Now");

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForSongs(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("We Hug Now", searchStringCaptor.getValue());
            assertEquals(3, limitCaptor.getValue());
        }
    }

    @Test
    void getRecommendationsArtistStrategy() {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);

            MusicRecommenderModel model = new MusicRecommenderModel();

            SpotifyArtist artist = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "");
            SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "");
            album.addArtist(artist);
            SpotifyTrack track = new SpotifyTrack("30h62oCDd1lhOsJibIFieh", "We Hug Now", album);
            track.addArtist(artist);
            SpotifyTrack track2 = new SpotifyTrack("2qSkIjg1o9h3YT9RAgYN75", "Espresso", album);

            List<SpotifyTrack> tracks = Mockito.mock(List.class);
            tracks.add(track);
            tracks.add(track2);
            Mockito.doReturn(tracks).when(api).searchForSongs(Mockito.anyString(), Mockito.anyInt());

            model.addSeed(artist);
            model.addSeed(album);
            model.addSeed(track);

            model.getRecommendations();

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForSongs(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("artist:\"Sydney Rose\"", searchStringCaptor.getValue());
            assertEquals(6, limitCaptor.getValue());
        }
    }

    @Test
    void getRecommendationsGenreStrategy() {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);

            MusicRecommenderModel model = new MusicRecommenderModel();

            SpotifyArtist artist = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sydney Rose", "");
            artist.addGenre("pop");
            SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "");
            album.addGenre("pop");
            SpotifyTrack track = new SpotifyTrack("30h62oCDd1lhOsJibIFieh", "We Hug Now", album);
            track.addArtist(artist);
            SpotifyTrack track2 = new SpotifyTrack("2qSkIjg1o9h3YT9RAgYN75", "Espresso", album);

            List<SpotifyTrack> tracks = Mockito.mock(List.class);
            tracks.add(track);
            tracks.add(track2);
            Mockito.doReturn(tracks).when(api).searchForSongs(Mockito.anyString(), Mockito.anyInt());

            model.addSeed(artist);
            model.addSeed(album);
            model.addSeed(track);

            model.getRecommendations();

            ArgumentCaptor<String> searchStringCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(api).searchForSongs(searchStringCaptor.capture(), limitCaptor.capture());
            assertEquals("genre:\"pop\"", searchStringCaptor.getValue());
            assertEquals(6, limitCaptor.getValue());
        }
    }

    @Test
    void getRecommendationsWithNoSeeds() {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);

            MusicRecommenderModel model = new MusicRecommenderModel();

            List<SpotifyObject> recs = model.getRecommendations();

            assert(recs.isEmpty());
        }
    }

    @Test
    void testGetAddAndClearSeeds() {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);

            MusicRecommenderModel model = new MusicRecommenderModel();

            SpotifyArtist artist = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv", "Sydney Rose", "");
            SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "");
            SpotifyTrack track = new SpotifyTrack("30h62oCDd1lhOsJibIFieh", "We Hug Now", album);

            model.addSeed(artist);
            model.addSeed(album);
            model.addSeed(track);

            assert model.getSeedObjects().size() == 3;
            assert model.getSeedObjects().contains(artist);
            assert model.getSeedObjects().contains(album);
            assert model.getSeedObjects().contains(track);

            model.clearSeeds();

            assert model.getSeedObjects().isEmpty();
            assert !model.getSeedObjects().contains(artist);
            assert !model.getSeedObjects().contains(album);
            assert !model.getSeedObjects().contains(track);
        }
    }
}