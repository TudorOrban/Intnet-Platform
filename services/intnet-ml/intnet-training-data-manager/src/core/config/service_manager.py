
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository


services = {}

def initialize_services():
    services["grid_graph_repository"] = create_grid_graph_repository()

def get_service(name: str):
    return services.get(name)