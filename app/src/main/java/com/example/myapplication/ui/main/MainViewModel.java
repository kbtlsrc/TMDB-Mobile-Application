package com.example.myapplication.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.main_list.SearchMain;
import com.example.myapplication.data.main_list.SearchPart;
import com.example.myapplication.services.ClientMovie;
import com.example.myapplication.services.IRequest;
import com.example.myapplication.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.util.Constants.CUSTOM_TAG;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<SearchPart>> searchList = new MutableLiveData<>();
    private MutableLiveData<Boolean> searchControl = new MutableLiveData<>();

    /**
     * Null check
     *
     * @param query as String
     */
    public void search(String query) {
        if (!query.isEmpty()) {
            searchControl.postValue(false);
            searchMovies(query);
        } else
            searchControl.postValue(true);
    }

    /**
     * Send HTTP Request for searching movies
     */
    private void searchMovies(String query) {
        IRequest request = ClientMovie.getApiClient().create(IRequest.class);
        Call<SearchMain> call = request.searchMovie(Constants.TEST_API_KEY, query);
        call.enqueue(new Callback<SearchMain>() {
            @Override
            public void onResponse(Call<SearchMain> call, Response<SearchMain> response) {
                if (response.isSuccessful()) {
                    searchList.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<SearchMain> call, Throwable t) {
                Log.d(CUSTOM_TAG, "onFailure: ");
            }
        });
    }

    /**
     * This method is used to observe the Live Data for search
     */
    public MutableLiveData<List<SearchPart>> getSearchList() {
        return searchList;
    }

    /**
     * This method is used to observe the Live Data for null check
     */
    public MutableLiveData<Boolean> getSearchControl() {
        return searchControl;
    }
}