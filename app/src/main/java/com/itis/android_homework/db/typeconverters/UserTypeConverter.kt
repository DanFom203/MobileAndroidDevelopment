package com.itis.android_homework.db.typeconverters

import androidx.room.TypeConverter
import com.itis.android_homework.model.UserModel

class UserTypeConverter {

    @TypeConverter
    fun fromUserToDbEntity(userData: UserModel): String {
        val resultStr = StringBuilder().apply {
            if (userData.emailAddress?.isNotEmpty() == true) {
                this.append(userData.emailAddress.toString())
                this.append(';')
            }
            this.append(userData.id)
            this.append(';')
            this.append(userData.name)
            this.append(';')
            this.append(userData.secondName)
        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToUserModel(input: String): UserModel {
        var index: Int = input.indexOf(';')
        val email: String? = if (input.contains('@')) {
            input.substringBefore(';')
        } else {
            null
        }
        return UserModel(
            id = "1",
            name = "2",
            secondName = "3",
            phoneNumber = "",
            emailAddress = email,
        )
    }
}