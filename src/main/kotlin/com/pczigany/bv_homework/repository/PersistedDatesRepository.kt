package com.pczigany.bv_homework.repository

import com.pczigany.bv_homework.data.document.PersistedDate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PersistedDatesRepository : MongoRepository<PersistedDate, LocalDate> {
}