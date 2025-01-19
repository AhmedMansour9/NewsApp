package com.eaapps.core.base.di

import android.util.Log
import com.eaapps.core.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e("retrofit", message)
        }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
}