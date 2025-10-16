from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import httpx
import os
from dotenv import load_dotenv
import json

# Load environment variables
load_dotenv()

app = FastAPI(title="Chatbox API", version="1.0.0")

# CORS configuration for Android app
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # In production, specify your Android app's origin
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Request/Response models
class ChatRequest(BaseModel):
    message: str

class ChatResponse(BaseModel):
    reply: str

# Qwen API configuration
QWEN_API_KEY = os.getenv("DASHSCOPE_API_KEY")
QWEN_BASE_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1"

if not QWEN_API_KEY:
    raise ValueError("DASHSCOPE_API_KEY environment variable is required")

@app.get("/")
async def root():
    return {"message": "Chatbox API is running"}

@app.post("/chat", response_model=ChatResponse)
async def chat(request: ChatRequest):
    """
    Chat endpoint that integrates with Alibaba Cloud Qwen API
    Based on: https://help.aliyun.com/zh/model-studio/use-qwen-by-calling-api
    """
    try:
        # Prepare the request for Qwen API
        qwen_request = {
            "model": "qwen-turbo",  # You can change this to other models like qwen-plus, qwen-max
            "messages": [
                {
                    "role": "user",
                    "content": request.message
                }
            ],
            "temperature": 0.7,
            "max_tokens": 1000
        }
        
        headers = {
            "Authorization": f"Bearer {QWEN_API_KEY}",
            "Content-Type": "application/json"
        }
        
        async with httpx.AsyncClient(timeout=30.0) as client:
            response = await client.post(
                f"{QWEN_BASE_URL}/chat/completions",
                headers=headers,
                json=qwen_request
            )
            
            if response.status_code != 200:
                raise HTTPException(
                    status_code=response.status_code,
                    detail=f"Qwen API error: {response.text}"
                )
            
            result = response.json()
            
            # Extract the reply from Qwen API response
            if "choices" in result and len(result["choices"]) > 0:
                reply = result["choices"][0]["message"]["content"]
                return ChatResponse(reply=reply)
            else:
                raise HTTPException(
                    status_code=500,
                    detail="Invalid response format from Qwen API"
                )
                
    except httpx.TimeoutException:
        raise HTTPException(status_code=504, detail="Request timeout")
    except httpx.RequestError as e:
        raise HTTPException(status_code=502, detail=f"Network error: {str(e)}")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal server error: {str(e)}")

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
