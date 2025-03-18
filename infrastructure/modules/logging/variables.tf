variable "resource_group_name" {
    type = string
    description = "The name of the resource group."
}

variable "location" {
    type = string
    description = "The Azure region where resources will be created."
}

variable "log_analytics_workspace_name" {
    type = string
    description = "The name of the Log Analytics workspace."
    default = "intnet-log-analytics"
}

variable "log_retention_days" {
    type = number
    description = "The retention period for logs in days."
    default = 30
}