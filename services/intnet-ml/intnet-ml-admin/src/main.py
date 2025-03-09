from dotenv import load_dotenv
from fastapi import FastAPI



app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet ML Admin Service is running"}

# initialize_services(app)