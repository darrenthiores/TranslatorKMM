package com.dev.translatorkmm.android.translate.di

import android.app.Application
import com.dev.translatorkmm.core.domain.dispatchers.DispatchersProvider
import com.dev.translatorkmm.database.TranslateDatabase
import com.dev.translatorkmm.translate.data.history.dao.HistoryDao
import com.dev.translatorkmm.translate.data.history.dao.SqlDelightHistoryDao
import com.dev.translatorkmm.translate.data.local.TranslateLocalDataSource
import com.dev.translatorkmm.translate.data.local.driver.DatabaseDriverFactory
import com.dev.translatorkmm.translate.data.remote.TranslateRemoteDataSource
import com.dev.translatorkmm.translate.data.remote.client.HttpClientFactory
import com.dev.translatorkmm.translate.data.translate.service.KtorTranslateApi
import com.dev.translatorkmm.translate.data.translate.service.TranslateApi
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslateDatabaseModule {

    @Provides
    @Singleton
    fun provideDriver(
        app: Application
    ): SqlDriver {
        return DatabaseDriverFactory(app)
            .create()
    }

    @Provides
    @Singleton
    fun provideTranslateDatabase(
        driver: SqlDriver
    ): TranslateDatabase {
        return TranslateDatabase(driver)
    }

    @Provides
    @Singleton
    fun provideHistoryDao(
        database: TranslateDatabase
    ): HistoryDao {
        return SqlDelightHistoryDao(database)
    }

    @Provides
    @Singleton
    fun provideTranslateLocalDataSource(
        historyDao: HistoryDao
    ): TranslateLocalDataSource {
        return TranslateLocalDataSource(
            historyDao = historyDao
        )
    }
}