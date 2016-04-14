package recommendations.music;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by alinanicorescu on 10/10/15.
 *
 * User song statistics (e.g. how many times a user listened a song)
 */
public class UserSongHits {

    private final String username;
    private final Map<String, Double> userSongListenCounts;

    public UserSongHits(String username) {
        this.username = username;
        userSongListenCounts = new HashMap<>();
    }

    public UserSongHits(String username, Map<String, Double> userSongListenCounts) {
        this.username = username;
        this.userSongListenCounts = userSongListenCounts;
    }


    public String getUsername() {
        return username;
    }

    public Map<String, Double> getUserSongListenCounts() {
        return userSongListenCounts;
    }

    public void addSongListings(String song, double listings) {
        userSongListenCounts.put(song, listings);
    }

}
