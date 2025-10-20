package com.alvin.chatbox.presentation.bloodpressure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.chatbox.domain.model.BloodPressureRecord
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

enum class TimeRange(val days: Int) {
    WEEK(7),
    MONTH(30),
    THREE_MONTHS(90),
    ALL(Int.MAX_VALUE)
}

data class BloodPressureChartUiState(
    val records: List<BloodPressureRecord> = emptyList(),
    val isLoading: Boolean = false,
    val selectedRange: TimeRange = TimeRange.WEEK,
    val averageSystolic: Double = 0.0,
    val averageDiastolic: Double = 0.0,
    val latestRecord: BloodPressureRecord? = null
)

@HiltViewModel
class BloodPressureChartViewModel @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BloodPressureChartUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        loadRecords(TimeRange.WEEK)
    }
    
    fun setTimeRange(range: TimeRange) {
        _uiState.update { it.copy(selectedRange = range) }
        loadRecords(range)
    }
    
    private fun loadRecords(range: TimeRange) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                val records = if (range == TimeRange.ALL) {
                    bloodPressureRepository.getAllRecords()
                } else {
                    val calendar = Calendar.getInstance()
                    val endTime = calendar.timeInMillis
                    calendar.add(Calendar.DAY_OF_MONTH, -range.days)
                    val startTime = calendar.timeInMillis
                    bloodPressureRepository.getRecordsByTimeRange(startTime, endTime)
                }
                
                records.collect { recordList ->
                    val sortedRecords = recordList.sortedBy { it.timestamp }
                    val averageSystolic = if (sortedRecords.isNotEmpty()) {
                        sortedRecords.map { it.systolic }.average()
                    } else 0.0
                    
                    val averageDiastolic = if (sortedRecords.isNotEmpty()) {
                        sortedRecords.map { it.diastolic }.average()
                    } else 0.0
                    
                    _uiState.update {
                        it.copy(
                            records = sortedRecords,
                            isLoading = false,
                            averageSystolic = averageSystolic,
                            averageDiastolic = averageDiastolic,
                            latestRecord = sortedRecords.lastOrNull()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        records = emptyList()
                    ) 
                }
            }
        }
    }
}
