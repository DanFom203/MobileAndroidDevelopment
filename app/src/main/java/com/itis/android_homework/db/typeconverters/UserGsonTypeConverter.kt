package com.itis.android_homework.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.itis.android_homework.model.UserModel

class UserGsonTypeConverter {

    @TypeConverter
    fun fromUserToString(user: UserModel): String {
        val gson = Gson()
        return try {
            gson.toJson(user)
        } catch (ex: Exception) {
            ""
        }
    }

    @TypeConverter
    fun fromStringToUser(input: String): UserModel? {
        return try {
            val gson = Gson()
            gson.fromJson(input, UserModel::class.java)
            null
        } catch (ex: Exception) {
            null
        }
    }
}