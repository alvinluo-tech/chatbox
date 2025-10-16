package com.alvin.chatbox.domain.usecase

import com.alvin.chatbox.domain.repository.ChatRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: String): String {
        return repository.sendMessage(message)
    }
}


