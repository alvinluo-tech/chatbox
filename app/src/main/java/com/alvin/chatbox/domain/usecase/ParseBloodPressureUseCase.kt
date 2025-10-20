package com.alvin.chatbox.domain.usecase

import com.alvin.chatbox.domain.model.BloodPressureRecord
import com.alvin.chatbox.domain.repository.BloodPressureRepository
import javax.inject.Inject

class ParseBloodPressureUseCase @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository
) {
    
    suspend operator fun invoke(voiceText: String): Result<BloodPressureRecord> {
        return try {
            val parsed = parseBloodPressureFromText(voiceText)
            val record = BloodPressureRecord(
                systolic = parsed.first,
                diastolic = parsed.second,
                isVoiceInput = true,
                notes = "语音输入: $voiceText"
            )
            Result.success(record)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun parseBloodPressureFromText(text: String): Pair<Int, Int> {
        // 移除空格和标点符号
        val cleanText = text.replace(Regex("[\\s\\p{Punct}]"), "")
        
        // 匹配各种血压表达方式
        val patterns = listOf(
            // "120/80" 格式
            Regex("(\\d{2,3})/(\\d{2,3})"),
            // "120 80" 格式
            Regex("(\\d{2,3})\\s*(\\d{2,3})"),
            // "收缩压120舒张压80" 格式
            Regex("收缩压(\\d{2,3}).*?舒张压(\\d{2,3})"),
            // "高压120低压80" 格式
            Regex("高压(\\d{2,3}).*?低压(\\d{2,3})"),
            // "一百二十八十" 中文数字格式
            Regex("(\\d{2,3}).*?(\\d{2,3})")
        )
        
        for (pattern in patterns) {
            val match = pattern.find(cleanText)
            if (match != null) {
                val systolic = match.groupValues[1].toInt()
                val diastolic = match.groupValues[2].toInt()
                
                // 验证血压值是否在合理范围内
                if (systolic in 60..250 && diastolic in 40..150) {
                    return Pair(systolic, diastolic)
                }
            }
        }
        
        throw IllegalArgumentException("无法从语音中解析出有效的血压数值: $text")
    }
}
