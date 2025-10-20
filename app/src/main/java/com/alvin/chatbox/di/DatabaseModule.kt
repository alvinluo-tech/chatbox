package com.alvin.chatbox.di

import android.content.Context
import com.alvin.chatbox.data.local.BloodPressureDatabase
import com.alvin.chatbox.data.local.BloodPressureDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBloodPressureDatabase(@ApplicationContext context: Context): BloodPressureDatabase {
        return BloodPressureDatabase.getDatabase(context)
    }

    @Provides
    fun provideBloodPressureDao(database: BloodPressureDatabase): BloodPressureDao {
        return database.bloodPressureDao()
    }
}
