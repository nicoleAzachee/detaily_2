package sampledetaily.sample.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import sampledetaily.sample.R;
import sampledetaily.sample.data.Song;
import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.presenter.SongListViewAdapter;
import sampledetaily.sample.utils.SharedPreferenceManager;

public class ListActivity extends AppCompatActivity {

    ArrayList<Song> songsArray;
    ListView songsListView;
    JSONObject songObject;
    private JSONArray jsonArray;
    JSONObject jsonObject;

    private String trackName;
    private String trackPrice;
    private String primaryGenreName;
    private String image;

    private String longDescription; //in detail view
    private String artistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_layout);
        songsArray = new ArrayList<>();
        songsListView = (ListView) findViewById(R.id.list);

        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               getDataForThisPosition(position);

            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(EnvironmentVariables.URL);
            }
        });
    }

    private void getDataForThisPosition(Integer position) {
        try {
            jsonArray =  jsonObject.getJSONArray("results");

            songObject = jsonArray.getJSONObject(position);
            trackName = songObject.getString("trackName");
            trackPrice = songObject.getString("trackPrice");
            primaryGenreName = songObject.getString("primaryGenreName");
            image = songObject.getString("artworkUrl100");

            longDescription = songObject.getString("longDescription");
            artistName = songObject.getString("artistName");

            Song currentSong = new Song(trackName, trackPrice, primaryGenreName, image);
            SharedPreferenceManager.saveSongSP(EnvironmentVariables.CURRENTSONG, currentSong);

            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra("trackName",trackName);
            intent.putExtra("trackPrice",trackPrice);
            intent.putExtra("primaryGenreName",primaryGenreName);
            intent.putExtra("longDescription",longDescription);
            intent.putExtra("artistName",artistName);

            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                jsonObject = new JSONObject(content);
                jsonArray =  jsonObject.getJSONArray("results");

                for(int i =0;i<jsonArray.length(); i++){
                    songObject = jsonArray.getJSONObject(i);
                    songsArray.add(new Song(
                            songObject.getString("trackName"),
                            songObject.getString("trackPrice"),
                            songObject.getString("primaryGenreName"),
                            songObject.getString("artworkUrl100")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SongListViewAdapter adapter = new SongListViewAdapter(
                    getApplicationContext(), R.layout.song_list_item, songsArray
            );
            songsListView.setAdapter(adapter);
        }
    }


    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
