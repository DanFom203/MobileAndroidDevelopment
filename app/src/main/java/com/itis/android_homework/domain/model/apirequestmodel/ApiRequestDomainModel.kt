package com.itis.android_homework.domain.model.apirequestmodel

data class ApiRequestDomainModel (
    val id: String,
    val method: String,
    val url: String,
    val headers: String,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String
)

fun ApiRequestDomainModel.isEmptyResponse(): Boolean {
    return id == "" && method == "" && url == "" && headers == "" && requestBody == "" && responseBody == ""
}