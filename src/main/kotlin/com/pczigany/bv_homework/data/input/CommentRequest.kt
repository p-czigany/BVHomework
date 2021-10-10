package com.pczigany.bv_homework.data.input

import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Timestamp

data class CommentRequest(
    @get:JsonProperty
    val message: String,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
)
