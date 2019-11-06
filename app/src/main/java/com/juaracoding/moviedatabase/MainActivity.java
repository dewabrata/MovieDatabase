package com.juaracoding.moviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juaracoding.moviedatabase.APIService.APIClient;
import com.juaracoding.moviedatabase.APIService.APIInterfacesRest;
import com.juaracoding.moviedatabase.model.ModelMovie;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    TextView txtTitle,txtSipnosis;
    Button btnSearch;
    EditText txtJudul;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);
        txtTitle = findViewById(R.id.txtTitle);
        txtSipnosis = findViewById(R.id.txtSipnosis);
        txtJudul = findViewById(R.id.txtJudul);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovies(txtJudul.getText().toString());
            }
        });



    }

    private void getMovies(String judul){


        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelMovie> movie = apiInterface.getMovies("80641bfb",judul);


        movie.enqueue(new Callback<ModelMovie>() {
            @Override
            public void onResponse(Call<ModelMovie> call, Response<ModelMovie> response) {
                ModelMovie dataMovie = response.body();


                if (response.body() != null) {


                    Picasso.get().load(dataMovie.getPoster()).into(image);
                    txtTitle.setText(dataMovie.getTitle());
                    txtSipnosis.setText(dataMovie.getPlot());






                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<ModelMovie> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();

            }

        });
    }

}
