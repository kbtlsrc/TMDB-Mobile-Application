package com.example.myapplication.ui.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.detail.MovieDetailModel;
import com.example.myapplication.services.ClientMovie;
import com.example.myapplication.services.IRequest;
import com.example.myapplication.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.util.Constants.CUSTOM_TAG;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    private MutableLiveData<MovieDetailModel> movieDetail = new MutableLiveData<>();

    /**
     * Send HTTP Request for getting the movie detail via movieId
     */
    public void getMovieDetail(int movieId) {
        IRequest request = ClientMovie.getApiClient().create(IRequest.class);
        Call<MovieDetailModel> call = request.getMovieDetail(movieId, Constants.TEST_API_KEY, Constants.API_LANGUAGE);
        call.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                if (response.isSuccessful()) {
                    movieDetail.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                Log.d(CUSTOM_TAG, "onFailure: ");
            }
        });
    }

    /**
     * This method is used to observe the Live Data for movie detail
     */
    public MutableLiveData<MovieDetailModel> getMovieDetail() {
        return movieDetail;
    }
}
