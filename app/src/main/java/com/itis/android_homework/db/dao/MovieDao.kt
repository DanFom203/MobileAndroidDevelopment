package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.db.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addMovie(movie: MovieEntity)

    @Query("SELECT * FROM films ORDER BY year DESC")
    fun getAllMoviesOrderByDesc(): List<MovieEntity>

    @Query("SELECT * FROM films ORDER BY year ASC")
    fun getAllMoviesOrderByAsc(): List<MovieEntity>

    @Query("SELECT * FROM films JOIN film_ratings ON films.movie_id = film_ratings.movie_id GROUP BY films.movie_id ORDER BY avg(film_ratings.rating) DESC")
    fun getAllMoviesOrderByRatingDesc(): List<MovieEntity>

    @Query("SELECT * FROM films JOIN film_ratings ON films.movie_id = film_ratings.movie_id GROUP BY films.movie_id ORDER BY avg(film_ratings.rating) ASC")
    fun getAllMoviesOrderByRatingAsc(): List<MovieEntity>

    @Query("SELECT * FROM films WHERE movie_id IN (:ids)")
    fun getMoviesByIds(ids: List<String>): List<MovieEntity>

    @Query("DELETE FROM films WHERE movie_id = :id")
    fun deleteMovie(id: String)

    @Query("SELECT * FROM films WHERE title = :title AND year = :year")
    suspend fun getMovieByTitleAndYear(title: String, year: Int) : MovieEntity?

}