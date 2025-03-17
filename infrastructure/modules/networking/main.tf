resource "azurerm_virtual_network" "vnet" {
    name = var.vnet_name
    location = var.location
    resource_group_name = var.resource_group_name
    address_space = var.vnet_address_space
}

resource "azurerm_subnet" "subnet" {
    for_each = var.subnet_prefixes
    name = each.key
    resource_group_name = var.resource_group_name
    virtual_network_name = azurerm_virtual_network.vnet.name
    address_prefixes = each.value

    dynamic "delegation" {
        for_each = each.key == "grid_data_db_subnet" ? [1] : []
        content {
            name = "fsdelegation"
            service_delegation {
                name = "Microsoft.DBforPostgreSQL/flexibleServers"
            }
        }
    }
}