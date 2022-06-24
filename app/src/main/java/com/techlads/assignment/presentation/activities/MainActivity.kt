package com.techlads.assignment.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.techlads.assignment.MovieApp
import com.techlads.assignment.R
import com.techlads.assignment.adapter.MoviesAdapter
import com.techlads.assignment.domain.model.Movie
import com.techlads.assignment.presentation.MoviesViewModel
import com.techlads.assignment.presentation.MoviesViewModelFactory
import com.techlads.assignment.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.searchActorBtn

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MoviesAdapter

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory((application as MovieApp).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addToDbBtn.setOnClickListener { viewModel.saveMoviesToDatabase() }
        searchMovieBtn.setOnClickListener { startActivity(MovieSearchActivity.start(this)) }
        searchActorBtn.setOnClickListener { startActivity(ActorSearchActivity.start(this)) }
        searchHereBtn.setOnClickListener { searchMovies() }

        setupObservers()
    }

    private fun searchMovies() {
        if (searchMovieEt.isVisible.not()) {
            searchMovieEt.visibility = View.VISIBLE
        } else if (searchMovieEt.isVisible && Utils.validateInput(searchMovieEt.text)) {
            viewModel.searchMoviesFromApi(searchMovieEt.text.toString())
        } else {
            searchMovieEt.error = "Please Enter Movie Name."
        }
    }

    private fun setupObservers() {
        viewModel.loading.observe(this, Observer { loading ->
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })

        viewModel.searchedMovies.observe(this, Observer { movies ->
            if (movies.isNotEmpty()) {
                setupAdapter(movies)
            } else {
                Utils.showMessage(parentll, "No Movie Found!!")
            }
        })

        viewModel.savedToDbSuccess.observe(this, Observer { success ->
            if (success) {
                Utils.showMessage(parentll, "Movie Saved Successfully!!")
            }
        })
    }

    private fun setupAdapter(movies: List<Movie>) {
        rv_recycler.visibility = View.VISIBLE
       rv_recycler.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter(movies)
        rv_recycler.adapter = adapter
    }
}