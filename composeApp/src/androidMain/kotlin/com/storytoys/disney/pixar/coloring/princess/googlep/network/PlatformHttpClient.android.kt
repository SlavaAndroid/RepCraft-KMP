package com.storytoys.disney.pixar.coloring.princess.googlep.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

actual suspend fun platformHttpGet(url: String): Pair<Int, String> = withContext(Dispatchers.IO) {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connectTimeout = 15_000
    connection.readTimeout = 15_000
    try {
        val statusCode = connection.responseCode
        val stream = if (statusCode in 200..299) connection.inputStream else connection.errorStream
        val body = stream?.bufferedReader()?.readText() ?: ""
        Pair(statusCode, body)
    } finally {
        connection.disconnect()
    }
}