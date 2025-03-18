module "networking" {
    source = "../../modules/networking"
    resource_group_name = var.resource_group_name
    location = var.location
    vnet_name = var.vnet_name
    subnet_prefixes = var.subnet_prefixes
}

module "postgres" {
    source = "../../modules/postgres"
    resource_group_name = var.resource_group_name
    location = var.location
    microservices = var.microservices
    postgres_server_name = var.postgres_server_name
    postgres_admin_user = var.postgres_admin_user
    postgres_admin_password = var.postgres_admin_password
    postgres_sku_name = var.postgres_sku_name
    subnet_id = module.networking.subnet_ids["postgres_subnet"]
    allowed_aks_subnet_ids = [module.networking.subnet_ids["aks_subnet"]]
    subnet_prefixes = var.subnet_prefixes
}

module "mongo" {
    source = "../../modules/mongo"
    resource_group_name = var.resource_group_name
    location = var.location
    mongo_account_name = var.mongo_account_name
    subnet_id = module.networking.subnet_ids["mongo_subnet"]
    allowed_aks_subnet_ids = [module.networking.subnet_ids["aks_subnet"]]
    mongo_database_names = var.mongo_database_names
    mongo_offer_type = var.mongo_offer_type
    mongo_server_version = var.mongo_server_version
}

module "acr" {
    source = "../../modules/acr"
    resource_group_name = var.resource_group_name
    location = var.location
    acr_name = var.acr_name
}

module "aks" {
    source = "../../modules/aks"
    resource_group_name = var.resource_group_name
    location = var.location
    aks_name = var.aks_name
    subnet_id = module.networking.subnet_ids["aks_subnet"]
    acr_id = module.acr.acr_id
    node_count = var.aks_node_count
    node_vm_size = var.aks_node_vm_size
    service_cidr = var.service_cidr
    network_plugin = var.network_plugin
    dns_service_ip = var.dns_service_ip
}

output "microservice_db_fqdns" {
    value = module.postgres.microservice_db_fqdns
}

output "kube_config" {
    value = module.aks.kube_config
    sensitive = true
}