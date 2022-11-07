package com.example.quotesapp.di

import com.example.quotesapp.util.ResourceProvider
import com.example.quotesapp.util.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ResourceModule {

    @Binds
    @Singleton
    fun bindResourceProvider(
        resourceProviderImpl: ResourceProviderImpl
    ): ResourceProvider
}