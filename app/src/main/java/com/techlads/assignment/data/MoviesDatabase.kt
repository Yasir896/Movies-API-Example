package com.techlads.assignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Created by Yasir on 3/25/2022.
 */
@Database(entities = [MovieEntity::class], version = 1)
public abstract class MoviesDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "movies_database"
                    ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}