package com.pczigany.bv_homework.data.document

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import java.sql.Timestamp

data class Comment(
    @JsonIgnore
    val id: String = ObjectId().toString(),
    val timestamp: Timestamp,
    val message: String
)
