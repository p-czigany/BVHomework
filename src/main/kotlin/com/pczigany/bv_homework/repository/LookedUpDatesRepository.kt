package com.pczigany.bv_homework.repository

import com.pczigany.bv_homework.data.document.LookedUpDate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface LookedUpDatesRepository : MongoRepository<LookedUpDate, LocalDate>
