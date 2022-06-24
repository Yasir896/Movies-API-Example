package com.techlads.assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Yasir on 3/24/2022.
 */
@Entity
data class MovieEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
)