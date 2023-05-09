package com.dev.translatorkmm.translate.domain.useCases

import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.core.domain.utils.Resource
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository

class Translate(
    private val repository: TranslateRepository
) {
    suspend fun execute(
        fromLanguage: Language,
        fromText:String,
        toLanguage: Language
    ): Resource<String> {
        return repository
            .translate(
                fromLanguage = fromLanguage,
                fromText = fromText,
                toLanguage = toLanguage
            )
    }
}