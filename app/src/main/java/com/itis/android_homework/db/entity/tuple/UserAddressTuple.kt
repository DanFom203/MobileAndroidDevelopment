package com.itis.android_homework.db.entity.tuple

import androidx.room.ColumnInfo

class UserAddressTuple(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "email")
    val emailAddress: String,
)