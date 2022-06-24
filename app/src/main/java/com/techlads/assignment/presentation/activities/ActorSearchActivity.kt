package com.techlads.assignment.presentation.activities

import android.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.techlads.assignment.MovieApp
import com.techlads.assignment.adapter.MoviesAdapter
import com.techlads.assignment.databinding.ActivityActorSearchBinding
import com.techlads.assignment.domain.model.Movie
import com.techlads.assignment.presentation.MoviesViewModel
import com.techlads.assignment.presentation.MoviesViewModelFactory
import com.techlads.assignment.utils.Utils

class ActorSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActorSearchBinding
    private lateinit var adapter: MoviesAdapter

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory((application as MovieApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActorSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpActionBar()

        binding.searchBtn.setOnClickListener { searchMovie() }
        setupObserver()

    }

    private fun searchMovie() {
        if (Utils.validateInput(binding.searchActorEt.text)) {
            viewModel.searchMovieByActorName(binding.searchActorEt.text.toString())
        } else {
            binding.searchActorEt.error = "This Field is Mandatory"
        }
    }

    private fun setupObserver() {
        viewModel.searchedMovies.observe(this, Observer { movies ->
            if (movies.size > 0) {
                setupAdapter(movies)
            } else {
                Utils.showMessage(binding.root, "No Movie Available.!!")
            }
        })

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

    private fun setupAdapter(movies: List<Movie>) {
        binding.rvRecycler.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter(movies)
        binding.rvRecycler.adapter = adapter
    }

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, ActorSearchActivity::class.java)
            return intent
        }
    }
}