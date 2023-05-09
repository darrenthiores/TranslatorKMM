package com.dev.translatorkmm.translate.presentation.mapper

import com.dev.translatorkmm.core.presentation.model.UiLanguage
import com.dev.translatorkmm.translate.domain.models.History
import com.dev.translatorkmm.translate.presentation.model.UiHistory

fun History.toUi(): UiHistory? {
    return UiHistory(
        id = id ?: return null,
        fromLanguage = UiLanguage.byCode(fromLanguageCode),
        fromText = fromText,
        toLanguage = UiLanguage.byCode(toLanguageCode),
        toText = toText
    )
}