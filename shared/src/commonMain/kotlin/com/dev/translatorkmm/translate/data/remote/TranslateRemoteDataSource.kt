package com.dev.translatorkmm.translate.data.remote

import com.dev.translatorkmm.core.data.utils.ApiResponse
import com.dev.translatorkmm.core.domain.dispatchers.DispatchersProvider
import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.translate.data.translate.dto.request.TranslateRequest
import com.dev.translatorkmm.translate.data.translate.service.TranslateApi
import com.dev.translatorkmm.translate.domain.utils.TranslateError
import com.dev.translatorkmm.translate.domain.utils.TranslateException
import kotlinx.coroutines.withContext

class TranslateRemoteDataSource(
    private val translateApi: TranslateApi,
    private val dispatchers: DispatchersProvider
) {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): ApiResponse<String> {
        return withContext(dispatchers.io) {
            try {
                val result = translateApi.translate(
                    TranslateRequest(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )

                ApiResponse.Success(result.translatedText)
            } catch (e: Exception) {
                e.printStackTrace()
                ApiResponse.Error(TranslateException(TranslateError.ClientError))
            }
        }
    }
}