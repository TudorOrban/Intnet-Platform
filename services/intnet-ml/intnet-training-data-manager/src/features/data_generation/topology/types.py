from pydantic import BaseModel, Field

class TopologySpecifications(BaseModel):
    num_buses: int = Field(10, description="Number of buses in the grid")
    num_generators: int = Field(3, description="Number of generators")
    num_loads: int = Field(4, description="Number of loads")
    der_density: float = Field(0.2, description="Density of DERs")
    storage_unit_density: float = Field(0.1, description="Density of storage units")
    edge_density: float = Field(0.1, description="Density of edges")