
from datetime import datetime
from core.common.data_types import GridGraph
from core.data_generators.random_data_generator.random_dynamic_data_generator import generate_random_dynamic_data
from core.data_generators.random_data_generator.random_grid_topology_generator import generate_random_topology
from core.data_generators.random_data_generator.random_static_data_generator import generate_random_static_data
from finetuning.data_repositories.real_grid_graph_repository_creator import create_real_grid_graph_repository
from initializer import initialize


def main():
    initialize()

    graph_topology = generate_random_topology(num_buses=8, num_generators=2, num_loads=4, edge_density=0.3)
    graph_specification = generate_random_static_data(graph_topology)
    graph_data = generate_random_dynamic_data(graph_specification)

    grid_graph = GridGraph(id=0, created_at=datetime.now(), graph_data=graph_data)

    graph_repository = create_real_grid_graph_repository()

    graph_repository.save(grid_graph)




if __name__ == "__main__":
    main()




    # Flat samples
    # flatSampleRepository = FlatTrainingSampleRepository()

    # samples = generate_flat_samples(topologies=4, specifications=3, records=6)

    # flatSampleRepository.add_samples(samples)

    # run_mlflow_train_gnn()

    # repository = create_grid_graph_repository()
    # service = GridGraphService()

    # samples = generate_flat_samples(topologies=4, specifications=3, records=6)
    
    # service.save_generated_samples(samples)

    # graphs = repository.find_all()
    # print(graphs[0])