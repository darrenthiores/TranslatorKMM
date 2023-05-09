package com.dev.translatorkmm.translate.domain.models

data class History(
    val id: Long?,
    val fromLanguageCode: String,
    val fromText: String,
    val toLanguageCode: String,
    val toText: String
)
