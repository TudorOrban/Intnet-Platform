resource "azurerm_log_analytics_workspace" "log_analytics" {
    name = var.log_analytics_workspace_name
    resource_group_name = var.resource_group_name
    location = var.location
    sku = "PerGB2018"
    retention_in_days = var.log_retention_days
}