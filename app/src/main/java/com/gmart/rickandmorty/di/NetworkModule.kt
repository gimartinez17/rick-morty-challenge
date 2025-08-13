package com.gmart.rickandmorty.di

import com.gmart.data.source.remote.RickAndMortyApiService
import com.gmart.rickandmorty.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptorProvider(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().setLevel(level)
    }


    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        gson: Gson,
        okHttpClient: OkHttpClient,
        endpoint: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesProductsApi(retrofit: Retrofit): RickAndMortyApiService {
        return retrofit.create(RickAndMortyApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesEndpoint(): String = "https://rickandmortyapi.com/api/"
}
