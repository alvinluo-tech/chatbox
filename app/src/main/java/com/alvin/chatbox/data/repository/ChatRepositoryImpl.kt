package com.alvin.chatbox.data.repository

import com.alvin.chatbox.data.remote.ChatApi
import com.alvin.chatbox.data.remote.ChatRequest
import com.alvin.chatbox.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ChatApi
) : ChatRepository {
    override suspend fun sendMessage(message: String): String {
        val resp = api.sendMessage(ChatRequest(message))
        return resp.reply
    }
}


