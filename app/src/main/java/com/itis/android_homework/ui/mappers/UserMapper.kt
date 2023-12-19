package com.itis.android_homework.ui.mappers

import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.model.UserModel

object UserMapper {
    fun toModel(entity: UserEntity): UserModel {
        return UserModel(
            userId = entity.userId,
            name = entity.name,
            secondName = entity.secondName,
            phoneNumber = entity.phoneNumber,
            emailAddress = entity.emailAddress
            )
    }

    fun toModelList(entities: List<UserEntity>): List<UserModel> {
        return entities.map { toModel(it) }
    }
}