package com.dev.translatorkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dev.translatorkmm.android.core.presentation.navigation.TranslatorRoutes
import com.dev.translatorkmm.android.translate.presentation.AndroidTranslateViewModel
import com.dev.translatorkmm.android.translate.presentation.TranslateScreen
import com.dev.translatorkmm.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.dev.translatorkmm.android.voice_to_text.presentation.VoiceToTextScreen
import com.dev.translatorkmm.translate.presentation.event.TranslateEvent
import com.dev.translatorkmm.voice_to_text.presentation.VoiceToTextEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                TranslatorRoot()
            }
        }
    }
}

@Composable
fun TranslatorRoot() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TranslatorRoutes.Translate.name
    ) {
        composable(TranslatorRoutes.Translate.name) {
            val viewModel: AndroidTranslateViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            val voiceResult by it
                .savedStateHandle
                .getStateFlow<String?>("voiceResult", null)
                .collectAsState()

            LaunchedEffect(voiceResult) {
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                it.savedStateHandle["voiceResult"] = null
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when(event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                TranslatorRoutes.VoiceToText.name + "/${state.fromLanguage.language.langCode}"
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable(
            route = TranslatorRoutes.VoiceToText.name + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) { backstackEntry ->
            val languageCode = backstackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel: AndroidVoiceToTextViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            VoiceToTextScreen(
                state = state,
                languageCode = languageCode,
                onResult = { spokenText ->
                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            "voiceResult",
                            spokenText
                        )

                    navController.popBackStack()
                },
                onEvent = { event ->
                    when(event) {
                        VoiceToTextEvent.Close -> {
                            navController.popBackStack()
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}