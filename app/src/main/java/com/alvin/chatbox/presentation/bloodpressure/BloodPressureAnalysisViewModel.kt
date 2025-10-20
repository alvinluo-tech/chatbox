package com.alvin.chatbox.presentation.bloodpressure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.chatbox.data.remote.BloodPressureAnalysisResponse
import com.alvin.chatbox.domain.usecase.AnalyzeBloodPressureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BloodPressureAnalysisUiState(
    val isLoading: Boolean = false,
    val analysisResult: BloodPressureAnalysisResponse? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class BloodPressureAnalysisViewModel @Inject constructor(
    private val analyzeBloodPressureUseCase: AnalyzeBloodPressureUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BloodPressureAnalysisUiState())
    val uiState = _uiState.asStateFlow()
    
    fun analyzeBloodPressure(email: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            analyzeBloodPressureUseCase(email)
                .onSuccess { result ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            analysisResult = result
                        ) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = "分析失败: ${error.message}"
                        ) 
                    }
                }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
