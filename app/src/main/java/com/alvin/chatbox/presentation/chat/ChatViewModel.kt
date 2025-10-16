package com.alvin.chatbox.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.chatbox.domain.usecase.SendChatMessageUseCase
import com.alvin.chatbox.domain.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import retrofit2.HttpException

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendChatMessage: SendChatMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return
        
        // Add user message immediately
        _uiState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + ChatMessage(text = userText, isUser = true),
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val response = sendChatMessage(userText)
                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + ChatMessage(text = response, isUser = false),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is UnknownHostException -> "无法连接到服务器，请检查网络连接"
                    is SocketTimeoutException -> "请求超时，请重试"
                    is HttpException -> when (e.code()) {
                        401 -> "API密钥无效，请联系管理员"
                        403 -> "访问被拒绝，请检查API权限"
                        429 -> "请求过于频繁，请稍后重试"
                        500 -> "服务器内部错误，请稍后重试"
                        502 -> "服务器暂时不可用，请稍后重试"
                        else -> "网络错误: ${e.code()}"
                    }
                    else -> "发送消息失败: ${e.message}"
                }
                
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}


