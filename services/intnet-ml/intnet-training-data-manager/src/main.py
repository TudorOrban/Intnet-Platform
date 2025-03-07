import logging
from fastapi import FastAPI
from dotenv import load_dotenv

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

load_dotenv()

app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}