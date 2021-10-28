package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.LookedUpDate
import com.pczigany.bv_homework.data.document.LookedUpGame
import com.pczigany.bv_homework.repository.LookedUpDateRepository
import com.pczigany.bv_homework.repository.LookedUpGameRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Date

@Service
class CacheCheckerService(
    private val lookedUpGameRepository: LookedUpGameRepository,
    private val lookedUpDateRepository: LookedUpDateRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun isDateLookedUp(date: Date): Boolean = lookedUpDateRepository.existsById(date)

    fun isGameCached(gameId: String): Boolean = lookedUpGameRepository.existsById(gameId)

    fun saveLookedUpDate(date: Date): LookedUpDate = lookedUpDateRepository.save(LookedUpDate(date))

    fun saveLookedUpGame(gameId: String): LookedUpGame = lookedUpGameRepository.save(LookedUpGame(gameId))
}
