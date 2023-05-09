package com.dev.translatorkmm.translate.domain.repository

import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.core.domain.utils.CommonFlow
import com.dev.translatorkmm.core.domain.utils.Resource
import com.dev.translatorkmm.translate.domain.models.History

interface TranslateRepository {
    suspend fun translate(
        fromLanguage: Language,
        fromText:String,
        toLanguage: Language
    ): Resource<String>

    fun getHistory(): CommonFlow<List<History>>
}