#!/usr/bin/env python3
"""
Test script for Chatbox FastAPI backend
Tests both health check and chat functionality
"""

import requests
import json
import time
import sys
from typing import Dict, Any

# Configuration
BASE_URL = "http://localhost:8000"
CHAT_ENDPOINT = f"{BASE_URL}/chat"
HEALTH_ENDPOINT = f"{BASE_URL}/"

def test_health_check() -> bool:
    """Test the root endpoint for basic connectivity"""
    print("🔍 Testing health check endpoint...")
    try:
        response = requests.get(HEALTH_ENDPOINT, timeout=10)
        if response.status_code == 200:
            data = response.json()
            print(f"✅ Health check passed: {data}")
            return True
        else:
            print(f"❌ Health check failed with status {response.status_code}")
            return False
    except requests.exceptions.RequestException as e:
        print(f"❌ Health check failed: {e}")
        return False

def test_chat_endpoint(message: str) -> bool:
    """Test the chat endpoint with a given message"""
    print(f"💬 Testing chat endpoint with message: '{message}'")
    
    payload = {"message": message}
    headers = {"Content-Type": "application/json"}
    
    try:
        response = requests.post(
            CHAT_ENDPOINT, 
            json=payload, 
            headers=headers, 
            timeout=30
        )
        
        if response.status_code == 200:
            data = response.json()
            if "reply" in data:
                print(f"✅ Chat test passed!")
                print(f"   User: {message}")
                print(f"   AI: {data['reply']}")
                return True
            else:
                print(f"❌ Chat test failed: Invalid response format")
                print(f"   Response: {data}")
                return False
        else:
            print(f"❌ Chat test failed with status {response.status_code}")
            print(f"   Response: {response.text}")
            return False
            
    except requests.exceptions.RequestException as e:
        print(f"❌ Chat test failed: {e}")
        return False

def test_error_handling() -> bool:
    """Test error handling with invalid requests"""
    print("🚨 Testing error handling...")
    
    # Test with empty message
    try:
        response = requests.post(
            CHAT_ENDPOINT,
            json={"message": ""},
            headers={"Content-Type": "application/json"},
            timeout=10
        )
        print(f"   Empty message test: Status {response.status_code}")
    except Exception as e:
        print(f"   Empty message test failed: {e}")
    
    # Test with invalid JSON
    try:
        response = requests.post(
            CHAT_ENDPOINT,
            data="invalid json",
            headers={"Content-Type": "application/json"},
            timeout=10
        )
        print(f"   Invalid JSON test: Status {response.status_code}")
    except Exception as e:
        print(f"   Invalid JSON test failed: {e}")
    
    return True

def run_performance_test() -> None:
    """Run a simple performance test"""
    print("⚡ Running performance test...")
    
    test_messages = [
        "Hello!",
        "What's the weather like?",
        "Tell me a joke",
        "How are you?",
        "What can you help me with?"
    ]
    
    start_time = time.time()
    success_count = 0
    
    for i, message in enumerate(test_messages, 1):
        print(f"   Test {i}/{len(test_messages)}: {message}")
        if test_chat_endpoint(message):
            success_count += 1
        time.sleep(1)  # Small delay between requests
    
    end_time = time.time()
    total_time = end_time - start_time
    
    print(f"📊 Performance Results:")
    print(f"   Total requests: {len(test_messages)}")
    print(f"   Successful: {success_count}")
    print(f"   Failed: {len(test_messages) - success_count}")
    print(f"   Total time: {total_time:.2f} seconds")
    print(f"   Average time per request: {total_time/len(test_messages):.2f} seconds")

def main():
    """Main test function"""
    print("🚀 Starting Chatbox Backend Tests")
    print("=" * 50)
    
    # Check if server is running
    if not test_health_check():
        print("\n❌ Server is not running or not accessible!")
        print("   Please start the FastAPI server first:")
        print("   cd backend && python main.py")
        sys.exit(1)
    
    print("\n" + "=" * 50)
    
    # Test basic chat functionality
    test_messages = [
        "Hello!",
        "What is artificial intelligence?",
        "Tell me a short story",
        "How do you work?",
        "What's 2+2?"
    ]
    
    all_passed = True
    
    for message in test_messages:
        if not test_chat_endpoint(message):
            all_passed = False
        print("-" * 30)
    
    # Test error handling
    test_error_handling()
    print("-" * 30)
    
    # Ask user if they want to run performance test
    print("\n🤔 Would you like to run a performance test? (y/n): ", end="")
    try:
        choice = input().lower().strip()
        if choice in ['y', 'yes']:
            print("\n" + "=" * 50)
            run_performance_test()
    except KeyboardInterrupt:
        print("\n\n👋 Test interrupted by user")
    
    print("\n" + "=" * 50)
    if all_passed:
        print("🎉 All tests passed! Your backend is working correctly.")
    else:
        print("⚠️  Some tests failed. Please check your configuration.")
        print("   Make sure:")
        print("   1. FastAPI server is running")
        print("   2. DASHSCOPE_API_KEY is set in .env file")
        print("   3. You have internet connection")
        print("   4. Your API key is valid")

if __name__ == "__main__":
    main()
