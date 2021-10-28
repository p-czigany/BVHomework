package com.pczigany.bv_homework.data.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "games")
data class Game(
    @Id
    var gameId: String,
    val date: Date?,
    val homeTeamName: String?,
    val homeTeamScore: Number?,
    val visitingTeamName: String?,
    val visitingTeamScore: Number?,
    val comments: MutableList<Comment> = mutableListOf(),
    val playerScores: List<PlayerScore>?
)
