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
    default = "eastus"
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