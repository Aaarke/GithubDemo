package com.example.github.di

import com.example.github.network.ApiInterface
import com.example.github.utility.Keys.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
// Safe here as we are dealing with a Dagger 2 module
object NetworkModule {
    /**
     * Provides the github service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient().build())
            .build()
    }

    private fun httpClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 2
        httpClient.dispatcher(dispatcher)
        httpClient.addInterceptor(logging)
        httpClient.readTimeout(60.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(60.toLong(), TimeUnit.SECONDS)
        return httpClient
    }
}