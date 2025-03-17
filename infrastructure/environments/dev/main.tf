module "networking" {
    source = "../../modules/networking"
    resource_group_name = var.resource_group_name
    location = var.location
    vnet_name = var.vnet_name
    subnet_prefixes = {
        aks_subnet = ["10.0.1.0/24"]
        ingress_subnet = ["10.0.2.0/24"]
        private_link_subnet = ["10.0.3.0/24"]
    }
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

output "kube_config" {
    value = module.aks.kube_config
    sensitive = true
}