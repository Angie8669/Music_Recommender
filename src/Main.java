import model.MusicRecommenderModel;
import model.SpotifyTrack;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MusicRecommenderModel model = new MusicRecommenderModel();

        List<SpotifyTrack> tracks = model.searchForTracks("We Hug Now");

        for (SpotifyTrack track : tracks) {
            System.out.println(track);
        }
    }
}
