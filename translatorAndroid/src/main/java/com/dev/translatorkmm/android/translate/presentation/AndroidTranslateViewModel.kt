package com.dev.translatorkmm.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.translatorkmm.translate.domain.useCases.GetHistory
import com.dev.translatorkmm.translate.domain.useCases.Translate
import com.dev.translatorkmm.translate.presentation.event.TranslateEvent
import com.dev.translatorkmm.translate.presentation.viewModel.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: Translate,
    private val getHistory: GetHistory
): ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translate = translate,
            getHistory = getHistory,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}