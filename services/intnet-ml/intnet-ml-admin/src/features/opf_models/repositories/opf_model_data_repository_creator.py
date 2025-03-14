
import os
from features.opf_models.repositories.cloud_opf_model_data_repository_impl import CloudOPFModelDataRepositoryImpl
from features.opf_models.repositories.local_opf_model_data_repository_impl import LocalOPFModelDataRepositoryImpl
from features.opf_models.repositories.opf_model_data_repository import OPFModelDataRepository


def create_opf_model_data_repository() -> OPFModelDataRepository:
    storage_type = os.getenv("STORAGE_TYPE", "local")

    if storage_type == "local":
        return LocalOPFModelDataRepositoryImpl()
    else:
        return CloudOPFModelDataRepositoryImpl()