package sampledetaily.sample.contract;

public interface SongListContract {
    void resetSongList();
    void loadSongList();
    void getDataAtPosition(Integer position);
    String readURL(String theUrl);
}
