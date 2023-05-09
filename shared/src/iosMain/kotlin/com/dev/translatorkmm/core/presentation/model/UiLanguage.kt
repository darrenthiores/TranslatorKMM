package com.dev.translatorkmm.core.presentation.model

import com.dev.translatorkmm.core.domain.language.Language

actual class UiLanguage(
    actual val language: Language,
    val imageName: String
) {
    actual companion object {
        actual fun byCode(code: String): UiLanguage {
            return allLanguages.find { it.language.langCode == code }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                    language = language,
                    imageName = language.langName.lowercase()
                )
            }.sortedBy { it.language.langName }
    }
}