package com.dev.translatorkmm.translate.data.translate.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateRequest(
    @SerialName("q") val textToTranslate: String,
    @SerialName("source") val sourceLanguageCode: String,
    @SerialName("target") val targetLanguageCode: String
)
