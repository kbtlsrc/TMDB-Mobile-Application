package com.example.myapplication.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.main_list.SearchPart;
import com.example.myapplication.databinding.ItemMovieBinding;
import com.example.myapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolders>  {

    private List<SearchPart> movies;
    private Context context;
    private ItemClickListener itemClickListener;

    public MovieAdapter(Context context) {
        this.context = context;
        this.movies = new ArrayList<>();
    }

    public void setAdapterList(List<SearchPart> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoviesViewHolders(binding);
    }

    public void onBindViewHolder(MoviesViewHolders holder, int position) {
        SearchPart movie= movies.get(position);
        if (!TextUtils.isEmpty(movie.getPosterPath())){
            String posterPath = "https://image.tmdb.org/t/p/w780/" + movie.getPosterPath();
            Glide.with(context)
                    .load(posterPath)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.binding.ivPoster);

        }

        if (!TextUtils.isEmpty((movie.getOriginalTitle())))
            holder.binding.tvTitle.setText(movie.getOriginalTitle());
        if (!TextUtils.isEmpty(movie.getPopularity().toString())){
            String popularity = "Popularity: " + movie.getPopularity().toString();
            holder.binding.tvPopularity.setText(popularity);
        }
        if (!TextUtils.isEmpty(movie.getVoteAverage().toString())){
            String score = "Score: " + movie.getVoteAverage().toString();
            holder.binding.tvScore.setText(score);
        }
           }


    private SearchPart getItem(int pos) {
        return movies.get(pos);
    }


    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public void setOnClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(int pos, SearchPart movie);
    }


    public class MoviesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMovieBinding binding;

        public MoviesViewHolders(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), getItem(getAdapterPosition()));
        }

    }

}