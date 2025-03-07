import logging
from fastapi import FastAPI, Depends
from dotenv import load_dotenv
from cassandra.cluster import Session

from core.storage.cassandra import get_cassandra_session

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

load_dotenv()

app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}


@app.get("/cassandra")
async def cassandra(session: Session = Depends(get_cassandra_session)):
    try:
        result = session.execute("SELECT now() FROM system.local")
        return {"cassandra_time": str(result[0].now)}
    except Exception as e:
        return {"error": str(e)}