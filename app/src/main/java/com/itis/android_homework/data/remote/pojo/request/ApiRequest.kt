package com.itis.android_homework.data.remote.pojo.request

data class ApiRequest(
    val method: String,
    val url: String,
    val headers: String,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String
)