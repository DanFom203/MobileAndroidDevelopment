package com.itis.android_homework.model

data class UserModel(
    val userId: String,
    val name: String,
    val secondName: String,
    val phoneNumber: String,
    val emailAddress: String?,
    val deletionTime: Long?
)
