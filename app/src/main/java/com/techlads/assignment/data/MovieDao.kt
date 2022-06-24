package com.techlads.assignment.data

import androidx.room.*
import com.techlads.assignment.domain.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Created by Yasir on 3/24/2022.
 */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentity")
    fun getMovies(): Flow<List<MovieEntity>>
}