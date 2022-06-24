package com.techlads.assignment.data

import com.techlads.assignment.domain.model.Movie


/**
 * Created by Yasir on 3/25/2022.
 */


fun MovieEntity.toMovie(): Movie {
    return Movie(
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot
    )
}
