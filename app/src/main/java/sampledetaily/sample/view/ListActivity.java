package sampledetaily.sample.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import sampledetaily.sample.R;
import sampledetaily.sample.contract.SongListContract;
import sampledetaily.sample.data.Song;
import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.SongListViewAdapter;
import sampledetaily.sample.utils.InputUtils;
import sampledetaily.sample.data.SharedPreferenceManager;
import sampledetaily.sample.utils.UserLoggerUtil;

public class ListActivity extends AppCompatActivity implements SongListContract {

    ArrayList<Song> songsArray;
    ListView songsListView;
    JSONObject songObject;
    private JSONArray jsonArray;
    JSONObject jsonObject;

    private String trackName = "";
    private String trackPrice = "";
    private String primaryGenreName = "";
    private String image = "";

    private String longDescription = "";
    private String artistName = "";

    SharedPreferenceManager sharedPreferenceManager;
    Context context;

    TextView sessionText;
    SwipeRefreshLayout refreshLayout;
    SongListViewAdapter adapter;
    ProgressBar refreshProgressBar;

    private boolean canRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_layout);
        context = this;
        songsArray = new ArrayList<>();
        canRefresh = false;

        songsListView = (ListView) findViewById(R.id.list);
        sessionText = (TextView) findViewById(R.id.last_session_text);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshProgressBar = (ProgressBar) findViewById(R.id.refresh_progress);

        adapter = new SongListViewAdapter(getApplicationContext(), R.layout.song_list_item, songsArray);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               getDataAtPosition(position);

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(canRefresh){
                    loadSongList();
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_action);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Show me love!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        loadSongList();

        String sessionValue = "User Logged Time:" + sharedPreferenceManager.getLastSeenTime() +
                  "on Activity: " + sharedPreferenceManager.getLastUserActivity();
        sessionText.setText(sessionValue);
    }

    @Override
    public void resetSongList(){
        if(null != songsArray){
            songsArray.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadSongList(){
        refreshProgressBar.setVisibility(View.VISIBLE);
        resetSongList();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RequestURL().execute(EnvironmentVariables.URL);
            }
        });
    }

    @Override
    protected void onPause() {
        UserLoggerUtil.updateLastActivity(this, EnvironmentVariables.LIST_VIEW_ACTIVITY);
        super.onPause();
    }

    @Override
    public String readURL(String theUrl) {
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

    @Override
    public void getDataAtPosition(Integer position) {
        try {
            jsonArray =  jsonObject.getJSONArray("results");

            songObject = jsonArray.getJSONObject(position);
            trackName = songObject.getString(EnvironmentVariables.TRACK_NAME);
            trackPrice = songObject.getString(EnvironmentVariables.TRACK_PRICE);
            primaryGenreName = songObject.getString(EnvironmentVariables.GENRE);
            image = songObject.getString(EnvironmentVariables.IMAGE);
            artistName = songObject.getString(EnvironmentVariables.ARTIST_NAME);

            longDescription = InputUtils.checkFieldExists(EnvironmentVariables.LONG_DESC, songObject);

            Song currentSong = new Song(trackName, trackPrice, primaryGenreName, image);
            sharedPreferenceManager.saveSongSP(EnvironmentVariables.CURRENTSONG, currentSong);

            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra( EnvironmentVariables.TRACK_NAME, trackName);
            intent.putExtra(EnvironmentVariables.TRACK_PRICE, trackPrice);
            intent.putExtra(EnvironmentVariables.GENRE, primaryGenreName);
            intent.putExtra(EnvironmentVariables.LONG_DESC, longDescription);
            intent.putExtra(EnvironmentVariables.ARTIST_NAME, artistName);
            intent.putExtra(EnvironmentVariables.PICASSO_IMAGE, image);

            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class RequestURL extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            canRefresh = false;
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                jsonObject = new JSONObject(content);
                jsonArray =  jsonObject.getJSONArray(EnvironmentVariables.RESULTS);

                for(int i =0;i<jsonArray.length(); i++){
                    songObject = jsonArray.getJSONObject(i);
                    songsArray.add(new Song(
                            songObject.getString(EnvironmentVariables.TRACK_NAME),
                            songObject.getString(EnvironmentVariables.TRACK_PRICE),
                            songObject.getString(EnvironmentVariables.GENRE),
                            songObject.getString(EnvironmentVariables.IMAGE)
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            songsListView.setAdapter(adapter);
            canRefresh = true;
            refreshProgressBar.setVisibility(View.GONE);
        }
    }

}
