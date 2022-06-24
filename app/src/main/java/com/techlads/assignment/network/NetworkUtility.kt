package com.techlads.assignment.network

import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resumeWithException


/**
 * Created by Yasir on 3/24/2022.
 */
class NetworkUtility {

    companion object {

        suspend fun request(url: String): String {
            return suspendCancellableCoroutine { continuation ->
                try {
                    val reader: BufferedReader
                    val url = URL(url)

                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"

                        reader = BufferedReader(InputStreamReader(inputStream) as Reader)
                        val response = StringBuffer()
                        var inputLine = reader.readLine()

                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = reader.readLine()
                        }
                        reader.close()
                        if (continuation.isActive) {
                            continuation.resume(response.toString(),{ er ->
                                Log.d("NetworkUtility", er.message.toString())
                            })
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    if (continuation.isActive) {
                        continuation.resumeWithException(ex)
                    }
                }
            }
        }
    }
}

