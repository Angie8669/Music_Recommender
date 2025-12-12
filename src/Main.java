import model.MusicRecommenderModel;
import model.Spotify.SpotifyData;
import model.Spotify.SpotifyTrack;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MusicRecommenderModel model = new MusicRecommenderModel();

        List<SpotifyTrack> tracks = model.searchForTracks("We Hug Now");

        for (SpotifyTrack track : tracks) {
            System.out.println(track);
        }

        System.out.println(SpotifyData.getArtistByName("Sydney Rose"));
        System.out.println(SpotifyData.getAlbumByName("We Hug Now"));
    }
}
