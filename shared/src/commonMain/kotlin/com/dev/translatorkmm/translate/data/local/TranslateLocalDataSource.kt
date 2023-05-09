package com.dev.translatorkmm.translate.data.local

import com.dev.translatorkmm.core.domain.utils.CommonFlow
import com.dev.translatorkmm.core.domain.utils.toCommonFlow
import com.dev.translatorkmm.translate.data.history.dao.HistoryDao
import com.dev.translatorkmm.translate.data.history.mapper.toHistory
import com.dev.translatorkmm.translate.domain.models.History
import kotlinx.coroutines.flow.map

class TranslateLocalDataSource(
    private val historyDao: HistoryDao
) {
    fun getHistory(): CommonFlow<List<History>> {
        return historyDao
            .getHistory()
            .map { histories ->
                histories.map { history ->
                    history.toHistory()
                }
            }
            .toCommonFlow()
    }

    suspend fun insertHistory(
        history: History
    ) {
        historyDao
            .insertHistory(history)
    }
}