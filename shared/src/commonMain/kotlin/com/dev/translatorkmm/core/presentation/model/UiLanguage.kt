package com.dev.translatorkmm.core.presentation.model

import com.dev.translatorkmm.core.domain.language.Language

expect class UiLanguage {
    val language: Language

    companion object {
        fun byCode(code: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}