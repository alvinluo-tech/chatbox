package com.alvin.chatbox.domain.usecase

import com.alvin.chatbox.data.remote.BloodPressureApi
import com.alvin.chatbox.data.remote.BloodPressureAnalysisRequest
import com.alvin.chatbox.data.remote.BloodPressureRecordDto
import com.alvin.chatbox.data.remote.BloodPressureAnalysisResponse
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AnalyzeBloodPressureUseCase @Inject constructor(
    private val bloodPressureApi: BloodPressureApi,
    private val bloodPressureRepository: BloodPressureRepository
) {
    
    suspend operator fun invoke(email: String? = null): Result<BloodPressureAnalysisResponse> {
        return try {
            // 获取最近的血压记录
            val records = bloodPressureRepository.getAllRecords().first()
            
            if (records.isEmpty()) {
                return Result.failure(Exception("没有血压记录可供分析"))
            }
            
            // 转换为API格式
            val recordDtos = records.map { record ->
                BloodPressureRecordDto(
                    systolic = record.systolic,
                    diastolic = record.diastolic,
                    heart_rate = record.heartRate,
                    // 转换为秒级时间戳，后端使用datetime.fromtimestamp解析
                    timestamp = record.timestamp / 1000f,
                    notes = record.notes
                )
            }
            
            val request = BloodPressureAnalysisRequest(
                records = recordDtos,
                email = email
            )
            
            val response = bloodPressureApi.analyzeBloodPressure(request)
            Result.success(response)
            
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
