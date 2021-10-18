package com.pczigany.bv_homework.data.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "lookedUpDates")
data class LookedUpDate(
    @Id
    val date: Date
)
