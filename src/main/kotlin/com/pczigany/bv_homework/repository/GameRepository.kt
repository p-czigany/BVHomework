package com.pczigany.bv_homework.repository

import com.pczigany.bv_homework.data.document.Game
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface GameRepository : MongoRepository<Game, String> {
    fun findByDate(date: Date): List<Game>
}
