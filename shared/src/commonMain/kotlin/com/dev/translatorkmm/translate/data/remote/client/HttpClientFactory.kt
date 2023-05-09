package com.dev.translatorkmm.translate.data.remote.client

import io.ktor.client.*

expect class HttpClientFactory {
    fun create(): HttpClient
}