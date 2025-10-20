package com.alvin.chatbox.presentation.bloodpressure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureAnalysisScreen(
    viewModel: BloodPressureAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "AI血压分析",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        // 分析按钮
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "获取AI健康建议",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("子女邮箱 (可选)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { viewModel.analyzeBloodPressure(email.takeIf { it.isNotBlank() }) },
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                    } else {
                        Text("开始分析")
                    }
                }
            }
        }
        
        // 分析结果
        if (uiState.analysisResult != null) {
            val result = uiState.analysisResult!!
            
            // 警告级别
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = when (result.alert_level) {
                        "critical" -> Color(0xFFFFEBEE)
                        "high" -> Color(0xFFFFF3E0)
                        "elevated" -> Color(0xFFFFF8E1)
                        else -> Color(0xFFE8F5E8)
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (result.alert_level) {
                                "critical" -> Icons.Default.Warning
                                "high" -> Icons.Default.Warning
                                "elevated" -> Icons.Default.Info
                                else -> Icons.Default.CheckCircle
                            },
                            contentDescription = null,
                            tint = when (result.alert_level) {
                                "critical" -> Color(0xFFD32F2F)
                                "high" -> Color(0xFFFF9800)
                                "elevated" -> Color(0xFFFFC107)
                                else -> Color(0xFF4CAF50)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (result.alert_level) {
                                "critical" -> "严重警告"
                                "high" -> "高度关注"
                                "elevated" -> "需要关注"
                                else -> "正常范围"
                            },
                            fontWeight = FontWeight.Bold,
                            color = when (result.alert_level) {
                                "critical" -> Color(0xFFD32F2F)
                                "high" -> Color(0xFFFF9800)
                                "elevated" -> Color(0xFFFFC107)
                                else -> Color(0xFF2E7D32)
                            }
                        )
                    }
                    
                    if (result.email_sent) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Color(0xFF2196F3)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "已发送邮件通知给家人",
                                color = Color(0xFF2196F3),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            
            // AI分析结果
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "AI分析结果",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = result.analysis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            // 建议措施
            if (result.recommendations.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "建议措施",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(result.recommendations) { recommendation ->
                                Row(
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = recommendation,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 错误信息
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
    }
}
