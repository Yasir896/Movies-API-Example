package com.techlads.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techlads.assignment.databinding.ItemRecyclerviewBinding
import com.techlads.assignment.domain.model.Movie


/**
 * Created by Yasir on 3/26/2022.
 */
class MoviesAdapter(var movies: List<Movie>):
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemRecyclerviewBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: Movie) {
                binding.titleTv.text = movie.title
                binding.yearTv.text =  movie.year
                binding.ratingTv.text = movie.rated
                binding.releasedTv.text = movie.released
                binding.runtimeTv.text =  movie.runtime
                binding.genreTv.text = movie.genre
                binding.directorTv.text = movie.director
                binding.writerTv.text = movie.writer
                binding.actorsTv.text = movie.actors
                binding.plotTv.text = movie.plot
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size
}