package com.alvin.chatbox.data.local

import androidx.room.*
import com.alvin.chatbox.domain.model.BloodPressureRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureDao {
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<BloodPressureRecord>>
    
    @Query("SELECT * FROM blood_pressure_records WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getRecordsByTimeRange(startTime: Long, endTime: Long): Flow<List<BloodPressureRecord>>
    
    @Query("SELECT * FROM blood_pressure_records WHERE id = :id")
    suspend fun getRecordById(id: Long): BloodPressureRecord?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: BloodPressureRecord): Long
    
    @Update
    suspend fun updateRecord(record: BloodPressureRecord)
    
    @Delete
    suspend fun deleteRecord(record: BloodPressureRecord)
    
    @Query("DELETE FROM blood_pressure_records WHERE id = :id")
    suspend fun deleteRecordById(id: Long)
    
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentRecords(limit: Int): Flow<List<BloodPressureRecord>>
    
    @Query("SELECT AVG(systolic) FROM blood_pressure_records WHERE timestamp BETWEEN :startTime AND :endTime")
    suspend fun getAverageSystolic(startTime: Long, endTime: Long): Double?
    
    @Query("SELECT AVG(diastolic) FROM blood_pressure_records WHERE timestamp BETWEEN :startTime AND :endTime")
    suspend fun getAverageDiastolic(startTime: Long, endTime: Long): Double?
}
