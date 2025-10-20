package com.alvin.chatbox.domain.usecase

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechRecognitionUseCase @Inject constructor() {
    
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()
    
    private val _recognitionResult = MutableStateFlow<String?>(null)
    val recognitionResult: StateFlow<String?> = _recognitionResult.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun createSpeechIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-CN") // 中文识别
            putExtra(RecognizerIntent.EXTRA_PROMPT, "请说出您的血压数值")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
    }
    
    fun onRecognitionResult(results: List<String>) {
        _recognitionResult.value = results.firstOrNull()
        _isListening.value = false
    }
    
    fun onError(errorCode: Int) {
        val errorMessage = when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "音频录制错误"
            SpeechRecognizer.ERROR_CLIENT -> "客户端错误"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "权限不足"
            SpeechRecognizer.ERROR_NETWORK -> "网络错误"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "网络超时"
            SpeechRecognizer.ERROR_NO_MATCH -> "没有识别到语音"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "识别器忙碌"
            SpeechRecognizer.ERROR_SERVER -> "服务器错误"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "语音超时"
            else -> "未知错误: $errorCode"
        }
        _error.value = errorMessage
        _isListening.value = false
    }
    
    fun startListening() {
        _isListening.value = true
        _error.value = null
        _recognitionResult.value = null
    }
    
    fun stopListening() {
        _isListening.value = false
    }
    
    fun clearResult() {
        _recognitionResult.value = null
        _error.value = null
    }
}
