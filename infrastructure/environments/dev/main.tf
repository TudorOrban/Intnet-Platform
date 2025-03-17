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