package com.dev.translatorkmm.translate.data.history.dao

import com.dev.translatorkmm.translate.domain.models.History
import database.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryDao {
    fun getHistory(): Flow<List<HistoryEntity>>

    suspend fun insertHistory(
        history: History
    )
}