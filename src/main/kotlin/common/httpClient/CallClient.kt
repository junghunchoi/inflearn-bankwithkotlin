package com.lecture.common.httpClient

import com.lecture.common.exception.CustomException
import com.lecture.common.exception.ErrorCode
import okhttp3.OkHttpClient
import okhttp3.*
import org.springframework.stereotype.Component

@Component
class CallClient(
    private val httpClient : OkHttpClient
) {
    fun GET(uri: String, header : Map<String, String> = emptyMap()): String {
        val request = okhttp3.Request.Builder()
            .url(uri)
            .apply {
                header.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }
            .build()
        return resultHandler(httpClient.newCall(request).execute())
    }

    fun POST(uri: String,  header: Map<String, String> = emptyMap(), body: RequestBody): String {
        val request = okhttp3.Request.Builder()
            .url(uri)
            .post(body)
            .apply {
                header.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }
            .build()

        return resultHandler(httpClient.newCall(request).execute())
    }

    private fun resultHandler(response: Response): String {
        response.use {
            if (!it.isSuccessful) {
                val msg = "http call failed with code: ${it.code}"
                throw CustomException(ErrorCode.FAILED_TO_CALL_CLIENT)
            }
            return it.body?.string() ?: throw CustomException(ErrorCode.CALL_RESULT_BODY_NULL)
        }
    }
}