import recommendations.music.MusicRecommender;
import recommendations.music.PearsonCorrelation;
import recommendations.music.SongRecommendation;
import recommendations.music.UserRecommendation;

import java.util.SortedSet;

/**
 * Created by alinanicorescu on 10/10/15.
 */
public class EntryPoint {

    public static void main(String[] args) {

        MusicRecommender musicRecommender = new MusicRecommender();
        musicRecommender.setCorrelationFunction(new PearsonCorrelation());
        musicRecommender.init();
        musicRecommender.computeScores("rockqueen");

        SortedSet<UserRecommendation> userRecommendations = musicRecommender.getUserRecommendations(0.1);

        System.out.println("\n---SIMILAR USERS---\n");
        for (UserRecommendation userRecommendation : userRecommendations) {
            System.out.println(userRecommendation);
        }

        System.out.println("\n---RECOMMENDED SONGS---\n");
        SortedSet<SongRecommendation> songRecommendations = musicRecommender.getSongRecommendations(0.1);

        for (SongRecommendation songRecommendation : songRecommendations) {
            System.out.println(songRecommendation);
        }
    }
}
