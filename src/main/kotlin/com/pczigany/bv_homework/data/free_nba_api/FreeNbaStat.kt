package com.pczigany.bv_homework.data.free_nba_api

data class FreeNbaStat(
//    val id: Number,
//    val ast: Number,
//    val blk: Number,
//    val dreb: Number,
//    val fg3Pct: Number,
//    val fg3a: Number,
//    val fg3m: Number,
//    val fgPct: Number,
//    val fga: Number,
//    val fgm: Number,
//    val ftPct: Number,
//    val fta: Number,
//    val ftm: Number,
//    val game: FreeNbaStatGame,
//    val min: String,
//    val oreb: Number,
//    val pf: Number,
    val player: FreeNbaStatPlayer,
    val pts: Int?,
//    val reb: Number,
//    val stl: Number,
//    val team: FreeNbaStatTeam,
//    val turnover: Number
)

data class FreeNbaStatGame(
    val id: Number,
    val date: String,
    val homeTeamId: Number,
    val homeTeamScore: Number,
    val period: Number,
    val postseason: Boolean,
    val season: Number,
    val status: String,
    val time: String,
    val visitorTeamId: Number,
    val visitorTeamScore: Number
)

data class FreeNbaStatPlayer(
    val id: Number,
    val firstName: String,
//    val heightFeet: Number,
//    val heightInches: Number,
    val lastName: String,
//    val position: String,
//    val teamId: Number,
//    val weightPounds: Number
)
//
//data class FreeNbaStatTeam(
//    val id: Number,
//    val abbreviation: String,
//    val city: String,
//    val conference: String,
//    val division: String,
//    val fullName: String,
//    val name: String
//)
