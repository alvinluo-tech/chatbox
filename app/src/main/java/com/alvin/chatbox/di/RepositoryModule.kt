package com.alvin.chatbox.di

import com.alvin.chatbox.data.repository.BloodPressureRepositoryImpl
import com.alvin.chatbox.data.repository.ChatRepositoryImpl
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import com.alvin.chatbox.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository
    
    @Binds
    @Singleton
    abstract fun bindBloodPressureRepository(impl: BloodPressureRepositoryImpl): BloodPressureRepository
}


