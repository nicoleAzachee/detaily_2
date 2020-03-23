package sampledetaily.sample.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import sampledetaily.sample.DetailyApplication;
import sampledetaily.sample.R;
import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.utils.InputUtils;

public class DetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail_layout);

        ImageButton back = (ImageButton)findViewById(R.id.back);

        TextView trackName = (TextView)findViewById(R.id.trackName);
        TextView trackPrice = (TextView)findViewById(R.id.trackPrice);
        TextView primaryGenreName = (TextView)findViewById(R.id.primaryGenreName);
        ImageView image = (ImageView)findViewById(R.id.image);
        TextView longDescription = (TextView)findViewById(R.id.longDescription);
        TextView artistName = (TextView)findViewById(R.id.artistName);

        StringBuilder sb = new StringBuilder();
        sb.append("$").append(getIntent().getExtras().getString("trackPrice"));
        String trackPriceString = sb.toString();

        trackName.setText(getIntent().getExtras().getString("trackName"));
        trackPrice.setText(trackPriceString);
        primaryGenreName.setText(getIntent().getExtras().getString("primaryGenreName"));
        longDescription.setText(getIntent().getExtras().getString("longDescription"));
        artistName.setText(getIntent().getExtras().getString("artistName"));

        int randomIndex = InputUtils.generateRandomIndex(0, 7);

        Picasso.get().load(getIntent().getExtras().getString("image"))
                .placeholder(EnvironmentVariables.PLACEHOLDERARRAY[randomIndex])
                .error(EnvironmentVariables.PLACEHOLDERARRAY[randomIndex])
                .into(image);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
