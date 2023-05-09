package com.dev.translatorkmm.android.translate.presentation

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.dev.translatorkmm.android.R
import com.dev.translatorkmm.android.translate.presentation.components.*
import com.dev.translatorkmm.translate.domain.utils.TranslateError
import com.dev.translatorkmm.translate.presentation.event.TranslateEvent
import com.dev.translatorkmm.translate.presentation.state.TranslateState
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        val message = when(state.error) {
            TranslateError.ServiceUnavailable -> context.getString(R.string.error_service_unavailable)
            TranslateError.ClientError -> context.getString(R.string.client_error)
            TranslateError.ServerError -> context.getString(R.string.server_error)
            TranslateError.UnknownError -> context.getString(R.string.unknown_error)
            null -> null
        }

        message?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()

            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TranslateEvent.RecordAudio)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(75.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                    contentDescription = stringResource(id = R.string.record_audio)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(paddingValues = PaddingValues(16.dp)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = {
                            onEvent(TranslateEvent.OpenFromLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguages = {
                            onEvent(TranslateEvent.ChooseFromLanguage(it))
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    )

                    SwapLanguageButton(
                        onClick = { onEvent(TranslateEvent.SwapLanguages) },
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                    LanguageDropDown(
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = {
                            onEvent(TranslateEvent.OpenToLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguages = {
                            onEvent(TranslateEvent.ChooseToLanguage(it))
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }
            }

            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val tts = rememberTextToSpeech()

                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = {
                        onEvent(TranslateEvent.ChangeTranslationText(it))
                    },
                    onCopyClick = { text ->
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(text)
                            }
                        )

                        Toast.makeText(
                            context,
                            context.getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onCloseClick = {
                        onEvent(TranslateEvent.CloseTranslation)
                    },
                    onSpeakerClick = {
                        tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        tts.speak(
                            state.toText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslateEvent.EditTranslation)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                if(state.history.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.h2
                    )
                }
            }

            items(
                items = state.history,
                key = { history -> history.id }
            ) { history ->
                TranslateHistoryItem(
                    item = history,
                    onClick = {
                        onEvent(TranslateEvent.SelectHistory(history))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}