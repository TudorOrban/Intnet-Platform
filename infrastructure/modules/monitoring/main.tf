resource "azurerm_monitor_workspace" "monitor_workspace" {
    name = var.monitor_workspace_name
    resource_group_name = var.resource_group_name
    location = var.location
}

resource "azurerm_monitor_action_group" "action_group" {
    name = var.action_group_name
    resource_group_name = var.resource_group_name
    short_name = substr(var.action_group_name, 0, 12)
}

resource "azurerm_dashboard_grafana" "grafana" {
    name = var.grafana_name
    resource_group_name = var.resource_group_name
    location = var.location
    grafana_major_version = 10
    api_key_enabled = true
    deterministic_outbound_ip_enabled = true
    public_network_access_enabled = false

    identity {
        type = "SystemAssigned"
    }
}