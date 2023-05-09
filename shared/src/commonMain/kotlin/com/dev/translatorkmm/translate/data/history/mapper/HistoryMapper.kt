package com.dev.translatorkmm.translate.data.history.mapper

import com.dev.translatorkmm.translate.domain.models.History
import database.HistoryEntity

fun HistoryEntity.toHistory(): History {
    return History(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText
    )
}