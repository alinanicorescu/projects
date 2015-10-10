package recommendations.music;

import java.util.*;

/**
 * Created by alinanicorescu on 10/10/15.
 *
 * Pearson correlation function implementation
 */
public class PearsonCorrelation implements CorrelationFunction {


    public double computePearsonCorrelation(UserSongListings userSongListings1, UserSongListings userSongListings2) {

        Map<String, Double> userSongListenCounts1 = userSongListings1.getUserSongListenCounts() ;

        Map<String, Double> userSongListenCounts2 =   userSongListings2.getUserSongListenCounts();

        //retain songs that are common to both users
        Set<String> songs  = new HashSet<>(userSongListenCounts1.keySet());
        songs.retainAll(userSongListenCounts2.keySet());
        if (songs.isEmpty()) {
            return 0;
        }

        //retain only song counts related to the common songs
        Map<String, Double> commonUserSongCounts1 = new HashMap<>(userSongListenCounts1);
        commonUserSongCounts1.keySet().retainAll(songs);

        Map<String, Double> commonUserSongCounts2 = new HashMap<>(userSongListenCounts2);
        commonUserSongCounts2.keySet().retainAll(songs);


        Collection<Double> commonCounts1 = commonUserSongCounts1.values();
        Collection<Double> commonCounts2 = commonUserSongCounts2.values();

        double sum1 = commonCounts1.parallelStream().mapToDouble(Double::doubleValue).sum();

        double sum2 = commonCounts2.parallelStream().mapToDouble(Double ::doubleValue).sum();

        double squaresSum1 = commonCounts1.parallelStream().mapToDouble(p -> Math.pow(p, 2)).sum();

        double squaresSum2 = commonCounts2.parallelStream().mapToDouble(p -> Math.pow(p, 2)).sum();

        double productsSum = songs.parallelStream().mapToDouble(song -> {
            return commonUserSongCounts1.get(song) * commonUserSongCounts2.get(song);}).sum();

        long songsCount = songs.size();
        double num = productsSum - (sum1 * sum2) /  songsCount;
        double den = Math.sqrt((squaresSum1 - Math.pow(sum1, 2)/songsCount) * (squaresSum2 - Math.pow(sum2, 2)/songsCount));

        if (den == 0) {
            return 0;
        }

        double r = num/den;

        return r;
    }

}
