package com.itis.android_homework.data.remote.interceptors

import com.itis.android_homework.base.Constants
import com.itis.android_homework.base.Keys
import okhttp3.Interceptor
import okhttp3.Response

class MetricInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter(Keys.UNITS_KEY, Constants.METRIC)
            .build()

        val requestBuilder = chain.request().newBuilder().url(newUrl)

        return chain.proceed(requestBuilder.build())
    }
}