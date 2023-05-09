package com.dev.translatorkmm.voice_to_text.presentation

import com.dev.translatorkmm.core.domain.utils.toCommonStateFlow
import com.dev.translatorkmm.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val parser: VoiceToTextParser,
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())
    val state = _state.combine(parser.state) { state, voiceResult ->
        state.copy(
            spokenText = voiceResult.result,
            recordError = if(state.canRecord) {
                voiceResult.error
            } else {
                "Can't record without permission"
            },
            displayState = when {
                !state.canRecord || voiceResult.error != null -> DisplayState.Error
                voiceResult.result.isNotBlank() && !voiceResult.isSpeaking -> DisplayState.DisplayingResult
                voiceResult.isSpeaking -> DisplayState.Speaking
                else -> DisplayState.WaitingToTalk
            }
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            VoiceToTextState()
        )
        .toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.Speaking) {
                    _state.update {
                        it.copy(
                            powerRatios = it.powerRatios + parser.state.value.powerRatio
                        )
                    }
                }

                delay(50L)
            }
        }
    }

    fun onEvent(event: VoiceToTextEvent) {
        when(event) {
            is VoiceToTextEvent.PermissionResult -> {
                _state.update {
                    it.copy(
                        canRecord = event.isGranted
                    )
                }
            }
            VoiceToTextEvent.Reset -> {
                parser.reset()
                _state.update {
                    VoiceToTextState()
                }
            }
            is VoiceToTextEvent.ToggleRecording -> toggleRecording(event.languageCode)
            else -> Unit
        }
    }

    private fun toggleRecording(languageCode: String) {
        _state.update { it.copy(powerRatios = emptyList()) }

        parser.cancel()

        if(state.value.displayState == DisplayState.Speaking) {
            parser.stopListening()
        } else {
            parser.startListening(languageCode)
        }
    }
}