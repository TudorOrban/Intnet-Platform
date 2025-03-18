# Provider
variable "subscription_id" {
    type = string
}

variable "tenant_id" {
    type = string
}

# General
variable "resource_group_name" {
    type = string
    default = "intnet-rg"
}

variable "location" {
    type = string
    default = "centralus"
}

variable "microservices" {
    type = map(object({
        sku_name          = optional(string, "B_Standard_B2ms")
        storage_mb        = optional(number, 32768)
        version           = optional(string, "14")
        database_name     = optional(string)
        zone              = optional(string, "2")
    }))
    default = {
        "intnet-grid-data" = {
            database_name = "grid_data_db"
        },
        "intnet-ml-admin" = {
            database_name = "ml_admin_db"
        },
    }
}

# Networking
variable "vnet_name" {
    type = string
    default = "intnet-vnet"
}

variable "vnet_address_space" {
    type = list(string)
    default = ["10.0.0.0/16"]
}

variable "subnet_prefixes" {
    type = map(list(string))
    description = "Map of subnet prefixes"
    default = {
        aks_subnet          = ["10.0.1.0/24"]
        ingress_subnet      = ["10.0.2.0/24"]
        private_link_subnet = ["10.0.3.0/24"]
        postgres_subnet = ["10.0.4.0/24"]
        mongo_subnet = ["10.0.5.0/24"]
    }
}

# Postgres
variable "postgres_sku_name" {
    type = string
}

variable "postgres_server_name" {
    type = string
}

variable "postgres_admin_user" {
    type = string
}

variable "postgres_admin_password" {
    type = string
    sensitive = true
}

# Mongo
variable "mongo_account_name" {
    type = string
    description = "The name of the CosmosDB Mongo account."
    default = "intnet-mongo"
}

variable "mongo_database_names" {
    type = list(string)
    description = "A list of MongoDB database names."
    default = ["training_data_db"]
}

variable "mongo_offer_type" {
    type = string
    description = "The offer type for the CosmosDB account."
    default = "Standard"
}

variable "mongo_server_version" {
  type = string
  description = "The MongoDB server version."
  default = "4.0"
}

# ACR
variable "acr_name" {
    type = string
    default = "intnet"
}

# AKS
variable "aks_name" {
    type = string
    default = "intnet-aks"
}

variable "aks_node_count" {
    type = number
    default = 2
}

variable "aks_node_vm_size" {
    type = string
    default = "Standard_D2s_v3"
}

variable "service_cidr" {
    type = string
    description = "The service CIDR for the AKS cluster."
    default = "10.2.0.0/16"
}

variable "network_plugin" {
    type = string
    description = "The network plugin for the AKS cluster."
    default = "azure"
}

variable "dns_service_ip" {
    type = string
    description = "The DNS service IP for the AKS cluster."
    default = "10.2.0.10"
}

# Kafka
variable "eventhub_namespace_name" {
    type = string
    description = "The name of the Event Hubs namespace."
    default = "intnet-eventhub-ns"
}

variable "eventhub_name" {
    type = string
    description = "The name of the Event Hub."
    default = "grid-events"
}

variable "eventhub_sku" {
    type = string
    description = "The SKU of the Event Hubs namespace."
    default = "Standard"
}

variable "eventhub_partition_count" {
  type = number
  description = "The partition count of the event hub"
  default = 4
}

variable "eventhub_message_retention" {
  type = number
  description = "The message retention of the event hub"
  default = 1
}

# Monitoring
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

# Logging
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