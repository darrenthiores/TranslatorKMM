package com.dev.translatorkmm.android.di

import com.dev.translatorkmm.core.domain.dispatchers.DispatchersProvider
import com.dev.translatorkmm.core.domain.dispatchers.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider {
        return StandardDispatchers()
    }
}