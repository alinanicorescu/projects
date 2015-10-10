package recommendations.music;

/**
 * Created by alinanicorescu on 10/10/15.
 */
public class SongRecommendation implements Comparable<SongRecommendation> {

    private String song;
    private double score;

    public SongRecommendation(String song, double score) {
        this.song = song;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getSong() {
        return song;
    }

    @Override
    public String toString() {
        return "recommendations.music.SongRecommendation{" +
                "song='" + song + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(SongRecommendation o) {
        if (this.getSong().equals(o.getSong())) {
            return 0;
        } else {
            return (-1) * Double.compare(this.getScore(), o.getScore());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongRecommendation)) return false;

        SongRecommendation that = (SongRecommendation) o;

        return getSong().equals(that.getSong());

    }

    @Override
    public int hashCode() {
        return getSong().hashCode();
    }
}
