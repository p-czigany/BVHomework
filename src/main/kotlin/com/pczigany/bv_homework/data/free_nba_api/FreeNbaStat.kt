package com.pczigany.bv_homework.data.free_nba_api

data class FreeNbaStat(
    val player: FreeNbaStatPlayer,
    val pts: Int?,
)

data class FreeNbaStatPlayer(
    val id: String,
    val firstName: String,
    val lastName: String,
)
