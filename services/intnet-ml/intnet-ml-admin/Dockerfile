FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt -f https://data.pyg.org/whl/torch-2.2.0+cpu.html

COPY .env .
COPY src/ .
COPY alembic/ .

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]