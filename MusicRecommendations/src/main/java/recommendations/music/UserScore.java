package recommendations.music;

/**
 * Created by alinanicorescu on 10/10/15.
 */
public class UserScore implements Comparable<UserScore> {

    private String user;
    private double score;

    public UserScore(String user, double score) {
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
    public int compareTo(UserScore o) {
        if (this.getUser().equals(o.getUser())) {
            return 0;
        } else {
            return (-1) * Double.compare(this.getScore(), o.getScore());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserScore)) return false;

        UserScore that = (UserScore) o;

        return getUser().equals(that.getUser());

    }

    @Override
    public int hashCode() {
        return getUser().hashCode();
    }

    @Override
    public String toString() {
        return "recommendations.music.UserScore{" +
                "user='" + user + '\'' +
                ", score=" + score +
                '}';
    }
}
