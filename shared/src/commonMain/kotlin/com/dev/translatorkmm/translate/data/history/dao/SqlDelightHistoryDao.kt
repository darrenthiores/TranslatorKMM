package com.dev.translatorkmm.translate.data.history.dao

import com.dev.translatorkmm.database.TranslateDatabase
import com.dev.translatorkmm.translate.domain.models.History
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.HistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class SqlDelightHistoryDao(
    db: TranslateDatabase
): HistoryDao {
    private val queries = db.translateQueries

    override fun getHistory(): Flow<List<HistoryEntity>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
    }

    override suspend fun insertHistory(history: History) {
        queries.insertHistory(
            id = history.id,
            fromLanguageCode = history.fromLanguageCode,
            fromText = history.fromText,
            toLanguageCode = history.toLanguageCode,
            toText = history.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}