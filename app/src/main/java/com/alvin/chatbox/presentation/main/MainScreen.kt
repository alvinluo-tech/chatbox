package com.alvin.chatbox.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alvin.chatbox.presentation.bloodpressure.BloodPressureAnalysisScreen
import com.alvin.chatbox.presentation.bloodpressure.BloodPressureChartScreen
import com.alvin.chatbox.presentation.bloodpressure.BloodPressureInputScreen
import com.alvin.chatbox.presentation.chat.ChatScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class Screen(val title: String, val icon: ImageVector) {
    object Chat : Screen("AI聊天", Icons.Default.Chat)
    object BloodPressureInput : Screen("血压记录", Icons.Default.Add)
    object BloodPressureChart : Screen("血压图表", Icons.Default.ShowChart)
    object BloodPressureAnalysis : Screen("AI分析", Icons.Default.Analytics)
}

private val screens = listOf(
    Screen.Chat,
    Screen.BloodPressureInput,
    Screen.BloodPressureChart,
    Screen.BloodPressureAnalysis
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = uiState.currentScreen == screen,
                        onClick = { viewModel.navigateTo(screen) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.currentScreen) {
                Screen.Chat -> ChatScreen()
                Screen.BloodPressureInput -> BloodPressureInputScreen()
                Screen.BloodPressureChart -> BloodPressureChartScreen()
                Screen.BloodPressureAnalysis -> BloodPressureAnalysisScreen()
            }
        }
    }
}

data class MainUiState(
    val currentScreen: Screen = Screen.Chat
)

@HiltViewModel
class MainViewModel @Inject constructor() : androidx.lifecycle.ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }
}
