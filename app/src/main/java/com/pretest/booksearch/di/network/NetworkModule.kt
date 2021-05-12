package com.pretest.booksearch.di.network

import com.pretest.network.buildHttpClient
import dagger.Module
import dagger.Provides
import io.ktor.client.*

@Module
class NetworkModule {

    @Provides
    fun provideHttpClient(): HttpClient {
        return buildHttpClient()
    }
}