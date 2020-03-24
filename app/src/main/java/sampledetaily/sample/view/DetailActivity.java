package sampledetaily.sample.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import sampledetaily.sample.R;
import sampledetaily.sample.contract.SongDetailContract;
import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.utils.InputUtils;
import sampledetaily.sample.utils.UserLoggerUtil;

public class DetailActivity extends AppCompatActivity implements SongDetailContract {

    Context context;

    ImageButton back;
    TextView trackName;
    TextView trackPrice;
    TextView primaryGenreName;
    ImageView image;
    TextView longDescription;
    TextView artistName;
    Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail_layout);
        context = this;

        initializeViews();
        setupViews();
        setListeners();
    }

    @Override
    public void initializeViews() {
        back = (ImageButton) findViewById(R.id.back);
        trackName = (TextView) findViewById(R.id.trackName);
        trackPrice = (TextView) findViewById(R.id.trackPrice);
        primaryGenreName = (TextView) findViewById(R.id.primaryGenreName);
        image = (ImageView) findViewById(R.id.image);
        longDescription = (TextView) findViewById(R.id.longDescription);
        artistName = (TextView) findViewById(R.id.artistName);
        buyButton = (Button) findViewById(R.id.buy_button);
    }

    @Override
    public void setupViews() {
        StringBuilder sb = new StringBuilder();
        sb.append("$ ").append(getIntent().getExtras().getString(EnvironmentVariables.TRACK_PRICE));
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
    }

    @Override
    public void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Added to cart! :)", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    protected void onPause() {
        UserLoggerUtil.updateLastActivity(this, EnvironmentVariables.DETAIL_VIEW_ACTIVITY);
        super.onPause();
    }

}
