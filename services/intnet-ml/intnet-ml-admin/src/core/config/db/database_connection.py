import os
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

engine = None
SessionLocal = None
Base = declarative_base()

def initialize_database(database_url: str = None):
    global engine, SessionLocal
    if database_url is None:
        database_url = os.getenv("DATABASE_URL", "postgresql://user:password@localhost/ml_admin_db")
    engine = create_engine(database_url)
    SessionLocal = sessionmaker(autoflush=False, autocommit=False, bind=engine)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()