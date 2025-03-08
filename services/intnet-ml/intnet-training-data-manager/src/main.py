from dotenv import load_dotenv
from fastapi import FastAPI
from core.config.app_initializer import initialize_routers, initialize_server

initialize_server()
    
app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}

initialize_routers(app)