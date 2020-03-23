package sampledetaily.sample.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import sampledetaily.sample.R;
import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.utils.InputUtils;

public class DetailActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail_layout);
        context = this;

        ImageButton back = (ImageButton)findViewById(R.id.back);

        TextView trackName = (TextView)findViewById(R.id.trackName);
        TextView trackPrice = (TextView)findViewById(R.id.trackPrice);
        TextView primaryGenreName = (TextView)findViewById(R.id.primaryGenreName);
        ImageView image = (ImageView)findViewById(R.id.image);
        TextView longDescription = (TextView)findViewById(R.id.longDescription);
        TextView artistName = (TextView)findViewById(R.id.artistName);

        StringBuilder sb = new StringBuilder();
        sb.append("$").append(getIntent().getExtras().getString(EnvironmentVariables.TRACK_PRICE));
        String trackPriceString = sb.toString();

        trackName.setText(getIntent().getExtras().getString(EnvironmentVariables.TRACK_NAME));
        trackPrice.setText(trackPriceString);
        primaryGenreName.setText(getIntent().getExtras().getString(EnvironmentVariables.GENRE));
        longDescription.setText(getIntent().getExtras().getString(EnvironmentVariables.LONG_DESC));
        artistName.setText(getIntent().getExtras().getString(EnvironmentVariables.ARTIST_NAME));

        int randomIndex = InputUtils.generateRandomIndex(0, 7);

        Picasso.get().load(getIntent().getExtras().getString(EnvironmentVariables.PICASSO_IMAGE))
                .placeholder(EnvironmentVariables.PLACEHOLDERARRAY[randomIndex])
                .error(EnvironmentVariables.PLACEHOLDERARRAY[randomIndex])
                .into(image);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buyButton = (Button) findViewById(R.id.buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Added to cart! :)",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }



}
