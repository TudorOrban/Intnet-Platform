
from features.dynamic_data_record.repositories.dynamic_data_record_repository_creator import create_dynamic_data_record_repository
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository


services = {}

def initialize_services():
    services["grid_graph_repository"] = create_grid_graph_repository()
    services["dynamic_data_record_repository"] = create_dynamic_data_record_repository()

def get_service(name: str):
    return services.get(name)