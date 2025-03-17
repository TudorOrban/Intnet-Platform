resource "azurerm_kubernetes_cluster" "aks" {
    name = var.aks_name
    location = var.location
    resource_group_name = var.resource_group_name
    dns_prefix = "${var.aks_name}-dns"
    network_profile {
        service_cidr = var.service_cidr
        network_plugin = var.network_plugin
        dns_service_ip = var.dns_service_ip
    }

    default_node_pool {
        name = "default"
        node_count = var.node_count
        vm_size = var.node_vm_size
        vnet_subnet_id = var.subnet_id
    }

    identity { 
        type = "SystemAssigned"
    }
}

data "azurerm_kubernetes_cluster" "aks" {
    name = var.aks_name
    resource_group_name = var.resource_group_name
    depends_on = [
        azurerm_kubernetes_cluster.aks
    ]
}

resource "azurerm_role_assignment" "acr_pull" {
    scope = var.acr_id
    role_definition_name = "AcrPull"
    principal_id = azurerm_kubernetes_cluster.aks.identity[0].principal_id
    depends_on = [
        azurerm_kubernetes_cluster.aks
    ]
}