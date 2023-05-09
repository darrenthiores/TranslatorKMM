package com.dev.translatorkmm.voice_to_text.domain

import com.dev.translatorkmm.core.domain.utils.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>

    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}