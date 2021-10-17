package com.pczigany.bv_homework.data.free_nba_api

data class PaginatedFreeNbaGamesResponse(
    val data: List<FreeNbaGame>,
    val meta: Meta
)
