package com.hassanhamdy.musicshowcase

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


class NetworkBase {
    private val BASE_URL: String = "http://staging-gateway.mondiamedia.com/"

    // TODO: before get musics should check time if token still valid or not
    fun getMusics(query: String, token: String): Future<String> {
        val reqParam = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8")
        return Executors.newSingleThreadExecutor().submit(Callable<String> {
            sendRequest(
                endPoint = "v2/api/sayt/flat",
                reqParam = reqParam,
                requestMethodType = NetworkRequestTypes.GET,
                accessToken = "C202558d6-4954-4818-97b1-407d4815e649"
            )
        })
    }

    fun getAccessToken(): Future<String> {
        return Executors.newSingleThreadExecutor().submit(Callable<String> {
            sendRequest(
                endPoint = "v0/api/gateway/token/client",
                requestMethodType = NetworkRequestTypes.POST
            )
        })
    }


    private fun sendRequest(
        endPoint: String,
        reqParam: String = "",
        requestMethodType: NetworkRequestTypes,
        accessToken: String = ""
    ): String {

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
                if(accessToken != "") {
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
                    return response.toString()
                }
            }
        } catch (e: IOException) {
            return e.message ?: "ERROR"
        }
    }
}