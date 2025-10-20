package com.alvin.chatbox.data.repository

import com.alvin.chatbox.data.local.BloodPressureDao
import com.alvin.chatbox.domain.model.BloodPressureRecord
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BloodPressureRepositoryImpl @Inject constructor(
    private val bloodPressureDao: BloodPressureDao
) : BloodPressureRepository {
    
    override fun getAllRecords(): Flow<List<BloodPressureRecord>> {
        return bloodPressureDao.getAllRecords()
    }
    
    override fun getRecordsByTimeRange(startTime: Long, endTime: Long): Flow<List<BloodPressureRecord>> {
        return bloodPressureDao.getRecordsByTimeRange(startTime, endTime)
    }
    
    override suspend fun getRecordById(id: Long): BloodPressureRecord? {
        return bloodPressureDao.getRecordById(id)
    }
    
    override suspend fun insertRecord(record: BloodPressureRecord): Long {
        return bloodPressureDao.insertRecord(record)
    }
    
    override suspend fun updateRecord(record: BloodPressureRecord) {
        bloodPressureDao.updateRecord(record)
    }
    
    override suspend fun deleteRecord(record: BloodPressureRecord) {
        bloodPressureDao.deleteRecord(record)
    }
    
    override suspend fun deleteRecordById(id: Long) {
        bloodPressureDao.deleteRecordById(id)
    }
    
    override fun getRecentRecords(limit: Int): Flow<List<BloodPressureRecord>> {
        return bloodPressureDao.getRecentRecords(limit)
    }
    
    override suspend fun getAverageSystolic(startTime: Long, endTime: Long): Double? {
        return bloodPressureDao.getAverageSystolic(startTime, endTime)
    }
    
    override suspend fun getAverageDiastolic(startTime: Long, endTime: Long): Double? {
        return bloodPressureDao.getAverageDiastolic(startTime, endTime)
    }
}
