package sampledetaily.sample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.data.Song;
import sampledetaily.sample.utils.InputUtils;


public class SongListViewAdapter extends ArrayAdapter<Song> {

    ArrayList<Song> songs;
    Context context;
    int resource;

    public SongListViewAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.songs = songs;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.song_list_item, null, true);

        }
        Song currentSong = getItem(position);
        ImageView album = (ImageView) convertView.findViewById(R.id.image);
        TextView trackName = (TextView) convertView.findViewById(R.id.trackName);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView price = (TextView) convertView.findViewById(R.id.trackPrice);

        int randomIndex = InputUtils.generateRandomIndex(0, 7);

        Picasso.get().load(currentSong.getImage())
                .placeholder(EnvironmentVariables.PLACEHOLDER_ARRAY[randomIndex])
                .error(EnvironmentVariables.PLACEHOLDER_ARRAY[randomIndex])
                .into(album);

        StringBuilder sb = new StringBuilder();
        sb.append("$ ").append(currentSong.getTrackPrice());
        String trackPriceString = sb.toString();

        trackName.setText(currentSong.getTrackName());
        price.setText(trackPriceString);
        genre.setText(currentSong.getPrimaryGenreName());

        return convertView;
    }
}
