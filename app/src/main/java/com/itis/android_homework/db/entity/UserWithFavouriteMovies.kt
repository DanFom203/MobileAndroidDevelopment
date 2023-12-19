package com.itis.android_homework.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithFavouriteMovies (
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "movie_id",
        associateBy =
            Junction(UsersFavouriteMoviesEntity::class)
    )
    val favouriteFilms: List<MovieEntity>
)