package com.dev.translatorkmm.android.translate.di

import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import com.dev.translatorkmm.translate.domain.useCases.GetHistory
import com.dev.translatorkmm.translate.domain.useCases.Translate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TranslateUseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideTranslateUseCase(
        repository: TranslateRepository
    ): Translate {
        return Translate(
            repository = repository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetHistoryUseCase(
        repository: TranslateRepository
    ): GetHistory {
        return GetHistory(
            repository = repository
        )
    }
}