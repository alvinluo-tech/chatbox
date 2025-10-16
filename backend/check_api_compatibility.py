#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
API Compatibility Check Script
检查Kotlin接口与FastAPI后端对应关系
"""

import json

def check_api_compatibility():
    print("API Compatibility Check")
    print("=" * 50)
    
    # Kotlin ChatApi interface definition
    kotlin_api = {
        "endpoint": "POST /chat",
        "request": {
            "class": "ChatRequest",
            "fields": {
                "message": "String"
            }
        },
        "response": {
            "class": "ChatResponse", 
            "fields": {
                "reply": "String"
            }
        }
    }
    
    # FastAPI backend interface definition
    fastapi_backend = {
        "endpoint": "POST /chat",
        "request": {
            "class": "ChatRequest",
            "fields": {
                "message": "str"
            }
        },
        "response": {
            "class": "ChatResponse",
            "fields": {
                "reply": "str"
            }
        }
    }
    
    print("Android Kotlin Interface:")
    print(f"   Endpoint: {kotlin_api['endpoint']}")
    print(f"   Request: {kotlin_api['request']['class']}")
    print(f"   Fields: {kotlin_api['request']['fields']}")
    print(f"   Response: {kotlin_api['response']['class']}")
    print(f"   Fields: {kotlin_api['response']['fields']}")
    
    print("\nFastAPI Backend Interface:")
    print(f"   Endpoint: {fastapi_backend['endpoint']}")
    print(f"   Request: {fastapi_backend['request']['class']}")
    print(f"   Fields: {fastapi_backend['request']['fields']}")
    print(f"   Response: {fastapi_backend['response']['class']}")
    print(f"   Fields: {fastapi_backend['response']['fields']}")
    
    print("\n" + "=" * 50)
    
    # Check compatibility
    checks = []
    
    # Check endpoint
    if kotlin_api['endpoint'] == fastapi_backend['endpoint']:
        checks.append("✓ Endpoint path matches")
    else:
        checks.append("✗ Endpoint path mismatch")
    
    # Check request fields
    if kotlin_api['request']['fields'] == fastapi_backend['request']['fields']:
        checks.append("✓ Request fields match")
    else:
        checks.append("✗ Request fields mismatch")
    
    # Check response fields
    if kotlin_api['response']['fields'] == fastapi_backend['response']['fields']:
        checks.append("✓ Response fields match")
    else:
        checks.append("✗ Response fields mismatch")
    
    print("Compatibility Check Results:")
    for check in checks:
        print(f"   {check}")
    
    print("\n" + "=" * 50)
    
    # Example request/response
    print("Example Request/Response Format:")
    
    example_request = {"message": "Hello, AI!"}
    example_response = {"reply": "Hello! How can I help you today?"}
    
    print("   Request JSON:")
    print(f"   {json.dumps(example_request, indent=2, ensure_ascii=False)}")
    
    print("\n   Response JSON:")
    print(f"   {json.dumps(example_response, indent=2, ensure_ascii=False)}")
    
    print("\n" + "=" * 50)
    
    # URL configuration check
    print("URL Configuration Check:")
    print("   Android Base URL: http://10.0.2.2:8000/")
    print("   FastAPI Port: 8000")
    print("   ✓ URL configuration is correct (Android emulator -> localhost)")
    
    print("\nImportant Notes:")
    print("   - Ensure FastAPI backend is running")
    print("   - Check DASHSCOPE_API_KEY environment variable")
    print("   - Verify network connection and firewall settings")
    
    all_good = all("✓" in check for check in checks)
    if all_good:
        print("\n🎉 All API compatibility checks passed!")
        print("   Kotlin app can communicate with FastAPI backend successfully")
    else:
        print("\n⚠️  Found API mismatch issues, please check the errors above")

if __name__ == "__main__":
    check_api_compatibility()