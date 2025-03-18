resource "azurerm_cosmosdb_account" "mongo" {
    name = var.mongo_account_name
    location = var.location
    resource_group_name = var.resource_group_name
    offer_type = var.mongo_offer_type
    kind = "MongoDB"
    enable_automatic_failover = true
    enable_free_tier = false
    enable_virtual_network_rules = true

    virtual_network_rule {
        id = var.subnet_id
        ignore_missing_vnet_service_endpoint = false
    }

    ip_range_filter = "0.0.0.0"
    consistency_policy {
        consistency_level = "Session"
    }

    geo_location {
        location = var.location
        failover_priority = 0
    }
}

resource "azurerm_cosmodb_mongo_database" "mongo_db" {
    for_each = toset(var.mongo_database_names)
    name = each.value
    resource_cosmodb_account_id = azurerm_cosmosdb_account.mongo.id
}