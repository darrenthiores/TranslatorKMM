package com.dev.translatorkmm.android.translate.di

import com.dev.translatorkmm.core.domain.dispatchers.DispatchersProvider
import com.dev.translatorkmm.translate.data.remote.TranslateRemoteDataSource
import com.dev.translatorkmm.translate.data.remote.client.HttpClientFactory
import com.dev.translatorkmm.translate.data.translate.service.KtorTranslateApi
import com.dev.translatorkmm.translate.data.translate.service.TranslateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslateNetworkModule {

    @Provides
    @Singleton
    fun provideClient(): HttpClient {
        return HttpClientFactory()
            .create()
    }

    @Provides
    @Singleton
    fun provideTranslateApi(
        client: HttpClient
    ): TranslateApi {
        return KtorTranslateApi(client = client)
    }

    @Provides
    @Singleton
    fun provideTranslateRemoteDataSource(
        translateApi: TranslateApi,
        dispatchers: DispatchersProvider
    ): TranslateRemoteDataSource {
        return TranslateRemoteDataSource(
            translateApi = translateApi,
            dispatchers = dispatchers
        )
    }
}