package com.example.myapplication.services;

import com.example.myapplication.data.detail.MovieDetailModel;
import com.example.myapplication.data.main_list.SearchMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRequest {

    @GET("search/movie?language=en-US&page=1")
    Call<SearchMain> searchMovie(@Query("api_key") String api_key,
                                 @Query("query") String query);


    @GET("movie/{movieId}")
    Call<MovieDetailModel> getMovieDetail(@Path("movieId") int movieId,
                                          @Query("api_key") String api_key,
                                          @Query("language") String language);

}
