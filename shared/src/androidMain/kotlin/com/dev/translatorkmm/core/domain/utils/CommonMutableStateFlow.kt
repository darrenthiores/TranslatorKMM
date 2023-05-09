package com.dev.translatorkmm.core.domain.utils

import kotlinx.coroutines.flow.MutableStateFlow

actual class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
): MutableStateFlow<T> by flow