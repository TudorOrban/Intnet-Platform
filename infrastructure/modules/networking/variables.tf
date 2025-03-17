
variable "resource_group_name" {
    type = string
    description = "The name of the resource group."
}

variable "location" {
    type = string
    description = "The Azure region where the networking resources will be created."
}

variable "vnet_name" {
    type = string
    description = "The name of the virtual network."
}

variable "vnet_address_space" {
    type = list(string)
    description = "The address space for the virtual network."
    default = ["10.0.0.0/16"]
}

variable "subnet_prefixes" {
    type = map(list(string))
    description = "A map subnet name -> subnet address prefix."
    default = {
        aks_subnet = ["10.0.1.0/24"]
        ingress_subnet = ["10.0.2.0/24"]
        private_link_subnet = ["10.0.3.0/24"]
        grid_data_db_subnet    = ["10.0.4.0/24"]
    }
}