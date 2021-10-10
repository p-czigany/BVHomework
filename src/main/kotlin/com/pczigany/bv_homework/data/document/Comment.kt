package com.pczigany.bv_homework.data.document

import org.bson.types.ObjectId
import java.sql.Timestamp

data class Comment(
    val id: String = ObjectId().toString(),
    val timestamp: Timestamp,
    val message: String
)
