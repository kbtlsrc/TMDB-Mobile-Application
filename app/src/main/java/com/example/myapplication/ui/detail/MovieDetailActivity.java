package com.example.myapplication.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.detail.Genre;
import com.example.myapplication.data.detail.MovieDetailModel;
import com.example.myapplication.databinding.ActivityMovieDetailBinding;
import com.example.myapplication.util.Constants;

import static com.example.myapplication.util.Constants.ARG_MOVIE_ID;

import java.util.concurrent.ExecutionException;

public class MovieDetailActivity  extends AppCompatActivity {
        private ActivityMovieDetailBinding binding;
        private MovieDetailViewModel mainViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mainViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        initObservers();
        checkArguments();


    }

    private void checkArguments() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int movieId = bundle.getInt(Constants.ARG_MOVIE_ID);

            mainViewModel.getMovieDetail(movieId);
        } else finish();
    }


    private void initObservers(){
         mainViewModel.getMovieDetail().observe(this, this::prepareView);
}

        private void prepareView(MovieDetailModel movie){

        //Backdrop image
            if(!TextUtils.isEmpty(movie.getBackdropPath())){
                String posterPath = Constants.BACKDROP_BASE_PATH + movie.getBackdropPath();

                    Glide.with(getApplicationContext())
                            .load(posterPath)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.ivBackdrop);

            }



            if (!TextUtils.isEmpty(movie.getOverview()))
                binding.tvOverview.setText(movie.getOverview());
            //Genres / Categories
            if (movie.getGenres() != null & movie.getGenres().size() > 0) {
                String genres = "";
                for (Genre genre : movie.getGenres()) {
                    if (genres.equals(""))
                        genres = genre.getName();
                    else genres += ", " + genre.getName();
                }
                binding.tvCategory.setText(genres);
            }
            //Release Date
            if (!TextUtils.isEmpty(movie.getReleaseDate()))
                binding.tvDate.setText(movie.getReleaseDate());
            //Score
            if (movie.getVoteAverage() != 0)
                binding.tvScore.setText(movie.getVoteAverage().toString());
        }


    }


