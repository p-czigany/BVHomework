package com.pczigany.bv_homework.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Date

class CacheService(
    private val cacheCheckerService: CacheCheckerService,
    private val gameDownloaderService: GameDownloaderService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun ensurePersistenceForDate(date: Date) {
        if (!cacheCheckerService.isDateLookedUp(date)) {
            logger.info("We still have to try caching date $date.")
            val gameIds: List<String> = gameDownloaderService.getAndStoreAllGamesForDate(date)
            cacheCheckerService.saveLookedUpDate(date)
            gameIds.forEach { gameId -> cacheCheckerService.saveLookedUpGame(gameId) }
        } else {
            logger.info("We have already tried to cache games for date $date.")
        }
    }

    fun ensurePersistenceForId(gameId: String) {
        if (cacheCheckerService.isGameCached(gameId)) {
            logger.info("We have already tried to cache game $gameId.")
        } else {
            logger.info("We still have to try caching game $gameId.")
            gameDownloaderService.getAndStoreGame(gameId)
            cacheCheckerService.saveLookedUpGame(gameId)
        }
    }
}
