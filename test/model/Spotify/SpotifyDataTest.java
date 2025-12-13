package model.Spotify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import service.APIService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyDataTest {
    SpotifyData spotifyData = SpotifyData.getInstance();

    SpotifyArtist artist = new SpotifyArtist("5vx4pDmiFDyKMhuOIgpiRv","Sabrina Carpenter", "");
    SpotifyAlbum album = new SpotifyAlbum("3iPSVi54hsacKKl1xIR2eH", "Short 'n Sweet", "");
    SpotifyTrack track = new SpotifyTrack("30h62oCDd1lhOsJibIFieh", "We Hug Now", album);

    @Test
    void getArtist() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleArtist = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleArtist.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleArtist).when(api).getArtist(Mockito.anyString());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            assert spySpotifyData.getArtist("5vx4pDmiFDyKMhuOIgpiRv").equals(artist);
        }
    }

    @Test
    void getAlbum() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleAlbum = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleAlbum.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleAlbum).when(api).getAlbum(Mockito.anyString());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            Mockito.doReturn(artist).when(spySpotifyData).getArtist(Mockito.anyString());
            Mockito.doReturn(track).when(spySpotifyData).getTrack(Mockito.anyString());

            assert spySpotifyData.getAlbum("3iPSVi54hsacKKl1xIR2eH").equals(album);
        }
    }

    @Test
    void getTrack() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleTrack = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleTrack.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleTrack).when(api).getTrack(Mockito.any());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            Mockito.doReturn(artist).when(spySpotifyData).getArtist(Mockito.anyString());
            Mockito.doReturn(album).when(spySpotifyData).getAlbum(Mockito.anyString());

            assertEquals(track, spySpotifyData.getTrack("30h62oCDd1lhOsJibIFieh"));
        }
    }

    @Test
    void getArtistByName() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleArtist = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleArtist.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleArtist).when(api).getArtist(Mockito.anyString());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            spySpotifyData.getArtist("5vx4pDmiFDyKMhuOIgpiRv");

            assertEquals(artist, spySpotifyData.getArtistByName("Sabrina Carpenter").getFirst());
        }
    }

    @Test
    void getAlbumByName() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleAlbum = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleAlbum.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleAlbum).when(api).getAlbum(Mockito.anyString());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            Mockito.doReturn(artist).when(spySpotifyData).getArtist(Mockito.anyString());
            Mockito.doReturn(track).when(spySpotifyData).getTrack(Mockito.anyString());

            spySpotifyData.getAlbum("3iPSVi54hsacKKl1xIR2eH");

            assertEquals(album, spySpotifyData.getAlbumByName("Short n' Sweet").getFirst());
        }
    }

    @Test
    void getTrackByName() throws IOException {
        APIService api = Mockito.mock(APIService.class);

        try (MockedStatic<APIService> apiServiceMockedStatic = Mockito.mockStatic(APIService.class)) {
            JSONObject sampleTrack = new JSONObject(Files.readString(Path.of("./assets/SampleData/sampleTrack.json"), StandardCharsets.UTF_8));

            apiServiceMockedStatic.when(APIService::getInstance).thenReturn(api);
            Mockito.doReturn(sampleTrack).when(api).getTrack(Mockito.any());

            SpotifyData spySpotifyData = Mockito.spy(spotifyData);

            Mockito.doReturn(artist).when(spySpotifyData).getArtist(Mockito.anyString());
            Mockito.doReturn(album).when(spySpotifyData).getAlbum(Mockito.anyString());

            spySpotifyData.getTrack("30h62oCDd1lhOsJibIFieh");

            assertEquals(track, spySpotifyData.getTrackByName("We Hug Now").getFirst());
        }
    }
}