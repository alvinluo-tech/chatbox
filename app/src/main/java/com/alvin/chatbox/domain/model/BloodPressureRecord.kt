package com.alvin.chatbox.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systolic: Int, // 收缩压
    val diastolic: Int, // 舒张压
    val heartRate: Int? = null, // 心率（可选）
    val timestamp: Long = System.currentTimeMillis(),
    val notes: String? = null, // 备注
    val isVoiceInput: Boolean = false // 是否通过语音输入
) {
    fun getBloodPressureCategory(): BloodPressureCategory {
        return when {
            systolic >= 180 || diastolic >= 120 -> BloodPressureCategory.HYPERTENSIVE_CRISIS
            systolic >= 140 || diastolic >= 90 -> BloodPressureCategory.STAGE_2_HYPERTENSION
            systolic >= 130 || diastolic >= 80 -> BloodPressureCategory.STAGE_1_HYPERTENSION
            systolic >= 120 || diastolic >= 80 -> BloodPressureCategory.ELEVATED
            systolic < 90 && diastolic < 60 -> BloodPressureCategory.LOW
            else -> BloodPressureCategory.NORMAL
        }
    }
    
    fun getFormattedTime(): String {
        val date = Date(timestamp)
        return java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(date)
    }
}

enum class BloodPressureCategory(val displayName: String, val color: Long) {
    LOW("低血压", 0xFF2196F3), // 蓝色
    NORMAL("正常", 0xFF4CAF50), // 绿色
    ELEVATED("偏高", 0xFFFF9800), // 橙色
    STAGE_1_HYPERTENSION("高血压1级", 0xFFFF5722), // 深橙色
    STAGE_2_HYPERTENSION("高血压2级", 0xFFE91E63), // 粉色
    HYPERTENSIVE_CRISIS("高血压危象", 0xFFF44336) // 红色
}
