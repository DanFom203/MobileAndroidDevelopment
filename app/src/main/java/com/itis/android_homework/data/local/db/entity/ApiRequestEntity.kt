package com.itis.android_homework.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "api_request")
data class ApiRequestEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "method")
    val method: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "headers")
    val headers: String,
    @ColumnInfo(name = "request_body")
    val requestBody: String,
    @ColumnInfo(name = "response_code")
    val responseCode: Int,
    @ColumnInfo(name = "response_body")
    val responseBody: String
)