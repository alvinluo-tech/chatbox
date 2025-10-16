# Chatbox Backend Test Suite

This directory contains comprehensive test scripts for the Chatbox FastAPI backend.

## Available Test Scripts

### 1. Python Test Script (`test_api.py`)
**Recommended for most users**

```bash
# Run the Python test script
python test_api.py
```

**Features:**
- ✅ Health check endpoint testing
- 💬 Chat endpoint testing with multiple messages
- 🚨 Error handling validation
- ⚡ Optional performance testing
- 📊 Detailed test results and timing
- 🎨 Colored output for better readability

### 2. Bash Test Script (`test_api.sh`)
**For Linux/macOS users**

```bash
# Make executable and run
chmod +x test_api.sh
./test_api.sh
```

**Features:**
- ✅ Health check endpoint testing
- 💬 Chat endpoint testing
- 🚨 Error handling validation
- 🎨 Colored output

### 3. Windows Batch Script (`test_api.bat`)
**For Windows users**

```cmd
# Run the batch script
test_api.bat
```

**Features:**
- ✅ Health check endpoint testing
- 💬 Chat endpoint testing
- 🚨 Error handling validation

## Test Coverage

All scripts test the following:

1. **Health Check** (`GET /`)
   - Verifies server is running
   - Checks basic connectivity

2. **Chat Endpoint** (`POST /chat`)
   - Tests with various message types
   - Validates request/response format
   - Checks AI response quality

3. **Error Handling**
   - Empty message validation
   - Invalid JSON handling
   - Network error simulation

4. **Performance** (Python script only)
   - Multiple concurrent requests
   - Response time measurement
   - Success rate calculation

## Prerequisites

Before running tests:

1. **Start the FastAPI server:**
   ```bash
   cd backend
   python main.py
   ```

2. **Configure environment variables:**
   - Copy `env.example` to `.env`
   - Set your `DASHSCOPE_API_KEY`

3. **Install dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

## Sample Test Output

```
🚀 Starting Chatbox Backend Tests
==================================================
🔍 Testing health check endpoint...
✅ Health check passed: {'message': 'Chatbox API is running'}
==================================================
💬 Testing chat endpoint with message: 'Hello!'
✅ Chat test passed!
   User: Hello!
   AI: Hello! How can I help you today?
------------------------------
💬 Testing chat endpoint with message: 'What is artificial intelligence?'
✅ Chat test passed!
   User: What is artificial intelligence?
   AI: Artificial Intelligence (AI) refers to the simulation of human intelligence in machines...
------------------------------
🚨 Testing error handling...
   Empty message test: Status 422
   Invalid JSON test: Status 422
------------------------------
==================================================
🎉 All tests passed! Your backend is working correctly.
```

## Troubleshooting

### Common Issues:

1. **"Server is not running"**
   - Start the FastAPI server: `python main.py`
   - Check if port 8000 is available

2. **"API key error"**
   - Verify `DASHSCOPE_API_KEY` is set in `.env`
   - Check if API key is valid at [DashScope Console](https://dashscope.console.aliyun.com/)

3. **"Network error"**
   - Check internet connection
   - Verify firewall settings
   - Test with curl manually

4. **"Invalid response format"**
   - Check if Qwen API is responding correctly
   - Verify API key permissions

## Manual Testing

You can also test manually with curl:

```bash
# Health check
curl http://localhost:8000/

# Chat test
curl -X POST "http://localhost:8000/chat" \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello!"}'
```

## Integration with Android App

Once backend tests pass, your Android app should be able to connect successfully. The Android app is configured to connect to:
- **Emulator**: `http://10.0.2.2:8000/`
- **Physical device**: `http://YOUR_COMPUTER_IP:8000/`

Make sure to update the `baseUrl` in `NetworkModule.kt` if using a different IP address.
