from fastapi import Depends, FastAPI
from requests import Session

from core.config.db.database_connection import database, get_db, initialize_database
from core.config.initialization.app_initializer import initialize_app

initialize_app()
initialize_database()

app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet ML Admin Service is running"}

@app.get("/test_db")
async def test_db(db: Session = Depends(get_db)):
    return {"message": "Database connection successful"}