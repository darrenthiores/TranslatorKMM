package com.dev.translatorkmm.translate.data.repository

import com.dev.translatorkmm.core.data.utils.ApiResponse
import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.core.domain.utils.CommonFlow
import com.dev.translatorkmm.core.domain.utils.Resource
import com.dev.translatorkmm.translate.data.local.TranslateLocalDataSource
import com.dev.translatorkmm.translate.data.remote.TranslateRemoteDataSource
import com.dev.translatorkmm.translate.domain.models.History
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import com.dev.translatorkmm.translate.domain.utils.TranslateError
import com.dev.translatorkmm.translate.domain.utils.TranslateException

class TranslateRepositoryImpl(
    private val remoteDataSource: TranslateRemoteDataSource,
    private val localDataSource: TranslateLocalDataSource
): TranslateRepository {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language,
    ): Resource<String> {
        val result = remoteDataSource.translate(
            fromLanguage = fromLanguage,
            fromText = fromText,
            toLanguage = toLanguage
        )

        return when(result) {
            is ApiResponse.Success -> {
                try {
                    localDataSource.insertHistory(
                        history = History(
                            id = null,
                            fromLanguageCode = fromLanguage.langCode,
                            fromText = fromText,
                            toLanguageCode = toLanguage.langCode,
                            toText = result.data
                        )
                    )

                    Resource.Success(data = result.data)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Resource.Error(
                        error = TranslateException(TranslateError.ClientError),
                        data = result.data
                    )
                }
            }
            is ApiResponse.Error -> Resource.Error(error = result.error)
            else -> Resource.Error(error = TranslateException(TranslateError.UnknownError))
        }
    }

    override fun getHistory(): CommonFlow<List<History>> {
        return localDataSource.getHistory()
    }
}