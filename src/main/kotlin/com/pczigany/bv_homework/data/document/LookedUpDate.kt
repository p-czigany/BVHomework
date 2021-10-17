package com.pczigany.bv_homework.data.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "lookedUpDates")
data class LookedUpDate(
    @Id
    val date: LocalDate
)
