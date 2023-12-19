package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.db.entity.RatingEntity

@Dao
interface RatingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRating(filmRating: RatingEntity)

    @Query("SELECT rating FROM film_ratings WHERE user_id = :userId AND movie_id = :movieId")
    fun getRatingByUserAndMovieId(userId: String, movieId: String): Float?

    @Query("SELECT avg(rating) FROM film_ratings WHERE movie_id = :movieId")
    fun getAverageRatingById(movieId: String) : Float?
}