package com.pczigany.bv_homework.data.document

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import java.util.Date

data class Comment(
    @JsonIgnore
    val id: String = ObjectId().toString(),
    val timestamp: Date,
    val message: String
)
