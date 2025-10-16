package com.alvin.chatbox.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("/chat")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}

@Serializable
data class ChatRequest(
    @SerialName("message") val message: String
)

@Serializable
data class ChatResponse(
    @SerialName("reply") val reply: String
)


