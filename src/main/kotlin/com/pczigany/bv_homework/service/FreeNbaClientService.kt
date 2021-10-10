package com.pczigany.bv_homework.service

import com.google.gson.Gson
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.data.free_nba_api.PaginatedFreeNbaResponse
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class FreeNbaClientService(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    fun getGame(gameId: String): FreeNbaGame {
        val response =
            client.newCall(buildRequest("https://free-nba.p.rapidapi.com/games/$gameId")).execute()
        println("Amit visszakaptunk az API-tól, így néz ki: $response")
        return gson.fromJson(response.body?.string()!!, FreeNbaGame::class.java)
    }

    fun getGamesForDate(date: LocalDate): List<FreeNbaGame> {
        val response = client.newCall(
            buildRequest(
                ("https://free-nba.p.rapidapi.com" + "/games").toHttpUrlOrNull()!!.newBuilder()
                    .addQueryParameter("dates[]", "$date")
                    .addQueryParameter("per_page", "100").build().toString()
            )
        ).execute()

        val paginatedFreeNbaGamesResponse: PaginatedFreeNbaResponse<FreeNbaGame> =
            gson.fromJson(response.body?.string()!!) // TODO: pagination cycle
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

        val paginatedFreeNbaStatsResponse: PaginatedFreeNbaResponse<FreeNbaStat> =
            gson.fromJson(response.body?.string()!!) // TODO: pagination cycle
        return paginatedFreeNbaStatsResponse.data
    }

    private fun buildRequest(url: String): Request = Request.Builder()
        .url(url = url)
        .get()
        .addHeader("x-rapidapi-host", "free-nba.p.rapidapi.com")
        .addHeader("x-rapidapi-key", "f18fd5505emsh4c5ca8337ad7d32p1f2803jsn7f94868f54eb")
        .build()

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson(json, T::class.java)
}
