package com.pczigany.bv_homework.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.data.free_nba_api.PaginatedFreeNbaResponse
import com.pczigany.bv_homework.exception.FreeNbaApiException
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Date

@Service
class FreeNbaClientService(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun getGame(gameId: String): FreeNbaGame? {
        val response =
            client.newCall(buildRequest("https://free-nba.p.rapidapi.com/games/$gameId")).execute()

        logger.info("Get Game for id $gameId response: $response")

        if (!response.isSuccessful) throw FreeNbaApiException()

        val responseJson = response.body?.string()

        return gson.fromJson(responseJson)
    }

    fun getGamesForDate(date: Date): List<FreeNbaGame> {
        val response = client.newCall(
            buildRequest(
                ("https://free-nba.p.rapidapi.com" + "/games").toHttpUrlOrNull()!!.newBuilder()
                    .addQueryParameter("dates[]", "$date")
                    .addQueryParameter("per_page", "100").build().toString()
            )
        ).execute()

        logger.info("Games for Date ($date) Response: $response")

        if (!response.isSuccessful) throw FreeNbaApiException()

        val responseJson = response.body?.string()

        val paginatedFreeNbaGamesResponse: PaginatedFreeNbaResponse<FreeNbaGame> =
            gson.fromJson(responseJson)
        return paginatedFreeNbaGamesResponse.data
    }

    fun getStatsForGame(gameId: String): List<FreeNbaStat> {
        val response = client.newCall(
            buildRequest(
                ("https://free-nba.p.rapidapi.com" + "/stats").toHttpUrlOrNull()!!.newBuilder()
                    .addQueryParameter("game_ids[]", gameId)
                    .addQueryParameter("per_page", "100").build().toString()
            )
        ).execute()

        logger.info("Game Stats Response: $response")

        if (!response.isSuccessful) throw FreeNbaApiException()

        val responseJson = response.body?.string()

        val paginatedFreeNbaStatsResponse: PaginatedFreeNbaResponse<FreeNbaStat> =
            gson.fromJson(responseJson)
        return paginatedFreeNbaStatsResponse.data
    }

    private fun buildRequest(url: String): Request = Request.Builder()
        .url(url = url)
        .get()
        .addHeader("x-rapidapi-host", "free-nba.p.rapidapi.com")
        .addHeader("x-rapidapi-key", "f18fd5505emsh4c5ca8337ad7d32p1f2803jsn7f94868f54eb")
        .build()

    private inline fun <reified T> Gson.fromJson(json: String?) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}
