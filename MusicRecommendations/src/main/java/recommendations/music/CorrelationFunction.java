package recommendations.music;

/**
 * Interface for the correlation compute algorithm.
 */


@FunctionalInterface
public interface CorrelationFunction  {

    double computePearsonCorrelation(UserSongListings userSongListings1, UserSongListings userSongListings2);


}
