package com.itis.android_homework.ui.mappers

import com.itis.android_homework.db.entity.MovieEntity
import com.itis.android_homework.model.MovieModel

object MovieMapper {
    fun toModel(entity: MovieEntity): MovieModel {
        return MovieModel(
            id = entity.id,
            title = entity.title,
            year = entity.year,
            description = entity.description,
            isLiked = entity.isLiked,

        // Добавьте другие поля, если необходимо
        )
    }
    fun toEntity(model: MovieModel): MovieEntity {
        return MovieEntity(
            id = model.id,
            title = model.title,
            year = model.year,
            description = model.description,
            isLiked = model.isLiked
        // Добавьте другие поля, если необходимо
        )
    }
    fun toModelList(entities: List<MovieEntity>): List<MovieModel> {
        return entities.map { toModel(it) }
    }
    fun toEntityList(models: List<MovieModel>): List<MovieEntity> {
        return models.map { toEntity(it) }
    }
}