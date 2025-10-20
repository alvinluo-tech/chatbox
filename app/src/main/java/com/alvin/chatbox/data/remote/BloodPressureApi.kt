package com.alvin.chatbox.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class BloodPressureAnalysisRequest(
    val records: List<BloodPressureRecordDto>,
    val email: String? = null
)

@Serializable
data class BloodPressureRecordDto(
    val systolic: Int,
    val diastolic: Int,
    val heart_rate: Int? = null,
    val timestamp: Float? = null,
    val notes: String? = null
)

@Serializable
data class BloodPressureAnalysisResponse(
    val analysis: String,
    val recommendations: List<String>,
    val alert_level: String,
    val email_sent: Boolean = false
)

interface BloodPressureApi {
    @POST("blood-pressure/analyze")
    suspend fun analyzeBloodPressure(@Body request: BloodPressureAnalysisRequest): BloodPressureAnalysisResponse
}
