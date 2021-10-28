package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.repository.GameRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Date
import com.pczigany.bv_homework.data.document.Game as GameDocument

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val cacheService: CacheService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun getByDate(date: Date): List<GameDocument> {
        cacheService.ensurePersistenceForDate(date)
        return gameRepository.findByDate(date)
    }

    fun getById(gameId: String): GameDocument? {
        cacheService.ensurePersistenceForGameId(gameId)
        return gameRepository.findByIdOrNull(gameId)
    }
}
