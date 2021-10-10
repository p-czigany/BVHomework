package com.pczigany.bv_homework.data.document

import com.fasterxml.jackson.annotation.JsonIgnore

data class PlayerScore(
    @JsonIgnore
    val id: Number,
    val name: String,
    val score: Int
)

