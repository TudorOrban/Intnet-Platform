resource "azurerm_postgresql_flexible_server" "postgres" {
    name                = var.postgres_server_name
    location            = var.location
    resource_group_name = var.resource_group_name
    administrator_login = var.postgres_admin_user
    administrator_password = var.postgres_admin_password
    sku_name            = var.postgres_sku_name
    storage_mb          = var.postgres_storage_mb
    version             = var.postgres_version
    delegated_subnet_id = var.subnet_id
    private_dns_zone_id = azurerm_private_dns_zone.postgres.id
    public_network_access_enabled = false
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "aks_access" {
    for_each = {
        for name, cidr in var.subnet_prefixes : name => cidr[0]
        if name == "aks_subnet"
    }
    name             = "aks-access"
    server_id        = azurerm_postgresql_flexible_server.postgres.id
    start_ip_address = cidrhost(each.value, 1)
    end_ip_address   = cidrhost(each.value, -1)
}

resource "azurerm_private_dns_zone" "postgres" {
    name                = "postgres-private.postgres.database.azure.com"
    resource_group_name = var.resource_group_name
}

resource "azurerm_private_dns_zone_virtual_network_link" "postgres_vnet_link" {
    name                 = "${var.postgres_server_name}-vnet-link"
    resource_group_name  = var.resource_group_name
    private_dns_zone_name = azurerm_private_dns_zone.postgres.name
    virtual_network_id   = regex("(.+)/subnets/", var.subnet_id)[0]
}