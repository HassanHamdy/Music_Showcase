package com.hassanhamdy.musicshowcase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


class NetworkBase {
    private val BASE_URL: String = "http://staging-gateway.mondiamedia.com/"

    fun getMusics(query: String, token: String): Future<Pair<Int, String>> {
        Log.d("HASSAN", "GET MUSIC CALLED")
        val reqParam = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8")
        return Executors.newSingleThreadExecutor().submit(Callable<Pair<Int, String>> {
            sendRequest(
                endPoint = "v2/api/sayt/flat",
                reqParam = reqParam,
                requestMethodType = NetworkRequestTypes.GET,
                accessToken = token
            )
        })
    }

    fun getAccessToken(): Future<Pair<Int, String>> {
        Log.d("HASSAN", "ACCESS TOKEN CALLED")
        return Executors.newSingleThreadExecutor().submit(Callable<Pair<Int, String>> {
            sendRequest(
                endPoint = "v0/api/gateway/token/client",
                requestMethodType = NetworkRequestTypes.POST
            )
        })
    }


    fun getNetworkImage(url: String): Future<Bitmap> {
        return Executors.newSingleThreadExecutor().submit(Callable<Bitmap> {
            getBitmapFromURL("http:$url")
        })
    }

    private fun getBitmapFromURL(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }


    private fun sendRequest(
        endPoint: String,
        reqParam: String = "",
        requestMethodType: NetworkRequestTypes,
        accessToken: String = ""
    ): Pair<Int, String> {

        var requestUrl = BASE_URL + endPoint
        if (reqParam != "")
            requestUrl += "?$reqParam"

        val mURL = URL(requestUrl)

        try {
            with(mURL.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = requestMethodType.requestType
                addRequestProperty("X-MM-GATEWAY-KEY", "Ge6c853cf-5593-a196-efdb-e3fd7b881eca")
                addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                if (accessToken != "") {
                    val authorization = "Bearer $accessToken"
                    addRequestProperty("Authorization", authorization)
                }

                println("URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    println("Response : $response")
                    return Pair(responseCode, response.toString())
                }
            }
        } catch (e: IOException) {
            return Pair(404, e.message ?: "ERROR")
        }
    }
}