# 🤝 贡献指南

感谢您对Chatbox项目的关注！我们欢迎任何形式的贡献，包括但不限于：

- 🐛 Bug报告
- ✨ 新功能建议
- 📝 文档改进
- 🔧 代码优化
- 🧪 测试用例

## 📋 贡献流程

### 1. Fork项目
点击GitHub页面右上角的"Fork"按钮，将项目fork到您的账户。

### 2. 克隆项目
```bash
git clone https://github.com/yourusername/chatbox.git
cd chatbox
```

### 3. 创建分支
```bash
git checkout -b feature/amazing-feature
# 或者
git checkout -b fix/bug-description
```

### 4. 进行开发
- 编写代码
- 添加测试
- 更新文档
- 确保代码质量

### 5. 提交更改
```bash
git add .
git commit -m "feat: 添加新功能描述"
```

### 6. 推送分支
```bash
git push origin feature/amazing-feature
```

### 7. 创建Pull Request
在GitHub上创建Pull Request，详细描述您的更改。

## 📝 代码规范

### Kotlin代码规范
- 遵循[官方Kotlin编码规范](https://kotlinlang.org/docs/coding-conventions.html)
- 使用4个空格缩进
- 类名使用PascalCase
- 函数和变量名使用camelCase
- 常量使用UPPER_SNAKE_CASE

### Python代码规范
- 遵循[PEP 8](https://www.python.org/dev/peps/pep-0008/)规范
- 使用4个空格缩进
- 行长度不超过88字符
- 导入语句按标准库、第三方库、本地模块分组

### 提交信息规范
使用[约定式提交](https://www.conventionalcommits.org/)格式：

```
类型(范围): 简短描述

详细描述（可选）

相关Issue: #123
```

**类型**:
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建/工具

**示例**:
```
feat(chat): 添加语音输入功能

- 集成语音识别API
- 添加录音权限处理
- 实现语音转文字功能

相关Issue: #15
```

## 🧪 测试要求

### Android测试
- 单元测试覆盖率 > 80%
- 集成测试覆盖主要功能
- UI测试覆盖关键用户流程

### 后端测试
- API接口测试
- 错误处理测试
- 性能测试

### 运行测试
```bash
# Android测试
./gradlew test
./gradlew connectedAndroidTest

# 后端测试
cd backend
python -m pytest
```

## 📚 文档要求

### 代码文档
- 公共API必须有文档注释
- 复杂逻辑需要行内注释
- 使用KDoc格式（Kotlin）或docstring（Python）

### README更新
- 新功能需要更新README
- 配置变更需要更新配置说明
- 添加使用示例

## 🐛 Bug报告

使用GitHub Issues报告bug时，请包含：

### 必需信息
- **问题描述**: 清晰描述遇到的问题
- **复现步骤**: 详细的操作步骤
- **预期行为**: 期望的正确行为
- **实际行为**: 实际发生的错误行为
- **环境信息**: 操作系统、版本等

### 可选信息
- **截图**: 如果有UI问题
- **日志**: 相关的错误日志
- **相关代码**: 问题相关的代码片段

### Bug报告模板
```markdown
## Bug描述
简要描述bug

## 复现步骤
1. 打开应用
2. 点击某个按钮
3. 观察错误

## 预期行为
应该发生什么

## 实际行为
实际发生了什么

## 环境信息
- Android版本: API 30
- 设备: Pixel 4
- 应用版本: 1.0.0

## 附加信息
任何其他相关信息
```

## ✨ 功能建议

### 建议格式
```markdown
## 功能描述
简要描述建议的功能

## 使用场景
什么情况下会用到这个功能

## 实现思路
如何实现这个功能（可选）

## 优先级
高/中/低
```

## 🔍 代码审查

### 审查要点
- **功能正确性**: 代码是否实现了预期功能
- **代码质量**: 是否遵循编码规范
- **性能考虑**: 是否有性能问题
- **安全性**: 是否有安全漏洞
- **可维护性**: 代码是否易于理解和维护

### 审查流程
1. 自动检查（CI/CD）
2. 代码审查者检查
3. 测试验证
4. 合并到主分支

## 📞 联系方式

- **GitHub Issues**: 用于bug报告和功能建议
- **Discussions**: 用于一般讨论
- **邮箱**: luoyaosheng123@gmail.com

## 🙏 致谢

感谢所有贡献者的努力！您的贡献让项目变得更好。

---

**记住**: 贡献代码是一种学习过程，不要担心犯错。我们都会帮助您改进！
