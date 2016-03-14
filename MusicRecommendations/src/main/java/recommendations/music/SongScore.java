package recommendations.music;

/**
 * Created by alinanicorescu on 10/10/15.
 *
 * Class representing a song and an associated score
 */
public final class SongScore implements Comparable<SongScore> {

    private final String song;
    private final double score;

    public SongScore(String song, double score) {
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
        return "recommendations.music.SongScore{" +
                "song='" + song + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(SongScore o) {
        if (this.getSong().equals(o.getSong())) {
            return 0;
        } else {
            return (-1) * Double.compare(this.getScore(), o.getScore());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongScore)) return false;
        SongScore that = (SongScore) o;

        return getSong().equals(that.getSong());

    }

    @Override
    public int hashCode() {
        return getSong().hashCode();
    }
}
