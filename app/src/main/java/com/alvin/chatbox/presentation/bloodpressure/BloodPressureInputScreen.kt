package com.alvin.chatbox.presentation.bloodpressure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alvin.chatbox.domain.model.BloodPressureRecord
import com.alvin.chatbox.domain.model.BloodPressureCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureInputScreen(
    viewModel: BloodPressureInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "血压记录",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        // 语音输入按钮
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (uiState.isListening) Color(0xFFFF5722) else Color(0xFF2196F3)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (uiState.isListening) "正在听取语音..." else "语音输入血压",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (uiState.isListening) {
                            viewModel.stopVoiceInput()
                        } else {
                            viewModel.startVoiceInput(context)
                        }
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = if (uiState.isListening) Color(0xFFFF5722) else Color(0xFF2196F3)
                    )
                ) {
                    Text(if (uiState.isListening) "停止" else "开始语音输入")
                }
            }
        }
        
        // 语音识别结果显示
        uiState.voiceResult?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "识别结果: $result",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { 
                                viewModel.parseVoiceResult(result)
                                systolic = ""
                                diastolic = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("确认使用")
                        }
                        Button(
                            onClick = { viewModel.clearVoiceResult() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                        ) {
                            Text("重新输入")
                        }
                    }
                }
            }
        }
        
        // 手动输入表单
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "手动输入",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = systolic,
                        onValueChange = { systolic = it },
                        label = { Text("收缩压") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = diastolic,
                        onValueChange = { diastolic = it },
                        label = { Text("舒张压") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
                
                OutlinedTextField(
                    value = heartRate,
                    onValueChange = { heartRate = it },
                    label = { Text("心率 (可选)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("备注") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                
                Button(
                    onClick = {
                        val record = BloodPressureRecord(
                            systolic = systolic.toIntOrNull() ?: 0,
                            diastolic = diastolic.toIntOrNull() ?: 0,
                            heartRate = heartRate.toIntOrNull(),
                            notes = notes.takeIf { it.isNotBlank() }
                        )
                        viewModel.saveRecord(record)
                        systolic = ""
                        diastolic = ""
                        heartRate = ""
                        notes = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = systolic.isNotBlank() && diastolic.isNotBlank() && !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                    } else {
                        Text("保存记录")
                    }
                }
            }
        }
        
        // 错误信息显示
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    text = error,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // 成功信息显示
        uiState.successMessage?.let { success ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8))
            ) {
                Text(
                    text = success,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
