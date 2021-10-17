package com.pczigany.bv_homework.data.free_nba_api

data class FreeNbaGame(
    val id: Int?,
    val date: String?,
    val homeTeam: FreeNbaTeam?,
    val homeTeamScore: Int?,
    val period: Int?,
    val postseason: Boolean?,
    val season: Int?,
    val status: String?,
    val time: String?,
    val visitorTeam: FreeNbaTeam?,
    val visitorTeamScore: Int?
)
