package com.dev.translatorkmm.di

import com.dev.translatorkmm.translate.data.FakeTranslateRepository
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import com.dev.translatorkmm.translate.domain.useCases.GetHistory
import com.dev.translatorkmm.translate.domain.useCases.Translate
import com.dev.translatorkmm.voice_to_text.data.FakeVoiceToTextParser
import com.dev.translatorkmm.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeRepository(): TranslateRepository {
        return FakeTranslateRepository()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        repository: TranslateRepository
    ): Translate {
        return Translate(
            repository = repository
        )
    }

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(
        repository: TranslateRepository
    ): GetHistory {
        return GetHistory(
            repository = repository
        )
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}