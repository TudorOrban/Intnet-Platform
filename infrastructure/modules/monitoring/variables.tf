variable "resource_group_name" {
    type = string
    description = "The name of the resource group."
}

variable "location" {
    type = string
    description = "The Azure region where resources will be created."
}

variable "monitor_workspace_name" {
    type = string
    description = "The name of the Azure Monitor workspace."
    default = "intnet-monitor-workspace"
}

variable "action_group_name" {
    type = string
    description = "The name of the Azure Monitor action group."
    default = "intnet-monitor-action-group"
}

variable "grafana_name" {
    type = string
    description = "The name of the Azure Managed Grafana instance."
    default = "intnet-grafana"
}