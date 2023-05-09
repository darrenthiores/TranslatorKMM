package com.dev.translatorkmm.translate.data.translate.service

import com.dev.translatorkmm.translate.data.translate.dto.request.TranslateRequest
import com.dev.translatorkmm.translate.data.translate.dto.response.TranslateResponse
import com.dev.translatorkmm.translate.domain.utils.TranslateError
import com.dev.translatorkmm.translate.domain.utils.TranslateException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class KtorTranslateApi(
    private val client: HttpClient
): TranslateApi {

    override suspend fun translate(
        request: TranslateRequest
    ): TranslateResponse {
        val result = ktorTryCatch {
            client.post {
                url(TranslateApi.TRANSLATE_URL)
                contentType(ContentType.Application.Json)

                setBody(request)
            }
        }

        checkResponseStatus(result)

        return result.body()
    }

    private fun checkResponseStatus(
        result: HttpResponse
    ) {
        when(result.status.value) {
            in 200..299 -> Unit
            500 -> throw TranslateException(TranslateError.ServerError)
            in 400..499 -> throw TranslateException(TranslateError.ClientError)
            else -> throw TranslateException(TranslateError.UnknownError)
        }
    }

    private suspend fun <T> ktorTryCatch(
        httpCall: suspend () -> T
    ): T {
        return try {
            httpCall()
        } catch (e: RedirectResponseException) {
            throw TranslateException(TranslateError.ServiceUnavailable)
        } catch (e: ClientRequestException) {
            throw TranslateException(TranslateError.ClientError)
        } catch (e: ServerResponseException) {
            throw TranslateException(TranslateError.ServerError)
        } catch (e: IOException) {
            throw TranslateException(TranslateError.ServiceUnavailable)
        } catch (e: Exception) {
            throw TranslateException(TranslateError.UnknownError)
        }
    }
}