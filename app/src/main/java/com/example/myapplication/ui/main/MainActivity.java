package com.example.myapplication.ui.main;

import static com.example.myapplication.util.Constants.ARG_MOVIE_ID;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.data.main_list.SearchPart;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.detail.MovieDetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mViewModel;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Prepare View Model
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        showWelcomeMessage();
        initComponents();
        initClicks();
        initObservers();

    }

    /**
     * Show the Toast message to user
     */
    private void showWelcomeMessage() {
        Toast.makeText(this, "Welcome to MyApp", Toast.LENGTH_SHORT).show();
    }

    /**
     * Initialize components & Create necessary adapter
     */
    private void initComponents() {
        //Recycler View initialize
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMovies.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieAdapter(this);
        binding.rvMovies.setAdapter(movieAdapter);
    }

    /**
     * Handle RecyclerView's clicks
     */
    private void initClicks() {
        //Search button
        binding.btnSearch.setOnClickListener(v -> mViewModel.search(binding.etSearch.getText().toString()));
        //Adapter's click
        movieAdapter.setOnClickListener((pos, movie) -> {
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra(ARG_MOVIE_ID, movie.getId());
            startActivity(intent);
        });
    }

    /**
     * Initialize observers for getting data from the ViewModel
     */
    private void initObservers() {
        mViewModel.getSearchList().observe(this, this::prepareRecycler);
        mViewModel.getSearchControl().observe(this, aBoolean -> {
            if (aBoolean)
                Toast.makeText(this, "You should enter at least one letter", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Set the data to the RecyclerView's adapter
     *
     * @param models as List<Result>
     */
    private void prepareRecycler(List<SearchPart> models) {
        movieAdapter.setAdapterList(models);
    }
}