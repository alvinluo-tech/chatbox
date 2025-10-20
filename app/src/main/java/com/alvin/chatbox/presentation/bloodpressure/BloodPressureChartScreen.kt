package com.alvin.chatbox.presentation.bloodpressure

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BloodPressureChartScreen(
    viewModel: BloodPressureChartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "血压趋势图",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // 时间范围选择
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "选择时间范围",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimeRangeButton(
                        text = "7天",
                        isSelected = uiState.selectedRange == TimeRange.WEEK,
                        onClick = { viewModel.setTimeRange(TimeRange.WEEK) }
                    )
                    TimeRangeButton(
                        text = "30天",
                        isSelected = uiState.selectedRange == TimeRange.MONTH,
                        onClick = { viewModel.setTimeRange(TimeRange.MONTH) }
                    )
                    TimeRangeButton(
                        text = "3个月",
                        isSelected = uiState.selectedRange == TimeRange.THREE_MONTHS,
                        onClick = { viewModel.setTimeRange(TimeRange.THREE_MONTHS) }
                    )
                    TimeRangeButton(
                        text = "全部",
                        isSelected = uiState.selectedRange == TimeRange.ALL,
                        onClick = { viewModel.setTimeRange(TimeRange.ALL) }
                    )
                }
            }
        }
        
        // 图表
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.records.isEmpty()) {
                    Text(
                        text = "暂无血压数据",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    BloodPressureChart(
                        records = uiState.records,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        
        // 统计信息
        if (uiState.records.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "统计信息",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatisticItem(
                            label = "平均收缩压",
                            value = "${uiState.averageSystolic.toInt()} mmHg"
                        )
                        StatisticItem(
                            label = "平均舒张压",
                            value = "${uiState.averageDiastolic.toInt()} mmHg"
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatisticItem(
                            label = "记录总数",
                            value = "${uiState.records.size} 条"
                        )
                        StatisticItem(
                            label = "最新记录",
                            value = uiState.latestRecord?.getFormattedTime() ?: "无"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimeRangeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary 
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(text)
    }
}

@Composable
fun StatisticItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun BloodPressureChart(
    records: List<com.alvin.chatbox.domain.model.BloodPressureRecord>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                setupChart()
                setData(createLineData(records))
                invalidate()
            }
        },
        modifier = modifier
    )
}

private fun LineChart.setupChart() {
    description.isEnabled = false
    setTouchEnabled(true)
    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)
    
    // 设置X轴
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        setDrawGridLines(false)
        granularity = 1f
        valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())
            override fun getFormattedValue(value: Float): String {
                try {
                    val dataSet = data?.dataSets?.getOrNull(0) ?: return ""
                    if (value.toInt() < 0 || value.toInt() >= dataSet.entryCount) return ""
                    val timestamp = dataSet.getEntryForIndex(value.toInt())?.data as? Long
                    return timestamp?.let { dateFormat.format(Date(it)) } ?: ""
                } catch (e: Exception) {
                    return ""
                }
            }
        }
    }
    
    // 设置Y轴
    axisLeft.apply {
        setDrawGridLines(true)
        axisMinimum = 60f
        axisMaximum = 200f
    }
    
    axisRight.isEnabled = false
    
    legend.apply {
        isEnabled = true
        textSize = 12f
    }
}

private fun LineChart.createLineData(records: List<com.alvin.chatbox.domain.model.BloodPressureRecord>): LineData {
    val sortedRecords = records.sortedBy { it.timestamp }
    
    // 收缩压数据
    val systolicEntries = sortedRecords.mapIndexed { index, record ->
        Entry(index.toFloat(), record.systolic.toFloat()).apply {
            data = record.timestamp
        }
    }
    
    // 舒张压数据
    val diastolicEntries = sortedRecords.mapIndexed { index, record ->
        Entry(index.toFloat(), record.diastolic.toFloat()).apply {
            data = record.timestamp
        }
    }
    
    val systolicDataSet = LineDataSet(systolicEntries, "收缩压").apply {
        color = Color.RED
        setCircleColor(Color.RED)
        lineWidth = 2f
        circleRadius = 4f
        setDrawValues(true)
        valueTextSize = 10f
    }
    
    val diastolicDataSet = LineDataSet(diastolicEntries, "舒张压").apply {
        color = Color.BLUE
        setCircleColor(Color.BLUE)
        lineWidth = 2f
        circleRadius = 4f
        setDrawValues(true)
        valueTextSize = 10f
    }
    
    return LineData(systolicDataSet, diastolicDataSet)
}
