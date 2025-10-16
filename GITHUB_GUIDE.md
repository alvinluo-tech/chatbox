# 🚀 GitHub推送指南

## 📋 推送前检查清单

### ✅ 1. 敏感信息检查
- [ ] 确认 `.env` 文件已添加到 `.gitignore`
- [ ] 检查代码中是否有硬编码的API密钥
- [ ] 验证 `local.properties` 已忽略
- [ ] 确认构建文件已忽略

### ✅ 2. 文件清理
- [ ] 删除临时文件和缓存
- [ ] 清理构建目录
- [ ] 移除IDE配置文件（如果不需要）

### ✅ 3. 项目完整性
- [ ] 确保所有必要文件都已添加
- [ ] 检查README.md是否完整
- [ ] 验证项目结构清晰

## 🔧 Git命令

### 初始化仓库
```bash
# 在项目根目录执行
git init
git add .
git commit -m "Initial commit: Android Chatbox with FastAPI backend"
```

### 添加远程仓库
```bash
# 替换为你的GitHub仓库URL
git remote add origin https://github.com/yourusername/chatbox.git
git branch -M main
git push -u origin main
```

### 日常推送
```bash
git add .
git commit -m "描述你的更改"
git push
```

## 📁 重要文件说明

### 必须包含的文件
- ✅ `README.md` - 项目说明
- ✅ `app/build.gradle.kts` - Android构建配置
- ✅ `backend/requirements.txt` - Python依赖
- ✅ `backend/env.example` - 环境变量示例
- ✅ 所有源代码文件

### 必须忽略的文件
- ❌ `.env` - 包含API密钥
- ❌ `local.properties` - 本地SDK路径
- ❌ `build/` - 构建输出
- ❌ `.idea/` - IDE配置
- ❌ `*.apk` - 应用包文件

## 🔒 安全最佳实践

### 1. 环境变量管理
```bash
# 创建 .env 文件（不要提交）
echo "DASHSCOPE_API_KEY=your_actual_key" > backend/.env

# 使用示例文件
cp backend/env.example backend/.env
```

### 2. API密钥保护
- ✅ 使用环境变量
- ✅ 提供示例配置文件
- ❌ 不要在代码中硬编码
- ❌ 不要提交真实密钥

### 3. 网络安全
- ✅ 使用网络安全配置
- ✅ 允许HTTP用于开发
- ✅ 生产环境使用HTTPS

## 📝 提交信息规范

### 格式
```
类型(范围): 简短描述

详细描述（可选）

相关Issue: #123
```

### 类型
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建/工具

### 示例
```bash
git commit -m "feat(chat): 添加AI聊天功能

- 集成阿里云通义千问API
- 实现实时对话界面
- 添加错误处理和加载状态

相关Issue: #1"
```

## 🎯 推送后检查

### 1. GitHub仓库检查
- [ ] 确认所有文件已上传
- [ ] 检查敏感信息是否泄露
- [ ] 验证README显示正确

### 2. 克隆测试
```bash
# 从GitHub克隆测试
git clone https://github.com/yourusername/chatbox.git
cd chatbox
# 按照README设置项目
```

## 🚨 常见问题

### 1. 推送被拒绝
```bash
# 拉取最新更改
git pull origin main
# 解决冲突后重新推送
git push
```

### 2. 敏感信息泄露
```bash
# 从历史中移除文件
git filter-branch --force --index-filter \
'git rm --cached --ignore-unmatch backend/.env' \
--prune-empty --tag-name-filter cat -- --all

# 强制推送
git push origin --force --all
```

### 3. 文件过大
```bash
# 使用Git LFS处理大文件
git lfs track "*.apk"
git lfs track "*.jar"
git add .gitattributes
```

## 📞 需要帮助？

如果遇到问题，请检查：
1. GitHub仓库权限设置
2. 网络连接状态
3. Git配置是否正确
4. 文件权限是否正常
