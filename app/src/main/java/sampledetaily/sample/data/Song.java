package sampledetaily.sample.data;

public class Song {

    private String trackName;
    private String trackPrice;
    private String primaryGenreName;
    private String image;

    public Song(String trackName, String trackPrice, String primaryGenreName, String image) {
        this.trackName = trackName;
        this.trackPrice = trackPrice;
        this.primaryGenreName = primaryGenreName;
        this.image = image;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
