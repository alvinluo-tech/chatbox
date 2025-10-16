# Chatbox Backend Setup Instructions

## Prerequisites
1. Python 3.8 or higher
2. Alibaba Cloud DashScope API Key

## Setup Steps

### 1. Install Dependencies
```bash
cd backend
pip install -r requirements.txt
```

### 2. Configure Environment Variables
1. Copy `env.example` to `.env`
2. Get your API key from [Alibaba Cloud DashScope Console](https://dashscope.console.aliyun.com/)
3. Replace `your_api_key_here` with your actual API key

### 3. Run the Server
```bash
python main.py
```

The server will start on `http://localhost:8000`

## API Endpoints

### POST /chat
Send a chat message and get AI response

**Request:**
```json
{
    "message": "Hello, how are you?"
}
```

**Response:**
```json
{
    "reply": "Hello! I'm doing well, thank you for asking. How can I help you today?"
}
```

## Available Qwen Models
- `qwen-turbo`: Fast and cost-effective
- `qwen-plus`: Balanced performance and cost
- `qwen-max`: Highest quality responses
- `qwen-long`: For long context conversations

Change the model in `main.py` line 45 if needed.

## Testing
You can test the API using curl:
```bash
curl -X POST "http://localhost:8000/chat" \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello!"}'
```
