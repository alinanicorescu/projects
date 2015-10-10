package recommendations.music;

import java.util.*;

/**
 * Created by alinanicorescu on 10/10/15.
 * Computes song and user recommendations for a given user
 */

public class MusicRecommender {

    private Map<String, UserSongListings> userSongListeningsMap = new HashMap<>();

    private CorrelationFunction correlationFunction;

    TreeMap<String, Double> songSimilarityScores = new TreeMap<>();
    Map<String, Double> userSimilarityScores = new HashMap<>();


    public void init() {

        //TODO read values from audioscrobbler.com
        String user1 = "rockqueen";
        UserSongListings l1 = new UserSongListings(user1);
        l1.addSongListings("Back in Black", 40);
        l1.addSongListings("The House of the Rising Sun", 89);
        l1.addSongListings("Spiral", 67);
        l1.addSongListings("Blue Dial", 85);
        userSongListeningsMap.put(user1, l1);

        String user2 = "disturbed";
        UserSongListings l2 = new UserSongListings(user2);
        l2.addSongListings("Back in Black", 30);
        l2.addSongListings("The House of the Rising Sun", 78);
        l2.addSongListings("Blue Dial", 85);
        l2.addSongListings("Something will happen", 65);
        l2.addSongListings("Where did the sun go", 867);
        userSongListeningsMap.put(user2, l2);


        String user3 = "jazzfan";
        UserSongListings l3 = new UserSongListings(user3);
        l3.addSongListings("Back in Black", 23);
        l3.addSongListings("The House of the Rising Sun", 12);
        l3.addSongListings("Blues in the house", 872);
        l3.addSongListings("Heart Song", 562);
        l3.addSongListings("Fields of Gold", 45);
        l3.addSongListings("Don't leave now", 97);
        l3.addSongListings("Everything fades", 32);
        userSongListeningsMap.put(user3, l3);


        String user4 = "shipofevil";
        UserSongListings l4 = new UserSongListings(user4);
        l4.addSongListings("The House of the Rising Sun", 12);
        l4.addSongListings("Roots", 872);
        l4.addSongListings("Fade to black", 562);
        l4.addSongListings("Omen", 45);
        l4.addSongListings("Return to Vallhala", 97);
        l4.addSongListings("Chains of my mind", 156);
        userSongListeningsMap.put(user4, l4);


        String user5 = "crazyvali";
        UserSongListings l5 = new UserSongListings(user5);
        l5.addSongListings("Back in Black", 45);
        l5.addSongListings("The House of the Rising Sun", 83);
        l5.addSongListings("Spiral", 43);
        l5.addSongListings("Blue Dial", 12);
        l5.addSongListings("September chill", 56);
        userSongListeningsMap.put(user5, l5);


        String user6 = "malevolent";
        UserSongListings l6 = new UserSongListings(user6);
        l6.addSongListings("Chains of my mind", 200);
        l6.addSongListings("Tell me your name", 134);
        l6.addSongListings("Spiral", 43);
        l6.addSongListings("Blue Dial", 78);
        l6.addSongListings("Roots", 1);
        userSongListeningsMap.put(user6, l6);

        String user7 = "surething";
        UserSongListings l7 = new UserSongListings(user7);
        l7.addSongListings("Seeing the truth", 200);
        l7.addSongListings("Forgive", 234);
        l7.addSongListings("Spiral", 30);
        l7.addSongListings("Nowhere to run", 78);
        l7.addSongListings("Balmorhea", 1);
        userSongListeningsMap.put(user7, l7);


        String user8 = "stripoffate";
        UserSongListings l8 = new UserSongListings(user8);
        l8.addSongListings("The House of the Rising Sun", 100);
        l8.addSongListings("Forgive", 100);
        l8.addSongListings("September chill", 78);
        l8.addSongListings("Nowhere to run", 78);
        l8.addSongListings("Balmorhea", 90);
        userSongListeningsMap.put(user8, l8);

        String user9 = "lasagna";
        UserSongListings l9 = new UserSongListings(user9);
        l9.addSongListings("The House of the Rising Sun", 10);
        l9.addSongListings("Roots", 200);
        l9.addSongListings("Fade to black", 45);
        l9.addSongListings("Omen", 45);
        l9.addSongListings("Return to Vallhala", 98);
        l9.addSongListings("Chains of my mind", 156);
        userSongListeningsMap.put(user9, l9);

        String user10 = "rockandall";
        UserSongListings l10 = new UserSongListings(user10);
        l10.addSongListings("Back in Black", 43);
        l10.addSongListings("The House of the Rising Sun", 76);
        l10.addSongListings("Spiral", 89);
        l10.addSongListings("Blue Dial", 97);
        l10.addSongListings("Chains of my mind", 159);
        l10.addSongListings("Balmorhea", 178);
        userSongListeningsMap.put(user10, l10);


    }


    public void setCorrelationFunction(CorrelationFunction correlationFunction) {
        this.correlationFunction = correlationFunction;
    }

    public void computeScores(String userName) {


        if (!userSongListeningsMap.containsKey(userName)) {
            System.out.println("The user has not made any listenings!");
            return;
        }

        UserSongListings referenceListenings = userSongListeningsMap.get(userName);

        Map<String, List<Double>> userSongScores = new HashMap<>();

        for (String user : userSongListeningsMap.keySet()) {

            if (user.equals(userName)) {
                continue;
            }

            UserSongListings listenings = userSongListeningsMap.get(user);

            double score = correlationFunction.computePearsonCorrelation(referenceListenings, listenings);
            userSimilarityScores.put(user, score);

            if (score > 0) {
                //compute songs similarity scores

                Map<String, Double> userSongListeningsCounts = listenings.getUserSongListenCounts();

                for (String song : userSongListeningsCounts.keySet()) {
                    userSongScores.putIfAbsent(song, new ArrayList<>());
                    userSongScores.get(song).add(score * userSongListeningsCounts.get(song));
                }
            }

        }

        //compute song similarities

        for (String song : userSongScores.keySet()) {
            List<Double> userScores = userSongScores.get(song);
            double songScoreSum = userScores.parallelStream().mapToDouble(Double::doubleValue).sum();
            double songSimilarityScore = songScoreSum / userSongScores.size();
            songSimilarityScores.put(song, songSimilarityScore);
        }

    }


    public SortedSet<SongRecommendation> getSongRecommendations(Double top) {
        TreeSet<SongRecommendation> songRecommendations = new TreeSet<>();

        for (Map.Entry<String, Double> songScore : songSimilarityScores.entrySet()) {
            songRecommendations.add(new SongRecommendation(songScore.getKey(), songScore.getValue()));

        }
        if (top != null && top > 0) {
            return songRecommendations.headSet(new SongRecommendation("", Double.parseDouble(top.toString())));
        }
        return songRecommendations;

    }

    public SortedSet<UserRecommendation> getUserRecommendations (Double top) {
        TreeSet<UserRecommendation> userRecommendations = new TreeSet<>();

        for (Map.Entry<String, Double> userScore : userSimilarityScores.entrySet()) {
            userRecommendations.add(new UserRecommendation(userScore.getKey(), userScore.getValue()));

        }
        if (top != null && top > 0) {
            return userRecommendations.headSet(new UserRecommendation("", Double.parseDouble(top.toString())));
        }
        return userRecommendations;
    }


}

