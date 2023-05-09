package com.dev.translatorkmm.translate.presentation.model

import com.dev.translatorkmm.core.presentation.model.UiLanguage

data class UiHistory(
    val id: Long,
    val fromLanguage: UiLanguage,
    val fromText: String,
    val toLanguage: UiLanguage,
    val toText: String
)
