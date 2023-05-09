package com.dev.translatorkmm.translate.data.translate.service

import com.dev.translatorkmm.translate.data.translate.dto.request.TranslateRequest
import com.dev.translatorkmm.translate.data.translate.dto.response.TranslateResponse

interface TranslateApi {

    suspend fun translate(request: TranslateRequest): TranslateResponse

    companion object {
        private const val BASE_URL = "https://translate.pl-coding.com"
        const val TRANSLATE_URL = "$BASE_URL/translate"
    }
}