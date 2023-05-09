package com.dev.translatorkmm.translate.data

import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.core.domain.utils.CommonFlow
import com.dev.translatorkmm.core.domain.utils.Resource
import com.dev.translatorkmm.core.domain.utils.toCommonFlow
import com.dev.translatorkmm.translate.domain.models.History
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTranslateRepository: TranslateRepository {
    private val _data = MutableStateFlow<List<History>>(emptyList())
    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language,
    ): Resource<String> {
        _data.value += History(
            id = 0,
            fromLanguageCode = fromLanguage.langCode,
            fromText = fromText,
            toLanguageCode = toLanguage.langCode,
            toText = translatedText
        )

        return Resource.Success(translatedText)
    }

    override fun getHistory(): CommonFlow<List<History>> {
        return _data.toCommonFlow()
    }
}