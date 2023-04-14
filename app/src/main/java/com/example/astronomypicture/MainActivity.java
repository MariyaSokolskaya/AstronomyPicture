package com.example.astronomypicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView spaceImage;
    TextView descriptionText, titleText;


    String iamToken;

    public final String ADDRESS = "https://api.nasa.gov";
    public final String IAMADDRESS = "https://iam.api.cloud.yandex.net";
    public final String KEY="DEMO_KEY";
    public final String oAuthToken = "";
    String folderID = "b1gn17lo67soo1vi7lse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceImage = findViewById(R.id.space);
        descriptionText = findViewById(R.id.description);
        titleText = findViewById(R.id.title_text);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpaceService spaceService = retrofit.create(SpaceService.class);
        Call<SpaceInfo> call = spaceService.getAPOD(KEY);
        call.enqueue(new ResponseSpace());


    }
    class ResponseSpace implements Callback<SpaceInfo>{

        @Override
        public void onResponse(Call<SpaceInfo> call, Response<SpaceInfo> response) {
            if(response.isSuccessful() && response.code() == 200){
                SpaceInfo spaceInfo = response.body();
                //String res = spaceInfo.title + "\n" + spaceInfo.explanation;
                titleText.setText(spaceInfo.title);
                descriptionText.setText(spaceInfo.explanation);
                if(spaceInfo.media_type.equals("image")) {
                    Picasso.get()
                            .load(spaceInfo.url)
                            .placeholder(R.drawable.bigvortex)
                            .into(spaceImage);
                }
            }
        }

        @Override
        public void onFailure(Call<SpaceInfo> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}