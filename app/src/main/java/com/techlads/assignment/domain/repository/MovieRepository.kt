package com.techlads.assignment.domain.repository

import com.techlads.assignment.domain.model.Movie
import com.techlads.assignment.data.MovieDao
import com.techlads.assignment.data.toMovie
import com.techlads.assignment.data.toMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Yasir on 3/25/2022.
 */

class MovieRepository( private val dao: MovieDao) {

    suspend fun insertMovie(movie: Movie) {
        dao.insertMovie(movie.toMovieEntity())
    }

    suspend fun getMovies(): Flow<List<Movie>> {
        return dao.getMovies().map { movie ->
                movie.map { it.toMovie() }
        }
    }

}