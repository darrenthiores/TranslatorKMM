package com.dev.translatorkmm.core.domain.utils

import kotlinx.coroutines.flow.MutableStateFlow

class IosMutableStateFlow<T>(
    initialValue: T
): CommonMutableStateFlow<T>(
    MutableStateFlow(initialValue)
)