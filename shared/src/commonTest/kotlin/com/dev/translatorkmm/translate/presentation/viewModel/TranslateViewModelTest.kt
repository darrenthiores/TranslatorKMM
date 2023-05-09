package com.dev.translatorkmm.translate.presentation.viewModel

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.dev.translatorkmm.core.domain.language.Language
import com.dev.translatorkmm.core.presentation.model.UiLanguage
import com.dev.translatorkmm.translate.data.repository.FakeTranslateRepository
import com.dev.translatorkmm.translate.domain.models.History
import com.dev.translatorkmm.translate.domain.useCases.GetHistory
import com.dev.translatorkmm.translate.domain.useCases.Translate
import com.dev.translatorkmm.translate.presentation.event.TranslateEvent
import com.dev.translatorkmm.translate.presentation.model.UiHistory
import com.dev.translatorkmm.translate.presentation.state.TranslateState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var repository: FakeTranslateRepository

    @BeforeTest
    fun setUp() {
        repository = FakeTranslateRepository()
        val translate = Translate(
            repository = repository
        )
        val getHistory = GetHistory(
            repository = repository
        )
        viewModel = TranslateViewModel(
            translate = translate,
            getHistory = getHistory,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `state and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = History(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )

            repository.translatedText = item.toText

            repository.translate(
                fromLanguage = Language.byCode(item.fromLanguageCode),
                fromText = item.fromText,
                toLanguage = Language.byCode(item.toLanguageCode)
            )

            val state = awaitItem()
            val expected = UiHistory(
                id = 0,
                fromText = item.fromText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toText = item.toText,
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(repository.translatedText)
        }
    }
}