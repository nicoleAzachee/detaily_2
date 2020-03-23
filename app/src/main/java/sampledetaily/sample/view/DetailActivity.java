package sampledetaily.sample.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import sampledetaily.sample.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail_layout);

        ImageButton back = (ImageButton)findViewById(R.id.back);

        TextView trackName = (TextView)findViewById(R.id.trackName);
        TextView trackPrice = (TextView)findViewById(R.id.trackPrice);
        TextView primaryGenreName = (TextView)findViewById(R.id.primaryGenreName);
        TextView image = (TextView)findViewById(R.id.image);
        TextView longDescription = (TextView)findViewById(R.id.longDescription);
        TextView artistName = (TextView)findViewById(R.id.artistName);

        trackName.setText("trackName  :  "+getIntent().getExtras().getString("trackName"));
        trackPrice.setText("trackPrice  :  "+getIntent().getExtras().getString("trackPrice")+"\n");
        primaryGenreName.setText("primaryGenreName  :  "+getIntent().getExtras().getString("primaryGenreName")+"\n");
        image.setText("image  :  "+getIntent().getExtras().getString("image")+"\n");
        longDescription.setText("longDescription  :  "+getIntent().getExtras().getString("longDescription")+"\n");
        artistName.setText("artistName  :  "+getIntent().getExtras().getString("artistName")+"\n");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
