package com.example.miguelangelrubiocaballero.a06recipesbook;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RestService {

    String BASE_URL = "https://www.food2fork.com/api/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("search")
    Call<Recipe> getTopRecipes(@Query("key") String api_key, @Query("sort") String sort);

    @GET("get")
    Call<Ingredients> getRecipe(@Query("key") String api_key, @Query("rId") String rId);

}

