package com.itis.android_homework.di

import android.annotation.SuppressLint
import android.content.Context
import com.itis.android_homework.BuildConfig
import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.mapper.WeatherDomainModelMapper
import com.itis.android_homework.data.remote.interceptors.AppIdInterceptor
import com.itis.android_homework.data.remote.interceptors.MetricInterceptor
import com.itis.android_homework.data.remote.OpenWeatherApi
import com.itis.android_homework.data.repository.WeatherRepositoryImpl
import com.itis.android_homework.domain.mapper.WeatherUiModelMapper
import com.itis.android_homework.domain.usecase.GetWeatherDataUseCase
import com.itis.android_homework.utils.ResManager
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceLocator {

    private lateinit var weatherApi: OpenWeatherApi

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var weatherRepository: WeatherRepositoryImpl

    lateinit var getWeatherUseCase: GetWeatherDataUseCase
        private set

    lateinit var exceptionHandlerDelegate: ExceptionHandlerDelegate
        private set

    private val dispatcher = Dispatchers.IO

    private val weatherDomainModelMapper = WeatherDomainModelMapper()

    private val weatherUiModelMapper = WeatherUiModelMapper()

    fun initDataDependencies(ctx: Context) {

        buildOkHttpClient()
        initWeatherApi()

        weatherRepository = WeatherRepositoryImpl(
            api = weatherApi,
            domainModelMapper = weatherDomainModelMapper,
            resManager = ResManager(ctx = ctx),
        )

        exceptionHandlerDelegate = ExceptionHandlerDelegate(resManager = ResManager(ctx))
    }

    fun initDomainDependencies() {
        getWeatherUseCase = GetWeatherDataUseCase(
            dispatcher = dispatcher,
            repository = weatherRepository,
            mapper = weatherUiModelMapper,
        )
    }


    private fun buildOkHttpClient() {
        val clientBuilder = createUnsafeClient().addInterceptor(AppIdInterceptor())
            .addInterceptor(MetricInterceptor())

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        okHttpClient = clientBuilder.build()
    }

    private fun createUnsafeClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

    private fun initWeatherApi() {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = builder.create(OpenWeatherApi::class.java)
    }
}