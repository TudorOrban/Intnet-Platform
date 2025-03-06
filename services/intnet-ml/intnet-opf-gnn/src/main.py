from finetuning.data_generators.dynamic_data_record_generator import generate_dynamic_data_records
from finetuning.data_repositories.dynamic_data_record_repository_creator import create_dynamic_data_record_repository
from finetuning.data_repositories.real_grid_graph_repository_creator import create_real_grid_graph_repository
from finetuning.model.finetuning_run import run_mlflow_finetune_gnn
from finetuning.model.model_finetuner import finetune_model
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

    run_mlflow_finetune_gnn()

    # graph_repository = create_real_grid_graph_repository()
    # record_repository = create_dynamic_data_record_repository()

    # graph = graph_repository.find()

    # records = generate_dynamic_data_records(graph_specification=graph.graph_data, record_count=5)
    # print("Before", records[0].bus_data)

    # record_repository.save_all(records)

    # saved_records = record_repository.find_all()

    # print("Saved", saved_records[0].bus_data)
    

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