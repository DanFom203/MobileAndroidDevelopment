package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.db.entity.UserWithFavouriteMovies
import com.itis.android_homework.db.entity.UsersFavouriteMoviesEntity

@Dao
interface UsersFavoriteMoviesDao {
    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserWithFavoriteFilms(userId: String): UserWithFavouriteMovies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(usersFavouriteMoviesEntity: UsersFavouriteMoviesEntity)

    @Query("DELETE FROM users_favourite_films WHERE movie_id = :movieId")
    fun removeFromFavorites(movieId: String)

    @Query("SELECT * FROM users_favourite_films WHERE user_id = :userId AND movie_id = :movieId")
    fun isFilmFavoriteForUser(userId: Int, movieId: Int): UsersFavouriteMoviesEntity?

    @Query("SELECT movie_id FROM users_favourite_films WHERE user_id = :userId")
    fun getFavoriteFilmIdsForUser(userId: String): List<String>
}