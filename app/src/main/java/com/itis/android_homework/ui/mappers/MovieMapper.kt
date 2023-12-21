package com.itis.android_homework.ui.mappers

import com.itis.android_homework.db.entity.MovieEntity
import com.itis.android_homework.model.MovieModel

object MovieMapper {
    fun toModel(entity: MovieEntity): MovieModel {
        return MovieModel(
            movieId = entity.movieId,
            title = entity.title,
            year = entity.year,
            description = entity.description,
            imgUrl = entity.imgUrl

        )
    }
    fun toEntity(model: MovieModel): MovieEntity {
        return MovieEntity(
            movieId = model.movieId,
            title = model.title,
            year = model.year,
            description = model.description,
            imgUrl = model.imgUrl

        )
    }
    fun toModelList(entities: List<MovieEntity>): List<MovieModel> {
        return entities.map { toModel(it) }
    }
    fun toEntityList(models: List<MovieModel>): List<MovieEntity> {
        return models.map { toEntity(it) }
    }
}