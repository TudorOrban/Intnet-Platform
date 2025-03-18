output "eventhub_namespace_connection_string" {
    value = azurerm_eventhub_namespace.eventhub_ns.default_primary_connection_string
    sensitive = true
}

output "eventhub_name" {
    value = azurerm_eventhub.eventhub.name
}

output "eventhub_namespace_name" {
    value = azurerm_eventhub_namespace.eventhub_ns.name
}