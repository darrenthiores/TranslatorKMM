package com.dev.translatorkmm.core.domain.utils

import kotlinx.coroutines.flow.Flow

actual class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
): Flow<T> by flow