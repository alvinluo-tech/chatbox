package com.alvin.chatbox.domain.repository

interface ChatRepository {
    suspend fun sendMessage(message: String): String
}


