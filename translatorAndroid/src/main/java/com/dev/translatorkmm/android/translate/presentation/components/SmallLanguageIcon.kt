package com.dev.translatorkmm.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dev.translatorkmm.core.presentation.model.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = language.drawRes,
        contentDescription = language.language.langName,
        modifier = modifier
            .size(25.dp)
    )
}