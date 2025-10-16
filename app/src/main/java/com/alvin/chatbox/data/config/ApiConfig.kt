package com.alvin.chatbox.data.config

object ApiConfig {
    // Base URLs for different environments
    const val LOCAL_BASE_URL = "http://10.0.2.2:8000/"  // Android emulator
    const val DEV_BASE_URL = "http://192.168.0.157:8000/"  // Replace with your computer's IP
    const val PROD_BASE_URL = "https://your-domain.com/"  // Production URL
    
    // Current environment - change this to switch environments
    const val CURRENT_BASE_URL = LOCAL_BASE_URL
    
    // API endpoints
    const val CHAT_ENDPOINT = "chat"
    
    // Timeout configurations
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
