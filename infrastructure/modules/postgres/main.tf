resource "azurerm_postgresql_flexible_server" "microservice_db" {
    for_each = var.microservices
    name = "${each.key}-db-server"
    location = var.location
    resource_group_name = var.resource_group_name
    administrator_login = var.postgres_admin_user
    administrator_password = var.postgres_admin_password
    sku_name = each.value.sku_name
    storage_mb = each.value.storage_mb
    version = each.value.version
    delegated_subnet_id = var.subnet_id
    private_dns_zone_id = azurerm_private_dns_zone.postgres.id
    public_network_access_enabled = false
    zone = each.value.zone
}

resource "azurerm_postgresql_flexible_server_database" "microservice_database" {
    for_each = var.microservices
    name = each.value.database_name != null ? each.value.database_name : replace(each.key, "-", "_")
    server_id = azurerm_postgresql_flexible_server.microservice_db[each.key].id
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "aks_access" {
    for_each = var.microservices
    name = "${each.key}-aks-access"
    server_id = azurerm_postgresql_flexible_server.microservice_db[each.key].id
    start_ip_address = cidrhost(var.subnet_prefixes["aks_subnet"][0], 1)
    end_ip_address = cidrhost(var.subnet_prefixes["aks_subnet"][0], -1)
}

resource "azurerm_private_dns_zone" "postgres" {
    name = "postgres-private.postgres.database.azure.com"
    resource_group_name = var.resource_group_name
}

resource "azurerm_private_dns_zone_virtual_network_link" "postgres_vnet_link" {
    name = "postgres-vnet-link"
    resource_group_name = var.resource_group_name
    private_dns_zone_name = azurerm_private_dns_zone.postgres.name
    virtual_network_id = regex("(.+)/subnets/", var.subnet_id)[0]
}