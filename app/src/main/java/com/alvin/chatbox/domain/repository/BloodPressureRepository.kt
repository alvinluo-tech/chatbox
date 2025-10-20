package com.alvin.chatbox.domain.repository

import com.alvin.chatbox.domain.model.BloodPressureRecord
import kotlinx.coroutines.flow.Flow

interface BloodPressureRepository {
    fun getAllRecords(): Flow<List<BloodPressureRecord>>
    fun getRecordsByTimeRange(startTime: Long, endTime: Long): Flow<List<BloodPressureRecord>>
    suspend fun getRecordById(id: Long): BloodPressureRecord?
    suspend fun insertRecord(record: BloodPressureRecord): Long
    suspend fun updateRecord(record: BloodPressureRecord)
    suspend fun deleteRecord(record: BloodPressureRecord)
    suspend fun deleteRecordById(id: Long)
    fun getRecentRecords(limit: Int): Flow<List<BloodPressureRecord>>
    suspend fun getAverageSystolic(startTime: Long, endTime: Long): Double?
    suspend fun getAverageDiastolic(startTime: Long, endTime: Long): Double?
}
