package com.dev.translatorkmm.android.translate.di

import com.dev.translatorkmm.translate.data.local.TranslateLocalDataSource
import com.dev.translatorkmm.translate.data.remote.TranslateRemoteDataSource
import com.dev.translatorkmm.translate.data.repository.TranslateRepositoryImpl
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslateRepositoryModule {

    @Provides
    @Singleton
    fun provideTranslateRepository(
        remoteDataSource: TranslateRemoteDataSource,
        localDataSource: TranslateLocalDataSource
    ): TranslateRepository {
        return TranslateRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}