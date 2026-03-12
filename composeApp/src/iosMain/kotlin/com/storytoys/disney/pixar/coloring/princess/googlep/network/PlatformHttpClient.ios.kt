package com.storytoys.disney.pixar.coloring.princess.googlep.network

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL

@OptIn(ExperimentalForeignApi::class)
actual suspend fun platformHttpGet(url: String): Pair<Int, String> = withContext(Dispatchers.Default) {
    val nsUrl = NSURL.URLWithString(url)
        ?: throw IllegalArgumentException("Invalid URL: $url")
    val data = NSData.dataWithContentsOfURL(nsUrl)
        ?: throw RuntimeException("Network request failed for: $url")
    val body = data.bytes?.readBytes(data.length.toInt())?.decodeToString() ?: ""
    Pair(200, body)
}