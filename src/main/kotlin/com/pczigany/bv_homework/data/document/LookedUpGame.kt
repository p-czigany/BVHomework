package com.pczigany.bv_homework.data.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "lookedUpGames")
data class LookedUpGame(
    @Id
    val gameId: String
)
