import asyncio
import logging
from pymodbus.server import ModbusTcpServer
from pymodbus.datastore import ModbusSequentialDataBlock, ModbusSlaveContext

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

# Initialize data store for holding registers
store = ModbusSequentialDataBlock(0, [12345] * 100)

context = {
    0: ModbusSlaveContext(
        di=ModbusSequentialDataBlock(0, [0]*100), # discrete inputs
        co=ModbusSequentialDataBlock(0, [0]*100), # coils
        hr=store,  # holding registers
        ir=ModbusSequentialDataBlock(0, [0]*100) # input registers
    )
}

async def run_server():
    port = 5020
    server = ModbusTcpServer(context=context, address=("0.0.0.0", port))
    logger.info(f"Starting Modbus TCP server on localhost:{port}...")

    try:
        await server.serve_forever() 
        logger.info("Server is running. Press Ctrl+C to stop.")
        await asyncio.Future()
    except KeyboardInterrupt:
        logger.info("Stopping Modbus TCP server...")
    finally:
        server.shutdown()
        logger.info("Modbus TCP server stopped.")

if __name__ == "__main__":
    asyncio.run(run_server())