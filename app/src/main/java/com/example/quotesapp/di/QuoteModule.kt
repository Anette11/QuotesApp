package com.example.quotesapp.di

import android.content.Context
import androidx.room.Room
import com.example.quotesapp.BuildConfig
import com.example.quotesapp.data.local.QuoteDatabase
import com.example.quotesapp.data.remote.QuoteApi
import com.example.quotesapp.data.remote.util.RemoteConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuoteModule {

    @Provides
    @Singleton
    fun provideInterceptor(
    ): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteConstants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideQuoteApi(
        retrofit: Retrofit
    ): QuoteApi = retrofit.create()

    @Provides
    @Singleton
    fun provideQuoteDatabase(
        @ApplicationContext context: Context
    ): QuoteDatabase =
        Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            QuoteDatabase.name
        ).build()

    @Provides
    @Singleton
    fun provideQuoteDao(
        quoteDatabase: QuoteDatabase
    ) = quoteDatabase.quoteDao

    @Provides
    @Singleton
    fun provideQuoteRemoteKeysDao(
        quoteDatabase: QuoteDatabase
    ) = quoteDatabase.quoteRemoteKeysDao
}