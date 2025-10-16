#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Quick Backend Test Script
快速测试后端是否正常运行
"""

import requests
import json

def test_backend():
    print("Testing Backend Connection")
    print("=" * 40)
    
    # Test URLs
    urls = [
        "http://localhost:8000/",
        "http://127.0.0.1:8000/",
        "http://10.0.2.2:8000/"
    ]
    
    for url in urls:
        print(f"Testing: {url}")
        try:
            response = requests.get(url, timeout=5)
            if response.status_code == 200:
                print(f"✓ SUCCESS: {response.json()}")
                return True
            else:
                print(f"✗ FAILED: Status {response.status_code}")
        except requests.exceptions.ConnectionError:
            print("✗ FAILED: Connection refused")
        except requests.exceptions.Timeout:
            print("✗ FAILED: Timeout")
        except Exception as e:
            print(f"✗ FAILED: {e}")
        print()
    
    print("=" * 40)
    print("Backend is not running!")
    print("Please start the backend with: python main.py")
    return False

def test_chat_endpoint():
    print("Testing Chat Endpoint")
    print("=" * 40)
    
    url = "http://localhost:8000/chat"
    payload = {"message": "Hello"}
    
    try:
        response = requests.post(url, json=payload, timeout=10)
        if response.status_code == 200:
            result = response.json()
            print(f"✓ SUCCESS: {result}")
            return True
        else:
            print(f"✗ FAILED: Status {response.status_code}")
            print(f"Response: {response.text}")
    except Exception as e:
        print(f"✗ FAILED: {e}")
    
    return False

if __name__ == "__main__":
    print("Backend Status Check")
    print("=" * 40)
    
    if test_backend():
        print("\nBackend is running!")
        test_chat_endpoint()
    else:
        print("\nPlease start the backend first:")
        print("cd backend")
        print("python main.py")
