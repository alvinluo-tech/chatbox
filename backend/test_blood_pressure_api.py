#!/usr/bin/env python3
"""
血压分析API测试脚本
"""

import requests
import json
from datetime import datetime, timedelta
import time

# 测试数据
test_records = [
    {
        "systolic": 200,
        "diastolic": 120,
        "heart_rate": 70,
        "timestamp": time.time(),
        "notes": "正常血压"
    },
    {
        "systolic": 180,
        "diastolic": 120,
        "heart_rate": 75,
        "timestamp": time.time() - 86400,  # 1天前
        "notes": "略高"
    },
    {
        "systolic": 230,
        "diastolic": 130,
        "heart_rate": 80,
        "timestamp": time.time() - 172800,  # 2天前
        "notes": "高血压1级"
    }
]

def test_blood_pressure_analysis():
    """测试血压分析API"""
    url = "http://localhost:8000/blood-pressure/analyze"
    
    payload = {
        "records": test_records,
        "email": "1545916566@qq.com"  # 可选
    }
    
    try:
        print("正在测试血压分析API...")
        response = requests.post(url, json=payload, timeout=30)
        
        if response.status_code == 200:
            result = response.json()
            print("✅ API调用成功!")
            print(f"分析结果: {result['analysis']}")
            print(f"建议措施: {result['recommendations']}")
            print(f"警告级别: {result['alert_level']}")
            print(f"邮件发送: {result['email_sent']}")
        else:
            print(f"❌ API调用失败: {response.status_code}")
            print(f"错误信息: {response.text}")
            
    except requests.exceptions.RequestException as e:
        print(f"❌ 网络错误: {e}")
    except Exception as e:
        print(f"❌ 其他错误: {e}")

def test_chat_api():
    """测试聊天API"""
    url = "http://localhost:8000/chat"
    
    payload = {
        "message": "你好，请介绍一下血压的正常范围"
    }
    
    try:
        print("\n正在测试聊天API...")
        response = requests.post(url, json=payload, timeout=30)
        
        if response.status_code == 200:
            result = response.json()
            print("✅ 聊天API调用成功!")
            print(f"AI回复: {result['reply']}")
        else:
            print(f"❌ 聊天API调用失败: {response.status_code}")
            print(f"错误信息: {response.text}")
            
    except requests.exceptions.RequestException as e:
        print(f"❌ 网络错误: {e}")
    except Exception as e:
        print(f"❌ 其他错误: {e}")

if __name__ == "__main__":
    print("🧪 开始测试血压监测API...")
    print("=" * 50)
    
    # 测试聊天API
    # test_chat_api()
    
    # 测试血压分析API
    test_blood_pressure_analysis()
    
    print("\n" + "=" * 50)
    print("🎉 测试完成!")
