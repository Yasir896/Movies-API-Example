package com.techlads.assignment

import android.app.Application
import com.techlads.assignment.data.MoviesDatabase
import com.techlads.assignment.domain.repository.MovieRepository


/**
 * Created by Yasir on 3/25/2022.
 */
class MovieApp: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val database by lazy { MoviesDatabase.getDatabase(this)}
    val repository by lazy { MovieRepository(database.movieDao())}
}