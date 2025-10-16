# 🤖 Chatbox - AI Chat Application

[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.104.1-green.svg)](https://fastapi.tiangolo.com)
[![Python](https://img.shields.io/badge/Python-3.8+-blue.svg)](https://python.org)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

一个基于Android + FastAPI + 阿里云通义千问的智能聊天应用，采用Clean Architecture架构设计，提供流畅的AI对话体验。

## ✨ 特性亮点

- 🎨 **现代化UI**: 基于Jetpack Compose的美观聊天界面
- 🏗️ **Clean Architecture**: 清晰的分层架构，易于维护和扩展
- 🔄 **实时对话**: 与阿里云通义千问AI进行流畅对话
- 🛡️ **安全可靠**: 完善的错误处理和网络安全配置
- 📱 **跨设备**: 支持Android模拟器和物理设备
- ⚡ **高性能**: 使用协程和Flow实现响应式编程

## 📱 项目结构

```
chatbox/
├── app/                          # Android应用
│   ├── src/main/java/com/alvin/chatbox/
│   │   ├── data/                 # 数据层
│   │   │   ├── config/           # API配置
│   │   │   │   └── ApiConfig.kt  # 网络配置
│   │   │   ├── remote/           # 网络接口
│   │   │   │   └── ChatApi.kt    # Retrofit接口
│   │   │   └── repository/       # 仓库实现
│   │   │       └── ChatRepositoryImpl.kt
│   │   ├── domain/               # 领域层
│   │   │   ├── model/            # 数据模型
│   │   │   │   └── ChatMessage.kt
│   │   │   ├── repository/       # 仓库接口
│   │   │   │   └── ChatRepository.kt
│   │   │   └── usecase/          # 用例
│   │   │       └── SendChatMessageUseCase.kt
│   │   ├── presentation/         # 表现层
│   │   │   └── chat/             # 聊天界面
│   │   │       ├── ChatScreen.kt # Compose UI
│   │   │       └── ChatViewModel.kt # ViewModel
│   │   └── di/                   # 依赖注入
│   │       ├── NetworkModule.kt  # 网络模块
│   │       └── RepositoryModule.kt # 仓库模块
│   ├── src/main/res/             # 资源文件
│   │   ├── xml/
│   │   │   └── network_security_config.xml # 网络安全配置
│   │   └── values/               # 字符串、颜色等资源
│   └── build.gradle.kts          # Android构建配置
├── backend/                      # FastAPI后端
│   ├── main.py                   # 主应用文件
│   ├── requirements.txt          # Python依赖
│   ├── env.example              # 环境变量示例
│   ├── test_api.py              # API测试脚本
│   ├── check_api_compatibility.py # 接口兼容性检查
│   └── quick_test.py            # 快速测试脚本
├── .gitignore                   # Git忽略文件
├── README.md                    # 项目说明
└── GITHUB_GUIDE.md             # GitHub推送指南
```

## 🚀 快速开始

### 📋 前置要求

- **Android开发环境**: Android Studio 2023.1+
- **Python环境**: Python 3.8+
- **阿里云账号**: 用于获取DashScope API密钥
- **Android设备**: API 24+ (Android 7.0+)

### 🔧 1. 后端设置

```bash
# 克隆项目
git clone https://github.com/yourusername/chatbox.git
cd chatbox

# 进入后端目录
cd backend

# 创建虚拟环境（推荐）
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate

# 安装依赖
pip install -r requirements.txt

# 配置环境变量
cp env.example .env
# 编辑 .env 文件，添加你的 DASHSCOPE_API_KEY

# 启动后端服务
python main.py
```

### 📱 2. Android应用设置

1. **打开项目**
   ```bash
   # 使用Android Studio打开项目根目录
   ```

2. **同步依赖**
   - 等待Gradle同步完成
   - 确保所有依赖下载成功

3. **运行应用**
   - 连接Android设备或启动模拟器
   - 点击运行按钮

### 🔑 3. 获取API密钥

1. 访问 [阿里云DashScope控制台](https://dashscope.console.aliyun.com/)
2. 注册/登录账号
3. 创建API密钥
4. 将密钥添加到 `backend/.env` 文件中：
   ```env
   DASHSCOPE_API_KEY=sk-your-actual-api-key-here
   QWEN_MODEL=qwen-turbo
   ```

## 🛠️ 技术栈详解

### 📱 Android端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| **Kotlin** | 2.0.21 | 主要开发语言 |
| **Jetpack Compose** | 2024.09.00 | 现代化UI框架 |
| **Clean Architecture** | - | 架构模式 |
| **Hilt** | 2.52 | 依赖注入框架 |
| **Retrofit** | 2.11.0 | 网络请求库 |
| **OkHttp** | 4.12.0 | HTTP客户端 |
| **Coroutines** | 1.9.0 | 异步编程 |
| **Flow** | - | 响应式数据流 |
| **kotlinx-serialization** | 1.7.3 | JSON序列化 |

### 🖥️ 后端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| **FastAPI** | 0.104.1 | Web框架 |
| **Python** | 3.8+ | 开发语言 |
| **httpx** | 0.25.2 | 异步HTTP客户端 |
| **pydantic** | 2.5.0 | 数据验证 |
| **python-dotenv** | 1.0.0 | 环境变量管理 |
| **uvicorn** | 0.24.0 | ASGI服务器 |

### 🤖 AI服务

- **阿里云通义千问**: 提供AI对话能力
- **支持模型**: qwen-turbo, qwen-plus, qwen-max, qwen-long

## 📋 功能特性

### ✨ 核心功能
- ✅ **实时AI对话**: 与通义千问AI进行自然对话
- ✅ **美观界面**: Material Design 3风格的聊天界面
- ✅ **消息气泡**: 用户和AI消息的差异化显示
- ✅ **加载状态**: 优雅的加载动画和提示
- ✅ **错误处理**: 完善的错误提示和恢复机制

### 🏗️ 架构特性
- ✅ **Clean Architecture**: 清晰的分层架构
- ✅ **依赖注入**: 使用Hilt进行依赖管理
- ✅ **响应式编程**: 基于Flow的状态管理
- ✅ **网络安全**: 完善的网络安全配置
- ✅ **环境配置**: 支持多环境切换

### 🔧 开发特性
- ✅ **类型安全**: 完整的Kotlin类型系统
- ✅ **测试支持**: 完整的测试框架
- ✅ **代码规范**: 遵循Android开发最佳实践
- ✅ **文档完善**: 详细的代码注释和文档

## 🔧 配置说明

### 🌐 API配置

在 `app/src/main/java/com/alvin/chatbox/data/config/ApiConfig.kt` 中配置：

```kotlin
object ApiConfig {
    // 不同环境的Base URL
    const val LOCAL_BASE_URL = "http://10.0.2.2:8000/"      // Android模拟器
    const val DEV_BASE_URL = "http://192.168.0.157:8000/"   // 物理设备
    const val PROD_BASE_URL = "https://your-domain.com/"   // 生产环境
    
    // 当前使用的环境
    const val CURRENT_BASE_URL = LOCAL_BASE_URL
    
    // API端点
    const val CHAT_ENDPOINT = "chat"
    
    // 超时配置
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
```

### 🔐 环境变量配置

在 `backend/.env` 中配置：

```env
# 阿里云DashScope API密钥
DASHSCOPE_API_KEY=sk-your-actual-api-key-here

# 通义千问模型选择
QWEN_MODEL=qwen-turbo

# 可选配置
DEBUG=true
LOG_LEVEL=INFO
```

### 📱 设备配置

| 设备类型 | URL配置 | 说明 |
|----------|---------|------|
| **Android模拟器** | `http://10.0.2.2:8000/` | 默认配置，无需修改 |
| **物理设备** | `http://192.168.0.157:8000/` | 替换为你的电脑IP |
| **生产环境** | `https://your-domain.com/` | 使用HTTPS |

## 🧪 测试指南

### 🔧 后端API测试

```bash
# 进入后端目录
cd backend

# 运行完整测试套件
python test_api.py

# 运行快速连接测试
python quick_test.py

# 检查接口兼容性
python check_api_compatibility.py
```

### 📱 Android应用测试

1. **单元测试**
   ```bash
   # 在Android Studio中运行
   ./gradlew test
   ```

2. **集成测试**
   - 确保后端服务正在运行
   - 启动Android应用
   - 发送测试消息验证功能

3. **UI测试**
   - 使用Android Studio的UI测试工具
   - 验证界面交互和响应

### 🌐 网络测试

```bash
# 测试后端连接
curl http://localhost:8000/

# 测试聊天接口
curl -X POST "http://localhost:8000/chat" \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello!"}'
```

## 📱 支持的设备和环境

### 📱 Android设备
- **最低版本**: API 24 (Android 7.0)
- **推荐版本**: API 30+ (Android 11+)
- **架构**: ARM64, ARMv7, x86_64

### 🖥️ 开发环境
- **Android Studio**: 2023.1+
- **Gradle**: 8.0+
- **JDK**: 11+
- **Python**: 3.8+

### 🌐 网络环境
- **开发环境**: HTTP (localhost)
- **生产环境**: HTTPS (推荐)
- **防火墙**: 确保端口8000可访问

## 🔒 安全最佳实践

### 🛡️ 数据安全
- ✅ **环境变量**: 使用`.env`文件管理敏感信息
- ✅ **网络安全**: 配置网络安全策略
- ✅ **API密钥**: 不在代码中硬编码
- ✅ **HTTPS**: 生产环境使用安全连接

### 🔐 代码安全
- ✅ **依赖管理**: 定期更新依赖版本
- ✅ **代码审查**: 遵循安全编码规范
- ✅ **敏感信息**: 使用`.gitignore`排除敏感文件
- ✅ **权限控制**: 最小权限原则

### 📋 安全检查清单
- [ ] `.env`文件已添加到`.gitignore`
- [ ] 代码中无硬编码的API密钥
- [ ] 网络安全配置正确
- [ ] 依赖版本为最新稳定版
- [ ] 生产环境使用HTTPS

## 🐛 常见问题解决

### ❌ 网络连接问题

**问题**: `无法连接到服务器`

**解决方案**:
1. 检查后端服务是否运行
   ```bash
   curl http://localhost:8000/
   ```
2. 验证IP地址配置
3. 检查防火墙设置
4. 确认网络连接正常

### ❌ API密钥问题

**问题**: `API密钥无效`

**解决方案**:
1. 验证API密钥格式
2. 检查环境变量配置
3. 确认API权限设置
4. 验证账户余额

### ❌ 构建错误

**问题**: Gradle构建失败

**解决方案**:
1. 清理项目: `Build -> Clean Project`
2. 重新同步Gradle
3. 检查依赖版本兼容性
4. 更新Android Studio

### ❌ 运行时错误

**问题**: 应用崩溃或异常

**解决方案**:
1. 查看Android Studio Logcat
2. 检查网络权限配置
3. 验证API接口格式
4. 检查数据模型匹配

## 📈 性能优化

### ⚡ Android端优化
- **图片加载**: 使用Coil进行图片缓存
- **网络请求**: 实现请求缓存和重试机制
- **内存管理**: 合理使用ViewModel和Compose
- **启动优化**: 减少启动时间

### 🚀 后端优化
- **异步处理**: 使用async/await提高并发
- **连接池**: 配置HTTP连接池
- **缓存策略**: 实现响应缓存
- **监控日志**: 添加性能监控

## 🔄 版本更新

### 📝 更新日志

#### v1.0.0 (当前版本)
- ✅ 初始版本发布
- ✅ 基础聊天功能
- ✅ Clean Architecture架构
- ✅ 网络安全配置

#### 计划功能
- 🔄 多轮对话历史
- 🔄 语音输入支持
- 🔄 图片消息支持
- 🔄 用户认证系统
- 🔄 消息加密传输

### 🚀 升级指南

1. **备份数据**: 备份重要配置和数据
2. **更新依赖**: 更新到最新版本
3. **测试功能**: 验证所有功能正常
4. **部署更新**: 逐步部署到生产环境

## 🤝 贡献指南

### 📋 贡献流程

1. **Fork项目**: 创建项目分支
2. **创建分支**: `git checkout -b feature/amazing-feature`
3. **提交更改**: `git commit -m 'Add amazing feature'`
4. **推送分支**: `git push origin feature/amazing-feature`
5. **创建PR**: 提交Pull Request

### 📝 代码规范

- **Kotlin**: 遵循官方编码规范
- **Python**: 使用PEP 8规范
- **提交信息**: 使用约定式提交格式
- **文档**: 保持代码注释和文档更新

### 🐛 问题报告

使用GitHub Issues报告问题时，请包含：
- 问题描述
- 复现步骤
- 预期行为
- 实际行为
- 环境信息
- 相关日志

## 📄 许可证

本项目采用 [MIT License](LICENSE) 许可证。

```
MIT License

Copyright (c) 2024 Chatbox Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📞 联系方式

- **GitHub Issues**: [项目Issues页面](https://github.com/yourusername/chatbox/issues)
- **邮箱**: your-email@example.com
- **QQ群**: 123456789
- **微信群**: 扫码加入

## 🙏 致谢

感谢以下开源项目和服务：

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - 现代化Android UI框架
- [FastAPI](https://fastapi.tiangolo.com) - 高性能Python Web框架
- [阿里云通义千问](https://help.aliyun.com/zh/model-studio/) - AI对话服务
- [Retrofit](https://square.github.io/retrofit/) - Android网络请求库
- [Hilt](https://dagger.dev/hilt/) - Android依赖注入框架

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给它一个Star！**

Made with ❤️ by [Your Name](https://github.com/yourusername)

</div>
