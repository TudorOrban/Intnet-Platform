variable "subscription_id" {
    type = string
}

variable "tenant_id" {
    type = string
}

variable "resource_group_name" {
    type = string
    default = "intnet-rg"
}

variable "location" {
    type = string
    default = "eastus"
}

variable "vnet_name" {
    type = string
    default = "intnet-vnet"
}

variable "vnet_address_space" {
    type = list(string)
    default = ["10.0.0.0/16"]
}