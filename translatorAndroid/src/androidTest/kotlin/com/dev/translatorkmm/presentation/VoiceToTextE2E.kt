package com.dev.translatorkmm.presentation

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.dev.translatorkmm.android.MainActivity
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import com.dev.translatorkmm.android.R
import com.dev.translatorkmm.android.di.AppModule
import com.dev.translatorkmm.android.translate.di.TranslateDatabaseModule
import com.dev.translatorkmm.android.translate.di.TranslateNetworkModule
import com.dev.translatorkmm.android.translate.di.TranslateRepositoryModule
import com.dev.translatorkmm.android.translate.di.TranslateUseCasesModule
import com.dev.translatorkmm.android.voice_to_text.di.VoiceToTextModule
import com.dev.translatorkmm.translate.data.FakeTranslateRepository
import com.dev.translatorkmm.translate.domain.repository.TranslateRepository
import com.dev.translatorkmm.voice_to_text.data.FakeVoiceToTextParser
import com.dev.translatorkmm.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(
    AppModule::class,
    TranslateDatabaseModule::class,
    TranslateNetworkModule::class,
    TranslateRepositoryModule::class,
    TranslateUseCasesModule::class,
    VoiceToTextModule::class
)
class VoiceToTextE2E {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeRepository: TranslateRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate(): Unit = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val repository = fakeRepository as FakeTranslateRepository

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onAllNodesWithText(parser.voiceResult)
            .onFirst()
            .assertIsDisplayed()

        composeRule
            .onAllNodesWithText(repository.translatedText)
            .onFirst()
            .assertIsDisplayed()
    }
}