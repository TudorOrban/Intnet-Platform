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
        grid_data_db_subnet = ["10.0.4.0/24"]
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