package com.dev.translatorkmm.translate.presentation.state

import com.dev.translatorkmm.core.presentation.model.UiLanguage
import com.dev.translatorkmm.translate.domain.utils.TranslateError
import com.dev.translatorkmm.translate.presentation.model.UiHistory

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("de"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistory> = emptyList()
)
