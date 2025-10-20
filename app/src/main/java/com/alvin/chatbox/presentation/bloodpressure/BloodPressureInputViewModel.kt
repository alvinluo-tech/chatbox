package com.alvin.chatbox.presentation.bloodpressure

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.chatbox.domain.model.BloodPressureRecord
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import com.alvin.chatbox.domain.usecase.ParseBloodPressureUseCase
import com.alvin.chatbox.domain.usecase.SpeechRecognitionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BloodPressureInputUiState(
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val voiceResult: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class BloodPressureInputViewModel @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository,
    private val speechRecognitionUseCase: SpeechRecognitionUseCase,
    private val parseBloodPressureUseCase: ParseBloodPressureUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BloodPressureInputUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        // 监听语音识别状态
        viewModelScope.launch {
            speechRecognitionUseCase.isListening.collect { isListening ->
                _uiState.update { it.copy(isListening = isListening) }
            }
        }
        
        // 监听语音识别结果
        viewModelScope.launch {
            speechRecognitionUseCase.recognitionResult.collect { result ->
                result?.let {
                    _uiState.update { it.copy(voiceResult = result) }
                }
            }
        }
        
        // 监听语音识别错误
        viewModelScope.launch {
            speechRecognitionUseCase.error.collect { error ->
                error?.let {
                    _uiState.update { 
                        it.copy(
                            errorMessage = error,
                            isListening = false
                        ) 
                    }
                }
            }
        }
    }
    
    fun startVoiceInput(context: Context) {
        speechRecognitionUseCase.startListening()
    }

    fun stopVoiceInput() {
        speechRecognitionUseCase.stopListening()
    }
    
    fun parseVoiceResult(voiceText: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            parseBloodPressureUseCase(voiceText)
                .onSuccess { record ->
                    saveRecord(record)
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            successMessage = "语音识别成功，血压记录已保存",
                            voiceResult = null
                        ) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = "语音解析失败: ${error.message}"
                        ) 
                    }
                }
        }
    }
    
    fun clearVoiceResult() {
        speechRecognitionUseCase.clearResult()
        _uiState.update { it.copy(voiceResult = null) }
    }
    
    fun saveRecord(record: BloodPressureRecord) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                bloodPressureRepository.insertRecord(record)
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        successMessage = "血压记录保存成功"
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "保存失败: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
    
    fun clearSuccess() {
        _uiState.update { it.copy(successMessage = null) }
    }
}
