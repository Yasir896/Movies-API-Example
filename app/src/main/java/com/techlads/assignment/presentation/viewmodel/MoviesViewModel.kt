package com.techlads.assignment.presentation

import androidx.lifecycle.*
import com.techlads.assignment.network.NetworkUtility
import com.techlads.assignment.domain.model.Movie
import com.techlads.assignment.domain.repository.MovieRepository
import com.techlads.assignment.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by Yasir on 3/25/2022.
 */
class MoviesViewModel(private val repository: MovieRepository): ViewModel() {

    private val searchedMoviesResponse = MutableLiveData<List<Movie>>()
    val loading = MutableLiveData<Boolean>()
    val searchedMovies: LiveData<List<Movie>> = searchedMoviesResponse
    val movieResponse = MutableLiveData<Movie>()
    val savedToDbSuccess = MutableLiveData<Boolean>(false)

    fun saveMoviesToDatabase() {
        val movies = Utils.getMovies()
        viewModelScope.launch(Dispatchers.IO) {
            movies.forEach { movie ->
                repository.insertMovie(movie)
            }
        }
        savedToDbSuccess.value = true
    }

    fun addMovieToDatabase(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movie)
        }
        savedToDbSuccess.value = true
    }

    fun fetchMovieFromServer(searchString: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val res = NetworkUtility.request("https://www.omdbapi.com/?apikey=35a0b355&t=${searchString}")
            withContext(Dispatchers.Main) {
                movieResponse.value = Utils.parseResponseObject(res)
                loading.value = false
            }
        }
    }

    fun searchMoviesFromApi(searchString: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val res = NetworkUtility.request("https://www.omdbapi.com/?apikey=35a0b355&s=${searchString}")
            withContext(Dispatchers.Main) {
                searchedMoviesResponse.value = Utils.parseResponseArray(res)
                loading.value = false
            }
        }
    }

    fun searchMovieByActorName(name: String) {
        viewModelScope.launch {
            repository.getMovies().collectLatest { movies ->
                val searchResponse = movies.filter { movie ->
                    movie.actors.contains(name, true)
                }
                searchedMoviesResponse.value = searchResponse
            }
        }
    }
}

class MoviesViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}