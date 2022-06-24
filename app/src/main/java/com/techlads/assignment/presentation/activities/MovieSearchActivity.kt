package com.techlads.assignment.presentation.activities

import android.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.techlads.assignment.MovieApp
import com.techlads.assignment.databinding.ActivityMovieSearchBinding
import com.techlads.assignment.domain.model.Movie
import com.techlads.assignment.presentation.MoviesViewModel
import com.techlads.assignment.presentation.MoviesViewModelFactory
import com.techlads.assignment.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_search.*
import kotlinx.android.synthetic.main.activity_movie_search.searchMovieEt

class MovieSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieSearchBinding

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory((application as MovieApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpActionBar()

        retrieveMovieBtn.setOnClickListener { retrieveMovie() }
        saveToDbBtn.setOnClickListener { saveToDatabase() }

        setUpObserver()

    }

    private fun saveToDatabase() {
        if (viewModel.movieResponse.value != null) {
            viewModel.addMovieToDatabase(viewModel.movieResponse.value!!)
        } else {
            Utils.showMessage(binding.root.rootView, "There is no movie available to save.")
        }
    }
    private fun retrieveMovie() {
        if (searchMovieEt.text.isNullOrEmpty().not()){
            viewModel.fetchMovieFromServer(searchMovieEt.text.toString())
        } else {
            searchMovieEt.error = "This field in Mandatory"
        }
    }

    private fun setUpObserver() {
        viewModel.movieResponse.observe(this, Observer { movie ->
            showMovieInfo(movie)
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.savedToDbSuccess.observe(this, Observer { success ->
            if (success) {
                Utils.showMessage(binding.root, "Movie Saved Successfully!!")
            }
        })
    }

    private fun showMovieInfo(movie: Movie) {
        binding.infoLayout.visibility = View.VISIBLE
        binding.saveToDbBtn.isClickable = true
        binding.movieInfoLayout.titleTv.text = movie.title
        binding.movieInfoLayout.yearTv.text =  movie.year
        binding.movieInfoLayout.ratingTv.text = movie.rated
        binding.movieInfoLayout.releasedTv.text = movie.released
        binding.movieInfoLayout.runtimeTv.text =  movie.runtime
        binding.movieInfoLayout.genreTv.text = movie.genre
        binding.movieInfoLayout.directorTv.text = movie.director
        binding.movieInfoLayout.writerTv.text = movie.writer
        binding.movieInfoLayout.actorsTv.text = movie.actors
        binding.movieInfoLayout.plotTv.text = movie.plot
    }

    fun setUpActionBar() {
        val actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.title = "Search Movie"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    companion object {
        fun start(context: Context): Intent {
            return Intent(context, MovieSearchActivity::class.java)
        }
    }
}