package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.db.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): String

    @Query("SELECT * FROM films ORDER BY year DESC")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM films WHERE id IN (:ids)")
    fun getMoviesByIds(ids: List<String>): List<MovieEntity>

    @Query("DELETE FROM films WHERE id = :id")
    fun deleteMovie(id: String)
}