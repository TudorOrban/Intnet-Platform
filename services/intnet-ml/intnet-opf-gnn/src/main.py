
from datetime import datetime
from core.common.data_types import GridGraph
from core.data_generators.random_data_generator.random_dynamic_data_generator import generate_random_dynamic_data
from core.data_generators.random_data_generator.random_grid_topology_generator import generate_random_topology
from core.data_generators.random_data_generator.random_static_data_generator import generate_random_static_data
from core.data_generators.random_data_generator.realistic_grid_topology_generator import generate_realistic_grid_topology
from core.data_generators.solution_generator.opf_solution_generator import generate_opf_sample
from finetuning.data_generators.dynamic_data_record_generator import generate_dynamic_data_records
from finetuning.data_repositories.real_grid_graph_repository_creator import create_real_grid_graph_repository
from initializer import initialize


def main():
    initialize()

    # tries = 20
    # try_no = 0

    # while try_no < tries:
    #     print("Try ", try_no)
    #     graph_topology = generate_realistic_grid_topology(num_buses=1000, num_generators=6, num_loads=200, edge_density=0.05)
    #     graph_specification = generate_random_static_data(graph_topology)
    #     graph_data = generate_random_dynamic_data(graph_specification)

    #     graph_data, has_converged = generate_opf_sample(graph_data)
    #     if has_converged:
    #         grid_graph = GridGraph(id=0, created_at=datetime.now(), graph_data=graph_data)

    #         graph_repository = create_real_grid_graph_repository()

    #         graph_repository.save(grid_graph)
    #         break

    #     try_no = try_no + 1

    graph_repository = create_real_grid_graph_repository()

    graph = graph_repository.find()

    records = generate_dynamic_data_records(graph_data=graph.graph_data, record_count=50)
    print(records)


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