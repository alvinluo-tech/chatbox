#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
GitHub推送前安全检查脚本
检查敏感信息和文件完整性
"""

import os
import re
import json

def check_sensitive_info():
    """检查敏感信息"""
    print("🔒 检查敏感信息...")
    
    sensitive_patterns = [
        r'DASHSCOPE_API_KEY\s*=\s*["\'][^"\']+["\']',  # 硬编码API密钥
        r'password\s*=\s*["\'][^"\']+["\']',           # 硬编码密码
        r'secret\s*=\s*["\'][^"\']+["\']',             # 硬编码密钥
        r'token\s*=\s*["\'][^"\']+["\']',              # 硬编码令牌
    ]
    
    issues = []
    
    # 检查源代码文件
    for root, dirs, files in os.walk('.'):
        # 跳过.git目录
        if '.git' in dirs:
            dirs.remove('.git')
        
        for file in files:
            if file.endswith(('.kt', '.java', '.py', '.js', '.ts')):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        for pattern in sensitive_patterns:
                            if re.search(pattern, content, re.IGNORECASE):
                                issues.append(f"⚠️  {file_path}: 发现可能的敏感信息")
                except:
                    pass
    
    if issues:
        print("发现潜在问题:")
        for issue in issues:
            print(f"   {issue}")
        return False
    else:
        print("✅ 未发现硬编码敏感信息")
        return True

def check_gitignore():
    """检查.gitignore文件"""
    print("\n📁 检查.gitignore文件...")
    
    required_ignores = [
        '.env',
        'local.properties',
        'build/',
        '.idea/',
        '*.apk',
        '__pycache__/',
        'venv/',
        '*.log'
    ]
    
    gitignore_files = []
    for root, dirs, files in os.walk('.'):
        if '.gitignore' in files:
            gitignore_files.append(os.path.join(root, '.gitignore'))
    
    if not gitignore_files:
        print("❌ 未找到.gitignore文件")
        return False
    
    all_good = True
    for gitignore_file in gitignore_files:
        try:
            with open(gitignore_file, 'r', encoding='utf-8') as f:
                content = f.read()
                missing = []
                for required in required_ignores:
                    if required not in content:
                        missing.append(required)
                
                if missing:
                    print(f"⚠️  {gitignore_file} 缺少:")
                    for item in missing:
                        print(f"      - {item}")
                    all_good = False
                else:
                    print(f"✅ {gitignore_file} 配置完整")
        except:
            print(f"❌ 无法读取 {gitignore_file}")
            all_good = False
    
    return all_good

def check_project_structure():
    """检查项目结构"""
    print("\n📂 检查项目结构...")
    
    required_files = [
        'README.md',
        'app/build.gradle.kts',
        'backend/requirements.txt',
        'backend/env.example',
        'backend/main.py'
    ]
    
    missing_files = []
    for file_path in required_files:
        if not os.path.exists(file_path):
            missing_files.append(file_path)
    
    if missing_files:
        print("❌ 缺少必要文件:")
        for file_path in missing_files:
            print(f"   - {file_path}")
        return False
    else:
        print("✅ 项目结构完整")
        return True

def check_env_example():
    """检查环境变量示例文件"""
    print("\n🔧 检查环境变量配置...")
    
    env_example_path = 'backend/env.example'
    if not os.path.exists(env_example_path):
        print("❌ 缺少 env.example 文件")
        return False
    
    try:
        with open(env_example_path, 'r', encoding='utf-8') as f:
            content = f.read()
            if 'DASHSCOPE_API_KEY' in content:
                print("✅ env.example 配置正确")
                return True
            else:
                print("❌ env.example 缺少 DASHSCOPE_API_KEY")
                return False
    except:
        print("❌ 无法读取 env.example 文件")
        return False

def main():
    """主检查函数"""
    print("🚀 GitHub推送前安全检查")
    print("=" * 50)
    
    checks = [
        check_sensitive_info,
        check_gitignore,
        check_project_structure,
        check_env_example
    ]
    
    results = []
    for check in checks:
        results.append(check())
    
    print("\n" + "=" * 50)
    
    if all(results):
        print("🎉 所有检查通过！可以安全推送到GitHub")
        print("\n📋 推送命令:")
        print("   git add .")
        print("   git commit -m \"Initial commit: Android Chatbox with FastAPI backend\"")
        print("   git push -u origin main")
    else:
        print("⚠️  发现问题，请修复后再推送")
        print("\n💡 建议:")
        print("   1. 检查敏感信息是否已移除")
        print("   2. 完善.gitignore文件")
        print("   3. 确保所有必要文件存在")
        print("   4. 配置环境变量示例文件")

if __name__ == "__main__":
    main()
