package recommendations.music;

/**
 * Created by alinanicorescu on 10/10/15.
 */
public class UserRecommendation implements Comparable<UserRecommendation> {

    private String user;
    private double score;

    public UserRecommendation(String user, double score) {
        this.user = user;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getUser() {
        return user;
    }

    @Override
    public int compareTo(UserRecommendation o) {
        if (this.getUser().equals(o.getUser())) {
            return 0;
        } else {
            return (-1) * Double.compare(this.getScore(), o.getScore());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRecommendation)) return false;

        UserRecommendation that = (UserRecommendation) o;

        return getUser().equals(that.getUser());

    }

    @Override
    public int hashCode() {
        return getUser().hashCode();
    }

    @Override
    public String toString() {
        return "recommendations.music.UserRecommendation{" +
                "user='" + user + '\'' +
                ", score=" + score +
                '}';
    }
}
