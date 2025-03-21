variable "resource_group_name" {
    type = string
    description = "The name of the resource group in which to create the ACR."
}

variable "location" {
    type = string
    description = "The Azure region where the ACR will be created."
}

variable "acr_name" {
    type = string
    description = "The name of the ACR."
}

variable "sku" {
    type = string
    description = "The SKU of the ACR."
    default = "Basic"
}