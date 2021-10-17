package com.pczigany.bv_homework.data.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "games")
data class Game(
    @Id
    val gameId: String,
    val date: LocalDate?,
    val homeTeamName: String?,
    val homeTeamScore: Number?,
    val visitingTeamName: String?,
    val visitingTeamScore: Number?,
    val comments: MutableList<Comment> = mutableListOf(),
    val playerScores: List<PlayerScore>?
)
