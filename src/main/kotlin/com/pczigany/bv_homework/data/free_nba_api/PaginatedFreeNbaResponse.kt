package com.pczigany.bv_homework.data.free_nba_api

data class PaginatedFreeNbaResponse<T>(
    val data: List<T>,
    val meta: Meta
)
