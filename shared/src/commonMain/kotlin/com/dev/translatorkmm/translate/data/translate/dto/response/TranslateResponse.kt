package com.dev.translatorkmm.translate.data.translate.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val translatedText: String
)
