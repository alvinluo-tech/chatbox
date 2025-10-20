package com.alvin.chatbox.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.alvin.chatbox.domain.model.BloodPressureRecord

@Database(
    entities = [BloodPressureRecord::class],
    version = 1,
    exportSchema = false
)
abstract class BloodPressureDatabase : RoomDatabase() {
    abstract fun bloodPressureDao(): BloodPressureDao
    
    companion object {

        @Volatile
        private var INSTANCE: BloodPressureDatabase? = null
        
        fun getDatabase(context: Context): BloodPressureDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BloodPressureDatabase::class.java,
                    "blood_pressure_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
