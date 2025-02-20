import asyncio
from pymodbus.server import ModbusTcpServer
from pymodbus.datastore import ModbusSequentialDataBlock, ModbusSlaveContext

# Initialize data store for holding registers
store = ModbusSequentialDataBlock(0, [0] * 100)

context = ModbusSlaveContext(di=ModbusSequentialDataBlock(0, [0]*100), # discrete inputs
                             co=ModbusSequentialDataBlock(0, [0]*100), # coils
                             hr=store,  # holding registers
                             ir=ModbusSequentialDataBlock(0, [0]*100)) # input registers

async def run_server():
    server = ModbusTcpServer(context=context, address=("localhost", 502))

    print("Starting Modbus TCP server on localhost:502...")

    try:
        server.serve_forever() 
        print("Server is running. Press Ctrl+C to stop.")
        await asyncio.Future()
    except KeyboardInterrupt:
        print("Stopping Modbus TCP server...")
    finally:
        server.shutdown()
        print("Modbus TCP server stopped.")

if __name__ == "__main__":
    asyncio.run(run_server())