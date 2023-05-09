package com.dev.translatorkmm.translate.domain.useCases

import com.dev.translatorkmm.core.domain.utils.CommonFlow
import com.dev.translatorkmm.translate.domain.models.History
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository

class GetHistory(
    private val repository: TranslateRepository
) {
    fun execute(): CommonFlow<List<History>> {
        return repository
            .getHistory()
    }
}