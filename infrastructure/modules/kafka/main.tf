resource "azurerm_eventhub_namespace" "eventhub_ns" {
    name = var.eventhub_namespace_name
    location = var.location
    resource_group_name = var.resource_group_name
    sku = var.eventhub_sku
    capacity = 1
}

resource "azurerm_eventhub" "eventhub" {
    name = var.eventhub_name
    namespace_name = azurerm_eventhub_namespace.eventhub_ns.name
    resource_group_name = var.resource_group_name
    partition_count = var.eventhub_partition_count
    message_retention = var.eventhub_message_retention
}