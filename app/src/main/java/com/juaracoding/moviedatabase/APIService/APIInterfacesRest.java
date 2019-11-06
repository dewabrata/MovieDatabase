package com.juaracoding.moviedatabase.APIService;

/**
 * Created by user on 1/10/2018.
 */



import com.juaracoding.moviedatabase.model.ModelMovie;



import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterfacesRest {

 @GET(".")
 Call<ModelMovie> getMovies(@Query("apikey") String apikey, @Query("t") String t);




}

