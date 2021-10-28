package com.pczigany.bv_homework.repository

import com.pczigany.bv_homework.data.document.LookedUpGame
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LookedUpGameRepository : MongoRepository<LookedUpGame, String>
