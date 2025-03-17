variable "resource_group_name" {
    type = string
    description = "The name of the resource group in which to create the AKS cluster."
}

variable "location" {
    type = string
    description = "The Azure region where the AKS cluster will be created."
}

variable "aks_name" {
    type = string
    description = "The name of the AKS cluster."
}

variable "subnet_id" {
    type = string
    description = "The subnet ID for the AKS node pool."
}

variable "acr_id" {
    type = string
    description = "The ACR ID to attach to the AKS cluster."
}

variable "node_count" {
    type = number
    description = "The number of nodes in the AKS node pool."
    default = 2
}

variable "node_vm_size" {
    type = string
    description = "The VM size for the AKS nodes."
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