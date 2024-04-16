package com.itis.android_homework.data.remote.interceptors

import com.itis.android_homework.data.remote.pojo.request.ApiRequest
import okhttp3.Interceptor
import okhttp3.Response

class CustomHttpLoggingInterceptor : Interceptor {

    private val maxRequests = 50
    private val requestList = mutableListOf<ApiRequest>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val apiRequest = ApiRequest(
            request.method,
            request.url.toString(),
            request.headers.toString(),
            request.body?.toString() ?: "",
            response.code,
            response.body?.string() ?: ""
        )

        synchronized(requestList) {
            if (requestList.size >= maxRequests) {
                requestList.removeAt(0) // Remove oldest request if max limit reached
            }
            requestList.add(apiRequest)
        }

        return response
    }

    fun getRecentRequests(): List<ApiRequest> {
        synchronized(requestList) {
            return requestList.toList()
        }
    }
}