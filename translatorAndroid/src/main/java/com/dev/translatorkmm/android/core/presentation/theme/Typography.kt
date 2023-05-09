package com.dev.translatorkmm.android.core.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dev.translatorkmm.android.R

val SfProText = FontFamily(
    Font(
        resId = R.font.sf_pro_text_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.sf_pro_text_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.sf_pro_text_bold,
        weight = FontWeight.Bold
    )
)

val typography = Typography(
    h1 = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)